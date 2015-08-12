import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class Http {

	public Http() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) throws Exception {
		Socket s = new Socket(InetAddress.getByName("nptel.ssn.net"), 80);
		PrintWriter pw = new PrintWriter(s.getOutputStream());
		pw.print("GET / HTTP/1.0\r\n");
	    pw.print("Host: nptel.ssn.net\r\n\r\n");		
		pw.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String t;
		while((t = br.readLine()) != null) System.out.println(t);
		br.close();
	}
}
