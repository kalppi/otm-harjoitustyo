package com.jarnoluu.tetris.domain.blocks;

public interface IBlock {
    public char getType();
    public int[][] getData();
    public double getX();
    public void setX(double x);
    public double getY();
    public void setY(double y);
    public int getWidth();
    public int getHeight();
    public IBlock clone();
    public IBlock rotateLeft();
    public IBlock rotateRight();
}
