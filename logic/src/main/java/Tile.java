import java.util.LinkedList;
import java.util.List;

public class Tile {
    private int id;
    private int groupNumber;
    private int groupSize;

    private List<Tile> groupMembers = new LinkedList<Tile>();
    private List<Tile> neighbours = new LinkedList<Tile>();

    private static int count;

    public Tile(){
        this.id = count;
        count++;
        groupNumber = 0;
        groupSize = 0;
    }
}
