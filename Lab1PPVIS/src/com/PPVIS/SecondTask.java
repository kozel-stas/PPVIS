package com.PPVIS;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
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
        Group group1=new Group(group,SWT.SHADOW_IN);
        RowLayout rowLayout=new RowLayout(SWT.HORIZONTAL);
        group1.setLayout(rowLayout);
        ButtonEight buttonChange=new ButtonEight(group1,SWT.PUSH);
        buttonChange.setText("Нажми");
        ButtonEight buttonSwap=new ButtonEight(group1,SWT.PUSH);
        buttonSwap.setText("Меня");
        buttonSwap.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseDown(MouseEvent e) {
                String swapLine=buttonSwap.getText();
                buttonSwap.setText(buttonChange.getText());
                buttonChange.setText(swapLine);
            }
        });
        buttonChange.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseDown(MouseEvent e) {
                if(!text.getText().isEmpty()){
                    buttonSwap.setText(text.getText());
                    text.setText("");
                } else warningEmpty.open();
            }
        });
    }

}
