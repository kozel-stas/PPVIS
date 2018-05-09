package com.PIVS.model;

import com.PIVS.controller.Controller;

import java.util.List;

public class SortList implements Runnable {
    private GenList genList;
    private Controller controller;
    private int numberTest;
    private boolean isAlive = false;
    private int numberIteration;
    private int iteration = 2;

    public SortList(Controller controller) {
        this.controller = controller;
        genList = new GenList();
    }

    public SortList(int numberTest, int numberIteration, Controller controller) {
        this.numberTest = numberTest;
        this.numberIteration = numberIteration;
        this.controller = controller;
        genList = new GenList();
    }

    public void start() {
        isAlive = true;
        iteration = 2;
    }

    public void start(int numberTest, int numberIteration) {
        this.numberTest = numberTest;
        this.numberIteration = numberIteration;
        start();
    }

    public void shutdown() {
        isAlive = false;
    }

    @Override
    public void run() {
        while (isAlive && iteration != numberIteration + 1) {
            double allTime = 0;
            for (int i = 0; i < numberTest; i++) {
                genList.genList(iteration);
                int[] sortList = genList.getRandList();
                double currentTime = System.nanoTime();
                sortList(sortList);
                allTime += System.nanoTime() - currentTime;
            }
            allTime = allTime / numberTest;
            System.out.println(iteration + "      " + allTime / 1000);
            iteration++;
        }
    }

    private void sortList(int[] sortList) {
        for (int i = 0; i < sortList.length; i++) {
            for (int j = sortList.length - 1; j > i; j--) {
                if (sortList[j - 1] > sortList[j]) {
                    int temp = sortList[j - 1];
                    sortList[j - 1] = sortList[j];
                    sortList[j] = temp;
                }
            }
        }
    }
}
