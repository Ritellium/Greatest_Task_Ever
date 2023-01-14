package Parsers;

import java.util.ArrayList;

public class FunctionParser extends ExpressionParser{

    public FunctionParser(String expression) {
        super(expression.replaceAll(" ", ""));
    }
    @Override
    public String parseExpression() {
        String result;
        try {
            result = parseExpressionRecursive(0, toParse.length());
            return result;
        }
        catch (ArithmeticException e){
            result = e.getMessage();
            return result;
        }
    }
    private String parseExpressionRecursive(int beginIndex, int endIndex) {

        ArrayList<String> sequence = new ArrayList<>();
        for (int i = beginIndex; i < endIndex; i++) {
            if (Character.isDigit(toParse.charAt(i)) || (toParse.charAt(i) == '-' && i == 0)) {
                int new_i = parseNumber(i);
                sequence.add(toParse.substring(i, new_i));
                i = new_i - 1;
            } else if (toParse.charAt(i) == '(') {
                int new_i = parseBrackets(i);
                sequence.add(parseExpressionRecursive(i + 1, new_i)); // recursion
                i = new_i;
            } else if (toParse.charAt(i) == ')') {
                throw new ArithmeticException("Redundant closing bracket at pos " + i);
            } else if (toParse.charAt(i) == '+' || toParse.charAt(i) == '-' || toParse.charAt(i) == '*' || toParse.charAt(i) == '/') {
                sequence.add(Character.toString(toParse.charAt(i)));
            } else {
                throw new ArithmeticException("Unsupported symbol at pos " + i);
            }
        }

        double result = calculate(sequence);
        return Double.toString(result);
    }
    private int parseNumber(int beginIndex) {
        int endIndex = beginIndex;
        for (int i = beginIndex; i < toParse.length(); i++) {
            if (Character.isDigit(toParse.charAt(i))) {
                endIndex++;
            } else if (toParse.charAt(i) == '-' && endIndex == beginIndex) {
                endIndex++;
            } else if (toParse.charAt(i) == '.') {
                endIndex++;
            } else {
                break;
            }
        }
        return endIndex;
    }
    private int parseBrackets(int beginIndex) {
        int counter = 0;
        for (int i = beginIndex; i < toParse.length(); i++) {
            if (toParse.charAt(i) == '(') {
                counter++;
            } else if (toParse.charAt(i) == ')') {
                counter--;
            }

            if (counter == 0) {
                return i;
            }
        }
        throw new ArithmeticException("No closing bracket for opening one at pos " + beginIndex);
    }
}
