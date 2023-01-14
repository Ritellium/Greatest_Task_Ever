package Parsers;

import java.util.ArrayList;

public abstract class ExpressionParser {
    public static final String plus = "+";
    public static final String minus = "-";
    public static final String multiplication = "*";
    public static final String division = "/";
    protected final String toParse;
    public ExpressionParser(String expression)
    {
        toParse = expression;
    }
    public abstract String parseExpression();

    protected static double calculate(ArrayList<String> numbersAndOperations) {
        double result;
        try {
            for (int i = 0; i < numbersAndOperations.size(); i++) {
                if (numbersAndOperations.get(i).compareTo(multiplication) == 0 || numbersAndOperations.get(i).compareTo(division) == 0) {
                    double numLeft = Double.parseDouble(numbersAndOperations.get(i - 1));
                    double numRight = Double.parseDouble(numbersAndOperations.get(i + 1));
                    double res;
                    if (numbersAndOperations.get(i).compareTo(multiplication) == 0) {
                        res = numLeft * numRight;
                    } else if (numRight != 0d) {
                        res = numLeft / numRight;
                    } else {
                        throw new ArithmeticException("Division by zero!");
                    }
                    numbersAndOperations.set(i - 1, Double.toString(res));
                }
            }

            result = Double.parseDouble(numbersAndOperations.get(0));
            for (int i = 0; i < numbersAndOperations.size(); i++) {
                if (numbersAndOperations.get(i).compareTo(plus) == 0) {
                    double numRight = Double.parseDouble(numbersAndOperations.get(i + 1));
                    result += numRight;
                } else if (numbersAndOperations.get(i).compareTo(minus) == 0) {
                    double numRight = Double.parseDouble(numbersAndOperations.get(i + 1));
                    result -= numRight;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new ArithmeticException("No left/right operand for some operation");
        } catch (NumberFormatException e) {
            throw new ArithmeticException("Two or more arithmetic operations at once");
        }
        return result;
    }
}
