package domain;

import com.jarnoluu.tetris.domain.Game;
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
        this.game = new Game(10, 10, 20);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void canSetName() {
        game.setName("Pera");
        assertEquals("Pera", game.getName());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void cantSetTooShortName() {
        game.setName("ab");
    }
    
    @Test
    public void blocksAreCreatedOnStart() {
        game.setName("test");
        game.start();
        
        assertNotEquals(null, game.getCurrentBlock());
        assertNotEquals(null, game.getNextBlock());
    }
    
    @Test(expected = IllegalStateException.class)
    public void gameMustBeStartedBeforeUpdateIsCalled() {
        game.update(0);
    }
    
    @Test
    public void gameStateIsUpdated() {
        game.setName("test");
        game.start();
        
        double y1 = game.getCurrentBlock().getY();
        
        game.update(0.01);
        
        double y2 = game.getCurrentBlock().getY();
        
        assertNotEquals(y1, y2);
    }
    
    @Test
    public void collisionWorksInHightSpeeds() {
        game.setName("test");
        game.start();
        
        game.update(100);
        
        assertEquals(0.0, game.getCurrentBlock().getY(), 0.00001);
    }
    
    @Test
    public void ghostBlockWorks() {
        game.setName("test");
        game.start();
        
        assertEquals(18.0, game.findGhost().getY(), 0.00001);
    }
}
