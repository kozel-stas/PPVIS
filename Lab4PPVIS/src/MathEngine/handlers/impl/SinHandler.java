package MathEngine.handlers.impl;

import MathEngine.handlers.MathHandler;
import MathEngine.model.NodeOperationTree;

import java.util.List;

public class SinHandler implements MathHandler {
    @Override
    public double processOperation(List<NodeOperationTree> leaves) {
        return Math.sin(leaves.get(0).getValue());
    }
}
