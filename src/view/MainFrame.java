package view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import timeTableController.TimeTableController;
import userController.UserController;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private UserController userController;
	private TimeTableController timeTableController;
	
	protected JMenuBar menuBar;
	protected JMenu userMenu,helpMenu;
	protected JMenuItem menuItemLogin, menuItemLogout, menuItemExit, menuItemHelp;
	
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
        
        String[] groupsInfo =  userController.groupsToString();
        for(String group : groupsInfo) {System.out.println(group);}
        
        String[] roomInfo = tTController.roomsToString();
        for(String room : roomInfo) {System.out.println(room);}
               
        //CREATE PANEL
        setLayout(new BorderLayout());
        
        CreateMenuBar();
        
        this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				int choix = JOptionPane.showConfirmDialog(MainFrame.this, "Etes-vous sur de vouloir quitter ?", "Fermeture", JOptionPane.YES_NO_OPTION);
				if (choix == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});
	}
	
	private void CreateMenuBar() {
        menuBar = new JMenuBar();     
        userMenu = new JMenu("User");
        helpMenu = new JMenu("Help");
        menuBar.add(userMenu); 
        menuBar.add(helpMenu);
        
        //sous boutons menu user
        menuItemLogin = new JMenuItem("Login");
        userMenu.add(menuItemLogin);
        userMenu.addSeparator();
        menuItemExit = new JMenuItem("Exit");
        userMenu.add(menuItemExit);
        menuItemHelp = new JMenuItem("Help");
        helpMenu.add(menuItemHelp);
        
        userMenu.setMnemonic('U');//ALT U pour ouvrir le menu user
		menuItemLogin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK));// ctrl L pour se log
		
		//evenement
		menuItemLogin.addActionListener((event) -> ActionMenuBar(menuItemLogin.getText()));
		menuItemExit.addActionListener((event) -> ActionMenuBar(menuItemExit.getText()));
		menuItemHelp.addActionListener((event) -> ActionMenuBar(menuItemHelp.getText()));
		
		this.add(menuBar, BorderLayout.NORTH);
	}
	
	private void ActionMenuBar(String text) {
		System.out.println(text);
		if(text.equals("Login")) {
			new LoginView(userController, timeTableController);
			this.dispose();
					
		}else if(text.equals("Help")){
			JOptionPane.showMessageDialog(MainFrame.this, "No help sorry....");
		}else {
			this.dispose();
		}
	}

}
