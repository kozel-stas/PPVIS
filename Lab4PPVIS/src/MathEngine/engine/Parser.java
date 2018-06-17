package MathEngine.engine;

import MathEngine.model.NodeOperationTree;
import MathEngine.model.Operation;

import java.text.ParseException;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Parser {
    private Analyzer analyzer;
    public static final String SEPARATOR_RPM = ";";
    private static String separator = "";
    private static Pattern patternNumber = Pattern.compile("^(\\d+)|(-\\d+)$");


    static {
        for (Operation operation : Operation.values())
            if (operation != Operation.NONE) separator += operation.getName();
    }

    public Parser() {
        analyzer = new Analyzer();
    }

    public NodeOperationTree parse(String expression) throws Exception {
        expression = expression.replaceAll("\\s+", "");
        if (!analyzer.analyze(expression)) throw new ParseException("Can't analyze", 0);
        expression = analyzer.prepareExpression(expression);
        expression = parseToRPM(expression);
        return convertToTree(expression, null);
    }

    public String parseToRPM(String expression) throws Exception {
        Stack<Operation> stackOperation = new Stack<>();
        String output = "";
        StringTokenizer stringTokenizer = new StringTokenizer(expression, separator, true);
        while (stringTokenizer.hasMoreTokens()) {
            String step = stringTokenizer.nextToken();
            if (separator.contains(step)) {
                Operation currOperation = Operation.getOperationByName(step);
                if (currOperation == null) throw new ParseException("Unknown operation", 0);
                Operation prevOperation = null;
                if (!stackOperation.empty())
                    prevOperation = stackOperation.peek();
                if (prevOperation == null) stackOperation.push(currOperation);
                else {
                    if (currOperation.getName().equals(Operation.CLOSE_BRACKET.getName())) {
                        while (!stackOperation.empty() && stackOperation.peek() != Operation.OPEN_BRACKET) {
                            Operation operation = stackOperation.pop();
                            output += operation.getName() + SEPARATOR_RPM;
                        }
                        if (stackOperation.pop() != Operation.OPEN_BRACKET)
                            throw new ParseException("Wrong type of record", 0);
                        continue;
                    } else {
                        while (true) {
                            boolean conditionPopOperation = prevOperation != Operation.OPEN_BRACKET && (prevOperation.getPriority() >= currOperation.getPriority());
                            if (conditionPopOperation) {
                                stackOperation.pop();
                                output += prevOperation.getName() + SEPARATOR_RPM;
                                if (!stackOperation.empty())
                                    prevOperation = stackOperation.peek();
                                else break;
                            } else break;
                        }
                    }
                    stackOperation.push(currOperation);
                }
            } else {
                if (!patternNumber.matcher(step).find()) throw new ParseException("Incorrect number", 0);
                output += step;
                output += SEPARATOR_RPM;

            }
        }
        while (!stackOperation.empty()) {
            Operation operation = stackOperation.pop();
            output += operation.getName() + SEPARATOR_RPM;
        }
        return output;
    }

    public NodeOperationTree convertToTree(String expression, NodeOperationTree nodeOperationTree) {
        String expressionRev = new StringBuilder(expression).reverse().toString();
        NodeOperationTree currNode;
        StringTokenizer stringTokenizer = new StringTokenizer(expressionRev, SEPARATOR_RPM, false);
        String step = stringTokenizer.nextToken();
        Operation operation = Operation.getOperationByName(step);
        if (operation == null) {
            currNode = new NodeOperationTree(Operation.NONE, Double.parseDouble(new StringBuilder(step).reverse().toString()));
            if (nodeOperationTree != null)
                nodeOperationTree.addLeave(currNode);
        } else {
            currNode = new NodeOperationTree(operation);
            if (nodeOperationTree != null) {
                nodeOperationTree.addLeave(currNode);
            }
            String prev = null;
            for (int i = 0; i < operation.getArity(); i++) {
                if (prev == null) {
                    prev = getBlock(expression.substring(0, expression.length() - 2));
                    convertToTree(prev, currNode);
                } else {
                    prev = getBlock(expression.substring(0, expression.lastIndexOf(prev)));
                    convertToTree(prev, currNode);
                }
            }
        }
        return currNode;
    }

    private String getBlock(String expression) {
        String expressionRev = new StringBuilder(expression).reverse().toString();
        StringTokenizer stringTokenizer = new StringTokenizer(expressionRev, SEPARATOR_RPM, false);
        int arity = 1;
        int len = 0;
        while (stringTokenizer.hasMoreTokens()) {
            String step = stringTokenizer.nextToken();
            Operation operation = Operation.getOperationByName(step);
            if (operation == null) {
                arity--;
            } else {
                arity--;
                arity += operation.getArity();
            }
            len = len + 1 + step.length();
            if (arity == 0) {
                break;
            }
        }
        return expression.substring(expression.length() - len, expression.length());
    }
}
