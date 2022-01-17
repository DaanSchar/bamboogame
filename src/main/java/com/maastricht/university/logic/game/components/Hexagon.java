package com.maastricht.university.logic.game.components;

import com.maastricht.university.logic.game.game.Move;
import com.maastricht.university.logic.game.util.interfaces.IHexagon;
import com.rits.cloning.Cloner;

import java.util.*;

public class Hexagon<T> implements IHexagon<T> {

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
     * in this structure we use the Axial Coordinates, but instead of having negative coordinates and the center
     * of the hexagon being 0,0 the non-existing bottom left index is 0,0. this might be easier for looping.
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
        Cloner cloner=new Cloner();
        IHexagon<T> copy = new Hexagon<T>(size);

        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                T clone = cloner.deepClone(this.get(i, j));
                copy.insert(i, j, clone);
            }
        }

        return copy;
    }


    @Override
    public boolean contains(T entity) {
        for (T otherEntity: this)
            if (otherEntity.equals(entity))
                return true;

        return false;
    }

    @Override
    public int size() {
        return arraySize;
    }

    @Override
    public List<T> getNeighbours(int q, int r) {
        ArrayList<T> neighbors = new ArrayList<>();

        neighbors.add(get(q, r - 1));
        neighbors.add(get(q + 1, r - 1));
        neighbors.add(get(q + 1, r));
        neighbors.add(get(q, r + 1));
        neighbors.add(get(q - 1, r + 1));
        neighbors.add(get(q - 1, r));

        while(neighbors.contains(null))
            neighbors.remove(null);

        return neighbors;
    }

    @Override
    public List<T> vector() {
        ArrayList<T> vector = new ArrayList<>();

        for (T entity: this)
            vector.add(entity);

        return vector;
    }

    public List<Move> moveVector() {
        ArrayList<Move> vector = new ArrayList<>();

        for (int i = 0; i < arraySize; i++)
            for (int j = 0; j < arraySize; j++)
                if (get(i, j) != null)
                    vector.add(new Move(i, j));

        return vector;
    }

    private boolean isOutsideHexagon(int q, int r) {
        try {
            T x = array[q][r];
        } catch (ArrayIndexOutOfBoundsException e) {
            return true;
        }

        return q + r < size || q + r > 3*size;
    }

    @Override
    public String toString() {
        String str = "";

        for (T[] row : array) {
            str += "\n";
            for (T element : row)
                if ( element == null)
                    str += "[ ] ";
                else
                    str += "[" + element + "] ";
        }
        return str;
    }

    @Override
    public HexagonIterator<T> iterator() {
        return new HexagonIterator<>();
    }

    class HexagonIterator<T> implements Iterator<T> {

        private int count = 0;
        private Stack<T> stack = new Stack<>();
        private int hexagonSize;

        HexagonIterator() {
            fillStack();
            this.hexagonSize = stack.size();
        }

        @Override
        public boolean hasNext() {
            return hexagonSize >= count + 1;
        }

        @Override
        public T next() {
            count++;
            return stack.pop();
        }

        private void fillStack() {
            for (int i = 0; i < arraySize; i++) {
                for (int j = 0; j < arraySize; j++) {
                    if (get(i, j) != null)
                        stack.push((T) get(i, j));
                }
            }
        }
    }

}
