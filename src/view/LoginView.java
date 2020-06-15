package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import java.awt.event.*;

import timeTableController.TimeTableController;
import userController.UserController;

public class LoginView extends JFrame{

	private static final long serialVersionUID = 1L;
	private JLabel lbLogin, lbPassword;
	private JTextField tfLogin;
	private JPasswordField pfPassword;
	private JButton btConfirm;
	protected String enteredLogin;
	protected String enteredPassword;	
	private UserController userController;
	private TimeTableController timeTableController;
	
	public LoginView(UserController userController, TimeTableController timeTableController) {
		super("Login");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setAlwaysOnTop(true);
		setVisible(true);
		setSize(300, 150);
		JPanel contentPane = (JPanel) this.getContentPane();
        setLocationRelativeTo(null);
        
        this.userController = userController;
        this.timeTableController = timeTableController;
        
        placeComponents(contentPane);
		
		this.addWindowListener(new WindowAdapter(){
			
			@Override
			public void windowClosing(WindowEvent e) {
				new MainFrame(userController, timeTableController);
			}
		});
	}
	
	private void placeComponents(JPanel panel) {

		panel.setLayout(null);

		lbLogin = new JLabel("Login :");
		lbLogin.setBounds(10, 8, 80, 35);
		
		lbPassword = new JLabel("Password :");
		lbPassword.setBounds(10, 38, 80, 35);
		
		tfLogin = new JTextField(20);
		tfLogin.setBounds(100, 10, 160, 28);
		
		pfPassword = new JPasswordField(20);
		pfPassword.setBounds(100, 40, 160, 28);
		btConfirm = new JButton("Confirm");
		btConfirm.setBounds(100, 80, 100, 25);
		
		panel.add(lbLogin); panel.add(tfLogin); panel.add(lbPassword); panel.add(pfPassword); panel.add(btConfirm);
		
		btConfirm.addActionListener((event) -> ConfirmButton());

	}
	
	public void ConfirmButton() {
		enteredLogin = tfLogin.getText();
		enteredPassword  = pfPassword.getText();
		
		String loginRole = userController.getUserClass(enteredLogin, enteredPassword);
		
		switch(loginRole) {
			case "Administrator":
				this.dispose();
				JOptionPane.showMessageDialog(LoginView.this, "Connecting:Admin");
				break;
			
			case "Teacher":
				this.dispose();
				JOptionPane.showMessageDialog(LoginView.this, "Connecting:Teacher");
				break;
				
			case "Student":
				this.dispose();
				JOptionPane.showMessageDialog(LoginView.this, "Connecting:Student");
				break;
				
			default:
				this.dispose();
				new MainFrame(userController, timeTableController);
				JOptionPane.showMessageDialog(LoginView.this, "Wrong Login/Password", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

}
