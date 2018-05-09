package com.PIVS.model;

import java.util.ArrayList;
import java.util.List;

public class Graphic {
    private List<Point> graphic;

    public Graphic() {
        graphic = new ArrayList<>();
    }

    public int addPoint(Point point) {
        synchronized (graphic) {
            graphic.add(point);
            return graphic.size()-1;
        }
    }

    public void removeAll() {
        synchronized (graphic) {
            graphic.clear();
        }
    }

    public List<Point> getGraphic() {
        synchronized (graphic) {
            return new ArrayList<>(graphic);
        }
    }
}
