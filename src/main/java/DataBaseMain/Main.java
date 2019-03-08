package DataBaseMain;

import ServerNatty.Server;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {

        MySQLComander mySQLComander = MySQLComander.GetInstance();

        try {
            //mySQLComander.InsertIntoUnitsDB("Units","Mishel","DeLacost@gmail.com",5100);
            //mySQLComander.ShowDB("Units");
            List<String> names = mySQLComander.GetArrOfNames("Units");
            System.out.println(names.stream().filter((x)->x.length() < 10).sorted().collect(Collectors.toList()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new Server(port).run();

    }
}
