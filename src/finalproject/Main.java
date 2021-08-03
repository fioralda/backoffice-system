package finalproject;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static Connection con;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String dbUser = "root";
            String dbPass = "root";

            String dbHost = "localhost";
            String dbPort = "3306";
            String dbName = "finalproject";
            String url = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

            con = DriverManager.getConnection(url, dbUser, dbPass);
            System.out.println("Connection established successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MainFrame();
    }


}
