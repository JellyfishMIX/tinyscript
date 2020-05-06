package lexer;

import common.AlphabetHelper;
import common.PeekIterator;

/**
 * @author JellyfishMIX
 * @date 2020/4/30 11:10 上午
 */
public class Token {
    TokenType _type;
    String _value;

    public Token(TokenType _type, String _value) {
        this._type = _type;
        this._value = _value;
    }

    public TokenType getType() {
        return _type;
    }

    public String getValue() {
        return _value;
    }

    @Override
    public String toString() {
        return String.format("type %s, value %s", _type, _value);
    }

    public boolean isVariable() {
        return _type == TokenType.VARIABLE;
    }

    public boolean isScalar() {
        return _type == TokenType.INTEGER || _type == TokenType.STRING || _type == TokenType.FLOAT || _type ==  TokenType.BOOLEAN;
    }

    /**
     * 提取变量或关键字
     *
     * @param it
     * @return
     */
    public static Token makeVarOrKeyword(PeekIterator<Character> it) {
        String s = "";

        while (it.hasNext()) {
            var lookahead = it.peek();
            if (AlphabetHelper.isLiteral(lookahead)) {
                s += lookahead;
            } else {
                break;
            }
            it.next();
            // 循环不变式
        }

        // 判断关键词OR变量
        if (Keywords.isKeyword(s)) {
            return new Token(TokenType.KEYWORD, s);
        }

        if (s.equals("true") || s.equals("false")) {
            return new Token(TokenType.BOOLEAN, s);
        }

        return new Token(TokenType.VARIABLE, s);
    }
}
