package com.maastricht.university.logic;


public class Main {

    public static void main(String[] args) throws Exception  {
        GameState state = new GameState(2,2);

        try {
            state.move(0, 3, 1);
        } catch (IllegalMoveException e) {
            System.out.println(e);
        }

        try {
            state.move(0, 4, 1);
        } catch (IllegalMoveException e) {
            System.out.println(e);
        }

        System.out.println(state.getBoard().getTileMap());

    }
}

