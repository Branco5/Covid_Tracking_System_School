package Lesson;

import Servers.SchoolServer;
import User.*;
import Room.*;
import java.util.*;

/**
 * Saves information of a lesson
 */
public class Lesson {
    private Map<User, Position> users; //Map with the users and their position in the classroom
    private Professor professor;
    private Room room;

    public Lesson(Professor prof, Room room) throws Exception {
        this.users = new HashMap<>();
        this.room = room;
        if(prof.isIsolated()){
            throw new Exception("User must be in isolation");
        }
        this.professor = prof;
        professor.generateID(room.getProfessorPosition());
        users.put(professor, room.getProfessorPosition());
    }

    /**
     * Returns the classroom of this lesson
     * @return
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Adds a student to the lesson, generating a new ID and exchanging ID's with previously registered students
     * @param studentNumber
     * @param server
     * @param position
     * @throws Exception
     */
    public void addStudent(int studentNumber, SchoolServer server, Position position) throws Exception {
        verifyPlacement(position);

        Student std = server.verifyUserStudent(server.getUserByNumber(studentNumber));

        if(std.isIsolated()){
            throw new Exception("Student must be in isolation");
        }

        if(users.containsKey(server.getUserByNumber(studentNumber))){
            throw new Exception("Estudante já registado nesta aula");
        }

        std.generateID(position);
        users.put(std, position);
        std.receiveIDs(users.keySet(), room);

        for (User s: users.keySet()) {
            s.receiveID(std, room);
        }
    }

    /**
     * Verifies if student placement is valid
     * @param position
     * @throws Exception
     */
    public void verifyPlacement(Position position) throws Exception {
        int y = position.getY();
        int x = position.getX();

        if(room.getLayout()[y][x]==0){
            throw new Exception("Position not valid for this room");
        }

        if(users.containsValue(position)){
            throw new Exception("Position already occupied");
        }
    }

    /**
     * Generates a new ID for each student in the lesson and exchanges it with ever other student
     * @throws Exception
     */
    public void exchangeIDs() throws Exception {
        for (User s: users.keySet()) {
            s.generateID(users.get(s));
        }

        for (User s: users.keySet()) {
            s.receiveIDs(users.keySet(), room);
        }
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "students=" + users +
                ", professor=" + professor +
                ", room=" + room +
                '}';
    }
}
