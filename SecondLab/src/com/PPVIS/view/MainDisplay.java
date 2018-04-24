package com.PPVIS.view;


import com.PPVIS.Conroller.Controller;
import com.PPVIS.Main;
import com.PPVIS.model.Exam;
import com.PPVIS.model.Student;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainDisplay {
    private static Display display = new Display();
    private Shell shell = new Shell(display, SWT.TITLE | SWT.CLOSE);
    private Table table;
    private File file;
    private int numberLineTable = 30;
    private int currentPage = 0;
    private Controller controller;
    
    public MainDisplay() {
        shell.setBackground(new Color(null, 222, 204, 204));
        shell.setText("TableEditor");
        shell.setSize(1500, 925);
        shell.setModified(false);
        centerWindow();
        initMenuBar();
        controller = new Controller();
        initTable();
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
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

    private void initMenuBar() {
        Menu menuBar = new Menu(shell, SWT.BAR);
        MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        fileMenuHeader.setText("File");

        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        fileMenuHeader.setMenu(fileMenu);

        MenuItem fileExitItem, fileSaveItem, fileOpenItem;
        fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
        fileSaveItem.setText("Save");
        fileSaveItem.setAccelerator(SWT.CTRL + 'S');
        fileSaveItem.addSelectionListener(new SelectionAdapter() {
            MessageBox messageBox = new MessageBox(shell);

            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                File file;
                if (MainDisplay.this.file == null) {
                    try {
                         file = new File(openFileDialog("Save"));
                    } catch (NullPointerException ex){
                        return;
                    }
                    try {
                        file.createNewFile();
                        MainDisplay.this.file = file;
                    } catch (IOException e) {
                        messageBox.setMessage("Ошибка при сохранении");
                        messageBox.open();
                        return;
                    }
                }
                if (!controller.save(MainDisplay.this.file)) {
                    messageBox.setMessage("Ошибка при сохранении");
                    messageBox.open();
                } else {
                    messageBox.setMessage("Сохранено");
                    messageBox.open();
                }
            }
        });

        fileOpenItem = new MenuItem(fileMenu, SWT.PUSH);
        fileOpenItem.setText("Open");
        fileOpenItem.setAccelerator(SWT.CTRL + 'O');
        fileOpenItem.addSelectionListener(new SelectionAdapter() {
            MessageBox messageBox = new MessageBox(shell);

            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                File file;
                try {
                     file = new File(openFileDialog("Open"));
                } catch (NullPointerException ex){
                    return;
                }
                if (!controller.open(file)) {
                    messageBox.setMessage("Ошибка при открытии");
                    messageBox.open();
                } else redraw();
            }
        });

        fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
        fileExitItem.setText("Exit");
        fileExitItem.setAccelerator(SWT.CTRL+'E');
        fileExitItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                shell.close();
            }
        });

        Menu functionMenu;
        MenuItem functionMenuHeader, findMenuItem, addMenuItem, delMenuItem;

        functionMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        functionMenuHeader.setText("Function");

        functionMenu = new Menu(shell, SWT.DROP_DOWN);
        functionMenuHeader.setMenu(functionMenu);

        addMenuItem = new MenuItem(functionMenu, SWT.PUSH);
        addMenuItem.setText("Add");
        addMenuItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                AddDisplay addDisplay = new AddDisplay(display, MainDisplay.this, controller);
            }
        });

        findMenuItem = new MenuItem(functionMenu, SWT.CASCADE);
        findMenuItem.setText("Find");

        Menu findMenu = new Menu(shell,SWT.DROP_DOWN);
        findMenuItem.setMenu(findMenu);

        MenuItem findAverage = new MenuItem(findMenu,SWT.PUSH);
        findAverage.setText("Find surname and average");
        findAverage.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new FindSurnameAverage(display,MainDisplay.this, controller);
            }
        });

        MenuItem findGroup = new MenuItem(findMenu,SWT.PUSH);
        findGroup.setText("Find surname and group");
        findGroup.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new FindSurnameGroup(display,MainDisplay.this,controller);
            }
        });

        MenuItem findMark = new MenuItem(findMenu,SWT.PUSH);
        findMark.setText("Find surname and mark");
        findMark.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new FindSurnameMark(display,MainDisplay.this,controller);
            }
        });

        delMenuItem = new MenuItem(functionMenu, SWT.CASCADE);
        delMenuItem.setText("Delete");

        Menu deleteMenu = new Menu(shell,SWT.DROP_DOWN);
        delMenuItem.setMenu(deleteMenu);

        MenuItem deleteAverage = new MenuItem(deleteMenu,SWT.PUSH);
        deleteAverage.setText("Delete surname and average");
        deleteAverage.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new DeleteSurnameAverage(display,MainDisplay.this,controller);
            }
        });

        MenuItem deleteGroup = new MenuItem(deleteMenu,SWT.PUSH);
        deleteGroup.setText("Delete surname and group");
        deleteGroup.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new DeleteSurnameGroup(display,MainDisplay.this,controller);
            }
        });

        MenuItem deleteMark = new MenuItem(deleteMenu,SWT.PUSH);
        deleteMark.setText("Delete surname and mark");
        deleteMark.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new DeleteSurnameMark(display,MainDisplay.this,controller);
            }
        });

        Menu helpMenu;
        MenuItem helpMenuHeader, helpGetHelpItem;

        helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
        helpMenuHeader.setText("Help");

        helpMenu = new Menu(shell, SWT.DROP_DOWN);
        helpMenuHeader.setMenu(helpMenu);

        helpGetHelpItem = new MenuItem(helpMenu, SWT.PUSH);
        helpGetHelpItem.setText("Get Help");

        shell.setMenuBar(menuBar);
///////////////////////////////////////////////////////////////////////////////////////////////menu
        ToolBar toolBar = new ToolBar(shell, SWT.BORDER);

        ToolItem addItem = new ToolItem(toolBar, SWT.PUSH);
        addItem.setText("Add");
        addItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new AddDisplay(display, MainDisplay.this, controller);
            }
        });

        ToolItem separator1 = new ToolItem(toolBar, SWT.SEPARATOR);

        ToolItem findItemAverage = new ToolItem(toolBar, SWT.PUSH);
        findItemAverage.setText("Find surname and average");
        findItemAverage.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new FindSurnameAverage(display,MainDisplay.this,controller);
            }
        });

        ToolItem separator5 = new ToolItem(toolBar, SWT.SEPARATOR);

        ToolItem findItemGroup = new ToolItem(toolBar, SWT.PUSH);
        findItemGroup.setText("Find surname and group");
        findItemGroup.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new FindSurnameGroup(display,MainDisplay.this,controller);
            }
        });

        ToolItem separator6 = new ToolItem(toolBar, SWT.SEPARATOR);


        ToolItem findItemMark = new ToolItem(toolBar, SWT.PUSH);
        findItemMark.setText("Find surname and mark");
        findItemMark.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new FindSurnameMark(display,MainDisplay.this,controller);
            }
        });

        ToolItem separator2 = new ToolItem(toolBar, SWT.SEPARATOR);

        ToolItem deleteItemAverage = new ToolItem(toolBar, SWT.PUSH);
        deleteItemAverage.setText("Delete surname and average");
        deleteItemAverage.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new DeleteSurnameAverage(display,MainDisplay.this,controller);
            }
        });

        ToolItem separator3 = new ToolItem(toolBar, SWT.SEPARATOR);

        ToolItem deleteItemGroup = new ToolItem(toolBar, SWT.PUSH);
        deleteItemGroup.setText("Delete surname and group");
        deleteItemGroup.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new DeleteSurnameGroup(display,MainDisplay.this,controller);
            }
        });

        ToolItem separator4 = new ToolItem(toolBar, SWT.SEPARATOR);

        ToolItem deleteItemMark = new ToolItem(toolBar, SWT.PUSH);
        deleteItemMark.setText("Delete surname and mark");
        deleteItemMark.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                new DeleteSurnameMark(display,MainDisplay.this,controller);
            }
        });

        toolBar.pack();
///////////////////////////////////////////////////////////////////////////////////////toolbar
    }

    private void initTable(){
        Table table = new Table(shell, SWT.FULL_SELECTION);
        this.table = table;
        table.setBounds(20, 50, 1442, 24 * (numberLineTable + 1));
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        TableColumn fioColumn = new TableColumn(table, SWT.LEFT);
        fioColumn.setText("ФИО");
        fioColumn.setResizable(false);
        fioColumn.setWidth(200);

        TableColumn groupColumn = new TableColumn(table, SWT.CENTER);
        groupColumn.setText("Группа");
        groupColumn.setResizable(false);
        groupColumn.setWidth(100);

        for (int i = 0; i < 5; i++) {
            TableColumn tableColumnName = new TableColumn(table, SWT.CENTER);
            tableColumnName.setText("Название");
            tableColumnName.setResizable(false);
            tableColumnName.setWidth(180);

            TableColumn tableColumnMark = new TableColumn(table, SWT.CENTER);
            tableColumnMark.setText("О");
            tableColumnMark.setResizable(false);
            tableColumnMark.setWidth(48);
        }
////////////////////////////////////////////////////////////////////////////////////table
        Button nextPageButton = new Button(shell, SWT.PUSH);
        nextPageButton.setBounds(20, 800, 100, 40);
        nextPageButton.setText("Next page");
        nextPageButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                if (table.getItems().length == numberLineTable) {
                    currentPage++;
                    redraw();
                }
            }
        });

        Button prevPageButton = new Button(shell, SWT.PUSH);
        prevPageButton.setBounds(120, 800, 100, 40);
        prevPageButton.setText("Prev page");
        prevPageButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                if (currentPage > 0) {
                    currentPage--;
                    redraw();
                }
            }
        });

        Text curPage = new Text(shell, SWT.SINGLE | SWT.BORDER);
        curPage.setBounds(520, 810, 100, 20);
        curPage.setText(String.valueOf(numberLineTable));

        Button updateButton = new Button(shell, SWT.PUSH);
        updateButton.setBounds(420, 800, 100, 40);
        updateButton.setText("UPDATE");
        updateButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                try {
                    if (Integer.parseInt(curPage.getText()) > 0) {
                        numberLineTable = Integer.parseInt(curPage.getText());
                        redraw();
                    }
                    curPage.setText(String.valueOf(numberLineTable));

                } catch (NumberFormatException ex) {
                    curPage.setText(String.valueOf(numberLineTable));
                }
            }
        });
    }

    public void redraw() {
        List<Student> studentList = controller.getStudent(currentPage, numberLineTable);
        table.removeAll();
        table.setBounds(20, 50, 1442, 24 * (numberLineTable + 1)+3);
        for (Student student : studentList) {
            TableItem tableItem = new TableItem(table, SWT.NULL);
            String array[] = new String[22];
            array[0] = student.getSurname() + " " + student.getName() + " " + student.getPatronymic();
            array[1] = String.valueOf(student.getGroup());
            int count = 0;
            for (Exam exam : student.getExams()) {
                array[count + 2] = exam.getNameExam();
                count++;
                array[count + 2] = String.valueOf(exam.getMark());
                count++;
            }
            tableItem.setText(array);
        }
        table.redraw();
    }

    private String openFileDialog(String type) {
        FileDialog fileDialog = new FileDialog(shell, "Save".equals(type)?SWT.SAVE:SWT.OPEN);
        fileDialog.setText(type);
        fileDialog.setFilterPath("C:/");
        String[] filterExst = new String[1];
        filterExst[0] = "*.xml";
        fileDialog.setFilterExtensions(filterExst);
        return fileDialog.open();
    }
}
