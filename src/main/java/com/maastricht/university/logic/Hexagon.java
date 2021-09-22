package com.maastricht.university.logic;

public class Hexagon<T> implements IHexagon<T>{

    private int size;
    private int arraySize;

    private T[][] array;

    public Hexagon(int size) {
        this.size = size;
        arraySize = 2*size + 1;

        array = (T[][]) new Object[arraySize][arraySize];
    }

    @Override
    public T get(int q, int r) {
        if (isOutsideHexagon(q, r))
            return null;

        return array[q][r];
    }

    @Override
    public T insert(int q, int r, T value) {
        if (isOutsideHexagon(q, r))
            return null;

        array[q][r] = value;
        return array[q][r];
    }

    @Override
    public T remove(int q, int r) {
        if (isOutsideHexagon(q, r))
            return null;

        T value = array[q][r];
        array[q][r] = null;
        return value;
    }

    @Override
    public IHexagon<T> clone() {

        IHexagon<T> copy = new Hexagon<T>(size);

        for (int i = 0; i < arraySize; i++)
            for (int j = 0; j < arraySize; j++)
                copy.insert(i,j, this.get(i, j));

        return copy;
    }

    @Override
    public boolean contains(T entity) {
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {

                if (this.get(i, j) == null)
                    break;
                if (this.get(i, j).equals(entity))
                    return true;
            }
        }
        return false;
    }

    @Override
    public int getSize() {
        return size;
    }

    private boolean isOutsideHexagon(int q, int r) {
        return q + r < size || q + r > 3*size;
    }


}
