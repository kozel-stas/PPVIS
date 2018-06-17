package org.eclipse.widgets;

public class GraphicPoint {
    private double time;
    private double numberElement;

    public GraphicPoint(double numberElement, double time) {
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
