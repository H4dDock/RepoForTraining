package DataBaseMain;

import java.sql.*;

public class UnitsDAO {

    private Connection GetConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/JDBCTraining", "root", "BeNzin99");
    }

    public Units GetUnit(int id) throws SQLException {
        try(Connection connection = GetConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, email, money from Units where id = ?")){
                preparedStatement.setInt(1,id);
                ResultSet rs = preparedStatement.executeQuery();
                rs.next();

                return new Units(rs.getInt("id"),rs.getString("name")
                        ,rs.getString("email"), rs.getLong("money"));

        }
    }

    public void AddToTable(Units unit) throws SQLException {
        try(Connection connection = GetConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Units(name, email, money) value(?,?,?) ")){
            preparedStatement.setString(1,unit.getName());
            preparedStatement.setString(2,unit.getEmail());
            preparedStatement.setLong(3,unit.getMoney());

            preparedStatement.execute();
        }
    }

    public StringBuilder ShowTable() throws SQLException {
        StringBuilder output = new StringBuilder();

        try(Connection connection = GetConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, email, money from Units")){
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                output.append(new Units(rs.getInt("id"),
                                        rs.getString("name"),
                                        rs.getString("email"),
                                        rs.getLong("money")).toString()).append("\n");
            }
        }
        return output;
    }
}
