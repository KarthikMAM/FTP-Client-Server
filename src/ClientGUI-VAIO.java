import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class ClientGUI extends JFrame implements ActionListener,
		ListSelectionListener {

	private JPanel contentPanel;

	private JLabel path;
	private JButton getButton;
	private JButton backButton;
	private JButton lsButton;

	private JScrollPane scroll;
	private JList listBox;
	private Vector listData;

	private Socket clientSocket;
	private DataInputStream inputData;
	private DataOutputStream outputData;
	
	private static String home = "e:";
	private static String sep = "\\";
	private String currentFolder = home + sep;

	public ClientGUI() {
		// TODO Set the window attributes
		setTitle("TCP File Transfer");
		setSize(300, 500);
		setBackground(Color.GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// add the fields
		init();

		// Initialize the client
		try {
			clientSocket = new Socket("localhost", 3000);
			inputData = new DataInputStream(clientSocket.getInputStream());
			outputData = new DataOutputStream(clientSocket.getOutputStream());

			outputData.writeUTF("karthik");
			outputData.writeUTF("karthik");
			inputData.readInt();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void init() {
		// Create the panel to hold all the contents
		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		getContentPane().add(contentPanel);

		labelInit();
		listInit();
		buttonInit();
	}

	public void labelInit() {
		path = new JLabel();

		path.setText(currentFolder);
		path.setFont(new Font(path.getFont().getName(), Font.PLAIN, 15));
		contentPanel.add(path, BorderLayout.NORTH);
	}

	public void listInit() {
		listData = new Vector();
		listBox = new JList(listData);
		listBox.addListSelectionListener(this);

		scroll = new JScrollPane();
		scroll.getViewport().add(listBox);
		contentPanel.add(scroll, BorderLayout.CENTER);
	}

	public void buttonInit() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		contentPanel.add(buttonPanel, BorderLayout.SOUTH);

		backButton = new JButton();
		backButton.addActionListener(this);
		backButton.setText("BACK");
		buttonPanel.add(backButton, BorderLayout.WEST);

		lsButton = new JButton();
		lsButton.addActionListener(this);
		lsButton.setText("LS");
		buttonPanel.add(lsButton, BorderLayout.CENTER);

		getButton = new JButton();
		getButton.addActionListener(this);
		getButton.setText("GET");
		buttonPanel.add(getButton, BorderLayout.EAST);

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == listBox && !e.getValueIsAdjusting()) {
			String temp = (String) listBox.getSelectedValue();
			if (temp != null) {
				path.setText(currentFolder + temp + sep);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			if (e.getSource() == lsButton || e.getSource() == backButton) {
				listData = new Vector();

				String temp = (String) listBox.getSelectedValue();
				if (e.getSource() == lsButton) {
					if (temp != null) {
						if (temp.contains(".") == false) {
							path.setText(currentFolder += temp + sep);
						} else {
							JOptionPane.showMessageDialog(null,
									"Select only folders");
						}
					}
				} else {
					if (currentFolder.equals(home + sep) == false
							&& currentFolder.contains(home + sep)) {
						path.setText(currentFolder = new File(currentFolder)
								.getParent() + sep);
					}
				}

				outputData.writeInt(1);
				outputData.writeUTF(currentFolder);
				while ((temp = inputData.readUTF()).equals("null") == false) {
					listData.addElement(temp);
					listBox.setListData(listData);
					scroll.revalidate();
					scroll.repaint();
				}

			} else if (e.getSource() == getButton) {
				if (((String) listBox.getSelectedValue()).contains(".") == true) {
					outputData.writeInt(3);
					outputData.writeUTF(currentFolder
							+ (String) listBox.getSelectedValue());
					FileOutputStream target = new FileOutputStream("output.txt");
					byte[] data = new byte[inputData.readInt()];
					inputData.read(data, 0, data.length);
					target.write(data);
					target.close();
					JOptionPane.showMessageDialog(null,
							"The file has been downloaded to " + " \" " + currentFolder + "output.txt" + " \"");
				} else {
					JOptionPane.showMessageDialog(null,
							"Select a file to download");
				}
			}
		} catch (Exception ex) {

		}
	}

	public static void main(String[] args) {
		new ClientGUI().setVisible(true);
	}
}
