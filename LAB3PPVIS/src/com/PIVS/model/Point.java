package com.PIVS.model;

public class Point {
    private double time;
    private double numberElement;

    public Point(double time, double numberElement) {
        this.time = time;
        this.numberElement = numberElement;
    }

    public double getTime() {
        return time;
    }

    public double getNumberElement() {
        return numberElement;
    }
}
