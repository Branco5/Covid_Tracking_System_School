package Servers;

import Lesson.Lesson;
import Room.*;
import User.*;

import java.util.*;

/**
 * Defines collections and functions to store and manipulate school data
 */
public class SchoolServer {

    private Set<Room> rooms;
    private Set<User> users;
    private List<Lesson> lessons;

    public SchoolServer(){
        users = new HashSet<>();
        lessons = new ArrayList<>();
        rooms = new HashSet<>();
    }

    /**
     * Returns user with given number
     * @param number
     * @return
     * @throws Exception
     */
    public User getUserByNumber(int number) throws Exception {
        for (User u : users) {
            if(u.getNumber() == number){
                return u;
            }
        }
        throw new Exception("User doesn't exist");
    }

    /**
     * Returns count of registered users
     * @return
     */
    public int getUserCount(){
        return this.users.size();
    }

    /**
     * Prints room classroom names and capacity
     */
    public void printRoomNames() {
        for (Room r: rooms) {
            r.printNameCap();
        }
    }

    /**
     * Returns set of room available
     * @return
     */
    public Set<Room> getRooms(){
        return rooms;
    }

    /**
     * Adds new classroom with given name and type
     * @param name
     * @param type
     * @throws Exception
     */
    public void addRoom(String name, String type) throws Exception {
        if(getRoomByName(name) != null) throw new Exception("Room already registered");
        Room room;
        switch (type){
            case "full" : room = new Full(name);
            break;
            case "half" : room = new Half(name);
            break;
            case "lab" : room = new Lab(name);
            break;
            default:
                throw new Exception("Invalid room type");
        }
        rooms.add(room);
    }

    /**
     * Removes classroom by name
     * @param roomName
     * @throws Exception
     */
    public void removeRoom(String roomName) throws Exception {
        if(getRoomByName(roomName) == null) throw new Exception("No class room with that name exists");
        rooms.remove(getRoomByName(roomName));
    }

    /**
     * Shows name and layout of each classroom
     */
    public void showRooms(){
        rooms.forEach(r-> r.show());
    }

    /**
     * Returns room with given name
     * @param name
     * @return
     */
    public Room getRoomByName(String name) throws Exception {
        for (Room r: rooms) {
            if(name.equalsIgnoreCase(r.getName())){
                return r;
            }
        }
        return null;
    }

    /**
     * Adds lesson to list of lessons
     * @param l
     */
    public void addLesson(Lesson l){
        lessons.add(l);
    }

    /**
     * Verifies if user with given number exists in user collection
     * @param number
     * @return
     * @throws Exception
     */
    public boolean containsUserNumber(int number) throws Exception {
        if(getUserByNumber(number)!=null){
            return true;
        }
        return false;
    }

    /**
     * Returns professor object if user is professor
     * @param u
     * @return
     * @throws Exception if user not professor or not in collection
     */
    public Professor verifyUserProfessor(User u) throws Exception {
        if(!containsUserNumber(u.getNumber())) throw new Exception("User not registered");
        try{
            return (Professor) u;
        }
        catch(ClassCastException e){
            throw new ClassCastException("User not professor");
        }
    }

    /**
     * Returns student object if user is student
     * @param u
     * @return
     * @throws Exception if user not student or not in collection
     */
    public Student verifyUserStudent(User u) throws Exception {
        if(!containsUserNumber(u.getNumber())) throw new Exception("User not registered");
        try{
            return (Student) u;
        }
        catch(ClassCastException e){
            throw new ClassCastException("User not a student");
        }
    }

    /**
     * Adds user to user collection
     * @param u
     * @throws Exception
     */
    public void addUser(User u) throws Exception {
        if(users.contains(u)){
            throw new Exception("User already registered in this server");
        }
        this.users.add(u);
    }

    /**
     * Returns String with general stats
     * @return
     */
    public String getStats(){
        StringBuilder sb = new StringBuilder();
        double infected = getNrUsersByState(State.INFECTED);
        double isolated = getNrUsersByState(State.ISOLATION);
        double cont = getNrUsersByState(State.CONTINUOUS);

        sb.append("Número de infetados: " + (int)infected  +
                "\nPercentagem: " + Math.round(infected*100/users.size())+"%");
        sb.append("\nNúmero de utilizadores em isolamento: " + (int)isolated +
                "\nPercentagem: " + Math.round(isolated*100/users.size())+"%");
        sb.append("\nNúmero de utilizadores em estado contínuo: " + (int)cont +
                "\nPercentagem: " + Math.round(cont*100/users.size())+"%");
        sb.append("\n\nBarchart:\n*-1 user, #-10 users" +
                        "\nNúmero de infetados: "+getAsts(infected)+
                        "\nNúmero de utilizadores em isolamento: "+getAsts(isolated)+
                        "\nNúmero de utilizadores em estado contínuo: "+getAsts(cont));

        return sb.toString();
    }

    /**
     * Returns String of symbols based on number in parameter
     * @param number
     * @return
     */
    public String getAsts(double number){
        StringBuilder s = new StringBuilder();
        while(number-10>=0){
            number-=10;
            s.append('#');
        }
        while(number-1>=0){
            number-=1;
            s.append('*');
        }
        return s.toString();
    }

    /**
     * Prints statistics
     */
    public void showStats(){
        System.out.println(getStats());
    }

    /**
     * Gets number of students in given state
     * @param state
     * @return
     */
    public int getNrUsersByState(State state){
        int count=0;
        for (User u : users) {
            if(u.getState()==state){
                count++;
            }
        }
        return count;
    }

    /**
     * Prints users and their state
     */
    public void showUsers() {
        System.out.println("Users: ");
        for (User u: users) {
            System.out.println("Student: "+u.getNumber()+" | State: " +u.getState());
        }
    }

    /**
     * Removes user with given ID
     * @param id
     * @throws Exception
     */
    public void removeUserByID(int id) throws Exception {
        for (User u : users) {
            if(u.getNumber()==id){
                users.remove(u);
                return;
            }
        }
        throw new Exception("User not found");
    }
}
