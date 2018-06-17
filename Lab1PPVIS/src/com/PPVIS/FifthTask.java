package com.PPVIS;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

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

    private void buttonSwapColumn(Table table, int numberColumn){
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
        layoutText.width=200;
        text.setLayoutData(layoutText);
        Group group1=new Group(group,SWT.SHADOW_IN);
        RowLayout rowLayout=new RowLayout(SWT.HORIZONTAL);
        group1.setLayout(rowLayout);
        ButtonEight buttonSwap=new ButtonEight(group1,SWT.PUSH);
        buttonSwap.setText("Нажми");
        ButtonEight buttonSwapColumn=new ButtonEight(group1,SWT.PUSH);
        buttonSwapColumn.setText("Меня");
        ButtonEight buttonSwapColumnBack=new ButtonEight(group1,SWT.PUSH);
        buttonSwapColumnBack.setText("Тоже");
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
        tableColumnFirst.setWidth(100);
        tableColumnSecond.setWidth(100);
        table.getColumn(0).pack();
        table.getColumn(1).pack();
        buttonSwap.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseDown(MouseEvent e) {
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
        buttonSwapColumn.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseDown(MouseEvent e) {
                if (table.getSelection().length > 0) {
                    buttonSwapColumn(table,0);
                } else warningNotExist1.open();

            }
        });
        buttonSwapColumnBack.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseDown(MouseEvent e) {
                if (table.getSelection().length > 0) {
                    buttonSwapColumn(table,1);
                } else warningNotExist2.open();
            }
        });
    }

}