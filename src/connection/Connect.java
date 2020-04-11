package connection;

import java.sql.*;

public class Connect {


    public  ResultSet connect (String query) {
        Connection conn;
        Statement stmt;
        ResultSet dataSet = null;

        try {
            // db parameters
            String url = "jdbc:sqlite:/Users/kellerbeckstead/Desktop/sqlite/softwareone.db";

            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

            stmt = conn.createStatement();
            dataSet = stmt.executeQuery(query);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
//        finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//            }
//        }
        return dataSet;
    }

}