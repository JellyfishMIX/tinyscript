package parser;

import lexer.Lexer;
import lexer.LexicalException;
import lexer.Token;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import parser.ast.ASTNode;
import parser.ast.Expr;
import parser.util.ParseException;
import parser.util.ParserUtils;
import parser.util.PeekTokenIterator;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author JellyfishMIX
 * @date 2020/5/14 10:48 上午
 */
class ParseExprTest {
    @Test
    @Disabled
    void simple() throws LexicalException, ParseException {
        ASTNode expr = createExpr("1+1+1");
        assertEquals("1 1 1 + +", ParserUtils.toPostfixExpression(expr));
    }

    @Test
    @Disabled
    void simple1() throws LexicalException, ParseException {
        ASTNode expr = createExpr("\"1\" == \"\"");
        assertEquals("\"1\" \"\" ==", ParserUtils.toPostfixExpression(expr));
    }

    @Test
    // @Disabled
    void complex() throws LexicalException, ParseException {
        ASTNode expr0 = createExpr("1*2");
        ASTNode expr1 = createExpr("1+2*3");
        ASTNode expr2 = createExpr("1*2+3");
        ASTNode expr3 = createExpr("10 * (7+4)");

        assertEquals("1 2 *", ParserUtils.toPostfixExpression(expr0));
        assertEquals("1 2 3 * +", ParserUtils.toPostfixExpression(expr1));
        assertEquals("1 2 * 3 +", ParserUtils.toPostfixExpression(expr2));
        assertEquals("10 7 4 + *", ParserUtils.toPostfixExpression(expr3));
    }

    private ASTNode createExpr(String src) throws LexicalException, ParseException {
        Lexer lexer = new Lexer();
        ArrayList<Token> tokens = lexer.analyse(src.chars().mapToObj(x -> (char)x));
        PeekTokenIterator tokenIt = new PeekTokenIterator(tokens.stream());
        return Expr.parse(tokenIt);
    }
}
