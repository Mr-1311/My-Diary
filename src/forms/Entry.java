package forms;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.lgooddatepicker.components.DatePicker;

import encryption.AES;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;

public class Entry extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	
	private MainForm mf;

	public Entry(MainForm mf) {
		
		this.mf = mf;
		
		setResizable(false);
		setTitle("Diary");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		WindowListener exitListener = new WindowAdapter() {

		    @Override
		    public void windowClosing(WindowEvent e) {
		        exit();
		    }
		};
		addWindowListener(exitListener);
		setSize(570, 620);
	    setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(12, 0, 538, 75);
		contentPane.add(panel);
		panel.setLayout(null);

		textField = new JTextField();
		textField.setBorder(new LineBorder(UIManager.getColor("Button.darkShadow"), 2, true));
		textField.setBounds(0, 43, 538, 32);
		panel.add(textField);
		textField.setColumns(10);

		DatePicker dp = new DatePicker();
		dp.getComponentDateTextField().setEditable(false);
		dp.setBounds(364, 8, 174, 32);
		panel.add(dp);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 81, 540, 453);
		contentPane.add(scrollPane);

		JTextArea textArea = new JTextArea();
		textArea.setColumns (160);
		textArea.setLineWrap (true);
		textArea.setWrapStyleWord (false);
		scrollPane.setViewportView(textArea);
		dp.setDateToToday();

		JButton btnNewButton = new JButton("SAVE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(textArea.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Please write something.");
					return;
				}
				
				String entryName = dp.getDateStringOrEmptyString() + " - " + textField.getText();
				try {

					entryName = AES.encrypt(entryName, MainForm.AESKey);
					String content = AES.encrypt(textArea.getText(), MainForm.AESKey);
					String filePath = ".users/." + MainForm.uid + "/"+MainForm.fileName+".txt/";

					File file = new File(filePath);

					BufferedWriter bw = new BufferedWriter(
							new FileWriter(filePath));

					bw.write(entryName);
					bw.newLine();
					bw.write(content);
					bw.close();
					
					close();

				} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
						| IllegalBlockSizeException | BadPaddingException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvalidAlgorithmParameterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(432, 546, 118, 25);
		contentPane.add(btnNewButton);
		getRootPane().setDefaultButton(btnNewButton);
	}
	
	private void close() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException{
		
		mf.setVisible(true);
		mf.reset();
		this.dispose();
		JOptionPane.showMessageDialog(null, "Diary saved.");
		
	}
	
	private void exit(){
		mf.setVisible(true);
		this.dispose();
	}
	
}
