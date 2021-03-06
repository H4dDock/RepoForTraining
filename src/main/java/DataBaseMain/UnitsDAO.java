package DataBaseMain;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

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

    public List<Units> GetAllUnits() throws SQLException {
        LinkedList<Units> output = new LinkedList<>();

        try(Connection connection = GetConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, email, money from Units")) {
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                output.add(new Units(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getLong("money")));
            }
        }

        return output;
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

    public void RemoveFromTable(int id) throws SQLException {
        try(Connection connection = GetConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Units WHERE id = ?")){
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
        }
    }
}
