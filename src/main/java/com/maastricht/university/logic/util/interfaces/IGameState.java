package com.maastricht.university.logic.util.interfaces;

import com.maastricht.university.logic.game.Board;

public interface IGameState {

    public void move(int q, int r, int playerColor);

    public boolean isLegal(int q, int r, int playerColor);

    public int getPlayerTurn();

    public Board getBoard();
}
