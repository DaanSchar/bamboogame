package com.maastricht.university.logic;

public interface IHexagon<T> {

    T get(int x, int y, int z);

    T insert(int x, int y, int z);

    T remove(int x, int y, int z);

    IHexagon<T> clone();

    boolean contains(T entity);

}
