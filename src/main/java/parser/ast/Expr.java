package parser.ast;

import parser.util.ExprHOF;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;
import parser.util.PriorityTable;

/**
 * @author JellyfishMIX
 * @date 2020/5/10 6:12 下午
 */
public class Expr extends ASTNode {
    private static PriorityTable priorityTable = new PriorityTable();

    public Expr(ASTNode parent) {
        super(parent);
    }

    // private static ASTNode E(ASTNode parent, PeekTokenIterator it, int k) throws ParseException {
    //     if (k < priorityTable.size() - 1) {
    //         return combine(parent, it, () -> E(parent, it, k + 1), () -> E_(parent, it, k));
    //     } else {
    //         return race(
    //                 it,
    //                 () -> combine(parent, it, () -> U(), () -> E_(parent, it, k)),
    //                 () -> combine(parent, it, () -> F(), () -> E_(parent, it, k))
    //         );
    //     }
    // }
    //
    // private static ASTNode E_(ASTNode parent, PeekTokenIterator it, int k) {
    //
    // }

    private static ASTNode combine(ASTNode parent, PeekTokenIterator it, ExprHOF aFunc, ExprHOF bFunc) throws ParseException {
        ASTNode a = aFunc.hoc();
        if (a == null) {
            return it.hasNext() ? bFunc.hoc() : null;
        }
        ASTNode b = it.hasNext() ? bFunc.hoc() : null;
        if (b == null) {
            return a;
        }

        Expr expr = new Expr(parent);
        expr.type = ASTNodeType.BINARY_EXPR;
        expr.lexeme = b.getLexeme();
        expr.label = b.getLabel();
        expr.addChild(a);
        expr.addChild(b);
        return expr;
    }

    private static ASTNode race(PeekTokenIterator it, ExprHOF aFunc, ExprHOF bFunc) throws ParseException {
        if (!it.hasNext()) {
            return null;
        }
        ASTNode a = aFunc.hoc();
        if (a != null) {
            return a;
        }
        return bFunc.hoc();
    }
}
