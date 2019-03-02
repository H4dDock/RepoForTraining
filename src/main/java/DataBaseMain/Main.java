package DataBaseMain;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        final String URL = "jdbc:mysql://localhost:3306/JDBCTraining";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try(Connection connection = DriverManager.getConnection(URL,"root","BeNzin99");
            Statement statement = connection.createStatement()) {
            System.out.println("Connection established");

            statement.addBatch("INSERT INTO Units(Name, email, money) value ('Georg', 'GrgM@gmail.com', 30)");
            statement.addBatch("INSERT INTO Units(Name, email, money) value ('Gercog', 'DarkLord@gmail.com', 25000)");
            statement.addBatch("INSERT INTO Units(Name, email, money) value ('Alexey', 'Leha@gmail.com', 138)");

            statement.executeBatch();
            statement.clearBatch();

            ResultSet resultSets = statement.executeQuery("select * from Units");

            while(resultSets.next()){
                System.out.println("["+resultSets.getInt("id")+"] "+resultSets.getString("name")
                +", mail: "+resultSets.getString("email")+", cash: "+resultSets.getString("money"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
