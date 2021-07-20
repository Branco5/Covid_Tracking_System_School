package User;

import Servers.SchoolServer;
import Lesson.Lesson;
import Room.Room;

/**
 * Defines a user instantiation for a professor
 */
public class Professor extends User {
    private Lesson openLesson;

    public Professor(int number) {
        super(number);
        openLesson = null;
    }

    /**
     * Starts lesson and returns it
     * @param room
     * @return
     * @throws Exception
     */
    public Lesson startLesson(Room room) throws Exception {
        if(this.openLesson!=null) throw new Exception("Ainda tem outra aula aberta");

        Lesson l = new Lesson(this, room);
        openLesson = l;
        return l;
    }

    /**
     * Generates and exchanges ID's of users in lesson and sets openLesson to null
     * @param server
     * @throws Exception
     */
    public void endLesson(SchoolServer server) throws Exception {
        if(openLesson==null){throw new Exception("Nenhuma aula iniciada");}
        openLesson.exchangeIDs();
        server.addLesson(openLesson);
        openLesson=null;
    }
}
