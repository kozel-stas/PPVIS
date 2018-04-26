package com.PPVIS.Conroller.strategy;

import com.PPVIS.model.CompareObject;
import com.PPVIS.model.Student;

public interface FindStrategy {
    public boolean filtrData(CompareObject compareObject, Student student2);
}
