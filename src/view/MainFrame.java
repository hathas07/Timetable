package view;

import java.awt.*;
import javax.swing.*;

import timeTableController.TimeTableController;
import userController.UserController;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private UserController userController;
	private TimeTableController timeTableController;
	
	protected JMenuBar menuBar;
	JMenu userMenu;
	JMenuItem menuItemLogin, menuItemLogout, menuItemExit;
	
	public MainFrame(UserController userController,TimeTableController tTController){
		super("Timetable");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        this.userController = userController;
        this.timeTableController = tTController;
        
        String[] userInfo =  userController.usersToString();
        for(String user : userInfo) {System.out.println(user);}
        
        String[] roomInfo = tTController.roomsToString();
        for(String room : roomInfo) {System.out.println(room);}
               
        //CREATE PANEL
        JPanel window = (JPanel) this.getContentPane();
        setLayout(new BorderLayout());
        
        CreateMenuBar();
        
	}
	
	private void CreateMenuBar() {
        menuBar = new JMenuBar();     
        userMenu = new JMenu("User");    
        menuBar.add(userMenu);        
        
        menuItemLogin = new JMenuItem("Login");
        userMenu.add(menuItemLogin);
        menuItemLogout = new JMenuItem("Logout");
        userMenu.add(menuItemLogout);
        menuItemExit = new JMenuItem("Exit");
        userMenu.add(menuItemExit);
 
        this.add(menuBar, BorderLayout.NORTH);
        
        menuItemLogin.addActionListener((event) -> ActionMenuBar(menuItemLogin.getText()));
        menuItemLogout.addActionListener((event) -> ActionMenuBar(menuItemLogout.getText()));
        menuItemExit.addActionListener((event) -> ActionMenuBar(menuItemExit.getText()));
	}
	
	private void ActionMenuBar(String text) {
		if(text.equals("Login")) {
			new LoginView(userController, timeTableController);
			this.dispose();
					
		}else if(text.equals("Logout")) {
			System.out.println("LOGOUT");
			
		}else {
			System.out.println("EXIT");
			
		}
	}

}
