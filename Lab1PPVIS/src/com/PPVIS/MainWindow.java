package com.PPVIS;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;


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

    public static void main(String[] args) {
        MainWindow mainWindow=new MainWindow();
        mainWindow.showWindow();
    }
}


