import java.util.Scanner;
import java.net.Socket;
import java.io.*;

/*
* JONATHAN SANTIAGO
* Client class for connecting to chat server
* Handles sending and receiving messages
*/

public class Client {

    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String clientName;

    // Constructs new client 
    private Client(Socket socket, String clientName) {
        this.socket = socket;
        this.clientName = clientName;
        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    // Sends message from client to server
    public void sendMessage() {
        try {
            bufferedWriter.write(clientName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner input = new Scanner(System.in);
            while (socket.isConnected()) {
                String message = input.nextLine();
                bufferedWriter.write(clientName + ":" + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } 
        } catch (IOException e) {
            closeEverything(socket, bufferedWriter, bufferedReader); 
            }
    }

    // "listen" for messages from any of the connected clients
    public void listenForMessage() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String messagesFromChat; 
                while (socket.isConnected()) {
                    try {
                        messagesFromChat = bufferedReader.readLine();
                        System.out.println(messagesFromChat);
                    } catch (IOException e) {
                        closeEverything(socket, bufferedWriter, bufferedReader);
                    }
                }    
            }
        }).start();
    }

    // Closes the sockets clients socket and streams  
    public void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        try {
            if(bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Main to run the Chat Application
    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);
        System.out.println("Enter your name for the chat server: ");
        String clientName = input.nextLine();

        Socket socket = new Socket("localhost",2022);
        Client client = new Client(socket, clientName);

        client.listenForMessage();
        client.sendMessage();
    }
}
    

