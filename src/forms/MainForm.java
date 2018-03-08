package forms;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JList;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.AbstractListModel;

import encryption.AES;
import encryption.Sha;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class MainForm extends JFrame {

	private JPanel contentPane;
	
	public static String uid;
	public static String AESKey;
	public static String fileName;
	public static int lastFileNumber;
	
	private JScrollPane scrollPane;
	private JList list;

	public MainForm() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		
		setTitle("My Diary");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(650, 743);
	    setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		AESKey = createAESKey(uid);
		
		JPanel jPanelBackground = new JPanel();
		jPanelBackground.setBounds(0, 0, 650, 743);
		contentPane.add(jPanelBackground);
		jPanelBackground.setLayout(null);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				createEntry();
				
			}
		});
		btnNewButton.setToolTipText("Add new entry");
		btnNewButton.setIcon(new ImageIcon(MainForm.class.getResource("/assets/add.png")));
		btnNewButton.setOpaque(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
	    btnNewButton.setForeground(Color.BLUE);
		btnNewButton.setBounds(560, 644, 50, 50);
		jPanelBackground.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("WELCOME AGAIN :)");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBackground(new Color(255, 255, 255, 80));
		lblNewLabel.setBounds(40, 23, 570, 57);
		jPanelBackground.add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBounds(40, 105, 570, 527);
		jPanelBackground.add(scrollPane);
		
		list = new JList();
		createJList(list);
		
		list.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {
		        	EntryRead er = null;
					try {
						er = new EntryRead(list.getSelectedIndex());
					} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException
							| NoSuchAlgorithmException | NoSuchPaddingException
							| InvalidAlgorithmParameterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					er.setVisible(true);
		        	
		        }
		    }
		});
		
		
		JLabel jLabelBackground = new JLabel("");
		jLabelBackground.setIcon(new ImageIcon(MainForm.class.getResource("/assets/mainBackground.png")));
		jLabelBackground.setBounds(0, 0, 650, 743);
		jPanelBackground.add(jLabelBackground);
	}
	
	
	private String[] entries() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException{
		
		ArrayList<String> entry = new ArrayList<String>();
		File file = new File(".users/." +uid+"/");
		String[] temp = file.list();
		
		int lastFileNumber = 0;
		
		for(String n : temp){
			if(n.charAt(0) == '.')
				continue;
			if(Integer.parseInt(n.substring(0, 8)) > lastFileNumber)
				lastFileNumber = Integer.parseInt(n.substring(0, 8));
			entry.add(n);
		}
		
		Collections.sort(entry);
		Collections.reverse(entry);
		
		String[] entries = new String[entry.size()];
		
		for(int i = 0; i < entry.size(); i++){
			

			try (BufferedReader br = new BufferedReader(new FileReader(".users/." + MainForm.uid + "/"+entry.get(i)))) {

				String sCurrentLine = br.readLine();

				sCurrentLine = AES.decrypt(sCurrentLine, AESKey);
				
				entries[i] = sCurrentLine;

			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		if(entries.length == 0){
			String[] entries2 = {"You don't hava any entry yet."};
			fileName = String.format("%08d", 0);
			return entries2;
		}
		
		this.lastFileNumber = lastFileNumber;
		fileName = String.format("%08d", lastFileNumber+1);
		return entries;
		
	}
	
	private void createEntry(){
		
		Entry entry = new Entry(this);
		entry.setVisible(true);
		this.setVisible(false);
		
	}
	
	private String createAESKey(String string){
		
		try {
			string = Sha.hashNsalt(string.substring(0, 20), string.substring(20));
			string = string.substring(1, 33);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return string;
	}
	
	private void createJList(JList list) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException{
		
		list.setFixedCellHeight(75);
		list.setModel(new AbstractListModel() {
			String[] values = entries();
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		scrollPane.setViewportView(list);
		
	}
	
	public void reset() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException{
		
		createJList(list);
		repaint();
		revalidate();
		
	}
}
