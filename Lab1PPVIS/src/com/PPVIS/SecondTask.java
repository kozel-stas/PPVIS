package com.PPVIS;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

class SecondTask{

    private Group group;
    private Shell shell;
    public SecondTask(Shell shell){
        this.shell=shell;
        RowLayout rowLayout=new RowLayout(SWT.VERTICAL);
        rowLayout.marginBottom=10;
        rowLayout.marginTop=10;
        rowLayout.marginLeft=20;
        rowLayout.marginRight=20;
        rowLayout.spacing=10;
        group=new Group(shell,SWT.SHADOW_IN);
        group.setText("Второе задание");
        group.setLayout(rowLayout);
    }

    public void showWindowSecondTask(){
        MessageBox warningEmpty=new MessageBox(shell,SWT.COLOR_RED);
        warningEmpty.setMessage("Введите текст");
        Text text=new Text(group,SWT.BORDER);
        RowData layoutText=new RowData();
        layoutText.width=150;
        text.setLayoutData(layoutText);
        Button buttonChange=new Button(group,SWT.PUSH);
        buttonChange.setText("Нажми");
        RowData layoutButtonChange=new RowData();
        layoutButtonChange.width=150;
        buttonChange.setLayoutData(layoutButtonChange);
        Button buttonSwap=new Button(group,SWT.PUSH);
        buttonSwap.setText("И меня тоже");
        RowData layoutButtonSwap=new RowData();
        layoutButtonSwap.width=150;
        buttonSwap.setLayoutData(layoutButtonSwap);
        buttonSwap.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String swapLine=buttonSwap.getText();
                buttonSwap.setText(buttonChange.getText());
                buttonChange.setText(swapLine);
            }
        });
        buttonChange.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if(!text.getText().isEmpty()){
                    buttonSwap.setText(text.getText());
                    text.setText("");
                } else warningEmpty.open();
            }
        });
    }

}
