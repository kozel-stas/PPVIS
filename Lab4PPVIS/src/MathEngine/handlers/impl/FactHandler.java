package MathEngine.handlers.impl;

import MathEngine.handlers.MathHandler;
import MathEngine.model.NodeOperationTree;

import java.util.List;

public class FactHandler implements MathHandler {
    @Override
    public double processOperation(List<NodeOperationTree> leaves) {
        double value = leaves.get(0).getValue();
        if (value % 1 == 0 && value >= 1) {
            int ans = 1;
            for (int i = 1; i <= value; i++){
                ans = ans * i;
            }
            return ans;
        }
        throw new ArithmeticException("Value can't be fractional or less than zero");
    }
}
