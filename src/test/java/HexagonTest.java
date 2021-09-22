import com.maastricht.university.logic.Hexagon;
import com.maastricht.university.logic.IHexagon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        IHexagon<Integer> hexagon = new Hexagon<>(2);
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
        IHexagon<Integer> a = new Hexagon<Integer>(4);

        for (int i = 0; i < 7; i++)
            for (int j = 0; j < 7; j++)
                a.insert(i,j,1);

        IHexagon<Integer> b = a.clone();
        a.insert(2,2, 4);
        Assertions.assertNotEquals(a.get(2,2), b.get(2,2));
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
