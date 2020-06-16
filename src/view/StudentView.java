package view;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

import timeTableController.TimeTableController;
import userController.UserController;

public class StudentView extends JFrame {
	private UserController userController;
	private TimeTableController timeTableController;
	
	protected JMenuBar menuBar;
	JMenu userMenu, viewMenu, helpMenu;
	JMenuItem menuItemLogout, menuItemExit, menuItemHelp;
	
	public StudentView(UserController userController,TimeTableController tTController){
		super("Student Timetable");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
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
        setLayout(new BorderLayout());
        
        CreateMenuBar();
        
        this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				int choix = JOptionPane.showConfirmDialog(StudentView.this, "Etes-vous sur de vouloir quitter ?",
						"Choix de fermeture", JOptionPane.YES_NO_OPTION);
				if (choix == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});   
	}
	
	private void CreateMenuBar() {
        menuBar = new JMenuBar();     
        userMenu = new JMenu("User");
        viewMenu = new JMenu("View");
        helpMenu = new JMenu("Help");
        menuBar.add(userMenu); 
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        
        //sous boutons menu user
        menuItemLogout = new JMenuItem("Logout");
        userMenu.add(menuItemLogout);
        userMenu.addSeparator();
        menuItemExit = new JMenuItem("Exit");
        userMenu.add(menuItemExit);
        menuItemHelp = new JMenuItem("Help");
        helpMenu.add(menuItemHelp);
        
        //evenements
        menuItemLogout.addActionListener((event) -> ActionMenuBar(menuItemLogout.getText()));
        menuItemExit.addActionListener((event) -> ActionMenuBar(menuItemExit.getText()));
        menuItemHelp.addActionListener((event) -> ActionMenuBar((menuItemHelp.getText())));
        
        userMenu.setMnemonic('U');//ALT U pour ouvrir le menu user
        viewMenu.setMnemonic('V');
		menuItemLogout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK));// ctrl L pour se log
		
		this.add(menuBar, BorderLayout.NORTH);
	}
	
	
	
	private void ActionMenuBar(String text) {
		System.out.println(text);
		if(text.equals("Logout")) {
			new MainFrame(userController, timeTableController);
			JOptionPane.showMessageDialog(StudentView.this, "Disconnection");
			this.dispose();
					
		}else if(text.equals("Help")){
			JOptionPane.showMessageDialog(StudentView.this, "No help sorry....");
		}else {
			this.dispose();
		}
	}
	
}
