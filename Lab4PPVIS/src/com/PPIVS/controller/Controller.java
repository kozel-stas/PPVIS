package com.PPIVS.controller;

import MathEngine.engine.Parser;
import MathEngine.model.NodeOperationTree;
import MathEngine.model.Operation;
import com.PPIVS.model.CalculatorBase;

import java.text.ParseException;

public class Controller {
    private CalculatorBase calculatorBase;
    private Parser parser;
    private int currDeep = -1;

    public Controller() {
        calculatorBase = new CalculatorBase();
        parser = new Parser();
    }

    public String processExpression(String expression) {
        currDeep = -1;
        NodeOperationTree nodeOperationTree;
        String answer;
        try {
            nodeOperationTree = parser.parse(expression);
            answer = String.valueOf(nodeOperationTree.getValue());
            calculatorBase.setAnswer(answer);
            calculatorBase.setNodeOperationTree(nodeOperationTree);
            currDeep = -1;
        } catch (ParseException ex) {
            answer = ex.getMessage();
            calculatorBase.setAnswer(answer);
            calculatorBase.setNodeOperationTree(null);
        } catch (ArithmeticException ex) {
            answer = ex.getMessage();
            calculatorBase.setAnswer(answer);
            calculatorBase.setNodeOperationTree(null);
        } catch (Exception ex) {
            answer = "NaN";
            calculatorBase.setAnswer(answer);
            calculatorBase.setNodeOperationTree(null);
        } finally {
            return calculatorBase.getAnswer();
        }
    }

    public NodeOperationTree getNodeOperationTree() {
        return calculatorBase.getNodeOperationTree();
    }

    public int getDeepTree() {
        if (currDeep == -1) {
            currDeep = 0;
            scoreTree(getNodeOperationTree(), 0);
        }
        return currDeep;
    }

    public void clearExpression() {
        currDeep = -1;
        calculatorBase.clearAll();
    }

    private void scoreTree(NodeOperationTree node, int deep) {
        deep++;
        if (node.getOperation() != Operation.NONE) {
            for (NodeOperationTree nodeOperationTree : node.getLeaves())
                scoreTree(nodeOperationTree, deep);
        }
        if (deep > currDeep) currDeep = deep;
    }

}
