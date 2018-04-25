package domain;

import com.jarnoluu.tetris.domain.Highscores;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class HighscoresTest {
    private Highscores scores;
    
    public HighscoresTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.scores = new Highscores(3);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void scoresAreOrdered() {
        this.scores.add("Pera", 300);
        this.scores.add("Masa", 100);
        this.scores.add("Irmeli", 200);
        
        int[] values =  this.scores.getScores().stream().mapToInt(s -> s.getScore()).toArray();
        
        assertArrayEquals(new int[] {300, 200, 100}, values);
    }
    
    @Test
    public void scoresAreLimited() {
        this.scores.add("Pera", 300);
        this.scores.add("Masa", 100);
        this.scores.add("Irmeli", 200);
        this.scores.add("Matti", 400);
        
        assertEquals(3, this.scores.getScores().size());
    }
}
