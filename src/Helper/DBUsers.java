package Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUsers {
    public static boolean userLogin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_name = '" + username + "' AND password = '" + password + "'";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        rs.next();
        if(rs.getString("User_Name").equals(username) && rs.getString("Password").equals(password)) {
            return true;
        }
        else  {
            return false;
        }
    }
}
