package uimain;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class LAN {
	public static String Read() {
		try {
			ServerSocket serverSock = new ServerSocket(8000);
			Socket connectionSock = serverSock.accept();
			BufferedReader clientInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
			DataOutputStream clientOutput = new DataOutputStream(connectionSock.getOutputStream());
			String clientText = clientInput.readLine();
			System.out.println("READ DATA(" + clientText + ")");
			clientOutput.close();
			clientInput.close();
			connectionSock.close();
			serverSock.close();
			return clientText;
		} catch (IOException e) {
			System.out.println("READ  ERROR: " + e.getMessage());
			return e.getMessage();
		}
	}

	public static void Write(String inp) {
		System.out.println("WRITE DATA(" + inp + ")");
		try {
			Socket cSock = new Socket("localhost", 8000);
			DataOutputStream serverOutput = new DataOutputStream(cSock.getOutputStream());
			serverOutput.writeBytes(inp);
			Sleep(1000);
			serverOutput.close();
			Sleep(150);
			cSock.close();
			Sleep(150);
		} catch (IOException e) {
			System.out.println("WRITE ERROR:" + e.getMessage());
		}
	}
	
	public static void Sleep(int inp) {
		try {
			Thread.sleep(inp);
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}
