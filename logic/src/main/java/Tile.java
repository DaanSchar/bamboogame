import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Tile {
    private Board board;
    private int groupID;
    private static int groupIdCount = 0;
    private int groupSize;
    private int colour; //0 for no colour, 1 for red, 2 for blue

    private boolean legalForRed;
    private boolean legalForBlue;

    private List<Tile> groupMembers = new LinkedList<Tile>();
    private List<Tile> neighbours = new LinkedList<Tile>();

    public Tile(Board b) {
        board = b;
        groupID = 0;
        groupSize = 0;
        colour = 0;
        legalForBlue = true;
        legalForRed = true;
    }

    public void setGroupSize(int number) {
        groupSize = number;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public int getColour() {
        return colour;
    }

    public void setGroupID(int id) {
        groupID = id;
    }

    public void addNeighbour(Tile neighbour) {
        if (!neighbours.contains(neighbour)) {
            neighbours.add(neighbour);
        }
    }

    public int getGroupID() {
        return groupID;
    }

    public List<Tile> getNeighbours() {
        return new LinkedList<Tile>(neighbours);
    }

    public List<Tile> getGroupMembers() {
        return new LinkedList<Tile>(groupMembers);
    }

    public List<Tile> getGroupMembersWithoutCopy() {
        return groupMembers;
    }

    public void setColour(int c) throws Exception {
        if (c == 1 && isLegalForRed()) {
            colour = c;
        } else if (c == 2 && isLegalForBlue()) {
            colour = c;
        } else {
            throw new Exception("illegal Colouring");
        }

        updateSurroundingTilesAndBoard(c);

    }

    private Collection<? extends Tile> updateSurroundingTilesAndBoard(int c) {
        List<Tile> tilesFromDifferentGroups = getSurroundedTilesFromDifferentGroups(c);     // not sure how to resolve this method call without reordering the methods

        //check if groupID needs to be changed. if new group, make a new groupID
        if (tilesFromDifferentGroups.size() == 0) {
            setGroupID(groupIdCount++);
        }

        //if only one group, use that groupId for this tile too
        if (tilesFromDifferentGroups.size() == 1) {
            setGroupID(tilesFromDifferentGroups.get(0).getGroupID());
        }
        //if multiple groups, set the groupId of all groups and this tile to the same (already existing Id)
        boolean changeGroupIds = false;
        int newGroupId = 100 + groupIdCount;
        if (tilesFromDifferentGroups.size() > 1) {
            changeGroupIds = true;
            newGroupId = tilesFromDifferentGroups.get(0).getGroupID();
        }

        //calculate new groupsize
        int groupSizesFromAllNeighbours = 0;
        for (int i = 0; i < tilesFromDifferentGroups.size(); i++) {
            groupSizesFromAllNeighbours += tilesFromDifferentGroups.get(i).getGroupSize();
        }
        groupSizesFromAllNeighbours++;

        //get tiles from all different connected groups
        setGroupSize(groupSizesFromAllNeighbours);
        for (int i = 0; i < tilesFromDifferentGroups.size(); i++) {
            //for all groupmembers of those (so all members of current group)
            //set new groupSize
            tilesFromDifferentGroups.get(i).setGroupSize(groupSizesFromAllNeighbours);
            //set new groupID
            if (changeGroupIds) {
                tilesFromDifferentGroups.get(i).setGroupID(newGroupId);
            }
            List<Tile> tempGroupMembers = tilesFromDifferentGroups.get(i).getGroupMembers();

            for (int j = 0; j < tempGroupMembers.size(); j++) {
                //set the new groupSize
                tempGroupMembers.get(j).setGroupSize(groupSizesFromAllNeighbours);
                //set new groupID
                if (changeGroupIds) {
                    tempGroupMembers.get(i).setGroupID(newGroupId);
                }
            }

            //TODO: update the groupMembers List (doesn't work yet right now)
            //make sure that the same object gets send to all connected tiles, so updated automatically if one changes
            //add tile itself to groupmemberList

            // add the Tile to a new list and merging object list with the tile. Unsure if this is the best approach.
            List<Tile> t = getGroupMembers();   // create a new list to add the Tile and merging objects. List name "t" may not be the wisest. Need to revise.
            var b = t.addAll(i, updateSurroundingTilesAndBoard(c));     // using the addAll method to append both the tile and the previous neighbor list
            if (b) {    //if objects were added the list create above, the list will execute the merging
                //t.updateSurroundingTilesAndBoard(c);      // commented out because unsure if this is correct or not
                t.addAll(tempGroupMembers);         // execute the merging
            }

            //merging list
            //https://stackoverflow.com/questions/45281454/combine-multiple-lists-in-java
            //https://stackoverflow.com/questions/21805999/how-to-append-a-list-to-another-list-in-java

            //refer to addAll()
            //https://docs.oracle.com/en/java/javase/16/docs/api/java.base/java/util/List.html
            //adding elements to a list
            //https://www.geeksforgeeks.org/list-add-method-in-java-with-examples/
            //https://stackoverflow.com/questions/21805999/how-to-append-a-list-to-another-list-in-java

        }
        if(c==1) {
            board.setNumberOfGroupsRed(board.getNumberOfGroupsRed()+1);
        }
        else if(c==2) {
            board.setNumberOfGroupsBlue(board.getNumberOfGroupsBlue()+1);
        }

        //TODO: implement return statement or remove it
        return null;
    }

    public List<Tile> getSurroundedTilesFromDifferentGroups (int c){
        List<Tile> neighbours = getNeighbours();
        List<Tile> tilesFromDifferentGroups = new LinkedList<Tile>();
        for (int i = 0; i < neighbours.size(); i++) {
            Tile tempTile = neighbours.get(i);
            //if they are coloured blue
            if (tempTile.getColour() == c) {
                //check if one of that group is already added to the list
                //only add if that is not the case (don't want duplicated groups)
                boolean duplicate = false;
                for (int j = 0; j < tilesFromDifferentGroups.size(); j++) {
                    if (tempTile.getGroupID() == tilesFromDifferentGroups.get(j).getGroupID()) {
                        duplicate = true;
                    }
                }
                if (!duplicate) {
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

    public boolean isLegalForRed() {
        //TODO: compute if legal
        int numberOfGroups = board.getNumberOfGroupsRed();

        //if the tile is already coloured, return false
        if(getColour()!=0)
        {
            return false;
        }

        //make list of all groups connected to tile
        List<Tile> tilesFromDifferentGroups = getSurroundedTilesFromDifferentGroups(1);
        List<Tile> tilesConnectedAndColoured = getSurroundedTilesFromGroups(1);

        //if no groups connected to that tile, return true
        if(tilesFromDifferentGroups.size()==0) {
            return true;
        }

        //if the the new groupsize (if coloured) will be too big, it's not possible
        int groupSizesFromAllNeighbours = 0;
        for(int i=0; i< tilesFromDifferentGroups.size(); i++) {
            groupSizesFromAllNeighbours += tilesFromDifferentGroups.get(i).getGroupSize();
        }
        if(groupSizesFromAllNeighbours+1 > numberOfGroups + 1 - (tilesFromDifferentGroups.size()))
        {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isLegalForBlue() {
        int numberOfGroups = board.getNumberOfGroupsBlue();

        //if the tile is already coloured, return false
        if(getColour()!=0)
        {
            return false;
        }

        //make list of all groups connected to tile
        List<Tile> tilesFromDifferentGroups = getSurroundedTilesFromDifferentGroups(1);
        List<Tile> tilesConnectedAndColoured = getSurroundedTilesFromGroups(1);

        //if no groups connected to that tile, return true
        if(tilesFromDifferentGroups.size()==0) {
            return true;
        }

        //if the the new groupsize (if coloured) will be too big, it's not possible
        int groupSizesFromAllNeighbours = 0;
        for(int i=0; i< tilesFromDifferentGroups.size(); i++) {
            groupSizesFromAllNeighbours += tilesFromDifferentGroups.get(i).getGroupSize();
        }
        if(groupSizesFromAllNeighbours+1 > numberOfGroups + 1 - (tilesFromDifferentGroups.size()))
        {
            return false;
        }
        else {
            return true;
        }
    }
}