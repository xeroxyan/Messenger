package client;

import common.Packet;
import common.PacketType;
import common.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String host = args.length > 0 ? args[0] : Protocol.DEFAULT_HOST;
        int port = args.length > 1 ? Integer.parseInt(args[1]) : Protocol.DEFAULT_PORT;

        try (Socket socket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             Scanner console = new Scanner(System.in)) {

            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Подключено к " + host + ":" + port + ". Пустая строка — выход.");

            while (console.hasNextLine()) {
                String text = console.nextLine();
                if (text.isBlank()) {
                    break;
                }
                out.writeObject(new Packet(PacketType.MESSAGE, "tester", null, text));
                out.flush();

                Packet reply = (Packet) in.readObject();
                System.out.println("Ответ: " + reply);
            }
        }
    }
}
