package lexer;

import common.AlphabetHelper;
import common.PeekIterator;

import java.util.ArrayList;

/**
 * @author JellyfishMIX
 * @date 2020/4/30 11:25 上午
 */
public class Lexer {
    // 标准的词法分析器应该返回给语法分析器一个Stream流，但是这里为了保持较低的复杂度，用ArrayList代替
    public ArrayList<Token> analyse(PeekIterator<Character> it) throws LexicalException {
        var tokens = new ArrayList<Token>();

        while(it.hasNext()) {
            char c = it.next();

            // 本编译器中，流的末尾会加一个0表示流结束
            if (c == 0) {
                break;
            }
            char lookahead = it.peek();

            // ' ' 和 '\n'对编译器来说是无用的字符
            if (c == ' ' || c == '\n') {
                continue;
            }

            if (c == '{' || c == '}' || c == '(' || c == ')') {
                tokens.add(new Token(TokenType.BRACKET, c + ""));
                continue;
            }

            if (c == '"' || c == '\'') {
                it.putBack();
                tokens.add(Token.makeString(it));
                continue;
            }

            // 可能出现包含+ - .的数字
            // +-: 3 + 5; +5; 3 * -5
            if ((c == '+' || c == '-' || c == '.') && AlphabetHelper.isNumber(lookahead)) {
                var lasToken = tokens.size() == 0 ? null : tokens.get(tokens.size() - 1);

                if (lasToken == null || lasToken.isNumber() || lasToken.isOperator()) {
                    it.putBack();
                    tokens.add(Token.makeNumber(it));
                    continue;
                }
            }

            if (AlphabetHelper.isOperator(c)) {
                it.putBack();
                tokens.add(Token.makeOperator(it));
            }

            throw new LexicalException(c);
        }
        return tokens;
    }
}