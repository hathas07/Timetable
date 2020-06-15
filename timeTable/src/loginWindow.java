import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;


public class loginWindow extends JFrame{
	public loginWindow() {
		super("Login");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(300,150);
		this.setLocationRelativeTo(null);
		
		JPanel contentPane = (JPanel) this.getContentPane();
		placeComponents(contentPane);
	}
	
	private void openFirstWindow(ActionEvent event){
		FirstTimeTable window = new FirstTimeTable();
		window.setVisible(true);
	}
	
	private void openStudenttWindow(ActionEvent event){
		studentWindow window = new studentWindow();
		window.setVisible(true);
	}
	
	private void openTeacherWindow(ActionEvent event){
		teacherWindow window = new teacherWindow();
		window.setVisible(true);
	}
	
	private void openAdminWindow(ActionEvent event){
		adminWindow window = new adminWindow();
		window.setVisible(true);
	}
	

	private void placeComponents(JPanel panel) {

		panel.setLayout(null);

		JLabel userLabel = new JLabel("User:");
		userLabel.setBounds(10, 10, 80, 35);
		panel.add(userLabel);

		JTextField userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 28);
		panel.add(userText);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(10, 40, 80, 35);
		panel.add(passwordLabel);

		JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 40, 160, 28);
		panel.add(passwordText);

		JButton loginButton = new JButton("Login");
		loginButton.setBounds(100, 80, 100, 25);
		panel.add(loginButton);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = userText.getText();
				if(text.equals("admin")) { 
					System.out.println("Connexion admin");
					openAdminWindow(e);
					dispose();
				}else if(text.equals("teacher")){
					System.out.println("Connexion prof");
					openTeacherWindow(e);
					dispose();
				}else if(text.equals("student")) {
					System.out.println("Connexion eleve");
					openStudenttWindow(e);
					dispose();
				}else {
					//bad logs
					JOptionPane.showMessageDialog(loginButton, "Wrong logs");
					dispose();
					openFirstWindow(e);
				}
			}
		});

		
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		
		loginWindow window = new loginWindow();
		window.setVisible(true);
	}

}
