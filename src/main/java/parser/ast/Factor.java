package parser.ast;

import lexer.Token;
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

    // public Factor(ASTNode parent, PeekTokenIterator it) {
    //     Token token = it.peek();
    //     TokenType type = token.getType();
    //
    //     if (type == TokenType.VARIABLE) {
    //         it.next();
    //         return new Variable(parent, it);
    //     } else if (token.isScalar()) {
    //         it.next();
    //         return new Scalar(parent, it);
    //     }
    //
    //     super(parent);
    //     this.label = token.getValue();
    //     this.lexeme = token;
    // }
    //
    // public static ASTNode parse(ASTNode parent, PeekTokenIterator it) {
    //     Token token = it.peek();
    //     TokenType type = token.getType();
    //     return null;
    // }
}
