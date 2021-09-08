import java.util.LinkedList;
import java.util.List;

public class Tile {
    private int groupNumber;
    private int groupSize;
    private int colour; //0 for no colour, 1 for red, 2 for blue

    private boolean legalForRed;
    private boolean legalForBlue;

    private List<Tile> groupMembers = new LinkedList<Tile>();
    private List<Tile> neighbours = new LinkedList<Tile>();

    public Tile(){
        groupNumber = 0;
        groupSize = 0;
        colour = 0;
        legalForBlue = true;
        legalForRed = true;
    }

    public void addNeighbour(Tile neighbour) {
        if(!neighbours.contains(neighbour)) {
            neighbours.add(neighbour);
        }
    }

    public List<Tile> getNeighbours() {
        return neighbours;
    }

    public List<Tile> getGroupMembers() {
        return groupMembers;
    }

    public void setColour(int c) throws Exception {
        if(c==1 || c==2) {
            colour = c;
        }
        else {
            throw new Exception("illegal Colour");
        }
    }
    public int getColour()
    {
        return colour;
    }
    public boolean isLegalForRed()
    {
        return legalForRed;
    }
    public boolean isLegalForBlue()
    {
        return legalForBlue;
    }
}
