package DataBaseMain;

import java.sql.*;

public class MySQLComander {
    private Connection connection;
    private Statement statement;

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
        statement.executeUpdate("INSERT into "+DBName+"(Name, email, money) value ('"+name+"','"+email+"',"+money+")");
    }

    public void ShowDB(String DBName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * from "+DBName);
        while (rs.next()){
            System.out.println(new Units(rs.getInt("id"), rs.getString("name"),
                    rs.getString("email"),rs.getLong("money")).toString());
        }
    }
}
