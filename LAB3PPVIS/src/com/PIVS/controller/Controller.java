package com.PIVS.controller;

import com.PIVS.model.Graphic;
import com.PIVS.model.Point;
import com.PIVS.model.SortList;
import com.PIVS.view.MainWindow;
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
        int index = graphic.addPoint(point);
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
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
        if (data.size() > index)
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
