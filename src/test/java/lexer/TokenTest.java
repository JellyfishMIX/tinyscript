package lexer;

import common.PeekIterator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author JellyfishMIX
 * @date 2020/5/7 12:54 上午
 */
class TokenTest {
    void assertToken(Token token, String value, TokenType type) {
        assertEquals(value, token.getValue());
        assertEquals(type, token.getType());
    }

    @Test
    @Disabled
    void testMakeVarOrKeyword() {
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

    @Test
    @Disabled
    void testMakeString() throws LexicalException {
        String[] testStringArray = {
                "\"123\"",
                "\'123\'"
        };

        for (String testString : testStringArray) {
            var it = new PeekIterator<Character>(testString.chars().mapToObj(x -> (char)x));
            var token = Token.makeString(it);
            assertToken(token, testString, TokenType.STRING);
        }
    }

    @Test
    @Disabled
    void testMakeOperator() throws LexicalException {
        String[] testStringArray = {
                "+ xxx",
                "++mmm",
                "/=g",
                "==1",
                "&=3982",
                "&777",
                "||xxxx",
                "^=111",
                "%7"
        };
        String[] resultStringArray = {"+", "++", "/=", "==", "&=", "&", "||", "^=", "%"};
        int i = 0;
        for (String testString : testStringArray) {
            var it = new PeekIterator<Character>(testString.chars().mapToObj(x -> (char)x));
            var token = Token.makeOperator(it);
            assertToken(token, resultStringArray[i++], TokenType.OPERATOR);
        }
    }

    @Test
    @Disabled
    void testMakeNumber() throws LexicalException {
        String[] testStringArray = {
                "+0 aa",
                "-0 aa",
                ".3 ccc",
                ".5555 ddd",
                "7789.8888 ooo",
                "-1000.123123*123123",
        };

        for (String testString : testStringArray) {
            var it = new PeekIterator<Character>(testString.chars().mapToObj(x -> (char)x));
            var token = Token.makeNumber(it);
            var splitValue = testString.split("[* ]+");
            assertToken(token, splitValue[0], (testString.indexOf('.') != -1) ? TokenType.FLOAT : TokenType.INTEGER);
        }
    }
}
