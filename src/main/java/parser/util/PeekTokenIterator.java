package parser.util;

import common.PeekIterator;
import lexer.Token;
import lexer.TokenType;

import java.util.stream.Stream;

/**
 * @author JellyfishMIX
 * @date 2020/5/11 7:19 下午
 */
public class PeekTokenIterator extends PeekIterator<Token> {
    public PeekTokenIterator(Stream<Token> stream) {
        super(stream);
    }

    public Token nextMatch(String value) throws ParseException {
        var token = this.next();
        if (!token.getValue().equals(value)) {
            throw new ParseException(token);
        }
        return token;
    }

    public Token nextMatch(TokenType tokenType) throws ParseException {
        var token = this.next();
        if (!token.getType().equals(tokenType)) {
            throw new ParseException(token);
        }
        return token;
    }
}
