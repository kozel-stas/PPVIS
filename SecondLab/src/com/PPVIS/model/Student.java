package com.PPVIS.model;

import java.util.List;

public class Student {
    private String name;
    private String surname;
    private String patronymic;
    private int group;
    private List<Exam> exams;
    private double averageScore=-1;

    public Student(String name, String surname, String patronymic,int group, List<Exam> exams) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.group=group;
        this.exams=exams;
    }

    protected Student(){}

    protected void setGroup(int group) {
        this.group = group;
    }

    protected void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    protected void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public double getAverageScore() {
        if(averageScore==-1) {
            averageScore=0;
            for (Exam exam : exams)
                averageScore += exam.getMark();
            averageScore=averageScore/exams.size();
        }
        return averageScore;
    }

    public int getGroup() {
        return group;
    }
}
