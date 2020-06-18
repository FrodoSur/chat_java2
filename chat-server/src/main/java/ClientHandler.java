import java.io.*;
import java.net.Socket;

import static sun.plugin.javascript.navig.JSType.History;

public class ClientHandler implements Runnable {
    public FileInputStream writer = new FileInputStream("History.txt");
    private FileOutputStream reader = new FileOutputStream("History.txt");
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nickName;
    private boolean running;

    public ClientHandler(Socket socket, String nickName) throws IOException {
        this.socket = socket;
        this.nickName = nickName;
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        running = true;
        welcome();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void welcome() throws IOException {
        out.writeUTF("Hello " + nickName);
        out.flush();
        System.out.println("Клиент "+ nickName + " принят");
        String output = " ";
        byte[] buf = new byte[20];
        try (FileInputStream in = new FileInputStream("History.txt")) {
            int count;
            while ((count = in.read(buf)) > 0) {
                for (int i = 0; i < count; i++) {
                    output +=(char) buf[i];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.writeUTF(output);
        out.flush();

    }

    public void broadCastMessage(String message) throws IOException {
        for (ClientHandler client : Server.getClients()) {
            //if (!client.equals(this)) {
                client.sendMessage(message);
           // }
        }
    }
    public void DirectCastMessage(String message) throws IOException {
        for (ClientHandler client : Server.getClients()) {
            if (!client.equals(this)) {
            client.sendMessage(message);
             }
        }
    }

    public void sendMessage(String message) throws IOException {
        out.writeUTF(message);
        out.flush();
        System.out.println("Клиент "+ nickName + " написал сообщение");
    }

    @Override
    public void run() {
        while (running) {
            try {
                if (socket.isConnected()) {
                    String clientMessage = in.readUTF();
                    if (clientMessage.equals("_exit_")) {
                        Server.getClients().remove(this);
                        sendMessage(clientMessage);
                        break;
                    }
                    System.out.println(clientMessage);
                    broadCastMessage(clientMessage);
                    byte[] outData = clientMessage.getBytes();
                    out.write(outData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
