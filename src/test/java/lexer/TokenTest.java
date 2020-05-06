package lexer;

import common.PeekIterator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author JellyfishMIX
 * @date 2020/5/7 12:54 上午
 */
public class TokenTest {
    void assertToken(Token token, String value, TokenType type) {
        assertEquals(value, token.getValue());
        assertEquals(type, token.getType());
    }

    @Test
    @Disabled
    public void test_varOrKeyword() {
        var it1 = new PeekIterator<Character>("if abc".chars().mapToObj(x -> (char)x));
        var it2 = new PeekIterator<Character>("true abc".chars().mapToObj(x -> (char)x));
        var token1 = Token.makeVarOrKeyword(it1);
        var token2 = Token.makeVarOrKeyword(it2);

        this.assertToken(token1, "if", TokenType.KEYWORD);
        this.assertToken(token2, "true", TokenType.BOOLEAN);
        // 滤掉空格符
        it1.next();
        var token3 = Token.makeVarOrKeyword(it1);

        assertToken(token3, "abc", TokenType.VARIABLE);
    }
}
