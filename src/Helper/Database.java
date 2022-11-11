package Helper;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbUrl = protocol + vendor + location + databaseName + "?connectionTimeZone=SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";
    public static  Connection connection;
    public static Connection getConnection;

    public static void openConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbUrl, username, password);
            System.out.println("connection successful");
        }
        catch(Exception e) {
            System.out.println("error:" + e.getMessage());
        }
    }
    public static Connection getConnection() {
        return connection;
    }
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("connection closed");
        }
        catch(Exception e) {
            System.out.println("error:" + e.getMessage());
        }
    }
}
