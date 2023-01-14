package Parsers;

/* Задача парсинга выражений со скобками произвольной вложенности требует рекурсии.
Подобный класс задач не решается в рамках regex по определению.
Поэтому regex будет использован исключительно для выражений БЕЗ скобок */

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularsParser extends ExpressionParser{

    public RegularsParser(String expression) {
        super(expression.replaceAll(" ", ""));
    }
    @Override
    public String parseExpression() {
        Pattern view = Pattern.compile("-*((\\d+)|([+\\-*/]))+");
        Matcher viewCheck = view.matcher(toParse);
        if (!viewCheck.matches()) {
            return "Unsupported symbol detected (not a digit or operation sign)";
        }

        Pattern sequencePart = Pattern.compile("(^-\\d+)|(\\d+)|([+\\-*/])");
        Matcher partFinder = sequencePart.matcher(toParse);

        ArrayList<String> sequence = new ArrayList<>();
        while (partFinder.find()) {
            sequence.add(toParse.substring(partFinder.start(), partFinder.end()));
        }

        double result;
        try {
            result = calculate(sequence);
        } catch (ArithmeticException e) {
            return e.getMessage();
        }
        return Double.toString(result);
    }
}
