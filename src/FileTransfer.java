import java.io.*;

public class FileTransfer {

	public FileTransfer() {
		// TODO Auto-generated constructor stub
	}

	String location = "";

	public static File[] getChildren(File folder) {
		return folder.listFiles();
	}

	public static File getParent(File folder) {
		return folder.getParentFile();
	}

	public static File[] getChildren(String folderPath) {
		return new File(folderPath).listFiles();
	}

	public static File getParent(String folderPath) {
		return new File(folderPath).getParentFile();
	}

	public static byte[] getFile(String filePath) throws Exception {
		File file = new File(filePath);

		FileInputStream source = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		source.read(data);

		source.close();

		return data;
	}

	public static boolean logIn(String usrName, String password) {
		System.out.println("User attempt : " + usrName);
		return usrName.equals("karthik") && password.equals("karthik");
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		/*
		 * FileOutputStream target = new FileOutputStream("output.txt");
		 * target.write(getFile("hello.txt")); target.close();
		 */

		for (File i : getChildren(new File("e:\\e-Learning\\"))) {
			System.out.println(i.getParentFile() + i.getName());
		}
	}

}
