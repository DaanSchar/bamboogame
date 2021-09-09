import java.util.LinkedList;
import java.util.List;

public class Tile {
    private int groupID;
    private static int groupIdCount = 0;
    private int groupSize;
    private int colour; //0 for no colour, 1 for red, 2 for blue


    private boolean legalForRed;
    private boolean legalForBlue;

    private List<Tile> groupMembers = new LinkedList<Tile>();
    private List<Tile> neighbours = new LinkedList<Tile>();

    public Tile(){
        groupID = 0;
        groupSize = 0;
        colour = 0;
        legalForBlue = true;
        legalForRed = true;
    }

    public void addNeighbour(Tile neighbour) {
        if (!neighbours.contains(neighbour)) {
            neighbours.add(neighbour);
        }
    }

    public int getGroupID() {return groupID;}

    public List<Tile> getNeighbours() {
        return neighbours;
    }

    public List<Tile> getGroupMembers() {
        return groupMembers;
    }

    public void setColour(int c) throws Exception {
        if(c==1 && isLegalForRed()) {
            colour = c;
        }
        else if(c==2 && isLegalForBlue()) {
            colour = c;
        }
        else {
            throw new Exception("illegal Colouring");
        }


        updateSurroundingTiles(c);

    }

    private void updateSurroundingTiles(int c){

        List<Tile> tilesFromDifferentGroups = getSurroundedTilesFromDifferentGroups(c);

        //check if groupID needs to be changed
        //if new group, make a new groupID
        if(tilesFromDifferentGroups.size()==0) {
            setGroupID(groupIdCount++);
        }
        //if only one group, use that groupId for this tile too
        if(tilesFromDifferentGroups.size()==1) {
            setGroupID(tilesFromDifferentGroups.get(0).getGroupID());
        }
        //if multiple groups, set the groupId of all groups and this tile to the same (already existing Id)
        boolean changeGroupIds = false;
        int newGroupId = 100+groupIdCount;
        if(tilesFromDifferentGroups.size()>1) {
            changeGroupIds = true;
            newGroupId = tilesFromDifferentGroups.get(0).getGroupID();
        }

        //calculate new groupsize
        int groupSizesFromAllNeighbours = 0;
        for(int i = 0; i< tilesFromDifferentGroups.size(); i++) {
            groupSizesFromAllNeighbours += tilesFromDifferentGroups.get(i).getGroupSize();
        }
        groupSizesFromAllNeighbours++;

        //get tiles from all different connected groups
        setGroupSize(groupSizesFromAllNeighbours);
        for(int i=0; i< tilesFromDifferentGroups.size(); i++) {
            //for all groupmembers of those (so all members of current group)
            //set new groupSize
            tilesFromDifferentGroups.get(i).setGroupSize(groupSizesFromAllNeighbours);
            //set new groupID
            if(changeGroupIds) {
                tilesFromDifferentGroups.get(i).setGroupID(newGroupId);
            }
            List<Tile> tempGroupMembers = tilesFromDifferentGroups.get(i).getGroupMembers();

            for(int j=0; j< tempGroupMembers.size(); j++) {
                //set the new groupSize
                tempGroupMembers.get(j).setGroupSize(groupSizesFromAllNeighbours);
                //set new groupID
                if(changeGroupIds) {
                    tempGroupMembers.get(i).setGroupID(newGroupId);
                }
            }
        }

        //TODO: add the new neighbours
        //make sure that the same object gets send to all connected tiles, so updated automatically if one changes
        //add tile itself to groupmemberList

        //TODO: update the groupMembers List


    }

    public List<Tile> getSurroundedTilesFromDifferentGroups(int c) {
        List<Tile> neighbours = getNeighbours();
        List<Tile> tilesFromDifferentGroups = new LinkedList<Tile>();
        for(int i=0; i<neighbours.size(); i++) {
            Tile tempTile = neighbours.get(i);
            //if they are coloured blue
            if(tempTile.getColour()==c) {

                //check if one of that group is already added to the list
                //only add if that is not the case (don't want duplicated groups)
                boolean duplicate = false;
                for(int j=0; j<tilesFromDifferentGroups.size(); j++) {
                    if (tempTile.getGroupID() == tilesFromDifferentGroups.get(j).getGroupID()) {
                        duplicate = true;
                    }
                }
                if(!duplicate) {
                    tilesFromDifferentGroups.add(tempTile);
                }
            }
        }
        return tilesFromDifferentGroups;
    }

    public List<Tile> getSurroundedTilesFromGroups(int c) {
        List<Tile> neighbours = getNeighbours();
        List<Tile> tilesConnectedAndColoured = new LinkedList<Tile>();
        for(int i=0; i<neighbours.size(); i++) {
            Tile tempTile = neighbours.get(i);
            //if they are coloured blue, add it too the list
            if(tempTile.getColour()==c) {
                tilesConnectedAndColoured.add(tempTile);
            }
        }
        return tilesConnectedAndColoured;
    }


    public void setGroupSize(int number) { groupSize = number; }
    public int getGroupSize() { return groupSize; }
    public int getColour() {return colour; }
    public void setGroupID(int id) { groupID = id; }
    public boolean isLegalForRed()
    {
        //TODO: compute if legal
        return legalForRed;
    }
    public boolean isLegalForBlue()
    {
        //TODO: compute if legal
        return legalForBlue;
    }
}
