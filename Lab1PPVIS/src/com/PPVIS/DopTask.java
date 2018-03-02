package com.PPVIS;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

public class DopTask {
    private Display display;
    private Shell shell;
    private Group group;
    public DopTask(Shell shell,Display display){
        this.shell=shell;
        this.display=display;
        FillLayout fillLayout=new FillLayout(SWT.HORIZONTAL);
        fillLayout.spacing=10;
        group=new Group(shell,SWT.SHADOW_IN);
        group.setText("Дополнительное задание");
        group.setLayout(fillLayout);
    }

    public void showWindowDopTask(){
        MessageBox messageBox=new MessageBox(shell);
        messageBox.setMessage("Кнопочка восьмерочка");
        Button buttonClose=new Button(group,SWT.PUSH);
        ButtonShape.setRegion(buttonClose);
        buttonClose.setBackground(new Color(display,255,0,0));
        buttonClose.addListener(SWT.Selection,event -> shell.close());
        Button buttonMessage=new Button(group,SWT.PUSH);
        ButtonShape.setRegion(buttonMessage);
        buttonMessage.setBackground(new Color(display,255,0,0));
        buttonMessage.addListener(SWT.Selection,event -> messageBox.open());

    }
}
