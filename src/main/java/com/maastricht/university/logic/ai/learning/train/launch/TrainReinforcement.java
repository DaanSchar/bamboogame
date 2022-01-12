package com.maastricht.university.logic.ai.learning.train.launch;

import com.maastricht.university.logic.ai.learning.networks.ReinforcementNetwork;
import com.maastricht.university.logic.ai.learning.train.Train;
import com.maastricht.university.logic.ai.reinforcement.environment.ReinforcementEnvironment;

public class TrainReinforcement {

    public static void main(String[] args) {
        train();
    }

    static void train() {
        Train.train(
                new ReinforcementNetwork(),
                new ReinforcementEnvironment()
        );
    }

}
