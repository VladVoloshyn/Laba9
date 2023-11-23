package CRUD;

import database.DatabaseConnection;
import models.Player;
import models.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamCRUD {
    private Connection connection;

    public TeamCRUD() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    // Create a new team
    public void addTeam(Team team) {
        String sql = "INSERT INTO Teams (name, city) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, team.getName());
            statement.setString(2, team.getCity());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read team by ID
    public Team getTeam(String name) {
        String sql = "SELECT * FROM Teams WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Team(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("city")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update team
    public void updateTeam(Team team) {
        String sql = "UPDATE Teams SET name = ?, city = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, team.getName());
            statement.setString(2, team.getCity());
            statement.setInt(3, team.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete team
    public void deleteTeam(int id) {
        String sql = "DELETE FROM Teams WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Player> getPlayersByTeamId(int teamId) {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM Players WHERE team_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, teamId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                players.add(new Player(
                        resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getInt("age"),
                        resultSet.getInt("team_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }
}
