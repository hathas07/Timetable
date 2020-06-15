package view;

import java.awt.*;
import javax.swing.*;
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
		setSize(250, 150);
		setLayout(new FlowLayout());
        setLocationRelativeTo(null);
        
        this.userController = userController;
        this.timeTableController = timeTableController;
		
		lbLogin = new JLabel("Login :");
		lbPassword = new JLabel("Password :");
		tfLogin = new JTextField();
		tfLogin.setPreferredSize(new Dimension(150, 25));
		pfPassword = new JPasswordField();
		pfPassword.setPreferredSize(new Dimension(150, 25));
		btConfirm = new JButton("Confirm");
		
		add(lbLogin); add(tfLogin); add(lbPassword); add(pfPassword); add(btConfirm);
		
		btConfirm.addActionListener((event) -> ConfirmButton());
		
		this.addWindowListener(new WindowAdapter(){
			
			@Override
			public void windowClosing(WindowEvent e) {
				
			}
		});
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
				JOptionPane.showMessageDialog(LoginView.this, "Wrong Login/Password");
		}
		
	}

}
