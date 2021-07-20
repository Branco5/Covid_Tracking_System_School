package Test;
import Servers.*;
import Lesson.Lesson;
import Room.*;
import User.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests general behaviour of application
 */
public class General {
    private SchoolServer server;
    private HealthOrgServer hs;

    @Before
    public void setUp() throws Exception {
        server = new SchoolServer();
        hs = new HealthOrgServer();
    }

    @Test
    public void general() throws Exception {

        server.addRoom("F1", "lab");
        server.addRoom("F2", "half");
        server.addRoom("F3", "full");
        assertEquals(3, server.getRooms().size());

        for(int i=1; i<4; i++){
            server.addUser(new Professor(i));
        }
        for(int i=4; i<=50;i++){
            server.addUser(new Student(i));
        }

        assertEquals(50, server.getUserCount());

        Room r;
        Lesson l;
        Professor prof = server.verifyUserProfessor(server.getUserByNumber(1));

        r = server.getRoomByName("F2");
        l = prof.startLesson(r);

        assertThrows(Exception.class,() -> l.addStudent(5, server, r.getProfessorPosition())); //espaço do professor

        //add students to lesson
        l.addStudent(5, server, r.getPosFromLabel(19));
        assertThrows(Exception.class,() -> l.addStudent(5, server, r.getPosFromLabel(15))); //estudante repetido

        l.addStudent(6, server, r.getPosFromLabel(15));
        l.addStudent(7, server, r.getPosFromLabel(10));
        l.addStudent(8, server, r.getPosFromLabel(16));
        l.addStudent(9, server, r.getPosFromLabel(17));
        l.addStudent(10, server, r.getPosFromLabel(18));
        assertEquals(1, server.getUserByNumber(10).getCodesEmitted().size());
        prof.endLesson(server);
        assertEquals(2, server.getUserByNumber(10).getCodesEmitted().size());

        User u = server.getUserByNumber(5);
        u.setState(State.INFECTED, hs);

        assertEquals(2, hs.getInfectedList().size()); //2 códigos gerados no princípio e fim da aula pelo utilizador infetado

        //print room layout
        System.out.println(r.toString());
        System.out.println();

        //Test distances
        assertEquals("Período de 15 dias de isolamento recomendado", prof.getHealthAdvice(hs));
        assertEquals("Período de 15 dias de isolamento recomendado",server.getUserByNumber(6).getHealthAdvice(hs));
        assertEquals("Medidas extraordinárias não necessárias", server.getUserByNumber(7).getHealthAdvice(hs));
        assertEquals("Vigiar sintomas", server.getUserByNumber(8).getHealthAdvice(hs));
        assertEquals("Vigiar sintomas", server.getUserByNumber(10).getHealthAdvice(hs));
        assertEquals("Medidas extraordinárias não necessárias", server.getUserByNumber(7).getHealthAdvice(hs));
        assertEquals("Medidas extraordinárias não necessárias", server.getUserByNumber(9).getHealthAdvice(hs));

        //Test state change
        prof.setState(State.ISOLATION, hs);
        assertEquals(State.ISOLATION, prof.getState());

        assertThrows(Exception.class,() -> prof.startLesson(r)); //professor em isolamento

        //Test statistics
        assertEquals("Número de infetados: 1\n" +
                "Percentagem: 2%\n" +
                "Número de utilizadores em isolamento: 1\n" +
                "Percentagem: 2%\n" +
                "Número de utilizadores em estado contínuo: 48\n" +
                "Percentagem: 96%\n\n" +
                "Barchart:\n" +
                "*-1 user, #-10 users\n" +
                "Número de infetados: *\n" +
                "Número de utilizadores em isolamento: *\n" +
                "Número de utilizadores em estado contínuo: ####********", server.getStats());

        // Test getDifferenceDays
        assertEquals(2, hs.getTodayInfected().size()); //2 códigos gerados pelo utilizador

        hs.printDates();
        hs.decrementDates(86400000); //Decrement by 1 day
        hs.printDates();

        assertEquals(0, hs.getTodayInfected().size());

    }
}
