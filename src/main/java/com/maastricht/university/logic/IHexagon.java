package com.maastricht.university.logic;

import java.util.List;

public interface IHexagon<T>{

    T get(int x, int y);

    T insert(int x, int y, T value);

    T remove(int x, int y);

    IHexagon<T> clone();

    boolean contains(T entity);

    int getSize();

    List<T> getNeighbours(int x, int y);

}
