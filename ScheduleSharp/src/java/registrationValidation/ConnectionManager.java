package registrationValidation;

import java.sql.*;

/**
 *
 * @author chaehoon
 */
public class ConnectionManager {

    static Connection con;
    static String url;

    public static Connection getConnection() throws InstantiationException, IllegalAccessException {

        try {
            String url = "jdbc:mysql://db4free.net:3306/schedulesharp";       

            Class.forName("com.mysql.jdbc.Driver").newInstance();     

            try {
                con = DriverManager.getConnection(url, "schedulesharp", "schedule");                
     
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        return con;
    }
}
