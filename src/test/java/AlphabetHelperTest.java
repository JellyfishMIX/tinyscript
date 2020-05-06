import common.AlphabetHelper;
import common.PeekIterator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author JellyfishMIX
 * @date 2020/5/6 10:56 下午
 */
public class AlphabetHelperTest {
    @Test
    // @Disabled
    public void test() {
        assertEquals(true, AlphabetHelper.isLetter('a'));
        assertEquals(true, AlphabetHelper.isLetter('b'));
        assertEquals( true, AlphabetHelper.isLiteral('a'));
        assertEquals( true, AlphabetHelper.isLiteral('_'));
        assertEquals( true, AlphabetHelper.isLiteral('0'));
        assertEquals(false, AlphabetHelper.isLiteral('*'));
        assertEquals(false, AlphabetHelper.isLiteral('|'));
        assertEquals(true, AlphabetHelper.isNumber('0'));
        assertEquals(false, AlphabetHelper.isNumber('i'));
        assertEquals(true, AlphabetHelper.isOperator('*'));
        assertEquals(false, AlphabetHelper.isOperator('a'));
    }
}
