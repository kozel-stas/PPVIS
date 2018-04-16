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

public class DeleteSurnameAverage {
    private Display display;
    private MainDisplay mainDisplay;
    private Shell shell;
    private Color color = new Color(null, 222, 204, 204);

    public DeleteSurnameAverage(Display display, MainDisplay mainDisplay) {
        this.display = display;
        this.mainDisplay = mainDisplay;
        shell = new Shell(display, SWT.TITLE | SWT.CLOSE);
        shell.setBackground(color);
        GridLayout gridLayout = new GridLayout(9, false);
        gridLayout.numColumns = 2;
        gridLayout.verticalSpacing = 8;
        shell.setLayout(gridLayout);
        shell.setText("DeleteWindow");
        shell.setSize(500, 280);
        initDeleteDisplay();
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
                if (!"".equals(topLimitText) && !"".equals(bottomLimitText) && !"".equals(surnameText)) {
                    try {
                        double top = Double.parseDouble(topLimitText);
                        double bottom = Double.parseDouble(bottomLimitText);
                        List<Student> students = Controller.getInstance().find(surnameText, top, bottom);
                        if (students.size() > 0) {
                            Controller.getInstance().deleteStudent(students);
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
