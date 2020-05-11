package parser.util;

import lexer.Token;

/**
 * @author JellyfishMIX
 * @date 2020/5/11 7:19 下午
 */
public class ParseException extends Exception {
    private String msg;

    public ParseException(String msg) {
        this.msg = msg;
    }

    public ParseException(Token token) {
        this.msg = String.format("syntax error, unexpected token %s", token.getValue());
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
