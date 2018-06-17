package com.PPVIS.view;

import com.PPVIS.Conroller.Controller;
import com.PPVIS.Conroller.strategy.FindStrategy;
import com.PPVIS.model.CompareObject;
import com.PPVIS.model.Exam;
import com.PPVIS.model.Student;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.widgets.FormComposite;
import org.eclipse.widgets.TableComposite;

import java.util.List;


public class FindDisplay {
    private Color color = new Color(null, 222, 204, 204);
    private Composite formComposite;

    public FindDisplay(Shell shell, Controller controller, FormComposite formComposite, Button button, TableComposite tableComposite, FindStrategy strategy) {
        this.formComposite = (Composite) formComposite;
        shell.setBackground(color);
        GridLayout gridLayout = new GridLayout(3, false);
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 8;
        shell.setLayout(gridLayout);

        GridData gridDataForm = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridDataForm.horizontalSpan = 4;
        this.formComposite.setLayoutData(gridDataForm);

        button.setText("Find");
        GridData gridDataButton = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridDataButton.horizontalSpan = 4;
        button.setLayoutData(gridDataButton);
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                MessageBox messageBox = new MessageBox(shell);
                CompareObject compareObject = formComposite.getCompareObject();
                List<Student> students;
                if (compareObject != null) {
                    students = controller.find(compareObject, strategy);
                    if(students.size()==0) {
                        messageBox.setMessage("Ненайдено таких студентов");
                        messageBox.open();
                    }
                    tableComposite.redraw(students);
                }

            }
        });

        GridData gridDataTable = new GridData(GridData.FILL_BOTH);
        gridDataTable.horizontalSpan = 1;
        tableComposite.setLayoutData(gridDataTable);

        shell.setSize(1475 + 16, this.formComposite.getSize().y + tableComposite.getSize().y + 24 + 35);
        shell.setText("FindDisplay");
        shell.open();
        shell.setFocus();
    }

}
