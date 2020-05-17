package parser.util;

import org.apache.commons.lang3.NotImplementedException;
import parser.ast.ASTNode;

/**
 * @author JellyfishMIX
 * @date 2020/5/14 10:36 上午
 */
public class ParserUtils {
    /**
     * Postfix
     * @param node 抽象语法树上的节点
     * @return
     */
    public static String toPostfixExpression(ASTNode node) {
        // left op right -> left right op
        String leftStr = "";
        String rightStr = "";

        switch (node.getType()) {
            case BINARY_EXPR:
                leftStr = toPostfixExpression(node.getChild(0));
                rightStr = toPostfixExpression(node.getChild(1));
                return leftStr + " " + rightStr + " " + node.getLexeme().getValue();
            case VARIABLE:
            case SCALAR:
                return node.getLexeme().getValue();
        }
        throw new NotImplementedException("not impl.");
    }
}
