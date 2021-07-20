package Code;

import Room.Position;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Defines a code object to be associated with a user
 */
public class Code {
    private Date date;
    private String id;
    private Position position;

    public Code(String code, Position p){
        date = new Date();
        this.id=code+" - "+ getDateString();
        this.position = p;
    }

    /**
     * Returns formatted date
     * @return
     */
    public String getDateString(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.format(date);
    }

    /**
     * Gets position associated with id
     * @return
     */
    public Position getPosition(){
        return position;
    }


    @Override
    public String toString() {
        return "Code{" +
                "'" + id + '\'' +
                '}';
    }

    /**
     * Returns date associated with this code
     * @return
     */
    public Date getDate(){
        return date;
    }

    /**
     * Returns id of code
     * @return
     */
    public String getID(){
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (!(obj instanceof Code))
            return false;

        Code code = (Code) obj;

        return this.id.equals(code.getID()) && this.getDate()==code.getDate();
    }

    /**
     * Decrements date value by millisecond in parameter
     * For testing only
     * @param millis
     */
    public void decrementDate(long millis){
        date.setTime(date.getTime()-millis);
    }
}
