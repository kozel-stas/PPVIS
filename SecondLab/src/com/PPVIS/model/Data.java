package com.PPVIS.model;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<Student> students = new ArrayList<>();

    public Data() {
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public boolean addStudent(Student student) {
        if (isUniqFIO(student)){
            students.add(student);
            return true;
        }
        return false;
    }

    private boolean isUniqFIO(Student student) {
        String name = student.getName();
        String surname = student.getSurname();
        String patronymic = student.getPatronymic();
        for (Student studentIter : students) {
            if (studentIter.getName().equals(name) && studentIter.getName().equals(surname) && studentIter.getName().equals(patronymic))
                return false;
        }
        return true;
    }

    public void remove(List<Student> list){
        students.removeAll(list);
    }

    public List<Student> getStudents(){return students;}


}
