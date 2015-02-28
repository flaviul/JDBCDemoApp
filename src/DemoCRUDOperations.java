import com.sun.org.apache.xpath.internal.SourceTree;

import java.sql.*;
import java.util.Scanner;


/**
 * Created by condor on 26/02/15.
 * FastTrackIT, 2015
 * <p/>
 * DEMO ONLY PURPOSES, IT MIGHT CONTAINS INTENTIONALLY ERRORS OR ESPECIALLY BAD PRACTICES
 * <p/>
 * make sure you refactor it and remove lots of bad practices like loading the driver multiple times or
 * repeating the same common code multiple times
 * <p/>
 * BTW, exercise 1: how we reorg this/refactor in small pieces
 */
public class DemoCRUDOperations {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("Hello database users! We are going to call DB from Java");
        do {
            printMenu();
            int option = readMenuOption();
            switch (option) {
                case 1:
                    demoCreate();
                    break;
                case 2:
                    demoRead();
                    break;
                case 3:
                    demoUpdate();
                    break;
                case 4:
                    demoDelete();
                    break;
                default:
                    System.out.println("No such field");
                    break;

            }

        } while (true);
    }

       // try {
            //demo CRUD operations
            // demoCreate();
            //demoRead();
//           demoUpdate();
//            demoDelete();

            // demoBlobInsert();
            // demoBlobRead();


//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }

    private static void printMenu() {
        System.out.println("1. demoCreate");
        System.out.println("2 .demoRead");
        System.out.println("3. demoUpdade");
        System.out.println("4. demoDelete");
    }

    private static int readMenuOption() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter option:");
        return scanner.nextInt();

    }


    private static void demoCreate() throws ClassNotFoundException, SQLException {
        Connection conn = getConnection();


        // 4. create a query statement
        PreparedStatement pSt = conn.prepareStatement("INSERT INTO AgendaTelefonica (Nume, Prenume, Telefon) VALUES (?,?,?)");
        pSt.setString(1, "Andrei");
        pSt.setString(2, "Bla");
        pSt.setString(3, "0733425134");
        // 5. execute a prepared statement
        int rowsInserted = pSt.executeUpdate();

        // 6. close the objects
        pSt.close();
        conn.close();
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        // 1. load driver
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/Flav_Agenda";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";

        // 3. obtain a connection
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    private static void demoRead() throws ClassNotFoundException, SQLException {
        // 1. load driver
        Connection conn = getConnection();

        // 4. create a query statement
        Statement st = conn.createStatement();

        // 5. execute a query
        ResultSet rs = st.executeQuery("SELECT Nume,Prenume,Telefon FROM AgendaTelefonica");

        // 6. iterate the result set and print the values
        while (rs.next()) {
            System.out.print(rs.getString("Nume").trim());
            System.out.print("---");
            System.out.println(rs.getString("Prenume").trim());
            System.out.print("---");
            System.out.println(rs.getString("Telefon").trim());
        }

        // 7. close the objects
        rs.close();
        st.close();
        conn.close();
    }

    private static void demoUpdate() throws ClassNotFoundException, SQLException {

        // 1. load driver
        Connection conn = getConnection();

        // 4. create a query statement
        PreparedStatement pSt = conn.prepareStatement("UPDATE AgendaTelefonica SET Nume=?, Prenume=?, Telefon=? WHERE id_agenda=?"); //so we have 3 params
        pSt.setString(1, "Andrei");
        pSt.setString(2, "Bla");
        pSt.setString(3, "0722435333");
        pSt.setLong(4, 1);

        // 5. execute a prepared statement
        int rowsUpdated = pSt.executeUpdate();

        // 6. close the objects
        pSt.close();
        conn.close();
    }


    private static void demoDelete() throws ClassNotFoundException, SQLException {

        // 1. load driver
        Connection conn = getConnection();

        // 4. create a query statement
        PreparedStatement pSt = conn.prepareStatement("DELETE FROM AgendaTelefonica WHERE id_agenda=?");
        pSt.setLong(1, 5);

        // 5. execute a prepared statement
        int rowsDeleted = pSt.executeUpdate();
        System.out.println(rowsDeleted + " rows were deleted.");
        // 6. close the objects
        pSt.close();
        conn.close();
    }
}

