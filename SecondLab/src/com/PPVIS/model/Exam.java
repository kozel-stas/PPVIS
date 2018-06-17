package com.PPVIS.model;

public class Exam {
    private String nameExam;
    private int mark;

    public Exam(String nameExam, int mark) {
        this.nameExam = nameExam;
        this.mark = mark;
    }

    protected Exam(){}

    protected void setMark(int mark) {
        this.mark = mark;
    }

    protected void setNameExam(String nameExam) {
        this.nameExam = nameExam;
    }

    public String getNameExam() {
        return nameExam;
    }

    public int getMark() {
        return mark;
    }
}
