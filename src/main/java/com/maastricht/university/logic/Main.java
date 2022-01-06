package com.maastricht.university.logic;


import com.maastricht.university.logic.ai.hybrid.LearningAgent;
import com.maastricht.university.logic.ai.reinforcement.network.Train;
import com.maastricht.university.logic.game.game.GameState;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Train.train();
    }
}

