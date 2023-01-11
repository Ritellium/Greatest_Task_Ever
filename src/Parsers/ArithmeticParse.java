package Parsers;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;

public class ArithmeticParse {

    public static String parseLibraryExp4j(String toParse) {
        try {
            Expression e = new ExpressionBuilder(toParse).build();
            Double result = e.evaluate();
            return result.toString();
        } catch (ArithmeticException | IllegalArgumentException ex) {
            return ex.getMessage();
        }
    }

    public static String parseSelfmadeRegulars(String toParse)
    {
        return toParse;
    }

    public static String parseSelfmadeAutomaton(String toParse)
    {
        return toParse;
    }

    public static ArrayList<String> parseStringArray(ArrayList<String> strings, int mode) {
        ArrayList<String> parsed = new ArrayList<>();

        for (String it : strings) {
            if (mode == 1) {
                parsed.add(parseSelfmadeRegulars(it));
            } else if (mode == 2) {
                parsed.add(parseSelfmadeAutomaton(it));
            } else {
                parsed.add(parseLibraryExp4j(it));
            }
        }

        return parsed;
    }
}
