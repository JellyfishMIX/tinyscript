package parser.util;

import parser.ast.ASTNode;

/**
 * HOF: high order function
 *
 * @author JellyfishMIX
 * @date 2020/5/13 5:27 下午
 */
@FunctionalInterface
public interface ExprHOF {
    ASTNode hoc() throws ParseException;
}
