package MathEngine.handlers.impl;

import MathEngine.handlers.MathHandler;
import MathEngine.model.NodeOperationTree;

import java.util.List;

public class CtgHandler implements MathHandler {
    @Override
    public double processOperation(List<NodeOperationTree> leaves) {
        return 1 / Math.tan(leaves.get(0).getValue());
    }
}
