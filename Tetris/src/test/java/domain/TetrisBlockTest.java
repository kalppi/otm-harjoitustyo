package domain;

import com.jarnoluu.tetris.domain.blocks.TetrisBlock;
import java.util.HashMap;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TetrisBlockTest {
    
    public TetrisBlockTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void blocksAreCreatedCorrectly() {
        HashMap<Character, String> blocks = new HashMap();
        
        blocks.put('i', "000\n111\n000");
        blocks.put('j', "111\n001\n000");
        blocks.put('l', "111\n100\n000");
        blocks.put('o', "110\n110\n000");
        blocks.put('s', "011\n110\n000");
        blocks.put('t', "111\n010\n000");
        blocks.put('z', "110\n011\n000");
        
        for(HashMap.Entry<Character, String> b : blocks.entrySet()) {
            assertEquals(b.getValue(), TetrisBlock.create(b.getKey()).toString());
        }
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void unknownBlockCantBeCreated() {
        TetrisBlock.create('x');
    }
    
    @Test
    public void rotateLeftWorks() {
        assertEquals("000\n110\n110", TetrisBlock.create('o').rotateLeft().toString());
    }
    
    @Test
    public void rotateRightWorks() {
        assertEquals("011\n011\n000", TetrisBlock.create('o').rotateRight().toString());
    }
    
    @Test
    public void getColorWorks() {
        assertEquals(Color.RED, TetrisBlock.getColor('i'));
    }
}
