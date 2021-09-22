package com.maastricht.university.logic;

public class Hexagon<T> implements IHexagon<T>{

    /**
     * this class represent a 2d hexagonal structure represented by a 2d array.
     * article: https://www.redblobgames.com/grids/hexagons/
     *
     * Implementation:
     * an array where the bottom left and top right indices have been cut out (the way that Fabian had
     * implemented it in the frontend package.). it wil look something like this:
     *
     * [1] [1] [1] [ ] [ ]
     * [1] [1] [1] [1] [ ]
     * [1] [1] [1] [1] [1]
     * [ ] [1] [1] [1] [1]
     * [ ] [ ] [1] [1] [1]
     *
     * (the size of this example is 2, aka the length from the center to the outside)
     *
     * you can simply loop through it as if it were a 2d array, however on the empty indices, it will
     * simply return null.
     *
     * NOTE: I actually didnt find anything about storing the array inside a 3-dimensional array,
     * so i chose to do it inside a 2d array, which seemed also a lot simpler. We could extend this class
     * if we wanted to add some translations of the coordinates to have the center of the hexagon be 0,0 ,
     * but for now that doesnt seem really necessary.
     */

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

    public int getArraySize() {
        return arraySize;
    }

    private boolean isOutsideHexagon(int q, int r) {
        return q + r < size || q + r > 3*size;
    }


}
