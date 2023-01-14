package Parsers;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ThirdPartyParser extends ExpressionParser {
    private final Expression libExp;
    public ThirdPartyParser(String expression)
    {
        super(expression);
        libExp = new ExpressionBuilder(toParse).build();
    }
    @Override
    public String parseExpression() {
        try {
            double result = libExp.evaluate();
            return Double.toString(result);
        } catch (ArithmeticException | IllegalArgumentException ex) {
            return ex.getMessage();
        }
    }
}
