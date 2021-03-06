package com.maastricht.university.logic.game.util.interfaces;

import java.util.ArrayList;
import java.util.List;

public interface IHexagon<T> extends Iterable<T> {

    T get(int x, int y);

    T insert(int x, int y, T value);

    T remove(int x, int y);

    IHexagon<T> clone();

    boolean contains(T entity);

    int size();

    List<T> getNeighbours(int x, int y);

    List<T> vector();

}
