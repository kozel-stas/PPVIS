package org.eclipse.widgets;

import com.PPVIS.model.CompareObject;
import com.PPVIS.model.Student;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class FindCompositeGroup extends Composite implements FormComposite {
    private Color color = new Color(null, 222, 204, 204);
    private Shell shell;
    private Text surname;
    private Text group;

    public FindCompositeGroup(Composite composite, int i) {
        super(composite, i);
        shell = (Shell) composite;
        GridLayout gridLayout = new GridLayout(19, false);
        gridLayout.numColumns = 5;
        gridLayout.verticalSpacing = 8;
        setLayout(gridLayout);
        setBackground(color);
        initNavigateInterface();
        setSize(1475, 180);
    }

    private void initNavigateInterface() {
        Label labelSurname = new Label(getShell(), SWT.NULL);
        labelSurname.setText("Фамилия");
        labelSurname.setBackground(color);

        surname = new Text(getShell(), SWT.SINGLE | SWT.BORDER);
        GridData gridDataSurname = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        gridDataSurname.horizontalSpan = 5;
        surname.setLayoutData(gridDataSurname);

        Label labelGroup = new Label(getShell(), SWT.NULL);
        labelGroup.setText("Группа");
        labelGroup.setBackground(color);

        group = new Text(getShell(), SWT.SINGLE | SWT.BORDER);
        GridData gridDataBottomLimit = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        gridDataBottomLimit.horizontalSpan = 5;
        group.setLayoutData(gridDataBottomLimit);
    }

    public CompareObject getCompareObject() {
        MessageBox messageBox = new MessageBox(shell);
        String groupText = group.getText();
        String surnameText = surname.getText();
        if (!"".equals(groupText) && !"".equals(surnameText)) {
            try {
                int group = Integer.parseInt(groupText);
                return new CompareObject(surnameText, group);
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
