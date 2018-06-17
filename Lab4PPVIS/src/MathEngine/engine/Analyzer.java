package MathEngine.engine;

import MathEngine.model.Operation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyzer {
    //    private static Pattern patternLog = Pattern.compile("log\\((.+),(.+)\\)");
    private static Pattern patternUnaryOperation = Pattern.compile("(((\\d)|(-\\d)|(\\))|(%)|(!))lg)|(((\\d)|(-\\d)|(\\))|(%)|(!))ln)|" +
            "(((\\d)|(-\\d)|(\\))|(%)|(!))sin)|(((\\d)|(-\\d)|(\\))|(%)|" +
            "(!))cos)|(((\\d)|(-\\d)|(\\))|(%)|(!))tg)|(((\\d)|(-\\d)|(\\))|" +
            "(%)|(!))ctg)|(((\\d)|(-\\d)|(\\))|(%)|(!))sqrt)|(%((\\d)|(-\\d)|(\\()))|(!((\\d)|(-\\d)|(\\()))");
    private static Pattern patternNegativeNumber = Pattern.compile("-\\d+");

    public Analyzer() {
    }

    public boolean analyze(String expression) {
        int numBranch = 0;
        for (Character character : expression.toCharArray()) {
            if (character == '(') numBranch++;
            if (character == ')') numBranch--;
        }
        if (numBranch != 0) return false;
        Matcher matcher = patternUnaryOperation.matcher(expression);
        if (matcher.find()) return false;
        return true;
    }

     String prepareExpression(String expression) {
        expression = expression.toLowerCase();
        StringBuilder stringBuilder = new StringBuilder(expression);
//        Matcher matcher = patternLog.matcher(expression);
//        while (matcher.find()) {
//            int index = 0;
//            String out = "";
//            for (int i = matcher.start() + 3; i < matcher.end() - 1; i++) {
//                if (expression.charAt(i) == ',') {
//                    index = i;
//                    break;
//                }
//            }
//            out += "lg(" + expression.substring(index + 1, matcher.end() - 1) + ")" + "/lg(" + expression.substring(matcher.start() + 4, index) + ")";
//            stringBuilder = new StringBuilder(stringBuilder).replace(matcher.start() + (stringBuilder.length() - expression.length()), matcher.end() + (stringBuilder.length() - expression.length()), out);
//        }
//        expression = stringBuilder.toString();
        for (Operation operation : Operation.values()) {
            expression = expression.replace(operation.toString().toLowerCase(), operation.getName());
        }
        Matcher matcher = patternNegativeNumber.matcher(expression);
        stringBuilder = new StringBuilder(expression);
        while (matcher.find()) {
            if (matcher.start() > 0) {
                if (Operation.getOperationByName(String.valueOf(expression.charAt(matcher.start() - 1))) != null) {
                    stringBuilder = stringBuilder.replace(matcher.start(), matcher.end(), "(0" + expression.substring(matcher.start(), matcher.end()) + ")");
                    continue;
                }
            } else
                stringBuilder = stringBuilder.replace(matcher.start(), matcher.end(), "(0" + expression.substring(matcher.start(), matcher.end()) + ")");
        }
        expression = stringBuilder.toString();
        return expression;
    }


}
