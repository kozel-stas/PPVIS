package com.PPVIS.Conroller.strategy;

import com.PPVIS.model.CompareObject;
import com.PPVIS.model.Student;

public interface FindStrategy {
    boolean filtrData(CompareObject compareObject, Student student);
}
