package paser;

/**
 * @author JellyfishMIX
 * @date 2020/5/10 5:29 下午
 */
public class DeclareStmt extends Stmt {
    public DeclareStmt(ASTNode parent) {
        super(parent, ASTNodeType.DECLARE_STMT, "declare");
    }
}
