package com.jarnoluu.tetris.domain;

import com.jarnoluu.tetris.domain.blocks.IBlock;
import com.jarnoluu.tetris.domain.blocks.TetrisBlock;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {
    Game game;
    
    public GameTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.game = new Game(GameConfig.defaults());
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void canSetName() {
        game.start("Pera");
        assertEquals("Pera", game.getName());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void cantSetTooShortName() {
        game.start("ab");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void cantSetNullName() {
        game.start(null);
    }
    
    @Test
    public void blocksAreCreatedOnStart() {
        game.start("test");
        
        assertNotEquals(null, game.getCurrentBlock());
        assertNotEquals(null, game.getNextBlock());
    }
    
    @Test(expected = IllegalStateException.class)
    public void gameMustBeStartedBeforeUpdateIsCalled() {
        game.update(0);
    }
    
    @Test
    public void gameStateIsUpdated() {
        game.start("test");
        
        double y1 = game.getCurrentBlock().getY();
        
        game.update(0.01);
        
        double y2 = game.getCurrentBlock().getY();
        
        assertNotEquals(y1, y2);
    }
    
    @Test
    public void collisionWorksInHightSpeeds() {
        game.start("test");
        
        game.update(100);
        
        assertEquals(0.0, game.getCurrentBlock().getY(), 0.00001);
    }
    
    @Test
    public void ghostBlockWorks() {
        game.start("test");
        
        assertEquals(18.0, game.findGhost().getY(), 0.00001);
    }
    
    @Test
    public void blocksExplode() {
        game.start("test");
        
        for(int x = 0; x < game.getAreaWidth(); x += 2) {
            IBlock block = TetrisBlock.create('o');
            block.setX(x);
            block.setY(game.getAreaHeight() - 2);
            
            game.freezeBlock(block);
        }
        
        List<Integer> explosions = game.findExplosions();
        
        assertEquals(2, explosions.size());
    }
    
    @Test
    public void explosionsGivePoints() {
        game.start("test");
        
        for(int x = 0; x < game.getAreaWidth(); x += 2) {
            IBlock block = TetrisBlock.create('o');
            block.setX(x);
            block.setY(game.getAreaHeight() - 2);
            
            game.freezeBlock(block);
        }
        
        List<Integer> explosions = game.findExplosions();
        
        assertEquals(2, explosions.size());
        
        game.update(0);

        assertTrue(game.getScore() > 0);
    }
    
    @Test
    public void explosionsClearBlocks() {
        game.start("test");
        
        for(int x = 0; x < game.getAreaWidth(); x += 2) {
            IBlock block = TetrisBlock.create('o');
            block.setX(x);
            block.setY(game.getAreaHeight() - 2);
            
            game.freezeBlock(block);
        }
        
        game.update(0);
        
        List<Integer> explosions = game.findExplosions();
        
        assertEquals(0, explosions.size());
    }
    
    @Test
    public void blockCanBeMovedFreely() {
        game.start("test");
        
        for (int i = 0; i < 20; i++) {
            game.moveRight();
            game.rotate();
        }
        
        for (int i = 0; i < 20; i++) {
            game.moveLeft();
            game.rotate();
        }
        
        assertTrue(game.getCurrentBlock().getX() <= 0);
    }
}
