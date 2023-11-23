package client;

import models.Team;

import java.io.*;
import java.net.Socket;
import java.time.Year;

public class Client {
    private static Socket socket;
    private static DataOutputStream output;
    private static DataInputStream input;

    public Client(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
    }
    private static byte[] serialize(Team team) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(team);
        objectOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
    public static Object deserializeObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object obj = objectInputStream.readObject();
        objectInputStream.close();
        return obj;
    }
    public static String sendQuery(Team team, String operation) throws IOException {
        byte[] serializedObject = serialize(team);
        // Відправлення об'єкта через DataOutputStream
        output.writeInt(serializedObject.length); // спочатку відправляємо довжину масиву
        output.write(serializedObject); // потім відправляємо сам масив
        output.writeUTF(operation);
        output.flush();
        return input.readUTF();
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try (Socket socket = new Socket("localhost", 8080);
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
             DataInputStream input = new DataInputStream(socket.getInputStream())) {
            // Відправлення запиту на сервер
            while (true) {
                Team team = new Team(5, "Liverpool", "Liverpool");
                byte[] serializedObject = serialize(team);
                output.writeInt(serializedObject.length);
                output.write(serializedObject);
                output.writeUTF("add"); // Наприклад, "add"
                output.flush();

                // Отримання відповіді від сервера
                String response = input.readUTF();
                System.out.println("Response from server: " + response);
            }
        }
    }
}
