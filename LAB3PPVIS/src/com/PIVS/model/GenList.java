package com.PIVS.model;

import java.util.Random;

public class GenList {
    private int[] randList;

    public GenList(){}

    public int[] genList(int size){
        randList = new int[size];
        Random random = new Random();
        for (int i=0;i<size;i++){
            randList[i]=random.nextInt();
        }
        return randList;
    }

    public int[] getRandList() {
        return randList;
    }
}
