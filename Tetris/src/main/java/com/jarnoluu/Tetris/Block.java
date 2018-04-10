package com.jarnoluu.Tetris;

public class Block {
    private char type;
    private int[][] data;
    
    public Block(char type, int[][] data) {
        this.type = type;
        this.data = data;
    }
    
    static public Block create(char type) throws IllegalArgumentException {
        type = Character.toLowerCase(type);
        
        switch(type) {
            case 'i':
                return new Block(type, new int[][] {
                    { 0, 0, 0 },
                    { 1, 1, 1 },
                    { 0, 0, 0 }
                });
            case 'j':
                return new Block(type, new int[][] {
                    { 1, 1, 1 },
                    { 0, 0, 1 },
                    { 0, 0, 0 }
                });
            case 'l':
                return new Block(type, new int[][] {
                    { 1, 1, 1 },
                    { 1, 0, 0 },
                    { 0, 0, 0 }
                });
            case 'o':
                return new Block(type, new int[][] {
                    { 1, 1, 0 },
                    { 1, 1, 0 },
                    { 0, 0, 0 }
                });
            case 's':
                return new Block(type, new int[][] {
                    { 0, 1, 1 },
                    { 1, 1, 0 },
                    { 0, 0, 0 }
                });
            case 't':
                return new Block(type, new int[][] {
                    { 1, 1, 1 },
                    { 0, 1, 0 },
                    { 0, 0, 0 }
                });
            case 'z':
                return new Block(type, new int[][] {
                    { 1, 1, 0 },
                    { 0, 1, 1 },
                    { 0, 0, 0 }
                });
            default:
                throw new IllegalArgumentException("Unknown block: " + type);
        }
    }
    
    public Block rotateLeft() {
        int[][] rotated = new int[3][3];
        
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                rotated[x][y] = this.data[y][3 - x - 1];
            }
        }
        
        this.data = rotated;
        
        return this;
    }
    
    public Block rotateRight() {
        int[][] rotated = new int[3][3];
        
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                rotated[x][y] = this.data[3 - y - 1][x];
            }
        }
        
        this.data = rotated;
        
        return this;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        
        for(int x = 0; x < 3; x++) {
            for(int y = 0; y < 3; y++) {
                s.append(this.data[x][y]);
            }
            if(x < 2) s.append("\n");
        }
        
        return s.toString();
    }
}
