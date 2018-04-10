package com.jarnoluu.Tetris;

public class Game {
    private String name;
    
    public void setName(String name) throws IllegalArgumentException {
        if(name.length() < 3) throw new IllegalArgumentException("Name needs to be at least 3 characters");
    
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
}
