package parser.ast;

import lexer.TokenType;
import parser.util.PeekTokenIterator;

/**
 * @author JellyfishMIX
 * @date 2020/5/10 5:43 下午
 */
public abstract class Factor extends ASTNode {
    public Factor(ASTNode parent, PeekTokenIterator it) {
        super(parent);
        var token = it.next();
        var type = token.getType();

        if (type == TokenType.VARIABLE) {
            this.type = ASTNodeType.VARIABLE;
        } else {
            this.type = ASTNodeType.SCALAR;
        }
        this.label = token.getValue();
        this.lexeme = token;
    }
}
