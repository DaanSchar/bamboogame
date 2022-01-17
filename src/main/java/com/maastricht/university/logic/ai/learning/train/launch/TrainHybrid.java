package com.maastricht.university.logic.ai.learning.train.launch;

import com.maastricht.university.logic.ai.hybrid.environment.HybridEnvironment;
import com.maastricht.university.logic.ai.learning.NetworkFactory;
import com.maastricht.university.logic.ai.learning.networks.HybridNetwork;
import com.maastricht.university.logic.ai.learning.train.Train;

public class TrainHybrid {

    public static void main(String[] args) {
        train();
    }

    static void train() {

        int[] epochs = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};

        for (int epoch : epochs) {
            NetworkFactory.setMinEpochs(epoch);

            for (int i = 0; i < 10; i++) {
                Train.train(
                        new HybridNetwork(),
                        new HybridEnvironment()
                );
            }
        }

    }
}
