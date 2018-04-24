package com.jarnoluu.tetris.domain;

import com.jarnoluu.tetris.domain.blocks.TetrisBlock;
import com.jarnoluu.tetris.domain.blocks.IBlock;
import java.util.Random;

public class Game {
    private final Random random = new Random();
    private final int blockSize;
    private final int areaWidth;
    private final int areaHeight;
    private final Character[][] staticBlocks;
    private double fallSpeed = 2.0;
    private String name = null;
    private double gameTime = 0;
    private TetrisBlock currentBlock;
    private TetrisBlock nextBlock;
    private boolean gameEnded = false;
    private boolean gameStarted = false;
    
    public Game(int blockSize, int areaWidth, int areaHeight) {
        this.areaWidth = areaWidth;
        this.areaHeight = areaHeight;
        this.blockSize = blockSize;
        this.staticBlocks = new Character[areaWidth][areaHeight];
    }
    
    private void setName(String name) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("Name can't be null");
        } else if (name.length() < 3) {
            throw new IllegalArgumentException("Name needs to be at least 3 characters");
        }
    
        this.name = name;
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
    
    public boolean isRunning() {
        return this.gameStarted && !this.gameEnded;
    }
    
    public boolean gameEnded() {
        return this.gameEnded;
    }
    
    public double getGameTime() {
        return this.gameTime;
    }
    
    public void moveLeft() {
        this.moveCurrentBlock(-1, 0);
    }
    
    public void moveRight() {        
        this.moveCurrentBlock(1, 0);
    }
    
    public void moveDown() {
        this.moveCurrentBlock(0, 1);
    }
    
    public void rotate() {
        this.currentBlock.rotateRight();
    }
    
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
    
    private void freezeBlock(IBlock block) {
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
        
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
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
        } else {
            if (!this.collides(this.currentBlock, changeX, 0)) {
                this.currentBlock.setX(this.currentBlock.getX() + changeX);
                this.currentBlock.setY(this.currentBlock.getY() + changeY);   
            }
        }
        
        if (this.gameIsFinished()) {
            this.gameEnded = true;
            this.currentBlock = null;
        }
    }
    
    public void update(double dt) throws IllegalStateException {
        if (!this.gameStarted) {
            throw new IllegalStateException("Can't call Game::update before Game::start");
        } else if (this.gameEnded) {
            return;
        }
        
        this.gameTime += dt;
        
        double changeY = dt * this.fallSpeed;
        
        this.moveCurrentBlock(0, changeY);
    }
    
    private void spawnBlock(TetrisBlock block) {
        block.setX(this.random.nextInt(this.areaWidth - 2));
        
        this.currentBlock = block;
    }
    
    public void start(String name) throws IllegalStateException {
        this.setName(name);
        
        this.spawnNextBlock();
        
        this.gameStarted =  true;
    }
}
