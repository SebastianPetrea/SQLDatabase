package employees.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class mysqlConnect {
    private static Connection connection;
    private static String url = "jdbc:mysql://localhost:3306/mydb";
    private static String conUsername = "root";
    private static String conPassword = "root";

    public static void connectToDatabase() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection(url, conUsername, conPassword);
            System.out.println("Successfully connected to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addUsers(String id, String username, String email) throws SQLException {
        String insertQuery = "INSERT INTO userdata (id, username, email) VALUES (?, ?, ?)";
        int row;
        //Add the id in the row colums !!!!!!!!!!!
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, email);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User added successfully.");
            } else {
                System.out.println("Failed to add user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static List<Users> fetchUsersList() {
        List<Users> userList = new ArrayList<>();

        try {
            String selectQuery = "SELECT * FROM userdata";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    System.out.println(id +username +email);
                    userList.add(new Users(id, username, email));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }
    
    public static void deleteRow(String id) throws SQLException {
        String deleteSQL = "DELETE FROM userdata WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, id);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            // Check the result
            if (rowsAffected > 0) {
                System.out.println("Row deleted successfully.");
            } else {
                System.out.println("No rows were deleted. Check your condition.");
            }
        }
    }




    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
