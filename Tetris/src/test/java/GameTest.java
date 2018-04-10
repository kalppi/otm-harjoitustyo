import com.jarnoluu.Tetris.Game;
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
        this.game = new Game();
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
}
