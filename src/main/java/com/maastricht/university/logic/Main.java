package com.maastricht.university.logic;


import com.maastricht.university.logic.game.game.GameState;

public class Main {

    public static void main(String[] args) throws Exception  {
        GameState state = new GameState(2,2);


        state.move(0, 3, 1);



        state.move(0, 4, 1);


        System.out.println(state.getBoard().getTileMap());

    }
}

