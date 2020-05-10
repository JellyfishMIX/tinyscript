package paser;

/**
 * @author JellyfishMIX
 * @date 2020/5/10 5:26 下午
 */
public class Block extends Stmt {
    public Block(ASTNode parent) {
        super(parent, ASTNodeType.BLOCK, "block");
    }
}
