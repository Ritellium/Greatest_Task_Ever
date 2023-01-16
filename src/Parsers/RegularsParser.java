package Parsers;

/* Задача парсинга выражений со скобками произвольной вложенности требует рекурсии.
Подобный класс задач не решается в рамках regex по определению.
Поэтому regex будет использован с дополнительными инструментами */

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularsParser extends ExpressionParser{
    public RegularsParser(String expression) {
        super(expression.replaceAll(" ", ""));
    }
    @Override
    public String parseExpression() {
        try {
            return parseExpressionRecursive(super.toParse);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
    private String parseExpressionRecursive(String Expression) throws Exception {
        Pattern view = Pattern.compile("-*([\\d.()+\\-*/])+");
        Matcher viewCheck = view.matcher(Expression);
        if (!viewCheck.matches()) {
            throw new Exception("Unsupported symbol detected (not a number, operation sign or bracket)");
        }
        Pattern brackets = Pattern.compile("([(][(]{0})([\\d.+\\-*/]+)[)]");
        Matcher bracketsFind;
        do {
            bracketsFind = brackets.matcher(Expression);
            if (!bracketsFind.find()) {
                break;
            }
            String number = parseSimple(Expression.substring(bracketsFind.start() + 1, bracketsFind.end() - 1));
            if (number.charAt(0) == '-') {
                number = "~" + number.substring(1);
            }
            Expression = Expression.substring(0, bracketsFind.start()) + number + Expression.substring(bracketsFind.end());
        } while (true);

        return parseSimple(Expression);
    }

    private String parseSimple(String simpleExpression) throws Exception {
        Pattern sequencePart = Pattern.compile("(~\\d+[.]*\\d*)|(\\d+[.]*\\d*)|([+\\-*/])");
        Matcher partFinder = sequencePart.matcher(simpleExpression);

        ArrayList<String> sequence = new ArrayList<>();
        while (partFinder.find()) {
            String object = simpleExpression.substring(partFinder.start(), partFinder.end());
            if (object.charAt(0) == '~') {
                object = "-" + object.substring(1);
            }
            sequence.add(object);
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
