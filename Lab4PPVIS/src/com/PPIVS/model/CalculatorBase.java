package com.PPIVS.model;

import MathEngine.model.NodeOperationTree;

public class CalculatorBase {
    private NodeOperationTree nodeOperationTree;
    private String answer;

    public NodeOperationTree getNodeOperationTree() {
        return nodeOperationTree;
    }

    public void setNodeOperationTree(NodeOperationTree nodeOperationTree) {
        this.nodeOperationTree = nodeOperationTree;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void clearAll() {
        nodeOperationTree = null;
        answer = null;
    }
}
