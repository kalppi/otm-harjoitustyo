package com.jarnoluu.tetris.ui.graphics;

import com.jarnoluu.tetris.domain.Game;
import com.jarnoluu.tetris.domain.blocks.IBlock;
import com.jarnoluu.tetris.domain.blocks.TetrisBlock;
import java.text.DecimalFormat;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Hoitaa pelitilanteen piirtämisen.
 */
public class FancyGraphics implements IGraphics {
    private final Canvas well;
    private final Canvas stats;
    private final Game game;
    private double hueAdd = 0;
    
    public FancyGraphics(Game game, Canvas well, Canvas stats) {
        this.game = game;
        this.well = well;
        this.stats = stats;
        
        this.draw();
    }
    
    private void drawGhostPart(GraphicsContext g, int bX, int bY, boolean left, boolean right, boolean top, boolean bottom) {
        final int size = this.game.getBlockSize();
        
        g.fillRect(
            bX,
            bY,
            size,
            size
        );
        
        if(left) {
            g.strokeLine(
                bX + 0.5,
                bY + 0.5,
                bX + 0.5,
                bY + size - 0.5
            );
        }
        
        if(right) {
            g.strokeLine(
                bX + size - 0.5,
                bY + 0.5,
                bX + size - 0.5,
                bY + size - 0.5
            );
        }
        
        if(top) {
            g.strokeLine(
                bX + 0.5,
                bY + 0.5,
                bX + size - 0.5,
                bY + 0.5
            );
        }
        
        if(bottom) {
            g.strokeLine(
                bX + 0.5,
                bY + size - 0.5,
                bX + size - 0.5,
                bY + size - 0.5
            );
        }
    }
    
    private void drawPart(GraphicsContext g, int bX, int bY) {
        final int size = this.game.getBlockSize();
        
        g.fillRect(
            bX,
            bY,
            size,
            size
        );

        g.strokeLine(
            bX,
            bY + size,
            bX + size,
            bY + size
        );

        g.strokeLine(
            bX + size,
            bY,
            bX + size,
            bY + size
        );
    }
    
    private void drawBlock(GraphicsContext g, IBlock block) {
        this.drawBlock(g, block, false);
    }
    
    private void drawBlock(GraphicsContext g, IBlock block, boolean ghost) {
        final int size = this.game.getBlockSize();
        
        this.drawBlock(g, block, (int)block.getX() * size, (int)block.getY() * size, ghost);
    }
    
    private void drawBlock(GraphicsContext g, IBlock block, int xx, int yy) {
        this.drawBlock(g, block, xx, yy, false);
    }
    
    private void drawBlock(GraphicsContext g, IBlock block, int xx, int yy, boolean ghost) {
        final int size = this.game.getBlockSize();
        
        if(ghost) {
            g.setFill(Color.rgb(50, 50, 50));
            g.setStroke(Color.WHITE);
            g.setLineWidth(1);
        } else {
            g.setFill(TetrisBlock.getColor(block.getType()));
            g.setStroke(Color.BLACK);
        }
        
        int[][] data = block.getData();

        for(int x = 0; x < block.getWidth(); x++) {
            for(int y = 0; y < block.getHeight(); y++) {
                if(data[y][x] == 1) {
                    int bX = xx + x * size;
                    int bY = yy + y * size;
                    
                    if(ghost) {
                        boolean left, right, top, bottom;
                        left = right = top = bottom = false;
                        
                        if(y == 0) top = true;
                        else if(data[y-1][x] == 0) top = true;
                        
                        if(y == 2) bottom = true;
                        else if(data[y+1][x] == 0) bottom = true;
                        
                        if(x == 0) left = true;
                        else if(data[y][x-1] == 0) left = true;
                        
                        if(x == 2) right = true;
                        else if(data[y][x+1] == 0) right = true;
                        
                        this.drawGhostPart(g, bX, bY, left, right, top, bottom);
                    } else {
                        this.drawPart(g, bX, bY);
                    }
                }
            }
        }
    }
    
    /**
     * Piirtää pelitilanteen.
     * @param dt edellisestä päivityksestä kulunut aika
     */
    @Override
    public void update(double dt) {
        if(!this.game.isRunning()) {
            this.hueAdd += dt * 60;
        }
        
        this.draw();
    }
    
    private void draw() {        
        final int size = this.game.getBlockSize();
        
        GraphicsContext g = this.well.getGraphicsContext2D();
        GraphicsContext g2 = this.stats.getGraphicsContext2D();
        
        g.setFill(Color.rgb(0, 20, 20));
        g.fillRect(0, 0, well.getWidth(), well.getHeight());
        g.setLineWidth(1);
        
        g2.setFill(Color.rgb(0, 0, 0));
        g2.fillRect(0, 0, stats.getWidth(), stats.getHeight());
        g2.setLineWidth(1);
        
        if(this.game.isRunning()) {
            g2.setFont(Font.font("Verdana", 14));
            g2.setFill(Color.YELLOWGREEN);
            g2.setTextAlign(TextAlignment.LEFT);
            g2.setTextBaseline(VPos.TOP);
            g2.fillText("Time: " + String.valueOf(Math.floor(this.game.getGameTime() * 10) / 10), 5, 5);

            IBlock nextBlock = this.game.getNextBlock();

            g2.fillText("Next block:", 5, 25);
            if(nextBlock != null) this.drawBlock(g2, nextBlock, 5, 45);
            
            g2.setFill(Color.YELLOWGREEN);
            g2.fillText("Score:", 5, 80);
            g2.fillText(String.valueOf(this.game.getScore()), 5, 95);
            
            DecimalFormat df = new DecimalFormat("#.00"); 
            
            g2.fillText("Speed:", 5, 115);
            g2.fillText(df.format(game.getSpeed()), 5, 130);
        }        
        
        double ha = this.hueAdd;
        
        final Character[][] blocks = this.game.getStaticBlocks();
        
        for(int x = 0; x < this.game.getAreaWidth(); x++) {
            for(int y = 0; y < this.game.getAreaHeight(); y++) {
                Character type = blocks[x][y];
                
                if(type != null) {
                    Color blockColor = TetrisBlock.getColor(type);
                    
                    if(!this.game.isRunning()) {
                        blockColor = Color.hsb(blockColor.getHue() + ha, blockColor.getSaturation(), blockColor.getBrightness());
                        ha -= 0.5;
                    }
                    
                    g.setFill(blockColor);
                    
                    int bX = x * size;
                    int bY = y * size;

                    this.drawPart(g, bX, bY);
                }
            }
        }
        
        if(this.game.getCurrentBlock() != null) {
            IBlock ghost = this.game.findGhost();
            this.drawBlock(g, ghost, true);
            
            this.drawBlock(g, this.game.getCurrentBlock());
        }
        
        if(this.game.gameEnded()) {
            long centerX = Math.round(this.well.getWidth() / 2);
            long centerY = Math.round(this.well.getHeight() / 2);
            
            g.setFill(Color.rgb(0, 0, 0, 0.5));
            g.fillRect(centerX - 30, centerY - 15, 60, 30);
            
            g.setFill(Color.WHITE);
            g.setFont(Font.font(20));
            g.setTextAlign(TextAlignment.CENTER);
            g.setTextBaseline(VPos.CENTER);
            
            g.fillText(
                "END",
                centerX,
                centerY
            );
        }
    }
}
