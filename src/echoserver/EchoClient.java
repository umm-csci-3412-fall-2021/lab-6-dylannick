package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient extends Throwable {
	public static final int PORT_NUMBER = 6013;
	public static InputStream socketInputStream = null;
	public static OutputStream socketOutputStream = null;
	public static Socket socket = null;


	public static void main(String[] args) throws IOException {
		String hostName;

		if (args.length == 0) {
			hostName = "127.0.0.1";
		} else {
			hostName = args[0];
		}


		try {
			socket = new Socket(hostName, PORT_NUMBER);
			socketInputStream = socket.getInputStream();
			socketOutputStream = socket.getOutputStream();

			System.out.println("thread");

			Thread outputWriter = new Thread(new outputWriter());


			Thread inputReader = new Thread(new inputReader());

			System.out.println("start");
			outputWriter.start();
			inputReader.start();

			System.out.println("join");
			outputWriter.join();
			inputReader.join();



			socket.close();

			System.out.println("close");
		} catch(Exception e){
			System.out.println(e);
		}

	}

	public static class inputReader implements Runnable {
		@Override
		public void run() {
			int currentByte;
			try {
				while ((currentByte = System.in.read()) != -1) {
					socketOutputStream.write(currentByte);
					socketOutputStream.flush();
				}
			} catch(Exception e){
				System.out.println(e);
			}
		}
	}

	public static class outputWriter implements Runnable {
		@Override
		public void run() {
			int currentByte;
			try {
				while ((currentByte = socketInputStream.read()) != -1) {
					System.out.write(currentByte);
					System.out.flush();
				}
			} catch(Exception e){
				System.out.println(e);
			}
		}
	}

}