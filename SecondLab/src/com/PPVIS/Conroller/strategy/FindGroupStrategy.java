package com.PPVIS.Conroller.strategy;

import com.PPVIS.model.CompareObject;
import com.PPVIS.model.Student;

public class FindGroupStrategy implements FindStrategy {
    @Override
    public boolean filtrData(CompareObject compareObject, Student student) {
        if(compareObject.getSurname().equalsIgnoreCase(student.getSurname()) && student.getGroup() == compareObject.getGroup())
            return true;
        return false;
    }
}
