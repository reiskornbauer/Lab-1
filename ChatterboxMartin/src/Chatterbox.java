import java.io.*;
import java.net.*;

public class Chatterbox {

	private DataOutputStream output;
	private static int PORT_NUMBER = 8000; //server connecten

	public static void main(String[] args) {

		// Chatterbox erstellen 
		//Chatterbox starten(serversocker erstellen)
		//warte auf client
		//set up streams 
		//lasse echo in einer schleife laufen
		

		try {
			ServerSocket ss = new ServerSocket(PORT_NUMBER);
			Socket s = ss.accept();
			DataInputStream dis = new DataInputStream(s.getInputStream());
			String str = (String)dis.readUTF();
			System.out.println("Client Says = " + str);
			ss.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void echo(){
		String message = readMessage();
		writeMessage(message);
	}
	
	
	private String readMessage() {

		return null;
	}
	
	
	private void writeMessage(String line) {
		try {
			output.writeUTF(line);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
		
}

