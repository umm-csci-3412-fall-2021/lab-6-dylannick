package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient extends Throwable {
	public static final int PORT_NUMBER = 6013;
	static InputStream socketInputStream;
	static OutputStream socketOutputStream;
	static Socket socket;


	public static void main(String[] args) throws IOException {
		String hostName;

		if (args.length == 0) {
			hostName = "127.0.0.1";
		} else {
			hostName = args[0];
		}


		try {
			socket = new Socket("localhost", PORT_NUMBER);
			socketInputStream = socket.getInputStream();
			socketOutputStream = socket.getOutputStream();


			Thread inputReader = new Thread(EchoClient::inputReader);
			Thread outputWriter = new Thread(EchoClient::outputWriter);


			inputReader.start();
			outputWriter.start();


			inputReader.join();
			outputWriter.join();


			socket.close();
		} catch(Exception e){
			System.out.println(e);
		}

	}


	public static void inputReader() {
		int currentByte;
		try {
			while ((currentByte = System.in.read()) != -1) {
				socketOutputStream.write(currentByte);
				socketOutputStream.flush();
			}
			socket.shutdownOutput();
		} catch(Exception e) {
			System.out.println(e);
		}
	}


	public static void outputWriter() {
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