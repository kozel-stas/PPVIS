package org.eclipse.widgets;

import com.PPVIS.Conroller.Controller;
import com.PPVIS.model.CompareObject;
import com.PPVIS.model.Student;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class FindCompositeAverage extends Composite implements FormComposite {
    private Color color = new Color(null, 222, 204, 204);
    private Text surname;
    private Text bottomLimit;
    private Text topLimit;
    private Shell shell;


    public FindCompositeAverage(Composite composite, int i) {
        super(composite, i);
        shell = (Shell) composite;
        GridLayout gridLayout = new GridLayout(19, false);
        gridLayout.numColumns = 5;
        gridLayout.verticalSpacing = 8;
        setLayout(gridLayout);
        setBackground(color);
        initNavigateInterface();
        setSize(1475, 230);
    }

    private void initNavigateInterface() {
        Label labelSurname = new Label(this, SWT.NULL);
        labelSurname.setText("Фамилия");
        labelSurname.setBackground(color);

        surname = new Text(this, SWT.SINGLE | SWT.BORDER);
        GridData gridDataSurname = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        gridDataSurname.horizontalSpan = 5;
        surname.setLayoutData(gridDataSurname);

        Label labelBottomLimit = new Label(this, SWT.NULL);
        labelBottomLimit.setText("Нижний предел");
        labelBottomLimit.setBackground(color);

        bottomLimit = new Text(this, SWT.SINGLE | SWT.BORDER);
        GridData gridDataBottomLimit = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        gridDataBottomLimit.horizontalSpan = 5;
        bottomLimit.setLayoutData(gridDataBottomLimit);

        Label labelTopLimit = new Label(this, SWT.NULL);
        labelTopLimit.setText("Верхний предел");
        labelTopLimit.setBackground(color);

        topLimit = new Text(this, SWT.SINGLE | SWT.BORDER);
        GridData gridDataTopLimit = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        gridDataTopLimit.horizontalSpan = 5;
        topLimit.setLayoutData(gridDataTopLimit);
    }


    public CompareObject getCompareObject() {
        MessageBox messageBox = new MessageBox(shell);
        String topLimitText = topLimit.getText();
        String bottomLimitText = bottomLimit.getText();
        String surnameText = surname.getText();
        if (!"".equals(topLimitText) && !"".equals(bottomLimitText) && !"".equals(surnameText)) {
            try {
                double top = Double.parseDouble(topLimitText);
                double bottom = Double.parseDouble(bottomLimitText);
                return new CompareObject(surnameText, top, bottom);
            } catch (NumberFormatException ex) {
                messageBox.setMessage("Недопустимые символы");
                messageBox.open();
            }
        } else {
            messageBox.setMessage("Недопустимые символы");
            messageBox.open();
        }
        return null;
    }
}
