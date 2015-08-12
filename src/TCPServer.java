import java.io.*;
import java.net.*;

public class TCPServer {

	public TCPServer() throws Exception {
		// TODO Auto-generated constructor stub
		ServerSocket server = new ServerSocket(3000);
		Socket client = server.accept();

		DataInputStream inputData = new DataInputStream(client.getInputStream());
		DataOutputStream outputData = new DataOutputStream(
				client.getOutputStream());

		while (true) {
			if (FileTransfer.logIn(inputData.readUTF(), inputData.readUTF())) {
				outputData.writeInt(1);
				break;
			} else {
				outputData.writeInt(0);
			}
		}

		boolean flag = true;
		while (flag) {
			switch (inputData.readInt()) {
			case 1: {
				for (File i : FileTransfer.getChildren(inputData.readUTF())) {
					outputData.writeUTF(i.getName());
				}
				outputData.writeUTF("null");
				break;
			}
			case 3: {
				byte[] contents = FileTransfer.getFile(inputData.readUTF());
				outputData.writeInt(contents.length);
				outputData.write(contents);
				break;
			}
			case 4: {
				flag = false;
			}
			}
		}

		client.close();
		server.close();
	}

	public static void main(String[] args) {
		try {
			new TCPServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
