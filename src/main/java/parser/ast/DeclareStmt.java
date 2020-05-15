package parser.ast;

import lexer.Token;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

/**
 * @author JellyfishMIX
 * @date 2020/5/10 5:29 下午
 */
public class DeclareStmt extends Stmt {
    public DeclareStmt(ASTNode parent) {
        super(parent, ASTNodeType.DECLARE_STMT, "declare");
    }

    public static ASTNode parse(ASTNode parent, PeekTokenIterator it) throws ParseException {
        DeclareStmt stmt = new DeclareStmt(parent);
        it.nextMatch("var");
        Token token = it.peek();

        ASTNode factor = Factor.parse(parent, it);
        if (factor == null) {
            throw new ParseException(token);
        }
        // 左节点
        stmt.addChild(factor);
        Token lexeme = it.nextMatch("=");
        ASTNode expr = Expr.parse(parent, it);
        // 右节点
        stmt.addChild(expr);
        // "="作为关联节点
        stmt.setLexeme(lexeme);
        return stmt;
    }
}
