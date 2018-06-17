package com.PPIVS.view;

import com.PPIVS.controller.Controller;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.widgets.GraphicComponent;
import org.eclipse.widgets.GraphicPoint;


public class MainWindow {
    private static Display display = new Display();
    private Shell shell = new Shell(display, SWT.TITLE | SWT.CLOSE);
    private Table table;
    private Controller controller;
    private GraphicComponent graphicComponent;

    public MainWindow() {
        shell.setBackground(new Color(null, 222, 204, 204));
        shell.setText("Graphic");
        shell.setSize(1520, 1000);
        shell.setModified(false);
        controller = new Controller(this);
        initLayout();
        centerWindow();
        initNavigateMenu();
        initTable();
        initGraphicComponent();
        shell.addListener(SWT.Close, new Listener() {
            public void handleEvent(Event event) {
                controller.close();
            }
        });
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        try {
            display.dispose();
        } catch (SWTException ex){
            System.out.println("Асинхронные задачи не выполненны");
            System.exit(0);
        }
    }

    private void initGraphicComponent(){
        graphicComponent = new GraphicComponent(shell);
        GridData gridData = new GridData(GridData.FILL_BOTH);
        gridData.horizontalSpan = 4;
        graphicComponent.setLayoutData(gridData);

    }

    private void initLayout() {
        GridLayout gridLayout = new GridLayout(6, true);
        gridLayout.numColumns = 6;
        gridLayout.verticalSpacing = 10;
        gridLayout.horizontalSpacing = 10;
        shell.setLayout(gridLayout);
    }

    private void centerWindow() {
        Rectangle rectangle = shell.getDisplay().getBounds();
        Point p = shell.getSize();
        int nLeft = (rectangle.width - p.x) / 2;
        int nTop = (rectangle.height - p.y) / 2;
        shell.setBounds(nLeft, nTop, p.x, p.y);
    }

    private void initNavigateMenu() {
        GridData gridDataLabelCurr = new GridData(GridData.FILL_HORIZONTAL);

        Label labelNumberTest = new Label(shell, SWT.NULL);
        labelNumberTest.setText("Количество тестов");
        labelNumberTest.setLayoutData(gridDataLabelCurr);

        GridData gridDataTextTest = new GridData(GridData.FILL_HORIZONTAL);
        Text textNumberTest = new Text(shell, SWT.SINGLE | SWT.BORDER);
        textNumberTest.setLayoutData(gridDataTextTest);

        GridData gridDataLabelIter = new GridData(GridData.FILL_HORIZONTAL);
        Label labelNumberIteration = new Label(shell, SWT.NULL);
        labelNumberIteration.setText("Количество элементов");
        labelNumberIteration.setLayoutData(gridDataLabelIter);

        GridData gridDataTextIter = new GridData(GridData.FILL_HORIZONTAL);
        Text textNumberIteration = new Text(shell, SWT.SINGLE | SWT.BORDER);
        textNumberIteration.setLayoutData(gridDataTextIter);

        Button buttonBuild = new Button(shell, SWT.PUSH);
        buttonBuild.setText("Построить");
        GridData gridDataButton = new GridData(GridData.FILL_HORIZONTAL);
        gridDataButton.horizontalSpan = 2;
        buttonBuild.setLayoutData(gridDataButton);

        buttonBuild.addSelectionListener(new SelectionAdapter() {
            MessageBox messageBox = new MessageBox(shell);

            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                try {
                    int numberTest = Integer.parseInt(textNumberTest.getText());
                    int numberIteration = Integer.parseInt(textNumberIteration.getText());
                    if (numberTest > 0 && numberIteration > 0) {
                        controller.startSortThread(numberTest, numberIteration);
                    } else {
                        messageBox.setMessage("Недопустимый диапазон числа!!!");
                        messageBox.open();
                    }
                } catch (NumberFormatException ex) {
                    messageBox.setMessage("Неправильный формат числа!!!");
                    messageBox.open();
                }

            }
        });
    }

    private void initTable() {
        Table table = new Table(shell, SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.NO_SCROLL);
        this.table = table;

        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        TableColumn fioColumn = new TableColumn(table, SWT.LEFT);
        fioColumn.setText("Количество элементов");
        fioColumn.setResizable(false);
        fioColumn.setWidth(235);

        TableColumn groupColumn = new TableColumn(table, SWT.CENTER);
        groupColumn.setText("Время");
        groupColumn.setResizable(false);
        groupColumn.setWidth(235);


        GridData gridDataTable = new GridData(GridData.FILL_BOTH);
        gridDataTable.horizontalSpan = 2;
        table.setLayoutData(gridDataTable);
    }

    public void updateData(int index) {
        com.PPIVS.model.Point point = controller.getData(index);
        if (point == null) return;
        TableItem tableItem = new TableItem(table, SWT.NULL);
        String[] context = new String[2];
        context[0] = String.valueOf(point.getNumberElement());
        context[1] = String.valueOf(point.getTime());
        tableItem.setText(context);
        graphicComponent.addData(new GraphicPoint(point.getNumberElement(),point.getTime()));
    }

    public void removeAllData() {
        table.removeAll();
        graphicComponent.removeAll();
    }

}
