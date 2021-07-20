import Servers.*;
import Lesson.Lesson;
import Room.Room;
import Views.View;
import User.*;

public class AppStart {
    public static void main(String[] args) throws Exception {

        SchoolServer server = new SchoolServer();
        HealthOrgServer hs = new HealthOrgServer();

        new View(server, hs);
    }
}

/*For testing

        server.addRoom("F1", "lab");
        server.addRoom("F2", "half");
        server.addRoom("F3", "full");

        for(int i =1; i<4; i++){
            server.addUser(new Professor(i));
        }
        for(int i=4; i<=50;i++){
            server.addUser(new Student(i));
        }

        Room r;
        Lesson l;
        Professor prof = server.verifyUserProfessor(server.getUserByNumber(1));

        r = server.getRoomByName("F2");
        l = prof.startLesson(r);

        l.addStudent(5, server, r.getPosFromLabel(19));
        l.addStudent(6, server, r.getPosFromLabel(18));
        l.addStudent(7, server, r.getPosFromLabel(10));
        l.addStudent(8, server, r.getPosFromLabel(16));
        prof.endLesson(server);

        prof.setState(State.INFECTED, hs);
        server.getUserByNumber(5).setState(State.INFECTED, hs);
*/
