package MathEngine.handlers.impl;

import MathEngine.handlers.MathHandler;
import MathEngine.model.NodeOperationTree;

import java.util.List;

public class MultiplyHandler implements MathHandler {
    @Override
    public double processOperation(List<NodeOperationTree> leaves) {
        double answer = 1;
        for(NodeOperationTree node:leaves){
            answer*=node.getValue();
        }
        return answer;
    }
}
