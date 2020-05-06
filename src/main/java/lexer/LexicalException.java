package lexer;

/**
 * @author JellyfishMIX
 * @date 2020/5/5 4:17 下午
 */
public class LexicalException extends Exception {
    private String msg;

    public LexicalException(char c) {
        msg = String.format("Unexpected character %c");
    }

    public LexicalException(String _msg) {
        this.msg = _msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
