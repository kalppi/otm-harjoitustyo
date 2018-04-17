package com.jarnoluu.tetris.domain.blocks;

import java.util.Random;
import javafx.scene.paint.Color;

public class TetrisBlock implements IBlock {
    private static final char[] BLOCK_TYPES = { 'i', 'j', 'l', 'o', 's', 't', 'z' };
    
    private char type;
    private int[][] data;
    private double x;
    private double y;
    
    private TetrisBlock(char type, int[][] data) {
        this.type = type;
        this.data = data;
    }
    
    private TetrisBlock(char type, int[][] data, double x, double y) {
        this.type = type;
        this.data = data;
        this.x = x;
        this.y = y;
    }
    
    public IBlock clone() {
        return new TetrisBlock(this.type, this.data, this.x, this.y);
    }
    
    @Override
    public char getType() {
        return this.type;
    }
    
    @Override
    public int[][] getData() {
        return this.data;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }
    
    public static Color getColor(char type) {
        switch (type) {
            case 'i':
                return Color.RED;
            case 'j':
                return Color.BLUE;
            case 'l':
                return Color.ORANGE;
            case 'o':
                return Color.YELLOW;
            case 's':
                return Color.MAGENTA;
            case 't':
                return Color.CYAN;
            case 'z':
                return Color.GREEN;
            default:
                return null;
        }
    }
    
    static public TetrisBlock randomBlock() {
        int i = new Random().nextInt(TetrisBlock.BLOCK_TYPES.length);
        
        return (TetrisBlock) TetrisBlock.create(TetrisBlock.BLOCK_TYPES[i]);
    }
    
    static public IBlock create(char type) throws IllegalArgumentException {
        type = Character.toLowerCase(type);
        
        switch (type) {
            case 'i':
                return new TetrisBlock(type, new int[][] {
                    { 0, 0, 0 },
                    { 1, 1, 1 },
                    { 0, 0, 0 }
                });
            case 'j':
                return new TetrisBlock(type, new int[][] {
                    { 1, 1, 1 },
                    { 0, 0, 1 },
                    { 0, 0, 0 }
                });
            case 'l':
                return new TetrisBlock(type, new int[][] {
                    { 1, 1, 1 },
                    { 1, 0, 0 },
                    { 0, 0, 0 }
                });
            case 'o':
                return new TetrisBlock(type, new int[][] {
                    { 1, 1, 0 },
                    { 1, 1, 0 },
                    { 0, 0, 0 }
                });
            case 's':
                return new TetrisBlock(type, new int[][] {
                    { 0, 1, 1 },
                    { 1, 1, 0 },
                    { 0, 0, 0 }
                });
            case 't':
                return new TetrisBlock(type, new int[][] {
                    { 1, 1, 1 },
                    { 0, 1, 0 },
                    { 0, 0, 0 }
                });
            case 'z':
                return new TetrisBlock(type, new int[][] {
                    { 1, 1, 0 },
                    { 0, 1, 1 },
                    { 0, 0, 0 }
                });
            default:
                throw new IllegalArgumentException("Unknown block: " + type);
        }
    }
    
    @Override
    public IBlock rotateLeft() {
        int[][] rotated = new int[3][3];
        
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                rotated[x][y] = this.data[y][3 - x - 1];
            }
        }
        
        this.data = rotated;
        
        return this;
    }
    
    @Override
    public IBlock rotateRight() {
        int[][] rotated = new int[3][3];
        
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                rotated[x][y] = this.data[3 - y - 1][x];
            }
        }
        
        this.data = rotated;
        
        return this;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                s.append(this.data[x][y]);
            }
            
            if (x < 2) {
                s.append("\n");
            }
        }
        
        return s.toString();
    }

    @Override
    public int getWidth() {
        return 3;
    }

    @Override
    public int getHeight() {
        return 3;
    }
}
