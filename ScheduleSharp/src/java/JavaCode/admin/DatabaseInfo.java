/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaCode.admin;

/**
 *
 * @author nikhilnair
 */
public class DatabaseInfo {

    private final String url = "jdbc:mysql://db4free.net:3306/";
    private final String dbName = "schedulesharp";
    private final String driver = "com.mysql.jdbc.Driver";
    private final String dbUserName = "schedulesharp";
    private final String dbPassword = "schedule";

    public String getUrl() {
        return url;
    }

    public String getDbName() {
        return dbName;
    }

    public String getDriver() {
        return driver;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public String getDbPassword() {
        return dbPassword;
    }
    
    
}
