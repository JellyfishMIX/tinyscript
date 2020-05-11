package parser.ast;

/**
 * @author JellyfishMIX
 * @date 2020/5/10 4:59 下午
 */
public enum ASTNodeType {
    /**
     * 代码块
     */
    BLOCK,
    /**
     * 二项表达式，1 + 1
     */
    BINARY_EXPR,
    /**
     * 单项表达式，++i
     */
    UNARY_EXPR,
    /**
     * 变量
     */
    VARIABLE,
    /**
     * 值类型，1.0, true...
     */
    SCALAR,
    /**
     * if语句
     */
    IF_STMT,
    /**
     * while语句
     */
    WHILE_STMT,
    /**
     * for语句
     */
    FOR_STMT,
    /**
     * 赋值语句
     */
    ASSIGN_STMT,
    /**
     * 声明语句
     */
    DECLARE_STMT,
    /**
     * 函数定义语句
     */
    FUNCTION_DEFINITION_STMT;
}
