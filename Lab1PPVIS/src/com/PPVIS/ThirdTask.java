package com.PPVIS;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import java.util.ArrayList;
import java.util.List;

class ThirdTask{
    private Group group;
    private Shell shell;

    public ThirdTask(Shell shell){
        this.shell=shell;
        RowLayout rowLayout=new RowLayout(SWT.VERTICAL);
        rowLayout.marginBottom=10;
        rowLayout.marginTop=10;
        rowLayout.marginLeft=20;
        rowLayout.marginRight=20;
        rowLayout.spacing=10;
        group=new Group(shell,SWT.SHADOW_IN);
        group.setText("Третье задание");
        group.setLayout(rowLayout);
    }

    public void showWindowThirdTask(){
        int radioCount = 3;
        List<Button> buttonSet=new ArrayList(radioCount);
        MessageBox warningNotExist=new MessageBox(shell,SWT.COLOR_RED);
        warningNotExist.setMessage("Нет такого пункта");
        MessageBox warningEmpty=new MessageBox(shell,SWT.COLOR_RED);
        warningEmpty.setMessage("Введите текст");
        Text text=new Text(group,SWT.BORDER);
        RowData layoutText=new RowData();
        layoutText.width=150;
        text.setLayoutData(layoutText);
        Button buttonCheck=new Button(group,SWT.PUSH);
        buttonCheck.setText("Нажми");
        RowData layoutButtonCheck=new RowData();
        layoutButtonCheck.width=150;
        buttonCheck.setLayoutData(layoutButtonCheck);
        RowData layoutButtonSwap = new RowData();
        layoutButtonSwap.width = 150;
        for(int buttonInd = 0; buttonInd< radioCount; buttonInd++) {
            Button buttonSwap = new Button(group, SWT.RADIO);
            buttonSwap.setLayoutData(layoutButtonSwap);
            buttonSwap.setText(String.valueOf(buttonInd+1));
            buttonSet.add(buttonSwap);
        }
        buttonCheck.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (!text.getText().isEmpty()){
                    boolean exist=false;
                    for(Button button: buttonSet)
                        if(button.getText().equals(text.getText())) {
                            button.setSelection(true);
                            exist=true;
                        }
                        else button.setSelection(false);
                    if(!exist) warningNotExist.open();
                    text.setText("");
                } else warningEmpty.open();
            }
        });
    }

}
