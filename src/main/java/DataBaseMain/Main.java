package DataBaseMain;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        final String URL = "jdbc:mysql://localhost:3306/JDBCTraining";

        MySQLComander mySQLComander = new MySQLComander(URL,"com.mysql.jdbc.Driver","root", "BeNzin99");

        try {
            //mySQLComander.InsertIntoUnitsDB("Units","Mishel","DeLacost@gmail.com",5100);
            mySQLComander.ShowDB("Units");
            List<String> names = mySQLComander.GetArrOfNames("Units");
            System.out.println(names.stream().filter((x)->x.length() < 10).sorted().collect(Collectors.toList()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
