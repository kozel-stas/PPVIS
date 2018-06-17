package com.PPIVS.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenList {
    private List<Integer> randList;

    public GenList(){}

    public List genList(int size){
        randList = new ArrayList<>(size);
        Random random = new Random();
        for (int i=0;i<size;i++){
            randList.add(random.nextInt());
        }
        return randList;
    }

    public List getRandList() {
        return randList;
    }
}
