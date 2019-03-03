package DataBaseMain;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        final String URL = "jdbc:mysql://localhost:3306/JDBCTraining";

        MySQLComander mySQLComander = new MySQLComander(URL,"com.mysql.jdbc.Driver","root", "BeNzin99");

        try {
            mySQLComander.InsertIntoUnitsDB("Units","Mihael","mihael78@gmail.com",5000);
            mySQLComander.ShowDB("Units");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
