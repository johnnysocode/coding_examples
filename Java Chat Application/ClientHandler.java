import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/*
* JONATHAN SANTIAGO
* ClientHandler class will manage client connection for chat server
* it handles the sending/receiving messages to/from clients
*/

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String clientName;

    // constructs a new ClientHandler
    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientName = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("Server: " + clientName + " has joined the server.");
        } catch(IOException e) {
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
    }

    // will listen for messages from this client and will broadcast them 
    @Override
    public void run() {
        String messagesFromChat;

        while(socket.isConnected()) {
            try {
                messagesFromChat = bufferedReader.readLine();
                broadcastMessage(messagesFromChat);
            } catch(IOException e) {
                closeEverything(socket, bufferedWriter, bufferedReader);
                break;
            }
        }
    }
    
    // broadcasts the message to all the clients except the sender
    public void broadcastMessage(String messageToSend) {
        for(ClientHandler clientHandler : clientHandlers) {
            try {
                if(!clientHandler.clientName.equals(clientName)) {
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch(IOException e ) {
                closeEverything(socket, bufferedWriter, bufferedReader);
            }

        }
    }

    // removes Client from the ClientHandler list and will notify others
    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("Server: " + clientName + " has left the server.");
        }
    

    // closes the socket and related streams
    public void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {

        removeClientHandler();
        try {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            if(socket != null) {
                socket.close();
            } 
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
