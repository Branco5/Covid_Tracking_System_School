package Views;

import Servers.*;
import Lesson.Lesson;
import Room.*;
import User.*;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Defines behavior of user interface
 */
public class View {

    private final Scanner reader;

    public View(SchoolServer server, HealthOrgServer hs) throws Exception {
        reader = new Scanner(System.in);
        mainMenu(server, hs);
    }

    /**
     * Defines main interface
     * @param server
     * @param hs
     */
    public void mainMenu(SchoolServer server, HealthOrgServer hs){

        boolean running = true;

        while (running) {

            System.out.println("***************************************************");
            System.out.println("*                                                 *");
            System.out.println("* Sistema de Rastreio de Contatos em Sala de Aula *");
            System.out.println("*                                                 *");
            System.out.println("*                   Bem Vindo!                    *");
            System.out.println("*                                                 *");
            System.out.println("* Insere a sua opção:                             *");
            System.out.println("* 1 - Registo de Presenças                        *");
            System.out.println("* 2 - Area de Utilizador                          *");
            System.out.println("* 3 - Area da Autoridade de Saúde                 *");
            System.out.println("* 4 - Area de Administrador                       *");
            System.out.println("* 5 - Sair                                        *");
            System.out.println("*                                                 *");
            System.out.println("***************************************************");
            System.out.print("\nOpção> ");

            try {
                int input = reader.nextInt();
                switch (input) {
                    case 1:
                        System.out.print("Número de Utilizador> ");
                        input = reader.nextInt();
                        server.verifyUserProfessor(server.getUserByNumber(input));
                        professorMenu(server, (Professor) server.getUserByNumber(input));
                        break;
                    case 2:
                        System.out.print("Número de Utilizador> ");
                        input = reader.nextInt();

                        if (server.containsUserNumber(input)) {
                            User user = server.getUserByNumber(input);
                            userMenu(user, hs);
                            break;
                        }
                    case 3:
                        HealthOrgMenu(server, hs);
                        break;

                    case 4:
                        AdminMenu(server);
                        break;
                    case 5:
                        running = false;

                    default:
                        System.out.println("Invalid input");

                }

            } catch (InputMismatchException e) {
                System.out.println("Input must be of type int");
                reader.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                reader.nextLine();
            }
        }
    }

    /**
     * Defines user menu
     * @param user
     * @param hs
     * @throws Exception
     */
    public void userMenu(User user, HealthOrgServer hs) throws Exception {

        boolean running = true;

        while (running) {
            System.out.println("***************************************************");
            System.out.println("*                                                 *");
            System.out.println("* Sistema de Rastreio de Contatos em Sala de Aula *");
            System.out.println("*                                                 *");
            System.out.println("*               Utilizador " + user.getNumber() + "                      *");
            System.out.println("*      Estado: " + user.getState() + " desde " + user.getStateChangeTimeString() + "          *");
            System.out.println("*                                                 *");
            System.out.println("* Insere a sua opção:                             *");
            System.out.println("* 1 - Verificar indicações da entidade de saúde   *");
            System.out.println("* 2 - Declarar-se como infetado                   *");
            System.out.println("* 3 - Entrar em isolamento profiliático           *");
            System.out.println("* 4 - Terminar isolamento                         *");
            System.out.println("* 5 - Ver estatísticas diárias                    *");
            System.out.println("* 6 - Sair                                        *");
            System.out.println("*                                                 *");
            System.out.println("***************************************************");
            System.out.print("\nOpção> ");

            try {
                int input = reader.nextInt();

                switch (input) {
                    case 1:
                        System.out.println(user.getHealthAdvice(hs));
                        break;
                    case 2:
                        user.setState(State.INFECTED, hs);
                        break;
                    case 3:
                        user.enterIsolation(hs);
                        break;
                    case 4:
                        user.verifyIsolation();
                        break;
                    case 5:
                        System.out.println("Number of codes of users infected today: " + hs.getTodayInfected().size());
                        System.out.print("Codes of users infected today: \n"); hs.printTodayInfected();
                        break;

                    case 6:
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid input");

                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be of type int");
                reader.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                reader.nextLine();
            }
        }
    }

    /**
     * Menu of the health organization
     * @param server
     * @param hs
     * @throws Exception
     */
    public void HealthOrgMenu(SchoolServer server, HealthOrgServer hs) throws Exception {

        boolean running = true;

        while(running){
            System.out.println("***************************************************");
            System.out.println("*                                                 *");
            System.out.println("* Sistema de Rastreio de Contatos em Sala de Aula *");
            System.out.println("*                                                 *");
            System.out.println("*          Area de Organização de Saúde           *");
            System.out.println("*                                                 *");
            System.out.println("* Insere a sua opção:                             *");
            System.out.println("* 1 - Enviar lista de infetados hoje              *");
            System.out.println("* 2 - Ver estatísticas                            *");
            System.out.println("* 3 - Sair                                        *");
            System.out.println("*                                                 *");
            System.out.println("***************************************************");
            System.out.print("\nOpção> ");

            try{
                int input = reader.nextInt();

                switch (input) {
                    case 1:
                        hs.printTodayInfected();
                        break;

                    case 2 :
                        server.showStats();
                        break;

                    case 3 :
                        running = false;
                        break;

                    default :
                        System.out.println("Invalid input");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be of type int");
                reader.nextLine();
            }
                catch (Exception e){
                System.out.println(e.getMessage());
                reader.nextLine();
            }
        }
    }

    /**
     * Menu for school admninistrator
     * @param server
     * @throws Exception
     */
    public void AdminMenu(SchoolServer server) throws Exception {

        boolean running = true;

        while (running) {
            System.out.println("***************************************************");
            System.out.println("*                                                 *");
            System.out.println("* Sistema de Rastreio de Contatos em Sala de Aula *");
            System.out.println("*                                                 *");
            System.out.println("*              Area de Administração              *");
            System.out.println("*                                                 *");
            System.out.println("* Insere a sua opção:                             *");
            System.out.println("* 1 - Criar utilizador                            *");
            System.out.println("* 2 - Eliminar utilizador                         *");
            System.out.println("* 3 - Mostrar lista de utilizadores               *");
            System.out.println("* 4 - Criar sala de aula                          *");
            System.out.println("* 5 - Eliminar sala de aula                       *");
            System.out.println("* 6 - Mostrar lista de salas                      *");
            System.out.println("* 7 - Sair                                        *");
            System.out.println("*                                                 *");
            System.out.println("***************************************************");
            System.out.print("\nOpção> ");

            try {
                int input = reader.nextInt();
                reader.nextLine();
                switch (input) {
                    case 1:
                        System.out.println("\n1 - Insert Student");
                        System.out.println("2 - Insert Professor");
                        System.out.print("\nOpção> ");

                        input = reader.nextInt();
                        reader.nextLine();
                        if (input == 1) {
                            System.out.print("\nInsert user number> ");
                            input = reader.nextInt();
                            server.addUser(new Student(input));
                            System.out.println("New user inserted: " + server.getUserByNumber(input));
                        } else if (input == 2) {
                            System.out.print("\nInsert user number> ");
                            input = reader.nextInt();
                            server.addUser(new Professor(input));
                            System.out.println("New user inserted: " + server.getUserByNumber(input));
                        } else {
                            System.out.println("Invalid input");
                        }
                        break;

                    case 2:
                        System.out.print("\nInsira id de utilizador a remover>");
                        try {
                            input = reader.nextInt();
                            reader.nextLine();
                            server.removeUserByID(input);
                            System.out.println("User removed");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 3:
                        server.showUsers();
                        break;
                    case 4:
                        try {
                            System.out.print("\nInsert classroom name and type(Full/Half/Lab)> ");
                            String in = reader.nextLine();
                            String[] arr;
                            arr = in.trim().split("\\s");
                            server.addRoom(arr[0], arr[1]);
                            System.out.println("Classroom added");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 5:
                        System.out.print("\nInsert classroom name to remove> ");
                        try {
                            String in = reader.nextLine();
                            server.removeRoom(in);
                            System.out.println("Classroom removed");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 6:
                        server.showRooms();
                        break;
                    case 7:
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid input");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be of type int");
                reader.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    /**
     * Menu with professor functionalities
     * @param server
     * @param user
     * @throws Exception
     */
    public void professorMenu(SchoolServer server, Professor user) throws Exception {

        boolean running = true;

        while (running) {
            System.out.println("***************************************************");
            System.out.println("*                                                 *");
            System.out.println("* Sistema de Rastreio de Contatos em Sala de Aula *");
            System.out.println("*                                                 *");
            System.out.println("*               Registo de Presenças              *");
            System.out.println("*                                                 *");
            System.out.println("* Insere a sua opção:                             *");
            System.out.println("* 1 - Registar presenças numa aula                *");
            System.out.println("* 2 - Terminar aula                               *");
            System.out.println("* 3 - Sair                                        *");
            System.out.println("*                                                 *");
            System.out.println("***************************************************");
            System.out.print("\nOpção> ");

            try {
                int input = reader.nextInt();
                reader.nextLine();

                switch (input) {
                    case 1:
                        Room r;
                        Lesson l;
                        String in;
                        System.out.println("List of Rooms available: ");
                        System.out.println();
                        server.printRoomNames();
                        System.out.print("\nInsert classroom> ");

                        in = reader.nextLine();

                        r = server.getRoomByName(in);
                        if(r != null){
                            l = user.startLesson(r);
                        }
                        else{throw new Exception("Invalid classroom name");}

                        r.show();

                        System.out.println("\nPress E to exit and generate ID's");
                        boolean run = true;
                        while (run) {
                            try {
                                System.out.print("\nInsert student number and position> ");
                                in = reader.nextLine();
                                if (in.equalsIgnoreCase("E")) {
                                    run = false;
                                } else {
                                    String[] arr;
                                    arr = in.trim().split("\\s");
                                    int[] numbers = convertArray(arr);
                                    Position p = l.getRoom().getPosFromLabel(numbers[1]);
                                    l.addStudent(numbers[0], server, p);
                                    System.out.println("Estudante inserido na posição: " + p.toString());
                                }
                            }
                            catch (InputMismatchException e) {
                                System.out.println("Input must be of type int");
                                reader.nextLine();
                            }
                            catch (Exception e){
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case 2:
                        user.endLesson(server);
                        System.out.println("Lesson ended");
                        break;
                    case 3:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid input");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be of type int");
                reader.nextLine();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Converts String array to int array
     * @param arr
     * @return
     */
    public int[] convertArray(String arr[]){
        int size = arr.length;
        int[] aux = new int[size];
        for (int i = 0; i < size; i++) {
            aux[i] = Integer.parseInt(arr[i]);
        }
        return aux;
    }
}
