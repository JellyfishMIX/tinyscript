package parser.ast;

/**
 * @author JellyfishMIX
 * @date 2020/5/10 5:27 下午
 */
public class IfStmt extends Stmt {
    public IfStmt(ASTNode parent) {
        super(parent, ASTNodeType.IF_STMT, "if");
    }
}
