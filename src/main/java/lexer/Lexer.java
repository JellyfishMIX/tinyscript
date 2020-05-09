package lexer;

import common.AlphabetHelper;
import common.PeekIterator;

import java.util.ArrayList;
import java.util.stream.Stream;

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

            // 本编译器中，流的末尾会加一个ascii 0表示流结束
            if (c == 0) {
                break;
            }
            char lookahead = it.peek();

            // ' ' 和 '\n'对编译器来说是无用的字符
            if (c == ' ' || c == '\n') {
                continue;
            }

            // 过滤注释
            if (c == '/') {
                if (lookahead == '/') {
                    while (it.hasNext() && it.next() != '\n');
                } else if (lookahead == '*') {
                    boolean valid = false;
                    while (it.hasNext()) {
                        char p = it.next();
                        if (p == '*' && it.peek() == '/') {
                            it.next();
                            valid = true;
                            break;
                        }
                    }
                    if (!valid) {
                        throw new LexicalException("comments not match");
                    }
                }
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

            if (AlphabetHelper.isLetter(c)) {
                it.putBack();
                tokens.add(Token.makeVarOrKeyword(it));
                continue;
            }

            if (AlphabetHelper.isNumber(c)) {
                it.putBack();
                tokens.add(Token.makeNumber(it));
                continue;
            }

            // 可能出现包含+ - .的数字
            // +-: 3 + 5; +5; 3 * -5
            if ((c == '+' || c == '-' || c == '.') && AlphabetHelper.isNumber(lookahead)) {
                var lasToken = tokens.size() == 0 ? null : tokens.get(tokens.size() - 1);

                if (lasToken == null || !lasToken.isNumber() || lasToken.isOperator()) {
                    it.putBack();
                    tokens.add(Token.makeNumber(it));
                    continue;
                }
            }

            if (AlphabetHelper.isOperator(c)) {
                it.putBack();
                tokens.add(Token.makeOperator(it));
                continue;
            }

            throw new LexicalException(c);
        }
        return tokens;
    }

    // 重载，方便传参
    public ArrayList<Token> analyse(Stream source) throws LexicalException {
        var it = new PeekIterator<Character>(source, (char)0);
        return this.analyse(it);
    }
}