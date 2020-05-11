package parser;

import lexer.Lexer;
import lexer.LexicalException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import parser.ast.ASTNode;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author JellyfishMIX
 * @date 2020/5/11 9:33 下午
 */
class SimpleParserTest {

    @Test
    // @Disabled
    void parse() throws LexicalException, ParseException {
        Stream<Character> source = "1+2+3+4".chars().mapToObj(x -> (char)x);
        Lexer lexer = new Lexer();
        PeekTokenIterator it = new PeekTokenIterator(lexer.analyse(source).stream());
        ASTNode expr = SimpleParser.parse(it);

        // assertEquals(2, expr.getChildren().size());
        // ASTNode c1 = expr.getChild(0);
        // assertEquals("1", c1.getLexeme().getValue());
        // assertEquals("+", expr.getLexeme().getValue());
        //
        // ASTNode c2 = expr.getChild(1);
        // ASTNode d1 = c2.getChild(0);
        // assertEquals("2", d1.getLexeme().getValue());
        // assertEquals("+", c2.getLexeme().getValue());
        //
        // ASTNode d2 = expr.getChild(1);
        // // ASTNode e1 = d2.getChild(0);
        // // assertEquals("3", e1.getLexeme().getValue());
        // // assertEquals("+", d2.getLexeme().getValue());
        //
        // ASTNode e2 = d2.getChild(1);
        // ASTNode e1 = d2.getChild(0);
        // System.out.println(e1.getLexeme().getValue());
        //
        // // assertEquals("4", e2.getLexeme().getValue());
        expr.print(0);
    }
}