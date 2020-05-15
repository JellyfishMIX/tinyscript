package parser.ast;

import lexer.Token;
import parser.util.PeekTokenIterator;

/**
 * @author JellyfishMIX
 * @date 2020/5/10 6:08 下午
 */
public class Scalar extends Factor {
    public Scalar(ASTNode parent, Token token) {
        super(parent, token);
    }
}
