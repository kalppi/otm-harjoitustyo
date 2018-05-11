package com.jarnoluu.tetris.domain;

import com.jarnoluu.tetris.dao.GoogleDataStorage;
import com.jarnoluu.tetris.dao.IDataStorage;
import com.jarnoluu.tetris.domain.blocks.TetrisBlock;
import com.jarnoluu.tetris.domain.blocks.IBlock;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Pelin kulusta ja logiikasta vastuussa oleva luokka.
 */
public class Game {
    private final Random random = new Random();
    private final int blockSize;
    private final int areaWidth;
    private final int areaHeight;
    private final Character[][] staticBlocks;
    private final Highscores scores;
    private final IDataStorage storage = new GoogleDataStorage();
    private double fallSpeed = 2.0;
    private String name = null;
    private double gameTime = 0;
    private TetrisBlock currentBlock;
    private TetrisBlock nextBlock;
    private boolean gameEnded = false;
    private boolean gameStarted = false;
    private int score = 0;
    
    public Game(int blockSize, int areaWidth, int areaHeight) {
        this.areaWidth = areaWidth;
        this.areaHeight = areaHeight;
        this.blockSize = blockSize;
        this.staticBlocks = new Character[areaWidth][areaHeight];
        this.scores = new Highscores(5);
        
        this.scores.load(this.storage.load());
    }
    
    private void setName(String name) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("Name can't be null");
        } else if (name.length() < 3) {
            throw new IllegalArgumentException("Name needs to be at least 3 characters");
        }
    
        this.name = name;
    }
    
    public double getSpeed() {
        return this.fallSpeed;
    }
    
    public int getScore() {
        return this.score;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Character[][] getStaticBlocks() {
        return this.staticBlocks;
    }
    
    public IBlock getCurrentBlock() {
        return this.currentBlock;
    }
    
    public IBlock getNextBlock() {
        return this.nextBlock;
    }
    
    public int getBlockSize() {
        return this.blockSize;
    }
    
    public int getAreaWidth() {
        return this.areaWidth;
    }
    
    public int getAreaHeight() {
        return this.areaHeight;
    }
    
    public Highscores getHighscores() {
        return this.scores;
    }
    
    public boolean isRunning() {
        return this.gameStarted && !this.gameEnded;
    }
    
    public boolean gameEnded() {
        return this.gameEnded;
    }
    
    public double getGameTime() {
        return this.gameTime;
    }
    
    /**
     * Liikuttaa pelattavaa palikkaa vasemmalle.
     */
    public void moveLeft() {
        this.moveCurrentBlock(-1, 0);
    }
    
    /**
     * Liikuttaa pelattavaa palikkaa oikealle.
     */
    public void moveRight() {        
        this.moveCurrentBlock(1, 0);
    }
    
    /**
     * Liikuttaa pelattavaa palikkaa alas.
     */
    public void moveDown() {
        this.moveCurrentBlock(0, 1);
    }
    
    /**
     * Pyörittää pelattavaa palikkaa oikealle.
     */
    public void rotate() {
        this.currentBlock.rotateRight();
        
        if (this.collides(this.currentBlock, 0, 0)) {
            this.currentBlock.rotateLeft();
        }
    }
    
    /**
     * Etsii nykyisen liikuteltavan palikan mukaisen "haamu" palikan.
     * Haamupakikan voi piirtää ruudlle, jolloin se näyttää mihin kohtaan
     * palikka asettuu.
     * 
     * @return Palikka
     */
    public IBlock findGhost() {
        IBlock ghost = this.currentBlock.clone();
        ghost.setY(0);
        
        for (int y = 0; y < this.areaHeight; y++) {
            if (this.collides(ghost, 0, y)) {
                ghost.setY(y - 1);
                break;
            }
        }
        
        return ghost;
    }
    
    protected void freezeBlock(IBlock block) {
        int[][] data = block.getData();
        
        for (int x = 0; x < block.getWidth(); x++) {
            for (int y = 0; y < block.getHeight(); y++) {
                if (data[y][x] == 1) {
                    int xx = (int) block.getX() + x;
                    int yy = (int) block.getY() + y;
                    
                    if (xx < 0 || yy < 0 || xx >= this.areaWidth || yy >= this.areaHeight) {
                        continue;
                    }
                    
                    this.staticBlocks[xx][yy] = block.getType();
                }
            }
        }
    }
    
    private boolean collides(IBlock block, double changeX, double changeY) {
        int[][] data = block.getData();
        
        for (int x = 0; x < block.getWidth(); x++) {
            for (int y = 0; y < block.getHeight(); y++) {
                if (data[y][x] == 1) {
                    int partX = (int) (block.getX() + changeX) + x,
                        partY = (int) (block.getY() + changeY) + y;
                    
                    if (partX < 0
                            || partY < 0
                            || partX >= this.areaWidth
                            || partY >= this.areaHeight) {
                        return true;
                    } else if (this.staticBlocks[partX][partY] != null) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    private boolean gameIsFinished() {
        for (int x = 0; x < this.areaWidth; x++) {
            if (this.staticBlocks[x][0] != null) {
                return true;
            }
        }
        
        return false;
    }
    
    private void spawnNextBlock() {
        if (this.currentBlock != null) {
            this.freezeBlock(this.currentBlock);
        }
        
        if (this.nextBlock == null) {
            this.spawnBlock(TetrisBlock.randomBlock());
        } else {
            this.spawnBlock(this.nextBlock);
        }
        
        this.nextBlock = TetrisBlock.randomBlock();
    }
    
    private void moveCurrentBlock(double changeX, double changeY) {
        if (this.currentBlock == null) {
            return;
        }
        
        boolean collision = false;        
        double lastY = 0;
        double step = 1;
        
        if (changeY > step) {
            // check collision in small steps, so that it also works in high speeds
            for (double y = 0; y <= changeY; y += step) {
                collision = this.collides(this.currentBlock, 0, y);

                if (collision) {
                    lastY = this.currentBlock.getY() + y - step;
                    this.currentBlock.setY(lastY);
                    break;
                }
            }
        } else {
            collision = this.collides(this.currentBlock, 0, changeY);
        }
        
        if (collision) {
            this.spawnNextBlock();
            
            this.fallSpeed += 0.1;
        } else {
            if (!this.collides(this.currentBlock, changeX, 0)) {
                this.currentBlock.setX(this.currentBlock.getX() + changeX);
                this.currentBlock.setY(this.currentBlock.getY() + changeY);   
            }
        }
        
        if (this.gameIsFinished()) {
            this.gameEnded = true;
            this.currentBlock = null;
            
            this.scores.add(this.name, this.score);
            
            this.storage.saveObject(this.scores);
        }
    }
    
    protected List<Integer> findExplosions() {
        List<Integer> expl = new ArrayList();
        
        for (int y = 0; y < this.areaHeight; y++) {
            boolean explode = true;
            
            for (int x = 0; x < this.areaWidth; x++) {
                if (this.staticBlocks[x][y] == null) {
                    explode = false;
                    break;
                }
            }
            
            if (explode) {
                expl.add(y);
            }
        }
        
        return expl;
    }
    
    private void clearExplosions(List<Integer> explosions) {
        for (int expl : explosions) {
            for (int x = 0; x < this.areaWidth; x++) {
                this.staticBlocks[x][expl] = null;
            }
        }
        
        for (int expl : explosions) {
            for (int y = expl; y > 0; y--) {
                for (int x = 0; x < this.areaWidth; x++) {
                    this.staticBlocks[x][y] = this.staticBlocks[x][y - 1];
                }
            }
        }
    }
    
    private int calculateScore(int explCount) {
        return (int) (explCount * 100 * (1 + ((explCount - 1) / 10.0)));
    }
    
    /**
     * Päivittää pelitilanteen muuttuneen ajan mukaisesti.
     * @param dt Kulunut aika edellisen päivityksen jälkeen
     * @throws IllegalStateException 
     */
    public void update(double dt) throws IllegalStateException {
        if (!this.gameStarted) {
            throw new IllegalStateException("Can't call Game::update before Game::start");
        } else if (this.gameEnded) {
            return;
        }
        
        this.gameTime += dt;
        
        double changeY = dt * this.fallSpeed;
        
        this.moveCurrentBlock(0, changeY);
        
        List<Integer> explosions = this.findExplosions();
        
        if (explosions.size() > 0) {
            this.score += this.calculateScore(explosions.size());
            
            this.clearExplosions(explosions);
        }
    }
    
    private void spawnBlock(TetrisBlock block) {
        block.setX(this.random.nextInt(this.areaWidth - 2));
        
        this.currentBlock = block;
    }
    
    /**
     * Aloittaa pelin.
     * @param name pelaajan nimi
     * @throws IllegalStateException 
     */
    public void start(String name) throws IllegalStateException {
        this.setName(name);
        
        this.spawnNextBlock();
        
        this.gameStarted =  true;
    }
}
