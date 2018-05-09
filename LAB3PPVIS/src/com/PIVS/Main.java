package com.PIVS;

import com.PIVS.controller.Controller;
import com.PIVS.model.SortList;

public class Main {

    public static void main(String[] args) {
        SortList sortList = new SortList(10000,10000, new Controller());
        sortList.start(100,1000);
	    Thread thread = new Thread(sortList);
	    thread.start();
    }
}
