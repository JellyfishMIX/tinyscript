package paser;

/**
 * @author JellyfishMIX
 * @date 2020/5/10 5:40 下午
 */
public class ForStmt extends Stmt {
    public ForStmt(ASTNode parent) {
        super(parent, ASTNodeType.FOR_STMT, "for");
    }
}
