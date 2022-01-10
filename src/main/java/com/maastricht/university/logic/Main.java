package com.maastricht.university.logic;

import com.maastricht.university.logic.ai.reinforcement.network.Train;

public class Main {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Train.train();
        System.out.println("Total Training time: " + (System.currentTimeMillis() - startTime));
    }
}

