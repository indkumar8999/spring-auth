package com.myweb.authmagic.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static Connection conn = null;
    
    public static void start(String dbName, String username, String password) {
        try {
            conn =
                DriverManager.getConnection("jdbc:mysql://localhost/"+dbName+"?" +
                                   "user="+username+"&password="+password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return conn;
    }

    public static void close() {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
