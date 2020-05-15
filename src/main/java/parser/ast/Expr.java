package parser.ast;

import lexer.Token;
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

    public Expr(ASTNode parent, ASTNodeType type, Token lexeme) {
        super(parent);
        this.type = type;
        this.lexeme = lexeme;
        this.label = lexeme.getValue();
    }

    private static ASTNode E(ASTNode parent, PeekTokenIterator it, int k) throws ParseException {
        if (k < priorityTable.size() - 1) {
            return combine(parent, it, () -> E(parent, it, k + 1), () -> E_(parent, it, k));
        } else {
            return race(
                    it,
                    () -> combine(parent, it, () -> U(parent, it), () -> E_(parent, it, k)),
                    () -> combine(parent, it, () -> F(parent, it), () -> E_(parent, it, k))
            );
        }
    }

    private static ASTNode E_(ASTNode parent, PeekTokenIterator it, int k) throws ParseException {
        Token token = it.peek();
        String value = token.getValue();

        if (priorityTable.get(k).indexOf(value) != -1) {
            Expr expr = new Expr(parent, ASTNodeType.BINARY_EXPR, it.nextMatch(value));
            expr.addChild(combine(parent, it,
                    () -> E(parent, it, k + 1),
                    () -> E_(parent, it, k)
                    ));
            return expr;
        }
        return null;
    }

    private static ASTNode U(ASTNode parent, PeekTokenIterator it) throws ParseException {
        Token token = it.peek();
        String value = token.getValue();
        ASTNode expr = null;

        if (value.equals("(")) {
            it.nextMatch("(");
            expr = E(parent, it, 0);
            it.nextMatch(")");
            return expr;
        } else if (value.equals("++") || value.equals("--") || value.equals("!")) {
            Token t = it.peek();
            it.nextMatch(value);
            Expr unaryExpr = new Expr(parent, ASTNodeType.UNARY_EXPR, t);
            unaryExpr.addChild(E(unaryExpr, it, 0));
            return unaryExpr;
        }
        return null;
    }

    private static ASTNode F(ASTNode parent, PeekTokenIterator it) {
        Token token = it.peek();
        if (token.isVariable()) {
            return new Variable(parent, token);
        } else {
            return new Scalar(parent, token);
        }
    }

    private static ASTNode combine(ASTNode parent, PeekTokenIterator it, ExprHOF aFunc, ExprHOF bFunc) throws ParseException {
        ASTNode a = aFunc.hoc();
        if (a == null) {
            return it.hasNext() ? bFunc.hoc() : null;
        }
        ASTNode b = it.hasNext() ? bFunc.hoc() : null;
        if (b == null) {
            return a;
        }

        Expr expr = new Expr(parent, ASTNodeType.BINARY_EXPR, b.lexeme);
        expr.addChild(a);
        expr.addChild(b.getChild(0));
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

    public static ASTNode parse(PeekTokenIterator it) throws ParseException {
        return E(null, it, 0);
    }

    public static ASTNode parse(ASTNode parent, PeekTokenIterator it) throws ParseException {
        return E(parent, it, 0);
    }
}
