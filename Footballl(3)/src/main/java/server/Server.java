package server;

import CRUD.TeamCRUD;
import models.Team;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
    private static TeamCRUD teamCRUD;
    public static Object deserializeObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object obj = objectInputStream.readObject();
        objectInputStream.close();
        return obj;
    }
    private static byte[] serialize(Team team) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(team);
        objectOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
    private static void makeDBRequest(Team team, String request, DataOutputStream output) throws SQLException, IOException {
        teamCRUD = new TeamCRUD();
        switch (request) {
            case "add":
                teamCRUD.addTeam(team);
                output.writeUTF("added");
                break;
            case "update":
                teamCRUD.updateTeam(team);
                output.writeUTF("updated");
                break;
            case "delete":
                teamCRUD.deleteTeam(team.getId());
                output.writeUTF("deleted");
                break;
            case "get":
                Team newTeam = teamCRUD.getTeam(team.getName());
                output.writeUTF("id " + newTeam.getId() + " name " + newTeam.getName() + " city " + newTeam.getCity());
        }
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        // Визначаємо порт для сервера та стовюємо сокет
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server started");
        Team team;
        while (true) {
            Socket clientSocket = serverSocket.accept();
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            System.out.println("client connected");
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
            int len = input.readInt();
            if (len > 0) {
                byte[] objectBytes = new byte[len];
                input.readFully(objectBytes);
                team = (Team) deserializeObject(objectBytes);
                System.out.println("Received object: " + team);
                String request = input.readUTF();
                makeDBRequest(team, request, output);
                //output.writeBytes("added");
            }

        }

    }
}
