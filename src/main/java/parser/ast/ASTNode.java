package parser.ast;

import lexer.Token;
import org.apache.commons.lang3.StringUtils;

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

    public List<ASTNode> getChildren() {
        return children;
    }

    public Token getLexeme() {
        return lexeme;
    }

    public void setLexeme(Token lexeme) {
        this.lexeme = lexeme;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ASTNodeType getType() {
        return type;
    }

    public void setType(ASTNodeType type) {
        this.type = type;
    }

    /**
     * 打印，查看抽象语法树的形状
     *
     * @param indent 起始缩进
     */
    public void print(int indent) {
        System.out.println(StringUtils.leftPad(" ", indent * 2) + label);
        for (ASTNode child : children) {
            child.print(indent + 1);
        }
    }
}
