package Room;

/**
 * Defines a 2 dimensional position
 */
public class Position{
    private int x;
    private int y;

    public Position(int y, int x){
        this.x=x;
        this.y=y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (!(obj instanceof Position))
            return false;

        Position pos = (Position) obj;

        return this.getY()== pos.getY() && this.getX()== pos.getX();
    }

    @Override
    public String toString() {
        return "Position{" +
                "y=" + y +
                ", x=" + x +
                '}';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}
