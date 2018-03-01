package com.PPVIS;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

class FirstTask{

    private Group group;
    private Shell shell;
    public FirstTask(Shell shell){
        this.shell=shell;
        RowLayout rowLayout=new RowLayout(SWT.VERTICAL);
        rowLayout.marginBottom=10;
        rowLayout.marginTop=10;
        rowLayout.marginLeft=20;
        rowLayout.marginRight=20;
        rowLayout.spacing=10;
        group=new Group(shell,SWT.SHADOW_IN);
        group.setText("Первое задание");
        group.setLayout(rowLayout);
    }

    public void showWindowFirstTask(){
        MessageBox warningEmpty=new MessageBox(shell,SWT.COLOR_RED);
        warningEmpty.setMessage("Введите текст");
        MessageBox warningSame=new MessageBox(shell,SWT.COLOR_RED);
        warningSame.setMessage("Такой пункт уже есть");
        Text text=new Text(group,SWT.BORDER);
        RowData layoutText=new RowData();
        layoutText.width=150;
        text.setLayoutData(layoutText);
        Combo combo=new Combo(group,SWT.DROP_DOWN| SWT.READ_ONLY);
        RowData layoutCombo=new RowData();
        layoutCombo.width=135;
        combo.setLayoutData(layoutCombo);
        Button button=new Button(group,SWT.PUSH);
        button.setText("Нажми");
        RowData layoutButton=new RowData();
        layoutButton.width=150;
        button.setLayoutData(layoutButton);
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if(!text.getText().isEmpty()){
                    for(int i=0;i<combo.getItemCount();i++)
                        if(combo.getItem(i).equals(text.getText())){
                            warningSame.open();
                            text.setText("");
                            return;
                        }
                    combo.add(text.getText());
                    text.setText("");
                } else warningEmpty.open();
            }
        });
    }

}