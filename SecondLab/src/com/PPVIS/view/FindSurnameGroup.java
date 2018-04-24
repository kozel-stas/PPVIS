package com.PPVIS.view;

import com.PPVIS.Conroller.Controller;
import com.PPVIS.model.Exam;
import com.PPVIS.model.Student;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.List;

public class FindSurnameGroup {
    private Display display;
    private MainDisplay mainDisplay;
    private Shell shell;
    private Color color = new Color(null, 222, 204, 204);
    private int currPage = 0;
    private int size = 10;
    private List<Student> students;
    private Table table;
    private Controller controller;

    public FindSurnameGroup(Display display, MainDisplay mainDisplay, Controller controller) {
        this.display = display;
        this.mainDisplay = mainDisplay;
        shell = new Shell(display, SWT.TITLE | SWT.CLOSE);
        shell.setBackground(color);
        GridLayout gridLayout = new GridLayout(19, false);
        gridLayout.numColumns = 2;
        gridLayout.verticalSpacing = 8;
        shell.setLayout(gridLayout);
        shell.setText("FindWindow");
        shell.setSize(1500, 525);
        initFindDisplay();
        this.controller = controller;
        shell.open();
        shell.setFocus();
    }

    private void initFindDisplay() {
        MessageBox messageBox = new MessageBox(shell);

        Label labelSurname = new Label(shell, SWT.NULL);
        labelSurname.setText("Фамилия");
        labelSurname.setBackground(color);

        Text surname = new Text(shell, SWT.SINGLE | SWT.BORDER);
        GridData gridDataSurname = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        gridDataSurname.horizontalSpan = 5;
        surname.setLayoutData(gridDataSurname);

        Label labelGroup = new Label(shell, SWT.NULL);
        labelGroup.setText("Группа");
        labelGroup.setBackground(color);

        Text group = new Text(shell, SWT.SINGLE | SWT.BORDER);
        GridData gridDataBottomLimit = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        gridDataBottomLimit.horizontalSpan = 5;
        group.setLayoutData(gridDataBottomLimit);

        Button delete = new Button(shell, SWT.PUSH);
        delete.setText("Find");
        GridData gridDataDelete = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        gridDataDelete.horizontalSpan = 5;
        delete.setLayoutData(gridDataDelete);
        delete.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                String groupText = group.getText();
                String surnameText = surname.getText();
                if (!"".equals(groupText) && !"".equals(surnameText)) {
                    try {
                        int group = Integer.parseInt(groupText);
                        students = controller.find(surnameText,group);
                        if(students.size()==0) {
                            messageBox.setMessage("Не найдено таких студентов");
                            messageBox.open();
                        }
                        redraw();
                    } catch (NumberFormatException ex){
                        messageBox.setMessage("Недопустимые символы");
                        messageBox.open();
                    }
                } else {
                    messageBox.setMessage("Недопустимые символы");
                    messageBox.open();
                }
            }
        });

        Table table = new Table(shell, SWT.FULL_SELECTION);
        this.table = table;
        table.setBounds(20, 50, 1442, 24 * 11 + 3);
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
        GridData gridDataTable = new GridData(SWT.FILL, SWT.FILL, false, true);
        gridDataTable.horizontalSpan = 5;
        table.setLayoutData(gridDataTable);

        Button nextPageButton = new Button(shell, SWT.PUSH);
        nextPageButton.setText("Next page");
        nextPageButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                if (table.getItems().length == size) {
                    currPage++;
                    redraw();
                }
            }
        });

        Button prevPageButton = new Button(shell, SWT.PUSH);
        prevPageButton.setText("Prev page");
        prevPageButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                if (currPage > 0) {
                    currPage--;
                    redraw();
                }
            }
        });
    }

    private void redraw() {
        List<Student> studentList = new ArrayList<>();
        if (currPage * size < students.size())
            studentList = students.subList(currPage * size, (currPage + 1) * size > students.size() ? students.size() : (currPage + 1) * size);
        table.removeAll();
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
}
