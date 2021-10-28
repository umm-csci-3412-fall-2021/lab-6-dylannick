package echoserver2;
import java.net.*;
import java.io.*;

class EchoServer extends Thread {
    public static void main(String[] args){
        try {
            ServerSocket ss = new ServerSocket(6013);
            Socket socket = null;
            OutputStream toClient = null;
            InputStream fromClient = null;
            byte[] buffer = new byte[4096];
            int bytesRead;

            while(true) {
                // Server scans for clients to connect
                try {
                    socket = ss.accept();
                    toClient = socket.getOutputStream();
                    fromClient = socket.getInputStream();
                    System.out.println("Client connected. ");
                } catch (IOException ex) {
                    System.out.println("Can't accept client connection. ");
                }

                // Client found, continuously write input from it to the buffer and then flush the buffer until EOF
                while ((bytesRead = fromClient.read(buffer)) != -1) {
                    toClient.write(buffer, 0, bytesRead);
                    toClient.flush();
                }

                // EOF received, gracefully shutdown the client connection
                System.out.println("Client disconnected. ");
                socket.shutdownOutput();

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}