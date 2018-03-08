package forms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import encryption.Sha;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.UIManager;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;

public class LoginPage extends JFrame {

	private JPanel contentPane;
	private JTextField jTFUsername;
	private JPasswordField jPFPassword;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JTextField textField_1;
	private JPanel loginPanel;
	private JPanel registerPanel;
	private JButton btnCreateAc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			} 
			catch (Exception ex) {
			ex.printStackTrace();
			}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage frame = new LoginPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginPage() {
		
		setTitle("My Diary");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(480,640);
	    setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		loginPanel = new JPanel();
		loginPanel.setBounds(0, 0, 472, 607);
		contentPane.add(loginPanel);
		loginPanel.setLayout(null);
		
		JLabel jLabelUsername = new JLabel("Username");
		jLabelUsername.setForeground(Color.WHITE);
		jLabelUsername.setBackground(Color.WHITE);
		jLabelUsername.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelUsername.setBounds(145, 195, 185, 52);
		loginPanel.add(jLabelUsername);
		
		jTFUsername = new JTextField();
		jTFUsername.setHorizontalAlignment(SwingConstants.CENTER);
		jTFUsername.setBounds(40, 249, 400, 45);
		loginPanel.add(jTFUsername);
		jTFUsername.setColumns(10);
		
		JLabel jLabelPassword = new JLabel("Password");
		jLabelPassword.setForeground(Color.WHITE);
		jLabelPassword.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelPassword.setBounds(145, 306, 185, 52);
		loginPanel.add(jLabelPassword);
		
		jPFPassword = new JPasswordField();
		jPFPassword.setHorizontalAlignment(SwingConstants.CENTER);
		jPFPassword.setBounds(40, 358, 400, 45);
		loginPanel.add(jPFPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String uid = Sha.hashNsalt(jTFUsername.getText(), jPFPassword.getText());
					
					File isExist = new File(".users/."+ uid + "/");
					if(!isExist.exists()){
						JOptionPane.showMessageDialog(null, "Username or Password wrong");
						return;
					}
					else if(isExist.exists()){
						
						MainForm.uid = uid;
						
					}
					
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					login();
				} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException
						| NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(40, 459, 195, 52);
		loginPanel.add(btnLogin);
		
		getRootPane().setDefaultButton(btnLogin);
		
		JButton btnRegiste = new JButton("Register");
		btnRegiste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				contentPane.remove(loginPanel);
				contentPane.add(registerPanel);
				
				repaint();
		        revalidate();
				
		        getRootPane().setDefaultButton(btnCreateAc);
			}
		});
		btnRegiste.setBounds(245, 459, 195, 52);
		loginPanel.add(btnRegiste);
		
		JLabel jLabelBackground = new JLabel("");
		jLabelBackground.setIcon(new ImageIcon(LoginPage.class.getResource("/assets/diary login.png")));
		jLabelBackground.setBounds(0, 0, 472, 607);
		loginPanel.add(jLabelBackground);
		
		
		
		registerPanel = new JPanel();
		registerPanel.setBackground(Color.WHITE);
		registerPanel.setBounds(0, 0, 472, 607);
		//contentPane.add(registerPanel);
		registerPanel.setLayout(null);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				contentPane.remove(registerPanel);
				contentPane.add(loginPanel);
				
				repaint();
		        revalidate();
		        
		        getRootPane().setDefaultButton(btnLogin);
				
			}
		});
		btnBack.setOpaque(true);
		btnBack.setBackground(new Color(255, 255, 255));
		btnBack.setBounds(40, 36, 117, 37);
		registerPanel.add(btnBack);
		
		JLabel label = new JLabel("*Username");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		label.setBackground(Color.WHITE);
		label.setBounds(148, 36, 185, 50);
		registerPanel.add(label);
		
		textField = new JTextField();
		textField.setBounds(40, 89, 400, 45);
		registerPanel.add(textField);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setColumns(10);
		
		JLabel label_1 = new JLabel("*Password");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.WHITE);
		label_1.setBounds(148, 144, 185, 50);
		registerPanel.add(label_1);
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBounds(40, 194, 400, 45);
		registerPanel.add(passwordField);
		
		JLabel label_2 = new JLabel("*Password Again");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setForeground(Color.WHITE);
		label_2.setBounds(123, 249, 235, 50);
		registerPanel.add(label_2);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField_1.setBounds(40, 299, 400, 45);
		registerPanel.add(passwordField_1);
		
		JLabel label_3 = new JLabel("E-Mail");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setForeground(Color.WHITE);
		label_3.setBounds(148, 354, 185, 50);
		registerPanel.add(label_3);
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setColumns(10);
		textField_1.setBounds(40, 404, 400, 45);
		registerPanel.add(textField_1);
		
		btnCreateAc = new JButton("Create Account");
		btnCreateAc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(textField.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Username can't be empty.");
					getRootPane().setDefaultButton(btnCreateAc);
					return;
				}
				if(passwordField.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Password can't be empty.");
					getRootPane().setDefaultButton(btnCreateAc);
					return;
				}
				if(passwordField_1.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Password can't be empty.");
					getRootPane().setDefaultButton(btnCreateAc);
					return;
				}
				if(!passwordField.getText().equals(passwordField_1.getText())){
					JOptionPane.showMessageDialog(null, "Passwords must be same.");
					getRootPane().setDefaultButton(btnCreateAc);
					return;
				}
				try {
					boolean f = new File(".users/").mkdir();
					String fn = Sha.hashNsalt(textField.getText(), passwordField.getText());
					boolean file = new File(".users/."+fn+"/").mkdir();
					if(!file){
						JOptionPane.showMessageDialog(null, "Username or Password has been taken.");
						getRootPane().setDefaultButton(btnCreateAc);
						return;
					}
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
				
				
				contentPane.remove(registerPanel);
				contentPane.add(loginPanel);
				
				repaint();
				revalidate();
				
				textField.setText("");
				textField_1.setText("");
				passwordField.setText("");
				passwordField_1.setText("");
				
				JOptionPane.showMessageDialog(null, "Account created. You can access now.");
				
				getRootPane().setDefaultButton(btnLogin);
				
			}
		});
		btnCreateAc.setBounds(40, 509, 400, 52);
		registerPanel.add(btnCreateAc);
		
		JLabel registerBackground = new JLabel("");
		registerBackground.setIcon(new ImageIcon(LoginPage.class.getResource("/assets/register background.png")));
		registerBackground.setBounds(0, 0, 472, 607);
		registerPanel.add(registerBackground);
		
	}
	
	private void login() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException{
		
		MainForm mf = new MainForm();
		mf.setVisible(true);
		this.hide();
		
	}
}
