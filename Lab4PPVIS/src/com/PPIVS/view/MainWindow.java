package com.PPIVS.view;

import MathEngine.model.NodeOperationTree;
import MathEngine.model.Operation;
import com.PPIVS.controller.Controller;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainWindow {
    private static List<String> listButton;
    private Map<String, Button> mapButton;
    private Controller controller;
    private Display display;
    private Shell shell;
    private Color color;
    private Tree tree;
    private Text textAns;
    private Text textForm;
    private boolean isAnswer = false;
    private int currDeep;

    static {
        listButton = new ArrayList<>();
        listButton.add("C");
        listButton.add("Delete");
        listButton.add("/");
        listButton.add("*");
        listButton.add("lg");
        listButton.add("sin");

        listButton.add("7");
        listButton.add("8");
        listButton.add("9");
        listButton.add("-");
        listButton.add("ln");
        listButton.add("cos");

        listButton.add("4");
        listButton.add("5");
        listButton.add("6");
        listButton.add("+");
        listButton.add("^");
        listButton.add("tg");

        listButton.add("1");
        listButton.add("2");
        listButton.add("3");
        listButton.add("=");
        listButton.add("sqrt");
        listButton.add("ctg");

        listButton.add("%");
        listButton.add("0");
        listButton.add(".");

        listButton.add("!");
        listButton.add("Eng");
    }

    public MainWindow() {
        display = new Display();
        mapButton = new HashMap<>();
        controller = new Controller();
        shell = new Shell(display, SWT.TITLE | SWT.CLOSE | SWT.DOUBLE_BUFFERED | SWT.RESIZE);
        color = new Color(null, 230, 230, 230);
        shell.setText("Calculator");
        shell.setSize(1000, 800);
        shell.setBackground(color);
        shell.setFocus();
        initGridLayout();
        centerWindow();
        initNavigateInterface();

        shell.open();
        while (!shell.isDisposed()) {
            if (display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    private void centerWindow() {
        Rectangle rectangle = shell.getDisplay().getBounds();
        Point p = shell.getSize();
        int nLeft = (rectangle.width - p.x) / 2;
        int nTop = (rectangle.height - p.y) / 2;
        shell.setBounds(nLeft, nTop, p.x, p.y);
    }

    private void initGridLayout() {
        GridLayout gridLayout = new GridLayout(8, true);
        gridLayout.numColumns = 10;
        gridLayout.verticalSpacing = 7;
        gridLayout.horizontalSpacing = 5;
        shell.setLayout(gridLayout);
    }

    private void initNavigateInterface() {
        textForm = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.DOUBLE_BUFFERED | SWT.NO_REDRAW_RESIZE | SWT.READ_ONLY);
        Font newFont = new Font(display, "Arial", 50, SWT.NULL);
        GridData gridDataText = getGridDataButton();
        gridDataText.horizontalSpan = 10;
        textForm.setLayoutData(gridDataText);
        textForm.setFont(newFont);

        textAns = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.DOUBLE_BUFFERED | SWT.NO_REDRAW_RESIZE | SWT.READ_ONLY);
        Font newFontTexForm = new Font(display, "Arial", 8, SWT.NULL);
        GridData gridDataTextForm = getGridDataButton();
        gridDataTextForm.horizontalSpan = 4;
        textAns.setLayoutData(gridDataTextForm);
        textAns.setFont(newFontTexForm);

        Button buttonM = new Button(shell, SWT.PUSH);
        GridData gridDataM = new GridData(GridData.FILL_BOTH);
        gridDataM.horizontalSpan = 1;
        gridDataM.verticalSpan = 1;
        buttonM.setLayoutData(gridDataM);
        buttonM.setText("<");
        buttonM.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                if (controller.getNodeOperationTree() != null && controller.getDeepTree() > currDeep) {
                    currDeep++;
                    NodeOperationTree buildTree = buildTree(controller.getNodeOperationTree(), 0);
                    drawTree(buildTree, null, true);
                    textForm.setText(buildTree.getExpression());
                }
            }
        });

        Button buttonL = new Button(shell, SWT.PUSH);
        GridData gridDataL = new GridData(GridData.FILL_BOTH);
        gridDataL.horizontalSpan = 1;
        gridDataL.verticalSpan = 1;
        buttonL.setLayoutData(gridDataL);
        buttonL.setText(">");
        buttonL.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                if (currDeep > 1 && controller.getNodeOperationTree() != null) {
                    currDeep--;
                    NodeOperationTree buildTree = buildTree(controller.getNodeOperationTree(), 0);
                    drawTree(buildTree, null, true);
                    textForm.setText(buildTree.getExpression());
                }
            }
        });

        Button buttonBracket = new Button(shell, SWT.PUSH);
        GridData gridDataBracket = new GridData(GridData.FILL_BOTH);
        gridDataBracket.horizontalSpan = 2;
        gridDataBracket.verticalSpan = 1;
        buttonBracket.setLayoutData(gridDataBracket);
        buttonBracket.setText("(");
        buttonBracket.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                if (!isAnswer)
                    textForm.setText(textForm.getText() + "(");
                else textForm.setText("(");
                isAnswer = false;
            }
        });

        Button buttonBracket2 = new Button(shell, SWT.PUSH);
        GridData gridDataBracket2 = new GridData(GridData.FILL_BOTH);
        gridDataBracket2.horizontalSpan = 2;
        gridDataBracket2.verticalSpan = 1;
        buttonBracket2.setLayoutData(gridDataBracket2);
        buttonBracket2.setText(")");
        buttonBracket2.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                if (!isAnswer)
                    textForm.setText(textForm.getText() + ")");
                else textForm.setText(")");
                isAnswer = false;
            }
        });

        tree = new Tree(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        GridData gridDataTree = new GridData(GridData.FILL_BOTH);
        gridDataTree.horizontalSpan = 4;
        gridDataTree.verticalSpan = 6;
        tree.setLayoutData(gridDataTree);

        for (String name : listButton) {
            Button button = new Button(shell, SWT.PUSH);
            button.setText(name);
            GridData gridData = getGridDataButton();
            if (name.equals("=")) {
                gridData.verticalSpan = 2;
                button.setLayoutData(gridData);
                button.setBackground(new Color(null, 104, 104, 255));
            }
            if (!name.equals("=") && !name.equals("Delete") && !name.equals("C") && !name.equals("Eng")) {
                button.addSelectionListener(new SelectionAdapter() {
                    @Override
                    public void widgetSelected(SelectionEvent selectionEvent) {
                        if (!isAnswer)
                            textForm.setText(textForm.getText() + button.getText());
                        else textForm.setText(button.getText());
                        isAnswer = false;
                    }
                });
            }
            button.setLayoutData(gridData);
            mapButton.put(name, button);
        }

        mapButton.get("=").addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                isAnswer = true;
                textAns.setText(controller.processExpression(textForm.getText()));
                if (controller.getNodeOperationTree() != null) {
                    currDeep = controller.getDeepTree();
                    drawTree(controller.getNodeOperationTree(), null, true);
                } else {
                    currDeep = -1;
                    tree.removeAll();
                }
            }
        });

        mapButton.get("Delete").addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                String stringText = textForm.getText();
                if (stringText.length() > 0)
                    textForm.setText(stringText.substring(0, stringText.length() - 1));
            }
        });

        mapButton.get("C").addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                controller.clearExpression();
                textForm.setText("");
                textAns.setText("");
                tree.removeAll();
            }
        });

        mapButton.get("Eng").addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                mapButton.get("tg").setEnabled(!mapButton.get("tg").isEnabled());
                mapButton.get("ctg").setEnabled(!mapButton.get("ctg").isEnabled());
                mapButton.get("^").setEnabled(!mapButton.get("^").isEnabled());
                mapButton.get("sqrt").setEnabled(!mapButton.get("sqrt").isEnabled());
                mapButton.get("sin").setEnabled(!mapButton.get("sin").isEnabled());
                mapButton.get("cos").setEnabled(!mapButton.get("cos").isEnabled());
                mapButton.get("!").setEnabled(!mapButton.get("!").isEnabled());
                mapButton.get("ln").setEnabled(!mapButton.get("ln").isEnabled());
                mapButton.get("lg").setEnabled(!mapButton.get("lg").isEnabled());
            }
        });
    }

    private GridData getGridDataButton() {
        GridData gridDataButton = new GridData(GridData.FILL);
        gridDataButton.horizontalSpan = 1;
        gridDataButton.verticalSpan = 1;
        gridDataButton.grabExcessVerticalSpace = true;
        gridDataButton.verticalAlignment = GridData.FILL;
        gridDataButton.horizontalAlignment = GridData.FILL;
        return gridDataButton;
    }

    private void drawTree(NodeOperationTree node, TreeItem treeItem, boolean isTree) {
        TreeItem treeItemNode;
        if (isTree) {
            tree.removeAll();
            treeItemNode = new TreeItem(tree, SWT.NULL);
        } else treeItemNode = new TreeItem(treeItem, SWT.NULL);
        if (node.getOperation() == Operation.NONE) {
            treeItemNode.setText(String.valueOf(node.getValue()));
        } else {
            treeItemNode.setText(node.getOperation().getDefineName());
            for (int i = node.getLeaves().size() - 1; i >= 0; i--) {
                drawTree(node.getLeave(i), treeItemNode, false);
            }
        }
        treeItemNode.setExpanded(true);
    }

    private NodeOperationTree buildTree(NodeOperationTree tree, int deep) {
        deep++;
        NodeOperationTree currNode;
        if (deep == currDeep) {
            currNode = new NodeOperationTree(Operation.NONE, tree.getValue());
            return currNode;
        } else {
            if (tree.getOperation() == Operation.NONE) {
                return new NodeOperationTree(Operation.NONE, tree.getValue());
            } else {
                currNode = new NodeOperationTree(tree.getOperation());
                for (NodeOperationTree nodeOperationTree : tree.getLeaves()) {
                    currNode.addLeave(buildTree(nodeOperationTree, deep));
                }
                return currNode;
            }

        }
    }
}
