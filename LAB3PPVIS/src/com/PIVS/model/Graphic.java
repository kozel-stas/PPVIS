package com.PIVS.model;

import java.util.ArrayList;
import java.util.List;

public class Graphic {
    private List<Point> graphic;

    public Graphic (){
        graphic = new ArrayList<>();
    }

    public void addPoint(Point point){
        graphic.add(point);
    }

    public void removeAll(){
        graphic.clear();
    }

    public List<Point> getGraphic() {
        return graphic;
    }
}
