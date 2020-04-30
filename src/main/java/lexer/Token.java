package lexer;

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
}
