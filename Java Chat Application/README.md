# Java Chat Application

This is a simple chat application implemented in Java, demonstrating the basic principles of network programming, multithreading, and stream handling. It allows multiple clients to connect to a server and exchange messages in real time.

## Features

- **Client-Server Architecture**: Implements a simple client-server model using sockets.
- **Multithreading**: Manages multiple client connections concurrently.
- **Real-time Communication**: Clients can send and receive messages instantly.
- **Error Handling**: Robust error handling and resource management.

## How to Run
1. Compile the code:
   - `javac Server.java`
   - `javac Client.java`
2. Start the server: `java Server`
3. In a new terminal, start each client: `java Client`

## Project Structure
- `Server.java`: The server program that handles multiple client connections.
- `Client.java`: The client program that connects to the server.
- `ClientHandler.java`: Manages client connections and message broadcasting.

## Concepts Demonstrated
- Socket Programming
- Multithreading and Concurrency
- Stream I/O
- Exception Handling
- Basic Network Programming in Java

## Future Enhancements
- Implement a GUI for the client program.
- Add features like private messaging, file transfer, etc.
- Improve the security of the communication.


