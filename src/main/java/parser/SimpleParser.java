package parser;

import parser.ast.ASTNode;
import parser.ast.ASTNodeType;
import parser.ast.Expr;
import parser.ast.Scalar;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

/**
 * @author JellyfishMIX
 * @date 2020/5/11 8:27 下午
 */
public class SimpleParser {
    /**
     * 右递归
     * Expr -> digit + Expr | digit
     * digit -> 0|1|2|3...|9
     *
     * @param it PeekTokenIterator，含有Stream<Token>的迭代器
     * @return 一个抽象语法树
     * @throws ParseException
     */
    public static ASTNode parse(PeekTokenIterator it) throws ParseException {
        Expr expr = new Expr(null);
        Scalar scalar = new Scalar(expr, it);
        // base condition
        if (!it.hasNext()) {
            return scalar;
        }

        // recursive, Expr
        expr.setLexeme(it.peek());
        it.nextMatch("+");
        expr.setLabel("+");
        expr.setType(ASTNodeType.BINARY_EXPR);
        // 加入左节点
        expr.addChild(scalar);
        var rightNode = parse(it);
        // 加入右节点
        expr.addChild(rightNode);
        return expr;
    }
}