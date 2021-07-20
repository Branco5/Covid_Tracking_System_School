package Code;

import Room.Position;

import java.util.Random;

/**
 * Responsible for generating random codes
 */
public class CodeGenerator {
    private Random random;
    private final int length;

    public CodeGenerator(){
        random = new Random();
        length=30;
    }

    /**
     * Returns a Code object with random id and given position
     * @param p
     * @return
     */
    public Code generateCode(Position p){
        Code code = new Code((new String(random.ints(length, 48, 126).toArray(), 0, length)),p);
        return code;
    }
}
