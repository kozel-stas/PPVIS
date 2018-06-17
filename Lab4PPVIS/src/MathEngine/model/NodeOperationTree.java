package MathEngine.model;

import java.util.ArrayList;
import java.util.List;

public class NodeOperationTree implements Cloneable {
    private Operation operation;
    private List<NodeOperationTree> leaves;
    private double value;

    public NodeOperationTree(Operation operation, double num) {
        this.operation = operation;
        this.value = num;
    }

    public NodeOperationTree(Operation operation) {
        this.operation = operation;
        leaves = new ArrayList<>(operation.getArity());
        value = -1;
    }

    public Operation getOperation() {
        return operation;
    }

    public boolean addLeave(NodeOperationTree nodeOperationTree) {
        if (leaves.size() < operation.getArity()) {
            leaves.add(nodeOperationTree);
            return true;
        }
        return false;
    }

    public NodeOperationTree getLeave(int index) {
        return leaves.get(index);
    }

    public List<NodeOperationTree> getLeaves() {
        return leaves;
    }

    public double getValue() {
        if (getOperation() == Operation.NONE) return value;
        else {
            return operation.getHandler().processOperation(getLeaves());
        }
    }

    public String getExpression() {
        if (operation == Operation.NONE) {
            return String.valueOf(value);
        } else {
            String answer = "(";
            if (operation.getArity() == 1) {
                answer += operation.getDefineName().substring(0, operation.getDefineName().length() - 1);
                answer += leaves.get(0).getExpression();
                answer += Operation.CLOSE_BRACKET.getDefineName();
            } else {
                int j = 0;
                for (int i = leaves.size() - 1; i >= 0; i--) {
                    j++;
                    if (j % 2 == 0) answer += operation.getDefineName();
                    answer += leaves.get(i).getExpression();
                }
            }
            answer += ")";
            return answer;
        }
    }
}
