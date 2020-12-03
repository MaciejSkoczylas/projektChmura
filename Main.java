import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main {

    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {

        while (true){
        TimeUnit.SECONDS.sleep(15);
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://Full2020_086406:3306/chmurka", "MSkoczylas", "root");
             Statement stmt = conn.createStatement())

        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Users (ID int NOT NULL AUTO_INCREMENT, Imie varchar(255), Nazwisko varchar(255), PRIMARY KEY (ID) );");
            String selectedOperation;
            do {
                System.out.println("1. Pokaż baze\n2. Dodaj uzytkownika\n3. Edytuj uzytkownika\n4. Usuń uzytkownika\nS. Q - aby wyjsc");
                selectedOperation = in.nextLine();
                switch (selectedOperation) {
                    case "1":
                        getResults(stmt);
                        break;
                    case "2":
                        insertUser(stmt);
                        break;
                    case "3":
                        updateUser(stmt);
                        break;
                    case "4":
                        deleteUser(stmt);
                        break;
                }
            } while (!selectedOperation.toUpperCase().equals("Q"));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }}
    }

    private static void deleteUser(Statement stmt) throws SQLException {
        ResultSet rsss = stmt.executeQuery("SELECT ID, Imie, Nazwisko FROM Users");
        printOutHeader();
        printOutResult(rsss);
        rsss.close();
        System.out.println("Podaj uzytkownika do usunięcia(ID)");
        final String id = in.nextLine();
        final String deleteSql = " DELETE FROM Users WHERE ID= '" + id + "';";
        stmt.executeUpdate(deleteSql);
    }

    private static void updateUser(Statement stmt) throws SQLException {
        String id;
        String imie;
        String nazwisko;
        String sql;

        ResultSet rss = stmt.executeQuery("SELECT ID, Imie, Nazwisko FROM Users");
        printOutHeader();

        printOutResult(rss);
        rss.close();
        System.out.println("Podaj uzytkownika do edycji(ID)");
        id = in.nextLine();

        System.out.println("Imie: ");
        imie = in.nextLine();

        System.out.println("Nazwisko: ");
        nazwisko = in.nextLine();

        sql = " UPDATE Users SET Imie = '" + imie + "' , Nazwisko = '" + nazwisko + "' WHERE ID= '" + id + "';";
        stmt.executeUpdate(sql);
    }

    private static void insertUser(Statement stmt) throws SQLException {


        System.out.println("Imie:");
        final String imie = in.nextLine();

        System.out.println("Nazwisko");
        final String nazwisko = in.nextLine();

        String sql = " INSERT INTO Users (Imie, Nazwisko) VALUES ('" + imie + "', '" + nazwisko + "')";
        stmt.executeUpdate(sql);
    }

    private static void getResults(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT ID, Imie, Nazwisko FROM Users");
        printOutHeader();
        printOutResult(rs);
        rs.close();
    }

    private static void printOutHeader() {
        System.out.println("ID    Imie    Nazwisko");
    }

    private static void printOutResult(ResultSet rs) throws SQLException {
        int id;
        String imie;
        String nazwisko;
        while (rs.next()) {
            id = rs.getInt("ID");
            imie = rs.getString("Imie");
            nazwisko = rs.getString("Nazwisko");
          

            System.out.println(id + "    " + imie + "    " + nazwisko);
        }
    }
}
