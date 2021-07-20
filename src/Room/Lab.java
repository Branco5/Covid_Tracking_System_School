package Room;

/**
 * Defines a particular room layout
 */
public class Lab extends Room{

    private final static int[][] layout = new int[][]{{1, 0, 0, 1},
                                                        {1, 0, 0, 1},
                                                        {1, 0, 0, 1},
                                                        {1, 0, 0, 1},
                                                        {1, 0, 0, 1},
                                                        {0, 0, 1, 0}};

    public Lab(String name){
        super(layout, 10, name);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int count = 0;

        s.append("\nSala: "+getName());

        for(int i = 0; i < layout.length; i++) {
            s.append("\n");
            for (int j = 0; j < layout[0].length; j++) {
                if (layout[i][j] == 0) {
                    s.append("# ");
                } else {
                    count++;
                    s.append(count+" ");
                }
            }
        }
        s.append("\n");
        return s.toString();
    }


}
