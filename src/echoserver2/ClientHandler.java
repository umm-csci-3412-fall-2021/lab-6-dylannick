package echoserver2;
import java.io.*;
import java.net.*;

class ClientHandler implements Runnable {
    private final Socket clientSocket;

    // Constructor
    public ClientHandler(Socket socket)
    {
        this.clientSocket = socket;
    }

    public void run()
    {
        byte[] buffer = new byte[4096];
        int bytesRead;
        OutputStream out = null;
        InputStream in = null;
        InputStream input = System.in;
        OutputStream serverOut = System.out;
        try {

            // get the outputstream of client
            OutputStream toServer = clientSocket.getOutputStream();

            // get the inputstream of client
            InputStream fromServer = clientSocket.getInputStream();

            while ((bytesRead = input.read(buffer)) != -1) {
                toServer.write(buffer, 0, bytesRead);
                toServer.flush(); // Always flush the buffer

                // Read the buffer received from the server, output it to the System.out OutputStream and flush our buffer once again
                bytesRead = fromServer.read(buffer);
                serverOut.write(buffer, 0, bytesRead);
                serverOut.flush();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                    clientSocket.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

