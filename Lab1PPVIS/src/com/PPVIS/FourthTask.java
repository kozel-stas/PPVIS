package com.PPVIS;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.List;

class FourthTask {
    private Group group;
    private Shell shell;

    public FourthTask(Shell shell) {
        this.shell = shell;
        RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
        rowLayout.marginBottom = 10;
        rowLayout.marginTop = 10;
        rowLayout.marginLeft = 20;
        rowLayout.marginRight = 20;
        rowLayout.spacing = 10;
        group = new Group(shell, SWT.SHADOW_IN);
        group.setText("Четвертое задание");
        group.setLayout(rowLayout);
    }

    public void showWindowFourthTask() {
        int pushCount = 3;
        List<Button> buttonSet = new ArrayList(pushCount);
        MessageBox warningNotExist = new MessageBox(shell, SWT.COLOR_RED);
        warningNotExist.setMessage("Нет такого пункта");
        MessageBox warningEmpty = new MessageBox(shell, SWT.COLOR_RED);
        warningEmpty.setMessage("Введите текст");
        Text text = new Text(group, SWT.BORDER);
        RowData layoutText = new RowData();
        layoutText.width = 150;
        text.setLayoutData(layoutText);
        ButtonEight buttonCheck = new ButtonEight(group, SWT.PUSH);
        buttonCheck.setText("Нажми");
        RowData layoutButtonSwap = new RowData();
        layoutButtonSwap.width = 150;
        for (int buttonInd = 0; buttonInd < pushCount; buttonInd++) {
            Button buttonSwap = new Button(group, SWT.CHECK);
            buttonSwap.setLayoutData(layoutButtonSwap);
            buttonSwap.setText(String.valueOf(buttonInd+1));
            buttonSet.add(buttonSwap);
        }
        buttonCheck.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseDown(MouseEvent e) {
                if (!text.getText().isEmpty()) {
                    for (Button button : buttonSet)
                        if (button.getText().equals(text.getText())) {
                            button.setSelection(!button.getSelection());
                            return;
                        }
                    warningNotExist.open();
                    text.setText("");
                } else warningEmpty.open();
            }
        });
    }
}
