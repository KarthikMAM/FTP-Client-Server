import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class TCPClient {

	static Scanner stdIn = new Scanner(System.in);

	public TCPClient() throws Exception {
		// TODO Auto-generated constructor stub
		String currentFolder = "e:\\";

		Socket client = new Socket("localhost", 3000);

		DataInputStream inputData = new DataInputStream(client.getInputStream());
		DataOutputStream outputData = new DataOutputStream(
				client.getOutputStream());

		while(true) {
			System.out.print("Enter the User Name : ");
			outputData.writeUTF(stdIn.nextLine());
			System.out.print("Enter the Password  : ");
			outputData.writeUTF(stdIn.nextLine());
			if (inputData.readInt() == 0) {
				System.out.println("Authentication Failed");
			} else {
				System.out.println("Authentication Successful");
				break;
			}
		}
		
		boolean flag = true;
		while (flag) {
			int req = stdIn.nextInt();
			stdIn.nextLine();
			outputData.writeInt(req);

			switch (req) {
			case 1: {
				outputData.writeUTF(currentFolder);
				String temp = "";
				while ((temp = inputData.readUTF()).equals("null") == false) {
					System.out.println(temp);
				}
				break;
			}
			case 2: {
				String temp = stdIn.nextLine();
				currentFolder += temp + "\\";
				break;
			}
			case 3: {
				String temp = stdIn.nextLine();
				outputData.writeUTF(currentFolder + "\\" + temp);
				
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					FileOutputStream target = new FileOutputStream(chooser.getSelectedFile());
					byte[] data = new byte[inputData.readInt()];
					inputData.read(data, 0, data.length);
					target.write(data); 
					target.close();
				}
				break;
			}
			case 4: {
				flag = false;
			}
			}
		}

		client.close();
	}

	public static void main(String[] args) throws Exception {
		new TCPClient();
	}

}