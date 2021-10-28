package echoserver2;
import java.net.*;
import java.io.*;


class EchoClient extends Thread{
    public static void main(String[] args){
        String hostName;
        byte[] buffer = new byte[4096];
        int bytesRead;

        // Define our hostname as localhost if it's not inputted in the command line argument
        if (args.length == 0) {
            hostName = "127.0.0.1";
        } else {
            hostName = args[0];
        }

        try {
            Socket socket = new Socket(hostName, 6013);

            InputStream input = System.in;
            OutputStream serverOut = System.out;

            InputStream fromServer = socket.getInputStream();
            OutputStream toServer = socket.getOutputStream();

            // Read the System.in InputStream into the buffer and write it to the server
            while ((bytesRead = input.read(buffer)) != -1) {
                toServer.write(buffer, 0, bytesRead);
                toServer.flush(); // Always flush the buffer

                // Read the buffer received from the server, output it to the System.out OutputStream and flush our buffer once again
                bytesRead = fromServer.read(buffer);
                serverOut.write(buffer, 0, bytesRead);
                serverOut.flush();
            }

            // EOF character received, tell the server we're not sending any more data
            socket.shutdownOutput();

            // Cleanup any last data coming from the server before the socket closes entirely
            while ((bytesRead = fromServer.read(buffer)) != -1) {
                serverOut.write(buffer, 0, bytesRead);
                serverOut.flush();
            }

            socket.close();

        } catch(Exception e){
            System.out.println(e);
        }
    }
}