package com.PIVS.controller;

import com.PIVS.model.Graphic;
import com.PIVS.model.Point;

public class Controller {
    private Graphic graphic;

    public Controller (){
        graphic = new Graphic();
    }

    public void addPoint(Point point){
        graphic.addPoint(point);
    }

}
