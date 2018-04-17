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
    private double fallSpeed = 70.0;
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
    
    public void setName(String name) throws IllegalArgumentException {
        if (name.length() < 3) {
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
                    
                    if (xx < 0 || yy < 0) {
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
                    int partX = (int) (block.getX() + changeX) + x;
                    int partY = (int) (block.getY() + changeY) + y;
                    
                    if (partX < 0) {
                        continue;
                    } else if (partY < 0) {
                        continue;
                    }

                    if (partY >= this.areaHeight) {
                        return true;
                    }
                    
                    if (this.staticBlocks[partX][partY] != null) {
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
    
    public void update(double dt) throws IllegalStateException {
        if (!this.gameStarted) {
            throw new IllegalStateException("Can't call Game::update before Game::start");
        } else if (this.gameEnded) {
            return;
        }
        
        this.gameTime += dt;
        
        double changeY = dt * this.fallSpeed;
        
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
            this.currentBlock.setY(this.currentBlock.getY() + changeY);
        }
        
        if (this.gameIsFinished()) {
            this.gameEnded = true;
            this.currentBlock = null;
        }
    }
    
    private void spawnBlock(TetrisBlock block) {
        block.setX(this.random.nextInt(this.areaWidth - 2));
        
        this.currentBlock = block;
    }
    
    public void start() throws IllegalStateException {
        if (this.name == null) {
            throw new IllegalStateException("Name is not set");
        }
        
        this.spawnNextBlock();
        
        this.gameStarted =  true;
    }
}
