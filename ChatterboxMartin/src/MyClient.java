import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.*;

public class MyClient {

	Socket socket;
	String ip;
	int port;
	
	
	private DataOutputStream output;

	public MyClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public static void main(String[] args) {

		MyClient client1 = new MyClient("127.0.0.1", 8000);
		try {
			client1.start();
			client1.waitForInput();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
		executor.scheduleAtFixedRate(client1.readMessageRunnable, 0, 200, TimeUnit.MILLISECONDS);
	}

	private void start() throws UnknownHostException, IOException {
		socket = new Socket(ip, port);
		output = new DataOutputStream(socket.getOutputStream());
	}

	void waitForInput() {
		System.out.println("Use the console to send messages.");
		Scanner scanner = new Scanner(System.in);
		try {
			while (true) {
				String line = scanner.nextLine();
				try {
					writeMessage(line);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	private void writeMessage(String line) {
		try {
			output.writeUTF(line);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	Runnable readMessageRunnable = new Runnable() {
		public void run() {
			DataInputStream dis;
			try {
				dis = new DataInputStream(socket.getInputStream());
				String str = (String)dis.readUTF();
				System.out.println("Client Says = " + str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

}
