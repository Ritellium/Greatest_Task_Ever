package Parsers;

import java.util.ArrayList;

// Parsers decorator. Chooses parser
public class TheParser {
    public static String parseExpression(String expression, final int mode) {
        ExpressionParser exParser;
        if (mode == 1) {
            exParser = new RegularsParser(expression);
        } else if (mode == 2) {
            exParser = new FunctionParser(expression);
        } else {
            exParser = new ThirdPartyParser(expression);
        }
        return exParser.parseExpression();
    }
    public static ArrayList<String> parseStringArray(ArrayList<String> strings, final int mode) {
        ArrayList<String> parsed = new ArrayList<>();
        for (String it : strings) {
            parsed.add(parseExpression(it, mode));
        }
        return parsed;
    }
}
