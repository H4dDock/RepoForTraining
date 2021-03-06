package DataBaseMain;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @deprecated
 */

public class MySQLComander {
    private static MySQLComander instance = new MySQLComander();
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    private MySQLComander() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JDBCTraining",
                    "root", "BeNzin99");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static MySQLComander GetInstance(){
        return instance;
    }

    public void InsertIntoUnitsDB(String DBName, String name, String email, long money) throws SQLException {
        preparedStatement = connection.prepareStatement("INSERT into "+DBName+"(Name, email, money) value(?,?,?)");
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,email);
        preparedStatement.setLong(3,money);
        preparedStatement.execute();
    }

    public void DeleteById(String DBName, int id) throws SQLException {
        preparedStatement = connection.prepareStatement("Delete from " + DBName + "Where id = ?");
        preparedStatement.setInt(1,id);
        preparedStatement.execute();
    }

    public void DeleteById(String DBName, String name) throws SQLException {
        preparedStatement = connection.prepareStatement("Delete from " + DBName + "Where name = ?");
        preparedStatement.setString(1,name);
        preparedStatement.execute();
    }

    public void UpdateNameById(String DBName, String newName, int id) throws SQLException {
        preparedStatement = connection.prepareStatement("UPDATE " + DBName +" SET name = ? where id = ?");
        preparedStatement.setString(1, newName);
        preparedStatement.setInt(2, id);
        preparedStatement.execute();
    }

    public void UpdateMoneyById(String DBName, long newMoney, int id) throws SQLException {
        preparedStatement = connection.prepareStatement("UPDATE " + DBName +" SET money = ? where id = ?");
        preparedStatement.setLong(1, newMoney);
        preparedStatement.setInt(2, id);
        preparedStatement.execute();
    }

    public StringBuffer StringDB(String DBName) throws SQLException {
        StringBuffer out = new StringBuffer("");
        ResultSet rs = statement.executeQuery("SELECT * from "+DBName);
        while (rs.next()){
            out.append(new Units(rs.getInt("id"), rs.getString("name"),
                    rs.getString("email"),rs.getLong("money")).toString()).append("\n");
        }
        return out;
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
