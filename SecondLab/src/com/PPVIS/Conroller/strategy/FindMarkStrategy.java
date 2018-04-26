package com.PPVIS.Conroller.strategy;

import com.PPVIS.model.CompareObject;
import com.PPVIS.model.Exam;
import com.PPVIS.model.Student;

public class FindMarkStrategy implements FindStrategy {
    @Override
    public boolean filtrData(CompareObject compareObject, Student student) {
        if (student.getSurname().equalsIgnoreCase(compareObject.getSurname())) {
            for (Exam exam : student.getExams())
                if (exam.getNameExam().equals(compareObject.getSubj()) && exam.getMark() <= compareObject.getTop() && exam.getMark() >= compareObject.getBottom())
                   return true;
        }
        return false;
    }
}
