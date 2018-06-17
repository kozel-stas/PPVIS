package MathEngine.handlers;

import MathEngine.model.NodeOperationTree;

import java.util.List;

public interface MathHandler {
    double processOperation(List<NodeOperationTree> leaves);
}
