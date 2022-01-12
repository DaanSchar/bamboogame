package com.maastricht.university.logic.ai.learning.train.launch;

import com.maastricht.university.logic.ai.hybrid.environment.HybridEnvironment;
import com.maastricht.university.logic.ai.learning.networks.HybridNetwork;
import com.maastricht.university.logic.ai.learning.train.Train;

public class TrainHybrid {

    public static void main(String[] args) {
        train();
    }

    static void train() {
        Train.train(
                new HybridNetwork(),
                new HybridEnvironment()
        );
    }
}
