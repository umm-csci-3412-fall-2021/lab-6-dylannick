package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer implements Runnable {
	private Socket socket;
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
			while (true) {
				Socket socketToClient = serverSocket.accept();
				new Thread(new EchoServer(socketToClient)).start();
			}
		} catch(Exception e){
			System.out.println(e);
		}
	}
	public EchoServer(Socket socket){
		this.socket = socket;
	}

	public void run() {
		try {
			InputStream fromClient = socket.getInputStream();
			OutputStream toClient = socket.getOutputStream();
			int currentByte;

			while((currentByte = fromClient .read()) != -1){
				toClient.write(currentByte);
				toClient.flush();
			}
			socket.shutdownOutput();
		} catch(Exception e){
			System.out.println(e);
		}
	}

}