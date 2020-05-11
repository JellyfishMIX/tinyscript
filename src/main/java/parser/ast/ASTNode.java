package parser.ast;

import lexer.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JellyfishMIX
 * @date 2020/5/10 2:41 下午
 */
public abstract class ASTNode {
    /**
     * 树
     */
    protected ArrayList<ASTNode> children = new ArrayList<>();
    protected ASTNode parent;

    // 关键信息

    /**
     * 词法单元
     */
    protected Token lexeme;
    /**
     * 备注（标签）
     */
    protected String label;
    /**
     * 类型
     */
    protected ASTNodeType type;

    // Constructor

    public ASTNode(ASTNode parent) {
        this.parent = parent;
    }

    public ASTNode(ASTNode parent, ASTNodeType type, String label) {
        this.parent = parent;
        this.type = type;
        this.label = label;
    }

    // Getter & Setter

    public ASTNode getChild(int index) {
        return children.get(index);
    }

    public void addChild(ASTNode node) {
        children.add(node);
    }

    public Token getLexeme() {
        return lexeme;
    }

    public List<ASTNode> getChildren() {
        return children;
    }
}
