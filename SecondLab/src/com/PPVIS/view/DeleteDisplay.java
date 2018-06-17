package com.PPVIS.view;

import com.PPVIS.Conroller.Controller;
import com.PPVIS.Conroller.strategy.FindStrategy;
import com.PPVIS.model.CompareObject;
import com.PPVIS.model.Student;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.widgets.FormComposite;

import java.util.List;

public class DeleteDisplay {
    private Color color = new Color(null, 222, 204, 204);
    private Composite formComposite;

    public DeleteDisplay(Shell shell, Controller controller, FormComposite formComposite, Button button,FindStrategy strategy,MainDisplay mainDisplay) {
        this.formComposite = (Composite) formComposite;
        shell.setBackground(color);
        GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 8;
        shell.setLayout(gridLayout);

        GridData gridDataForm = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridDataForm.horizontalSpan = 1;
        this.formComposite.setLayoutData(gridDataForm);

        button.setText("Delete");
        GridData gridDataButton = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gridDataButton.horizontalSpan = 1;
        button.setLayoutData(gridDataButton);
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent) {
                MessageBox messageBox = new MessageBox(shell);
                CompareObject compareObject = formComposite.getCompareObject();
                List<Student> students;
                if (compareObject != null) {
                    students = controller.find(compareObject,strategy);
                    if(students.size()>0){
                        controller.deleteStudent(students);
                        messageBox.setMessage("Удалено "+students.size()+" студентов" );
                        messageBox.open();
                        mainDisplay.redraw();
                        shell.close();
                    } else {
                        messageBox.setMessage("Не найдено таких студентов");
                        messageBox.open();
                    }
                }

            }
        });

        shell.setSize(190, this.formComposite.getSize().y + 24 + 35);
        shell.setText("FindDisplay");
        shell.open();
        shell.setFocus();
    }

}
