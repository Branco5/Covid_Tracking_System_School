package Room;

/**
 * Defines a particular room layout
 */
public class Full extends Room {

    private final static int[][] layout = new int[][]{{1, 1, 1, 1, 1},
                                                        {1, 1, 1, 1, 1},
                                                        {1, 1, 1, 1, 1},
                                                        {1, 1, 1, 1, 1},
                                                        {1, 1, 1, 1, 1},
                                                        {0, 0, 0, 0, 1}};

    public Full(String name){
        super(layout, 25, name);
    }



}
