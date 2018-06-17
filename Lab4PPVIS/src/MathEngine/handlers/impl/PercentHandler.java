package MathEngine.handlers.impl;

import MathEngine.handlers.MathHandler;
import MathEngine.model.NodeOperationTree;

import java.util.List;

public class PercentHandler implements MathHandler {
    @Override
    public double processOperation(List<NodeOperationTree> leaves) {
        return leaves.get(0).getValue()/100;
    }
}
