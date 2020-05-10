package paser;

/**
 * @author JellyfishMIX
 * @date 2020/5/10 5:43 下午
 */
public abstract class Factor extends ASTNode {
    public Factor(ASTNode parent, ASTNodeType type, String label) {
        super(parent, type, label);
    }
}
