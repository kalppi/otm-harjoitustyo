package domain;

import com.jarnoluu.tetris.domain.blocks.CharacterBlock;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CharacterBlockTest {
    
    public CharacterBlockTest() {
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
        
        blocks.put('g', "11111\n10000\n10111\n10001\n11111");
        blocks.put('a', "11111\n10001\n11111\n10001\n10001");
        blocks.put('m', "10001\n11011\n10101\n10001\n10001");
        
        for(HashMap.Entry<Character, String> b : blocks.entrySet()) {
            assertEquals(b.getValue(), CharacterBlock.create(b.getKey()).toString());
        }
    }
}