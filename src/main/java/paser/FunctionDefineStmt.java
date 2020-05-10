package paser;

/**
 * @author JellyfishMIX
 * @date 2020/5/10 5:41 下午
 */
public class FunctionDefineStmt extends Stmt {
    public FunctionDefineStmt(ASTNode parent) {
        super(parent, ASTNodeType.FUNCTION_DEFINITION_STMT, "func");
    }
}
