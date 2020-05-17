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

    /**
     * left: E(k) -> E(k) op(k) E(k+1) | E(k+1)
     * right:
     *      E(k) -> E(k+1) E_(k)
     *      E_(k) -> op(k) E(k+1) E_(k) | ∂
     *          var e = new Expr(); e.left = E(k+1); e.op = op(k); e.right = E(k+1) E_(k)
     * 最高优先级的处理：
     * E(t) -> U E_(k) | F E_(k)
     * E_(t) -> op(t) E(t) E_(t) | ∂
     * @param k 操作符优先级
     * @return
     */
    public static ASTNode E(ASTNode parent, int k, PeekTokenIterator it) throws ParseException {
        if (k < priorityTable.size() - 1) {
            return combine(parent, it, () -> E(parent, k + 1, it), () -> E_(parent, k, it));
        } else {
            // 最高优先级的处理。一次U和E_(k)的combine，一次F和E_(k)的combine
            return race(
                    it,
                    () -> combine(parent, it, () -> U(parent, it), () -> E_(parent, k ,it)),
                    () -> combine(parent, it, () -> F(parent, it), () -> E_(parent, k ,it))
            );
        }
    }

    private static ASTNode E_(ASTNode parent, int k, PeekTokenIterator it) throws ParseException {
        Token token = it.peek();
        String value = token.getValue();

        if (priorityTable.get(k).indexOf(value) == -1) {
            return null;
        }
        ASTNode expr = new Expr(parent, ASTNodeType.BINARY_EXPR, it.nextMatch(value));
        expr.addChild(combine(parent, it,
                () -> E(parent, k + 1, it),
                () -> E_(parent, k, it)
                ));
        return expr;
    }

    private static ASTNode U(ASTNode parent, PeekTokenIterator it) throws ParseException {
        Token token = it.peek();
        String value = token.getValue();
        ASTNode expr = null;

        if (value.equals("(")) {
            it.nextMatch("(");
            expr = E(parent, 0, it);
            it.nextMatch(")");
            return expr;
        } else if (value.equals("++") || value.equals("--") || value.equals("!")) {
            // 吃掉token之前先记录token
            Token t = it.peek();
            it.nextMatch(value);
            Expr unaryExpr = new Expr(parent, ASTNodeType.UNARY_EXPR, t);
            unaryExpr.addChild(E(unaryExpr, 0, it));
            return unaryExpr;
        }
        return null;
    }

    private static ASTNode F(ASTNode parent, PeekTokenIterator it) {
        Token token = it.peek();
        if (token.isVariable()) {
            return new Variable(parent, it);
        } else {
            return new Scalar(parent, it);
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
        return E(null, 0, it);
    }
}
