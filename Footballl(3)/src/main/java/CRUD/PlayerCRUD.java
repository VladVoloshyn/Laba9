package CRUD;
import models.Player;

import java.sql.*;

public class PlayerCRUD {
    private Connection connection;

    public PlayerCRUD(Connection connection) {
        this.connection = connection;
    }

    // ... Методи для команд (Teams) ...

    // Додавання нового гравця
    public void addPlayer(Player player) {
        String sql = "INSERT INTO Players (firstName, lastName, age, team_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, player.getFirstName());
            statement.setString(2, player.getLastName());
            statement.setInt(3, player.getAge());
            statement.setInt(4, player.getTeamId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Читання даних гравця за ID
    public Player getPlayer(int id) {
        String sql = "SELECT * FROM Players WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Player(
                        resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getInt("age"),
                        resultSet.getInt("team_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Оновлення даних гравця
    public void updatePlayer(Player player) {
        String sql = "UPDATE Players SET firstName = ?, lastName = ?, age = ?, team_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, player.getFirstName());
            statement.setString(2, player.getLastName());
            statement.setInt(3, player.getAge());
            statement.setInt(4, player.getTeamId());
            statement.setInt(5, player.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Видалення гравця
    public void deletePlayer(int id) {
        String sql = "DELETE FROM Players WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}