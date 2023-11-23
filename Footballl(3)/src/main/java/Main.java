import CRUD.PlayerCRUD;
import CRUD.TeamCRUD;
import database.DatabaseConnection;
import database.DatabaseSetup;
import models.Player;
import models.Team;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
//        Connection = DatabaseConnection.getConnection();
//        DatabaseSetup.createDatabase();
//        PlayerCRUD crud = new PlayerCRUD(connection);
//        Connection connection1 = DatabaseConnection.getConnection();
//        TeamCRUD teamCRUD = new TeamCRUD(connection1);
////        Team newTeam = new Team(0, "Team Name", "City Name");
////        teamCRUD.addTeam(newTeam);
////        // Додавання, читання, оновлення, видалення для команд (Teams)...
////
////        // Додавання нового гравця
////        Player newPlayer = new Player(0, "FirstName", "LastName", 25, 1); // 1 - ID команди
////        crud.addPlayer(newPlayer);
//
//        // Отримання гравця за ID
//        Player player = crud.getPlayer(1);
//        System.out.println(player);
//
//        // Оновлення даних гравця
//        player.setLastName("UpdatedLastName");
//        crud.updatePlayer(player);
//
//        // Видалення гравця
//        crud.deletePlayer(1);
    }
}
