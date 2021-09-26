package com.maastricht.university.logic;


public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("This is where the logic happens");

        GameState state = new GameState(4,2);
        System.out.println(state.getPlayerTurn());
    }
}

