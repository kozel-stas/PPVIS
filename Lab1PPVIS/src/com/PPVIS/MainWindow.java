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
import java.util.List;

public class MainWindow {
    private Display display;
    private Shell shell;

    public MainWindow(){
        display=new Display();
        shell =new Shell(display, SWT.CENTER | SWT.SHELL_TRIM);
        shell.setText("PPVIS_LAB1");
        shell.setSize(800,600);
        RowLayout rowLayout=new RowLayout();
        shell.setLayout(rowLayout);
        rowLayout.pack=false;
        rowLayout.spacing=20;
        rowLayout.marginBottom=15;
        rowLayout.marginTop=15;
        rowLayout.marginLeft=15;
        rowLayout.marginRight=20;
        centeringWindow();
    }

    private void centeringWindow(){
        Point resolution=new Point(display.getBounds().width,display.getBounds().height);
        Point point =new Point(shell.getSize().x,shell.getSize().y);
        shell.setBounds((resolution.x-point.x)/2,(resolution.y-point.y)/2,point.x,point.y);
    }

    public void showWindow(){
        new FirstTask(shell).showWindowFirstTask();
        new SecondTask(shell).showWindowSecondTask();
        new ThirdTask(shell).showWindowThirdTask();
        new FourthTask(shell).showWindowFourthTask();
        new FifthTask(shell).showWindowFirthTask();
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
        Button buttonCheck = new Button(group, SWT.PUSH);
        buttonCheck.setText("Нажми");
        RowData layoutButtonCheck = new RowData();
        layoutButtonCheck.width = 150;
        buttonCheck.setLayoutData(layoutButtonCheck);
        RowData layoutButtonSwap = new RowData();
        layoutButtonSwap.width = 150;
        for (int buttonInd = 0; buttonInd < pushCount; buttonInd++) {
            Button buttonSwap = new Button(group, SWT.CHECK);
            buttonSwap.setLayoutData(layoutButtonSwap);
            buttonSwap.setText(String.valueOf(buttonInd+1));
            buttonSet.add(buttonSwap);
        }
        buttonCheck.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (!text.getText().isEmpty()) {
                    for (Button button : buttonSet)
                        if (button.getText().equals(text.getText())) {
                            button.setSelection((button.getSelection()==false)?true:false);
                            return;
                        }
                    warningNotExist.open();
                    text.setText("");
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
    }

    private void buttonSwapColumn(Table table,int numberColumn){
        MessageBox warningNotExist1=new MessageBox(shell,SWT.COLOR_RED);
        warningNotExist1.setMessage("Нет элементов выделенных в "+String.valueOf(numberColumn+1)+" столбце");
        TableItem tableItem=table.getSelection()[0];
        if (!tableItem.getText(numberColumn).isEmpty()) {
            tableItem.setText(numberColumn==0?1:0, tableItem.getText(numberColumn));
            tableItem.setText(numberColumn, "");

        } else warningNotExist1.open();
        table.deselectAll();
    }

    public void showWindowFirthTask () {
        MessageBox warningSame=new MessageBox(shell,SWT.COLOR_RED);
        warningSame.setMessage("Такой пункт уже есть");
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
                String text1 = text.getText();
                if(!text1.isEmpty()){
                    for(TableItem tableItem:table.getItems())
                        if(tableItem.getText(0).equals(text1) || tableItem.getText().equals(text1)) {
                            warningSame.open();
                            text.setText("");
                            return;
                        }
                    TableItem tableItem=new TableItem(table,SWT.NONE);
                    tableItem.setText(0, text1);
                    text.setText("");
                } else warningEmpty.open();
            }
        });
        buttonSwapColumn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (table.getSelection().length > 0) {
                    buttonSwapColumn(table,0);
                } else warningNotExist1.open();

            }
        });
        buttonSwapColumnBack.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (table.getSelection().length > 0) {
                    buttonSwapColumn(table,1);
                } else warningNotExist2.open();
            }
        });
    }

}