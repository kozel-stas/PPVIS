package com.PPVIS.view;

import com.PPVIS.Conroller.Controller;
import com.PPVIS.model.Student;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.List;

public class DeleteSurnameMark {
    private Display display;
    private MainDisplay mainDisplay;
    private Shell shell;
    private Color color = new Color(null, 222, 204, 204);
    private Controller controller;

    public DeleteSurnameMark(Display display, MainDisplay mainDisplay, Controller controller) {
        this.display = display;
        this.mainDisplay = mainDisplay;
        shell = new Shell(display, SWT.TITLE | SWT.CLOSE);
        shell.setBackground(color);
        GridLayout gridLayout = new GridLayout(9, false);
        gridLayout.numColumns = 2;
        gridLayout.verticalSpacing = 8;
        shell.setLayout(gridLayout);
        shell.setText("DeleteWindow");
        shell.setSize(500, 340);
        initDeleteDisplay();
        this.controller = controller;
        shell.open();
        shell.setFocus();
    }

    private void initDeleteDisplay() {
        MessageBox messageBox = new MessageBox(shell);

        Label labelSurname = new Label(shell, SWT.NULL);
        labelSurname.setText("Фамилия");
        labelSurname.setBackground(color);

        Text surname = new Text(shell, SWT.SINGLE | SWT.BORDER);
        GridData gridDataSurname = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        gridDataSurname.horizontalSpan = 5;
        surname.setLayoutData(gridDataSurname);

        Label labelBottomLimit = new Label(shell, SWT.NULL);
        labelBottomLimit.setText("Нижний предел");
        labelBottomLimit.setBackground(color);

        Text bottomLimit = new Text(shell, SWT.SINGLE | SWT.BORDER);
        GridData gridDataBottomLimit = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        gridDataBottomLimit.horizontalSpan = 5;
        bottomLimit.setLayoutData(gridDataBottomLimit);

        Label labelTopLimit = new Label(shell, SWT.NULL);
        labelTopLimit.setText("Верхний предел");
        labelTopLimit.setBackground(color);

        Text topLimit = new Text(shell, SWT.SINGLE | SWT.BORDER);
        GridData gridDataTopLimit = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        gridDataTopLimit.horizontalSpan = 5;
        topLimit.setLayoutData(gridDataTopLimit);

        Label labelSubj = new Label(shell, SWT.NULL);
        labelSubj.setText("Предмет");
        labelSubj.setBackground(color);

        Text subj = new Text(shell, SWT.SINGLE | SWT.BORDER);
        GridData gridDataSubj = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        gridDataSubj.horizontalSpan = 5;
        subj.setLayoutData(gridDataSubj);

        Button delete = new Button(shell, SWT.PUSH);
        delete.setText("Delete");
        GridData gridDataDelete = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        gridDataDelete.horizontalSpan = 5;
        delete.setLayoutData(gridDataDelete);
        delete.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                String topLimitText = topLimit.getText();
                String bottomLimitText = bottomLimit.getText();
                String surnameText = surname.getText();
                String subjText = subj.getText();
                if (!"".equals(topLimitText) && !"".equals(bottomLimitText) && !"".equals(surnameText) && !"".equals(subjText)) {
                    try {
                        int top = Integer.parseInt(topLimitText);
                        int bottom = Integer.parseInt(bottomLimitText);
                        List<Student> students = controller.find(surnameText, subjText, top, bottom);
                        if (students.size() > 0) {
                            controller.deleteStudent(students);
                            messageBox.setMessage("Удалено " + students.size() + " студентов");
                            messageBox.open();
                            mainDisplay.redraw();
                            shell.close();
                        } else {
                            messageBox.setMessage("Не найдено таких студентов");
                            messageBox.open();
                        }
                    } catch (NumberFormatException ex) {
                        messageBox.setMessage("Недопустимые символы");
                        messageBox.open();
                    }
                } else {
                    messageBox.setMessage("Недопустимые символы");
                    messageBox.open();
                }
            }
        });
    }
}
