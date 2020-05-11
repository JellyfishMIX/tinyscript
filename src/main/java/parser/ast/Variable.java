package parser.ast;

import parser.util.PeekTokenIterator;

/**
 * @author JellyfishMIX
 * @date 2020/5/10 6:10 下午
 */
public class Variable extends Factor {
    public Variable(ASTNode parent, PeekTokenIterator it) {
        super(parent, it);
    }
}
