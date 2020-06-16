package view;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import timeTableController.TimeTableController;
import userController.UserController;

public class AdminView extends JFrame {

	private UserController userController;
	private TimeTableController timeTableController;
	
	protected JMenuBar menuBar;
	protected JMenu userMenu,helpMenu,adminRoomMenu,adminGroupMenu,adminUserMenu;
	protected JMenuItem mILogout,mIExit,mIHelp, mIRoomAdd, miRoomDel, mIUserDel, mIAdminAdd, mITeacherAdd, mIStudentAdd, mIGroupAdd, mIGroupDel, mIGroupManage;
	
	public AdminView(UserController userController, TimeTableController timeTableController) {
		super("Timetable - Administrator");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        
		this.userController = userController;
		this.timeTableController = timeTableController;
		
		CreateMenuBar();
	}

	private void CreateMenuBar() {
		 menuBar = new JMenuBar();     
	     userMenu = new JMenu("User");
	     helpMenu = new JMenu("Help");
	     adminRoomMenu = new JMenu("Manage Rooms");
	     adminGroupMenu = new JMenu("Manage Groups");
	     adminUserMenu = new JMenu("Manage Users");
	     
	     menuBar.add(userMenu); 
	     menuBar.add(adminRoomMenu);
	     menuBar.add(adminGroupMenu);
	     menuBar.add(adminUserMenu);
	     menuBar.add(helpMenu);
	        
	     //sous boutons menu user
	     mILogout = new JMenuItem("Logout");
	     userMenu.add(mILogout);
	     userMenu.addSeparator();
	     mIExit = new JMenuItem("Exit");
	     userMenu.add(mIExit);
	     mIHelp = new JMenuItem("Help");
	     helpMenu.add(mIHelp);
	        
	     userMenu.setMnemonic('U');//ALT U pour ouvrir le menu user
	     mILogout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK));// ctrl L pour se log
			
	     this.add(menuBar, BorderLayout.NORTH);
	}
	
}