package Room;

/**
 * Defines a particular room layout
 */
public class Half extends Room {

    private final static int[][] layout = new int[][]{{1, 1, 0, 1, 1},
                                                        {1, 1, 0, 1, 1},
                                                        {1, 1, 0, 1, 1},
                                                        {1, 1, 0, 1, 1},
                                                        {1, 1, 0, 1, 1},
                                                        {0, 0, 0, 1, 0}};

    public Half(String name) {
        super(layout, 20, name);
    }


}
