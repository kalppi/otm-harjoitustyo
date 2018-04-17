package com.jarnoluu.tetris.domain.blocks;

public class CharacterBlock implements IBlock {
    private char type;
    private int[][] data;
    private double x;
    private double y;
    
    public CharacterBlock(char type, int[][] data) {
        this.type = type;
        this.data = data;
        this.x = 0;
        this.y = 0;
    }
    
    public CharacterBlock(char type, int[][] data, double x, double y) {
        this.type = type;
        this.data = data;
        this.x = x;
        this.y = x;
    }
    
    static public IBlock create(char type) throws IllegalArgumentException {
        type = Character.toLowerCase(type);
        
        switch (type) {
            case 'g':
                return new CharacterBlock(type, new int[][] {
                    { 1, 1, 1, 1, 1 },
                    { 1, 0, 0, 0, 0 },
                    { 1, 0, 1, 1, 1 },
                    { 1, 0, 0, 0, 1 },
                    { 1, 1, 1, 1, 1 }
                });
            case 'a':
                return new CharacterBlock(type, new int[][] {
                    { 1, 1, 1, 1, 1 },
                    { 1, 0, 0, 0, 1 },
                    { 1, 1, 1, 1, 1 },
                    { 1, 0, 0, 0, 1 },
                    { 1, 0, 0, 0, 1 }
                });
            case 'm':
                return new CharacterBlock(type, new int[][] {
                    { 1, 0, 0, 0, 1 },
                    { 1, 1, 0, 1, 1 },
                    { 1, 0, 1, 0, 1 },
                    { 1, 0, 0, 0, 1 },
                    { 1, 0, 0, 0, 1 }
                });
            case 'e':
                return new CharacterBlock(type, new int[][] {
                    { 1, 1, 1, 1, 1 },
                    { 1, 0, 0, 0, 0 },
                    { 1, 1, 1, 1, 1 },
                    { 1, 0, 0, 0, 0 },
                    { 1, 1, 1, 1, 1 }
                });
            case 'o':
                return new CharacterBlock(type, new int[][] {
                    { 1, 1, 1, 1, 1 },
                    { 1, 0, 0, 0, 1 },
                    { 1, 0, 0, 0, 1 },
                    { 1, 0, 0, 0, 1 },
                    { 1, 1, 1, 1, 1 }
                });
            case 'v':
                return new CharacterBlock(type, new int[][] {
                    { 1, 0, 0, 0, 1 },
                    { 1, 0, 0, 0, 1 },
                    { 1, 0, 0, 0, 1 },
                    { 0, 1, 0, 1, 0 },
                    { 0, 0, 1, 0, 0 }
                });
            case 'r':
                return new CharacterBlock(type, new int[][] {
                    { 1, 1, 1, 1, 1 },
                    { 1, 0, 0, 0, 1 },
                    { 1, 1, 1, 1, 1 },
                    { 1, 0, 0, 1, 0 },
                    { 1, 0, 0, 0, 1 }
                });
            default:
                return null;
        }
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public int getWidth() {
        return 5;
    }

    @Override
    public int getHeight() {
        return 5;
    }

    @Override
    public IBlock clone() {
        return new CharacterBlock(this.type, this.data, this.x, this.y);
    }

    @Override
    public IBlock rotateLeft() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IBlock rotateRight() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
