package com.maastricht.university.logic;

import java.util.Objects;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        System.out.println("This is where the logic happens");
        //TODO: clean up code more
        //TODO: write testFile (to check if logic actually works)

        Random r = new Random();

        IHexagon<Integer> a = new Hexagon<>(3);
        for (int i = 0; i < 3*2+1; i++) {
            for (int j = 0; j < 3*2+1; j++) {
                a.insert(i,j, 1);
            }
        }

        for (int i = 0; i < 3*2+1; i++) {
            System.out.println();
            for (int j = 0; j < 3*2+1; j++) {
                if (a.get(i,j) == null)
                    System.out.print(" . ");
                else
                    System.out.print(" " + a.get(i,j) + " ");
            }
        }

        System.out.print("\n" + a.getNeighbours(6,0));
    }
}
