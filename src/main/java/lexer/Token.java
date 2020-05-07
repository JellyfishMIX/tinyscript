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

    public static Token makeString (PeekIterator<Character> it) throws LexicalException {
        String s = "";
        int state = 0;

        // while + switch构成一个状态机
        while (it.hasNext()) {
            char c = it.next();
            switch (state) {
                case 0:
                    if (c == '\"') {
                        state = 1;
                    } else {
                        state = 2;
                    }
                    s += c;
                    break;
                case 1:
                    s += c;
                    if (c == '"') {
                        return new Token(TokenType.STRING, s);
                    }
                    break;
                case 2:
                    s += c;
                    if (c == '\'') {
                        return new Token(TokenType.STRING, s);
                    }
                    break;
            }
        }
        // 正常情况不会执行到这里，如果执行到这里，说明出现异常
        throw new LexicalException("Unexpected exception");
    }
}
