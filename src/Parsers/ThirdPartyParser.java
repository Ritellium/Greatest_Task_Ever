package Parsers;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ThirdPartyParser extends ExpressionParser {
    public ThirdPartyParser(String expression)
    {
        super(expression);
    }
    @Override
    public String parseExpression() {
        try {
            Expression libExp = new ExpressionBuilder(toParse).build();
            double result = libExp.evaluate();
            return Double.toString(result);
        } catch (RuntimeException ex) {
            return ex.getMessage();
        }
    }
}
