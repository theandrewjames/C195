package Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Manipulates database users
 */
public class DBUsers {
    /**
     * Attempts to login using username/password
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public static boolean userLogin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_name = '" + username + "' AND password = '" + password + "'";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) return true;
        else return false;
    }
}
