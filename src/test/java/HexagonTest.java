import com.maastricht.university.logic.Hexagon;
import com.maastricht.university.logic.IHexagon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class HexagonTest {

    @Test
    public void getTotalIndicesSize1() {
        IHexagon<Integer> hexagon = new Hexagon<>(1);
        Assertions.assertEquals(7 , getTotalIndices(hexagon));
    }

    @Test
    public void getTotalIndicesSize2() {
        IHexagon<Integer> hexagon = new Hexagon<>(2);
        Assertions.assertEquals(19, getTotalIndices(hexagon));
    }

    @Test
    public void getTotalIndicesSize3() {
        IHexagon<Integer> hexagon = new Hexagon<>(3);
        Assertions.assertEquals(37, getTotalIndices(hexagon));
    }

    @Test
    public void insertInsideHexagon() {
        IHexagon<Integer> hexagon = new Hexagon<>(3);
        hexagon.insert(2,2, 12);
        Assertions.assertEquals(12, hexagon.insert(2,2, 12));
    }

    @Test
    public void insertOutsideHexagon() {
        IHexagon<Integer> hexagon = new Hexagon<>(3);
        hexagon.insert(1,1, 12);
        Assertions.assertEquals(null, hexagon.insert(1,1, 12));
    }

    @Test
    public void cloneTest() {
        Random r = new Random();

        class Point{
            int a;
            int b;

            public Point(int a, int b) {
                this.a = a;
                this.b = b;
            }

            @Override
            public String toString() {
                return a + " " + b;
            }
        }

        IHexagon<Point> a = new Hexagon<>(3);

        for (int i = 0; i < 3*2+1; i++)
            for (int j = 0; j < 3*2+1; j++)
                a.insert(i,j, new Point(r.nextInt(5)+1, r.nextInt(5)+1));

        IHexagon<Point> b = a.clone();

        a.get(0,6).a = 20;
        Assertions.assertNotEquals(a.get(0,6).a, b.get(0,6).a);
    }

    public int getTotalIndices(IHexagon<Integer> hexagon) {
        int arraySize = hexagon.size()*2+1;
        int total = 0;

        for (int i = 0; i < arraySize; i++)
            for (int j = 0; j < arraySize; j++)
                hexagon.insert(i,j, 1);

        for (int i = 0; i < arraySize; i++)
            for (int j = 0; j < arraySize; j++)
                if (hexagon.get(i, j) != null)
                    total++;

        return total;
    }

}
