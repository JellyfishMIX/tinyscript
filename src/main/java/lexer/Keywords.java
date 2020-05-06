package lexer;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 关键词表
 *
 * @author JellyfishMIX
 * @date 2020/5/7 12:40 上午
 */
public class Keywords {
    static String[] keywords = {
            "var",
            "if",
            "else",
            "for",
            "while",
            "break",
            "func",
            "return"
    };

    static HashSet<String> set = new HashSet<>(Arrays.asList(keywords));

    public static boolean isKeyword(String word) {
        return set.contains(word);
    }
}
