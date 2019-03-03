package DataBaseMain;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class MySQLComander {
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public MySQLComander(String URL, String driver, String user, String password) {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(URL, user, password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void InsertIntoUnitsDB(String DBName, String name, String email, long money) throws SQLException {
        preparedStatement = connection.prepareStatement("INSERT into "+DBName+"(Name, email, money) value(?,?,?)");
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,email);
        preparedStatement.setLong(3,money);
        preparedStatement.execute();
    }

    public void ShowDB(String DBName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * from "+DBName);
        while (rs.next()){
            System.out.println(new Units(rs.getInt("id"), rs.getString("name"),
                    rs.getString("email"),rs.getLong("money")).toString());
        }
    }

    public List<String> GetArrOfNames(String DBName) throws SQLException {
        List<String> output = new LinkedList<>();
        ResultSet rs = statement.executeQuery("Select name from "+DBName);
        while (rs.next()){
            output.add(rs.getString("name"));
        }
        return output;
    }
}
