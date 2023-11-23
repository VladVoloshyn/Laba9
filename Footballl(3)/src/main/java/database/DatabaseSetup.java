package database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseSetup {
    public static void createDatabase() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement();

            // Create the "Football" database
            String sql = "CREATE TABLE IF NOT EXISTS Teams (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "city VARCHAR(255) NOT NULL)";
            statement.executeUpdate(sql);

            // Create the "Players" table with a link to the "Teams" table
            sql = "CREATE TABLE IF NOT EXISTS Players (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "firstName VARCHAR(255) NOT NULL," +
                    "lastName VARCHAR(255) NOT NULL," +
                    "age INT NOT NULL," +
                    "team_id INT," +
                    "FOREIGN KEY (team_id) REFERENCES Teams(id))";
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
