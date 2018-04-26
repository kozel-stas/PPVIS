package com.PPVIS.Conroller.strategy;

import com.PPVIS.model.CompareObject;
import com.PPVIS.model.Student;

public class FindAverageStrategy implements  FindStrategy {
    @Override
    public boolean filtrData(CompareObject compareObject, Student student) {
       if(compareObject.getSurname().equalsIgnoreCase(student.getSurname()) && student.getAverageScore() > compareObject.getBottom() && student.getAverageScore() < compareObject.getTop())
           return true;
       return false;
    }
}
