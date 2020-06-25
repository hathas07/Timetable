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
		setResizable(false); //taille non modifiable permet de definir des tailles fixes pour les composants
		setAlwaysOnTop(true);
		setVisible(true);
		setSize(300, 150); //taille petite car non modifiable, elle doit convenir a toute taille d'écran
		JPanel contentPane = (JPanel) this.getContentPane();
        setLocationRelativeTo(null); //fenetre centrée
        
        this.userController = userController;
        this.timeTableController = timeTableController;
		timeTableController.loadDB();

        //placements des composants (boutons, textes, etc)
        placeComponents(contentPane);
		
        //ré-ouverture de la main frame en cas de fermeture de la login
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
		
		btConfirm.addActionListener((event) -> ConfirmButton()); //evenement du bouton

	}
	
	//fonction executée lors d'un clic sur le bouton de confirmation :
	@SuppressWarnings("deprecation")
	public void ConfirmButton() {
		enteredLogin = tfLogin.getText();
		enteredPassword  = pfPassword.getText();
		
		String loginRole = userController.getUserClass(enteredLogin, enteredPassword);
		
		//on ouvre la fenetre correspondant au role entré 
		switch(loginRole) {
			case "Administrator":
				JOptionPane.showMessageDialog(LoginView.this, "Connecting:Admin");
				this.dispose();
				break;
			
			case "Teacher":
				new TeacherView(userController, timeTableController, enteredLogin);
				JOptionPane.showMessageDialog(LoginView.this, "Connecting:Teacher");
				this.dispose();
				break;
				
			case "Student":
				new StudentView(userController, timeTableController, userController.getStudentGroup(enteredLogin));
				JOptionPane.showMessageDialog(LoginView.this, "Connecting:Student");
				this.dispose();
				break;
				
			//en cas de non correspondance	
			default:
				new MainFrame(userController, timeTableController);
				JOptionPane.showMessageDialog(LoginView.this, "Wrong Login/Password", "Error", JOptionPane.INFORMATION_MESSAGE);
				this.dispose();

		}
		
	}

}
