package com.jarnoluu.tetris.domain;

import javafx.animation.AnimationTimer;
import com.jarnoluu.tetris.ui.graphics.IGraphics;

public class GameManager extends AnimationTimer {
    private Game game;
    private IGraphics graphics;
    private long lastFrameTime;
    
    public void start(Game game, IGraphics graphics) {
        this.game = game;
        this.graphics = graphics;
        
        game.start();
        
        this.lastFrameTime = System.nanoTime();
        
        super.start();
    }
    
    @Override
    public void handle(long currentTime) {
        double dt = (currentTime - this.lastFrameTime) / 1000000000.0;
        this.lastFrameTime = currentTime;
        
        this.game.update(dt);
        this.graphics.update(dt);
    }
}
