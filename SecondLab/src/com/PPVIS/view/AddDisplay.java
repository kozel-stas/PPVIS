package com.PPVIS.view;

import com.PPVIS.Conroller.Controller;
import com.PPVIS.model.Exam;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.List;

public class AddDisplay {
    private Display display;
    private MainDisplay mainDisplay;
    private Shell shell;
    private Color color = new Color(null, 222, 204, 204);
    private Controller controller;

    public AddDisplay(Display display, MainDisplay mainDisplay, Controller controller) {
        this.display = display;
        this.mainDisplay = mainDisplay;
        shell = new Shell(display, SWT.TITLE | SWT.CLOSE);
        shell.setBackground(color);
        GridLayout gridLayout = new GridLayout(9, false);
        gridLayout.numColumns = 2;
        gridLayout.verticalSpacing = 8;
        shell.setLayout(gridLayout);
        shell.setText("AddWindow");
        shell.setSize(500, 480);
        initAddDisplay();
        this.controller=controller;
        shell.open();
        shell.setFocus();
    }

    private void initAddDisplay() {
        List<Exam> exams = new ArrayList(5);
        MessageBox messageBox = new MessageBox(shell);

        Label labelSurname = new Label(shell, SWT.NULL);
        labelSurname.setText("Фамилия");
        labelSurname.setBackground(color);

        Text surname = new Text(shell, SWT.SINGLE | SWT.BORDER);
        GridData gridDataSurname = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        gridDataSurname.horizontalSpan = 5;
        surname.setLayoutData(gridDataSurname);

        Label labelName = new Label(shell, SWT.NULL);
        labelName.setText("Имя");
        labelName.setBackground(color);

        Text name = new Text(shell, SWT.SINGLE | SWT.BORDER);
        GridData gridDataName = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        gridDataName.horizontalSpan = 5;
        name.setLayoutData(gridDataName);

        Label labelPatronymic = new Label(shell, SWT.NULL);
        labelPatronymic.setText("Отчество");
        labelPatronymic.setBackground(color);

        Text patronymic = new Text(shell, SWT.SINGLE | SWT.BORDER);
        GridData gridDataPatronymic = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        gridDataPatronymic.horizontalSpan = 5;
        patronymic.setLayoutData(gridDataPatronymic);

        Label labelGroup = new Label(shell, SWT.NULL);
        labelGroup.setText("Группа");
        labelGroup.setBackground(color);

        Text groupNumber = new Text(shell, SWT.SINGLE | SWT.BORDER);
        GridData gridDataGroupNumber = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        gridDataGroupNumber.horizontalSpan = 5;
        groupNumber.setLayoutData(gridDataGroupNumber);

        Label subjectName = new Label(shell, SWT.NONE);
        subjectName.setText("Название предмета");
        subjectName.setBackground(color);

        Text textSubjectName = new Text(shell, SWT.SINGLE | SWT.BORDER);
        GridData gridDataSubjectName = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        textSubjectName.setLayoutData(gridDataSubjectName);

        Label mark = new Label(shell, SWT.NONE);
        mark.setText("Оценка");
        mark.setBackground(color);

        Text textMark = new Text(shell, SWT.SINGLE | SWT.BORDER);
        GridData gridDataMark = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        textMark.setLayoutData(gridDataMark);

        Combo combo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
        GridData gridDataCombo = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        gridDataCombo.horizontalSpan = 5;
        combo.setLayoutData(gridDataCombo);

        Button addSubject = new Button(shell, SWT.PUSH);
        addSubject.setText("Add subject");
        addSubject.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                if (exams.size() != 5) {
                    String subject = textSubjectName.getText();
                    String markString = textMark.getText();
                    if (!"".equals(subject) && !"".equals(markString)) {
                        int mark;
                        try {
                            mark = Integer.parseInt(markString);
                            if (mark > 0 && mark < 11) {
                                Exam exam = new Exam(subject, mark);
                                exams.add(exam);
                                textSubjectName.setText("");
                                textMark.setText("");
                                combo.add(subject + "        " + mark);
                            } else {
                                messageBox.setMessage("Недопустимый диапазон оценки");
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
                } else {
                    messageBox.setMessage("Число экзаменов не может быть больше 5!");
                    messageBox.open();
                }
            }
        });

        Button del = new Button(shell, SWT.PUSH);
        del.setText("Delete subject");
        del.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                int index = combo.getSelectionIndex();
                if (index != -1) {
                    String exam = combo.getItem(index);
                    String sub = exam.substring(0, exam.indexOf("        "));
                    for (Exam examIter : exams)
                        if (examIter.getNameExam().equals(sub)) {
                            exams.remove(examIter);
                            combo.remove(index);
                            break;
                        }
                }
            }
        });

        Button add = new Button(shell, SWT.PUSH);
        add.setText("Add student");
        add.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                if (!"".equals(name.getText()) && !"".equals(surname.getText()) && !"".equals(patronymic.getText())) {
                    if (exams.size() == 5) {
                        if (controller.addStudent(name.getText(), surname.getText(), patronymic.getText(), groupNumber.getText(), exams)) {
                            messageBox.setMessage("Студент успешно добавлен");
                            messageBox.open();
                            mainDisplay.redraw();
                            shell.close();
                        } else {
                            messageBox.setMessage("Студент не добавлен, либо такой студент уже есть, либо введенный данные неккорректны");
                            messageBox.open();
                        }
                    } else {
                        messageBox.setMessage("Число экзаменов не может быть меньше 5!");
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
