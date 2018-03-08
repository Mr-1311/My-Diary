package forms;

import java.awt.BorderLayout;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import encryption.AES;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EntryRead extends JFrame {

	private JPanel contentPane;


	private int index;
	private String fileName;
	
	public EntryRead(int index) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		
		this.index = index;
		fileName = String.format("%08d", MainForm.lastFileNumber-index)+".txt";
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(750, 600);
	    setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setColumns (160);
		textArea.setLineWrap (true);
		textArea.setWrapStyleWord (false);
		scrollPane.setViewportView(textArea);
		
		try (BufferedReader br = new BufferedReader(new FileReader(".users/." + MainForm.uid + "/"+fileName+"/"))) {

			String title = br.readLine();
			
			title = AES.decrypt(title, MainForm.AESKey);
			textArea.setText("\n   "+title+"\n\n");
			
			String content;

			while ((content = br.readLine()) != null) {
				content = AES.decrypt(content, MainForm.AESKey);
				textArea.append(content);
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		repaint();
		revalidate();
	}

}
