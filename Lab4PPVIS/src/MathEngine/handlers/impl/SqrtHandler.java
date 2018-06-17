package MathEngine.handlers.impl;

import MathEngine.handlers.MathHandler;
import MathEngine.model.NodeOperationTree;

import java.util.List;

public class SqrtHandler implements MathHandler {
    @Override
    public double processOperation(List<NodeOperationTree> leaves) {
        double value = leaves.get(0).getValue();
        if (value >= 0) return Math.sqrt(value);
        throw new ArithmeticException("Value can't be less than zero");
    }
}
