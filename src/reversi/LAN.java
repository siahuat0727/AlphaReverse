package reversi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class LAN {
	public static String Read() {
		try {
			ServerSocket serverSock = new ServerSocket(8000);
			Socket connectionSock = serverSock.accept();
			DataInputStream in = new DataInputStream(connectionSock.getInputStream());
			String clientText = in.readUTF();
			System.out.println("READ  DATA :" + clientText);
			connectionSock.close();
			serverSock.close();
			in.close();
			return clientText;
		} catch (IOException e) {
			System.out.println("READ  ERROR: " + e.getMessage());
			return e.getMessage();
		}
	}

	public static void Write(String inp , String ServerIP) {
		System.out.println("WRITE DATA :" + inp);
		try {
			Socket cSock = new Socket(ServerIP, 8000);
			OutputStream outToServer = cSock.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			out.writeUTF(inp);
			outToServer.close();
			cSock.close();
		} catch (IOException e) {
			System.out.println("WRITE ERROR:" + e.getMessage());
		}
	}
	
	public static void delay(int inp) {
		try {
			Thread.sleep(inp);
		}
		catch (Exception e) {
			System.out.println("SLEEP ERRPR:" + e);
		}
	}
}