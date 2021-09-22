package com.maastricht.university.logic;

import java.util.Objects;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("This is where the logic happens");
        //TODO: clean up code more
        //TODO: write testFile (to check if logic actually works)

        Random r = new Random();

        IHexagon<Point> a = new Hexagon<>(3);

        for (int i = 0; i < 3*2+1; i++) {
            for (int j = 0; j < 3*2+1; j++) {
                a.insert(i,j, new Point(r.nextInt(5)+1, r.nextInt(5)+1));
            }
        }

        IHexagon<Point> b = a.clone();

        for (int i = 0; i < 3*2+1; i++) {
            System.out.println();
            for (int j = 0; j < 3*2+1; j++) {
                if (a.get(i,j) == null)
                    System.out.print(" . ");
                else
                    System.out.print(" " + a.get(i,j) + " ");
            }
        }

        a.get(0,6).a = 20;

        System.out.println("\n" + a.get(0,6));
        System.out.println("\n" + b.get(0,6));


    }
}

class Point{
    int a;
    int b;

    public Point(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return a + " " + b;
    }
}
