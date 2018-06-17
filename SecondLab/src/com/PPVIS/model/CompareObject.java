package com.PPVIS.model;

public class CompareObject {
    private String surname;
    private double top;
    private double bottom;
    private int group;
    private String subj;

    public CompareObject(String surname, double top, double bottom) {
        this.surname = surname;
        this.top = top;
        this.bottom = bottom;
        group = -1;
        subj = null;
    }

    public CompareObject(String surname, int group) {
        this.surname = surname;
        this.group = group;
        top = -1;
        bottom = -1;
        subj = null;
    }

    public CompareObject(String surname, String subj, double top, double bottom) {
        this.surname = surname;
        this.top = top;
        this.bottom = bottom;
        this.subj = subj;
        group = -1;
    }

    public String getSubj() {
        return subj;
    }

    public int getGroup() {
        return group;
    }

    public double getBottom() {
        return bottom;
    }

    public double getTop() {
        return top;
    }

    public String getSurname() {
        return surname;
    }


}
