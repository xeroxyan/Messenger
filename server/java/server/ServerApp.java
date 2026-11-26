package server;

import common.Packet;
import common.Protocol;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : Protocol.DEFAULT_PORT;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер слушает порт " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> handleClient(socket)).start();
            }
        }
    }

    private static void handleClient(Socket socket) {
        String address = socket.getRemoteSocketAddress().toString();
        System.out.println("Подключился " + address);

        try (socket;
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Packet packet = (Packet) in.readObject();
                System.out.println(address + " -> " + packet);
                out.writeObject(packet);
                out.flush();
            }
        } catch (EOFException e) {

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ошибка с " + address + ": " + e.getMessage());
        }
        System.out.println("отключился " + address);
    }
}
