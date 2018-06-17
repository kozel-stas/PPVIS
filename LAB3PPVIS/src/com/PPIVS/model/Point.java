package com.PPIVS.model;

public class Point {
    private double time;
    private int numberElement;

    public Point(int numberElement, double time) {
        this.time = time;
        this.numberElement = numberElement;
    }

    public double getTime() {
        return time;
    }

    public int getNumberElement() {
        return numberElement;
    }
}
