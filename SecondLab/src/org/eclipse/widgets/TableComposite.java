package org.eclipse.widgets;

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

public class TableComposite extends Composite {
    private Table table;
    private int numberLineTable = 30;
    private int currentPage = 0;
    private int maxPage = 1;
    private int maxNumberLineTable = 30;
    private Color color = new Color(null, 222, 204, 204);
    private Label labelCurrPage;
    private List<Student> students;

    public TableComposite(Composite composite, int i) {
        super(composite, i);
        setBackground(color);
        GridLayout gridLayout = new GridLayout(19, false);
        gridLayout.numColumns = 4;
        gridLayout.verticalSpacing = 8;
        setLayout(gridLayout);
        initTable();
        initNavigateInterface();
        setSize(1475, 24 * (numberLineTable + 1) + 90);

    }

    public TableComposite(Composite composite, int i, int maxNumberLineTable) {
        super(composite, i);
        setBackground(color);
        GridLayout gridLayout = new GridLayout(19, false);
        gridLayout.numColumns = 4;
        gridLayout.verticalSpacing = 8;
        setLayout(gridLayout);
        this.maxNumberLineTable = maxNumberLineTable;
        numberLineTable = maxNumberLineTable;
        initTable();
        initNavigateInterface();
        setSize(1475, 24 * (numberLineTable + 1) + 90);

    }

    private void initTable() {
        Table table = new Table(this, SWT.FULL_SELECTION);
        this.table = table;

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

        GridData gridDataTable = new GridData(GridData.FILL_BOTH);
        gridDataTable.horizontalSpan = 4;
        table.setLayoutData(gridDataTable);

////////////////////////////////////////////////////////////////////////////////////table
    }

    private void initNavigateInterface() {
        labelCurrPage = new Label(this, SWT.NULL);
        labelCurrPage.setBackground(color);
        labelCurrPage.setText(String.valueOf(currentPage + 1) + '/' + String.valueOf(maxPage));
        GridData gridDataLabelCurr = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridDataLabelCurr.horizontalSpan = 2;
        labelCurrPage.setLayoutData(gridDataLabelCurr);

        Text curPage = new Text(this, SWT.SINGLE | SWT.BORDER);
        curPage.setText(String.valueOf(numberLineTable));

        Button updateButton = new Button(this, SWT.PUSH);
        updateButton.setText("UPDATE");
        updateButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                try {
                    if (Integer.parseInt(curPage.getText()) > 0 && Integer.parseInt(curPage.getText()) <= maxNumberLineTable) {
                        numberLineTable = Integer.parseInt(curPage.getText());
                        redraw();
                    }
                    curPage.setText(String.valueOf(numberLineTable));
                    labelCurrPage.setText(String.valueOf(currentPage + 1) + '/' + String.valueOf(maxPage));

                } catch (NumberFormatException ex) {
                    curPage.setText(String.valueOf(numberLineTable));
                }
            }
        });

        Button prevPageButton = new Button(this, SWT.PUSH);
        prevPageButton.setText("Prev page");
        prevPageButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                if (currentPage > 0) {
                    currentPage--;
                    labelCurrPage.setText(String.valueOf(currentPage + 1) + '/');
                    redraw();
                }
            }
        });

        Button nextPageButton = new Button(this, SWT.PUSH);
        nextPageButton.setText("Next page");
        nextPageButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                if (table.getItems().length == numberLineTable && currentPage + 1 < maxPage) {
                    currentPage++;
                    labelCurrPage.setText(String.valueOf(currentPage + 1));
                    redraw();
                }
            }
        });

        Button firstPage = new Button(this, SWT.PUSH);
        firstPage.setText("First page");
        firstPage.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                currentPage = 0;
                redraw();
            }
        });

        Button lastPage = new Button(this, SWT.PUSH);
        lastPage.setText("Last page");
        lastPage.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                currentPage = maxPage - 1;
                redraw();
            }
        });

    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void redraw(List<Student> students) {
        setStudents(students);
        redraw();
    }

    public void setBounds(int i, int i1) {
        super.setBounds(i, i1, 1475, 24 * (numberLineTable + 1) + 90);
    }

    public void redraw() {
        if (students == null) return;
        List<Student> studentList = new ArrayList<>();
        maxPage = (int) students.size() % numberLineTable == 0 ? students.size() / numberLineTable : students.size() / numberLineTable + 1;
        if (currentPage + 1 > maxPage) currentPage = 0;
        if (currentPage * numberLineTable < students.size())
            studentList = students.subList(currentPage * numberLineTable, (currentPage + 1) * numberLineTable > students.size() ? students.size() : (currentPage + 1) * numberLineTable);
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
        labelCurrPage.setText(String.valueOf(currentPage + 1) + '/' + String.valueOf(maxPage));
        table.redraw();
        super.redraw();
    }
}
