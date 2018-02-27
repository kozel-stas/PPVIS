package com.PPVIS;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;
import java.util.ArrayList;

public class MainWindow {
    private Display display;
    private Shell shell;

    public MainWindow(){
        display=new Display();
        shell =new Shell(display, SWT.CENTER | SWT.SHELL_TRIM);
        shell.setText("PPVIS_LAB1");
        shell.setSize(800,600);
        centeringWindow();
    }

    private void centeringWindow(){
        Rectangle resolution =display.getBounds();
        shell.setBounds((resolution.width-shell.getSize().x)/2,(resolution.height-shell.getSize().y)/2,shell.getSize().x,shell.getSize().y);
    }

    public void showWindow(){
        RowLayout rowLayout=new RowLayout();
        shell.setLayout(rowLayout);
        new FirstTask(shell);
        new SecondTask(shell);
        new ThirdTask(shell);
        new FourthTask(shell);
        new FifthTask(shell);
        shell.pack();
        shell.open();
        while (!shell.isDisposed()){
            if(display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }
}


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
        showWindowFirstTask();
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
        showWindowSecondTask();
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
        showWindowThirdTask();
    }

    public void showWindowThirdTask(){
        ArrayList<Button> buttonSet=new ArrayList(3);
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
        for(int i=0;i<3;i++) {
            Button buttonSwap = new Button(group, SWT.RADIO);
            buttonSet.add(buttonSwap);
            RowData layoutButtonSwap = new RowData();
            layoutButtonSwap.width = 150;
            buttonSwap.setLayoutData(layoutButtonSwap);
        }
        buttonSet.get(0).setText("Я");
        buttonSet.get(1).setText("есть");
        buttonSet.get(2).setText("Грут");
        buttonCheck.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                boolean exist=false;
                if (!text.getText().isEmpty()){
                    for(Button button: buttonSet)
                        if(button.getText().equals(text.getText())) {button.setSelection(true); exist=true;}
                        else button.setSelection(false);
                    if(!exist) warningNotExist.open();
                } else warningEmpty.open();
            }
        });
    }

}

class FourthTask{
    private Group group;
    private Shell shell;

    public FourthTask(Shell shell){
        this.shell=shell;
        RowLayout rowLayout=new RowLayout(SWT.VERTICAL);
        rowLayout.marginBottom=10;
        rowLayout.marginTop=10;
        rowLayout.marginLeft=20;
        rowLayout.marginRight=20;
        rowLayout.spacing=10;
        group=new Group(shell,SWT.SHADOW_IN);
        group.setText("Четвертое задание");
        group.setLayout(rowLayout);
        showWindowFourthTask();
    }

    public void showWindowFourthTask () {
        ArrayList<Button> buttonSet=new ArrayList(3);
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
        for(int i=0;i<3;i++) {
            Button buttonSwap = new Button(group, SWT.CHECK);
            buttonSet.add(buttonSwap);
            RowData layoutButtonSwap = new RowData();
            layoutButtonSwap.width = 150;
            buttonSwap.setLayoutData(layoutButtonSwap);
        }
        buttonSet.get(0).setText("Я");
        buttonSet.get(1).setText("есть");
        buttonSet.get(2).setText("Грут");
        buttonCheck.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                boolean exist=false;
                if (!text.getText().isEmpty()){
                    for(Button button: buttonSet)
                        if(button.getText().equals(text.getText())) {button.setSelection(true); exist=true;}
                        else button.setSelection(false);
                    if(!exist) warningNotExist.open();
                } else warningEmpty.open();
            }
        });
    }

}

class FifthTask{
    private Group group;
    private Shell shell;

    public FifthTask(Shell shell){
        this.shell=shell;
        RowLayout rowLayout=new RowLayout(SWT.VERTICAL);
        rowLayout.marginBottom=10;
        rowLayout.marginTop=10;
        rowLayout.marginLeft=20;
        rowLayout.marginRight=20;
        rowLayout.spacing=10;
        group=new Group(shell,SWT.SHADOW_IN);
        group.setText("Пятое задание");
        group.setLayout(rowLayout);
        showWindowFirthTask();;
    }

    public void showWindowFirthTask () {
        MessageBox warningNotExist1=new MessageBox(shell,SWT.COLOR_RED);
        warningNotExist1.setMessage("Нет элементов выделенных в 1 столбце");
        MessageBox warningNotExist2=new MessageBox(shell,SWT.COLOR_RED);
        warningNotExist2.setMessage("Нет элементов выделенных в 2 столбце");
        MessageBox warningEmpty=new MessageBox(shell,SWT.COLOR_RED);
        warningEmpty.setMessage("Введите текст");
        Text text=new Text(group,SWT.BORDER);
        RowData layoutText=new RowData();
        layoutText.width=150;
        text.setLayoutData(layoutText);
        Button buttonSwap=new Button(group,SWT.PUSH);
        buttonSwap.setText("Нажми");
        RowData layoutButtonSwap=new RowData();
        layoutButtonSwap.width=150;
        buttonSwap.setLayoutData(layoutButtonSwap);
        Button buttonSwapColumn=new Button(group,SWT.PUSH);
        buttonSwapColumn.setText("Меня");
        RowData layoutButtonSwapColumn=new RowData();
        layoutButtonSwapColumn.width=150;
        buttonSwapColumn.setLayoutData(layoutButtonSwapColumn);
        Button buttonSwapColumnBack=new Button(group,SWT.PUSH);
        buttonSwapColumnBack.setText("А я?");
        RowData layoutButtonSwapColumnBack=new RowData();
        layoutButtonSwapColumnBack.width=150;
        buttonSwapColumnBack.setLayoutData(layoutButtonSwapColumnBack);
        final Table table=new Table(group,SWT.NONE);
        RowData layoutTable=new RowData();
        layoutTable.width=150;
        layoutTable.height=75;
        table.setLayoutData(layoutTable);
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        TableColumn tableColumnFirst=new TableColumn(table,SWT.NONE);
        TableColumn tableColumnSecond=new TableColumn(table,SWT.NONE);
        tableColumnFirst.setText("Первый");
        tableColumnSecond.setText("Второй");
        tableColumnFirst.setWidth(75);
        tableColumnSecond.setWidth(75);
        table.getColumn(0).pack();
        table.getColumn(1).pack();
        buttonSwap.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if(!text.getText().isEmpty()){
                    TableItem tableItem=new TableItem(table,SWT.NONE);
                    tableItem.setText(0,text.getText());
                    text.setText("");
                } else warningEmpty.open();
            }
        });
        buttonSwapColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (table.getSelection().length > 0) {
                    if (!table.getSelection()[0].getText(0).isEmpty()) {
                        table.getSelection()[0].setText(1, table.getSelection()[0].getText(0));
                        table.getSelection()[0].setText(0, "");

                    } else warningNotExist1.open();
                        table.deselectAll();
                } else warningNotExist1.open();

            }
        });
        buttonSwapColumnBack.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (table.getSelection().length > 0) {
                    if (!table.getSelection()[0].getText(1).isEmpty()) {
                        table.getSelection()[0].setText(0, table.getSelection()[0].getText(1));
                        table.getSelection()[0].setText(1, "");
                    } else warningNotExist2.open();
                    table.deselectAll();
                } else warningNotExist2.open();
            }
        });
    }

}