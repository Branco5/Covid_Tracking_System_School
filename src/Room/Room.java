package Room;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines attributes and behavior common to all Room instances
 */
public abstract class Room {

    private String name;
    private final int[][] layout;
    private Map<Integer, Position> labels;
    private int capacity;

    public Room(int[][] layout, int capacity, String name) {
        this.layout = layout;
        this.capacity = capacity;
        this.name = name;
        labels = new HashMap<>();
        setPositionLabels();
    }

    /**
     * Returns position based on numeric label
     * @param label
     * @return
     * @throws Exception
     */
    public Position getPosFromLabel(int label) throws Exception {
        for (int i: labels.keySet()) {
            if(label == i){
                return labels.get(i);
            }
        }

        throw new Exception("Position label not valid");
    }

    /**
     * Defines position labels based on room layout
     */
    public void setPositionLabels(){
        int count = 0;
        for(int i = 0; i < layout.length-1; i++){
            for(int j = 0; j < layout[0].length; j++){
                if(layout[i][j] == 1){
                    count++;
                    labels.put(count,new Position(i, j));
                }
            }
        }
    }

    /**
     * Prints room name and capacity
     */
    public void printNameCap(){
        System.out.println(name + " - Student Capacity: " + capacity);
    }

    /**
     * Returns layout of classroom as String
     * @return
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int count = 0;
        int lastColIdx = layout.length - 1;

        s.append("\nSala: " + getName());

        for (int i = 0; i < layout.length - 1; i++) {
            s.append("\n");
            for (int j = 0; j < layout[0].length; j++) {
                if (layout[i][j] == 0) {
                    s.append("# ");
                } else {
                    count++;
                    if (count >= 10) {
                        s.append(count + " ");
                    } else {
                        s.append(count + "  ");
                    }
                }
            }
        }

        s.append("\n");

        for (int i = 0; i < layout[0].length; i++) {
            if (layout[lastColIdx][i] == 0 && layout[lastColIdx-1][i] == 0) {
                s.append("# ");
            }
            else if (layout[lastColIdx][i] == 0) {
                s.append("#  ");
            }
            else {
                count++;
                s.append(count + " ");
            }
        }
        s.append("\n");
        return s.toString();
    }

    /**
     * Prints layout of room
     */
    public void show(){
        System.out.println(toString());
    }

    public Position getProfessorPosition(){
        int y = layout.length-1;

        for(int i = 0; i<layout[0].length; i++){
            if(layout[y][i]==1){
                return new Position(y,i);
            }
        }
        return null;
    }

    /**
     * Returns name of room
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if position is valid for this layout
     * @param p
     * @throws Exception
     */
    public void checkPosition(Position p) throws Exception {
        int y1 = p.getY();
        int x1 = p.getX();

        if(layout[y1][x1]!=1){
            throw new Exception("Position invalid for this layout");
        }
    }

    /**
     * Returns int value representative of a distance based on relative positioning between 2 positions
     * @param p1
     * @param p2
     * @return
     * @throws Exception
     */
    public int comparePositions(Position p1, Position p2) throws Exception {
        int y1 = p1.getY();
        int x1 = p1.getX();
        int y2 = p2.getY();
        int x2 = p2.getX();

        checkPosition(p1);
        checkPosition(p2);

        if((y1==y2 && (x1==x2-1 || x1==x2+1)) || (x1==x2 && (y1==y2-1 || y1==y2+1))){
            return 2;
        }
        else if((y1==y2 && (x1==x2-2 || x1==x2+2)) || (x1==x2 && (y1==y2-2 || y1==y2+2)) ||
                    ((y1==y2+1 || y1==y2-1) && (x1==x2-1 || x1==x2+1))){
            return 4;
        }
        else{return 6;}

    }

    /**
     * Returns room layout
     * @return
     */
    public int[][] getLayout(){
        return layout;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (!(obj instanceof Room))
            return false;

        Room room = (Room) obj;

        return this.name.equals(room.getName());
    }
}
