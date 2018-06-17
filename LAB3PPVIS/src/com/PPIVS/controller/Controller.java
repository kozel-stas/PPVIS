package com.PPIVS.controller;

import com.PPIVS.model.Graphic;
import com.PPIVS.model.Point;
import com.PPIVS.model.SortList;
import com.PPIVS.view.MainWindow;
import org.eclipse.swt.widgets.Display;

import java.util.List;

public class Controller {
    private Graphic graphic;
    private MainWindow mainWindow;
    private SortList sortList;
    private Thread thread;

    public Controller(MainWindow mainWindow) {
        graphic = new Graphic();
        this.mainWindow = mainWindow;
        this.sortList = new SortList(this);
    }

    public void addPoint(Point point) {
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                int index = graphic.addPoint(point);
                mainWindow.updateData(index);
            }
        });
    }

    public void startSortThread(int numberTest, int numberIteration) {
        if (thread != null) {
            sortList.shutdown();
            while (thread.isAlive()) ;
            graphic.removeAll();
            mainWindow.removeAllData();
        }
        sortList.start(numberTest, numberIteration);
        thread = new Thread(sortList);
        thread.start();
    }

    public Point getData(int index) {
        List<Point> data = graphic.getGraphic();
        if (index!=-1)
            return data.get(index);
        return null;
    }

    public void close() {
        if (thread != null) {
            sortList.shutdown();
            while (thread.isAlive()) ;
            Display.getDefault();
        }
    }

}
