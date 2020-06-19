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
	protected JMenuItem mILogout,mIExit,mIHelp, mIRoomAdd, mIRoomDel, mIUserDel, mIAdminAdd, mITeacherAdd, mIStudentAdd, mIGroupAdd, mIGroupDel, mIGroupManage;
	protected String adminLogin;
	
	public AdminView(UserController userController, TimeTableController timeTableController, String adminLogin) {
		super("Timetable - Administrator");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        
		this.userController = userController;
		this.timeTableController = timeTableController;
		this.adminLogin = adminLogin;
		
		CreateMenuBar();
		AssignButtonFunction();
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
	    mIExit = new JMenuItem("Exit");
	    mIHelp = new JMenuItem("Help");
	    userMenu.add(mILogout);
	    userMenu.addSeparator();
	    userMenu.add(mIExit);
	    helpMenu.add(mIHelp);
	     
	    //Sous boutons menu admin room
	    mIRoomAdd = new JMenuItem("Add Room");
	    mIRoomDel = new JMenuItem("Delete Room");
	    adminRoomMenu.add(mIRoomAdd);
	    adminRoomMenu.add(mIRoomDel);
	    
	    //Sous boutons menu admin group
	    mIGroupAdd = new JMenuItem("Add Group");
	    mIGroupDel = new JMenuItem("Delete Group");
	    mIGroupManage = new JMenuItem("Manage Groups");
	    adminGroupMenu.add(mIGroupAdd);
	    adminGroupMenu.add(mIGroupDel);
	    adminGroupMenu.add(mIGroupManage);
	     
	    //Sous boutons menu admin user
	    mIUserDel = new JMenuItem("Delete User");
	    mIAdminAdd = new JMenuItem("Add Admin");
	    mITeacherAdd = new JMenuItem("Add Teacher");
	    mIStudentAdd = new JMenuItem("Add Student");
	    adminUserMenu.add(mIStudentAdd);
	    adminUserMenu.add(mITeacherAdd);
	    adminUserMenu.add(mIAdminAdd);
	    adminUserMenu.add(mIUserDel);

	    this.add(menuBar, BorderLayout.NORTH);
	}
	
	private void AssignButtonFunction(){
	    mILogout.addActionListener((event) -> ActionUserMenu(mILogout.getText()));
	    mIExit.addActionListener((event) -> ActionUserMenu(mIExit.getText()));
	    mIHelp.addActionListener((event) -> ActionUserMenu(mIHelp.getText()));
	    
	    
	    mIRoomAdd.addActionListener((event) -> ActionAddRoom());
	    mIRoomDel.addActionListener((event) -> ActionDeleteRoom()); 
	    
	    
	    mIGroupAdd.addActionListener((event) -> ActionAddGroup());
	    mIGroupDel.addActionListener((event) -> ActionDeleteGroup());
	    mIGroupManage.addActionListener((event) -> ActionManageGroup());
	    
	    mIUserDel.addActionListener((event) -> ActionDeleteUser());
	    mITeacherAdd.addActionListener((event) -> ActionAddUser("Teacher"));
	    mIStudentAdd.addActionListener((event) -> ActionAddUser("Student"));
	    mIAdminAdd.addActionListener((event) -> ActionAddUser("Admin"));
	    
	}
	
	private void ActionUserMenu(String buttonName) {
		switch(buttonName) {
			case "Logout":
				new MainFrame(userController, timeTableController);
				JOptionPane.showMessageDialog(AdminView.this, "Disconnection");
				this.dispose();
				break;
				
			case "Exit":
				this.dispose();
				break;
				
			case "Help":
				JOptionPane.showMessageDialog(AdminView.this, "No help sorry....");
				break;
		}
	}

	private void ActionAddRoom() {
		//Creation fenetre
		JFrame inputData = CreateWindow("Add a room", 150);
		JPanel contentPane = (JPanel) inputData.getContentPane();
		contentPane.setLayout(null);
		
		JLabel lbRoomID = new JLabel("Room Id:");
		lbRoomID.setBounds(10, 8, 80, 35);
		
		JTextField tfRoomID = new JTextField();
		tfRoomID.setBounds(100, 10, 160, 28);
		
		JLabel lbRoomCapacity = new JLabel("Capacity:");
		lbRoomCapacity.setBounds(10, 38, 80, 35);
		
		JTextField tfRoomCapacity = new JTextField();
		tfRoomCapacity.setBounds(100, 40, 160, 28);
		
		JButton btConfirm = new JButton("Apply");
		btConfirm.setBounds(100, 80, 100, 25);
		
		contentPane.add(lbRoomID); contentPane.add(tfRoomID); contentPane.add(lbRoomCapacity); contentPane.add(tfRoomCapacity); contentPane.add(btConfirm);

		//Action bouton
		btConfirm.addActionListener((event) -> {
			int roomID = Integer.parseInt(tfRoomID.getText());
			int roomCapacity = Integer.parseInt(tfRoomCapacity.getText());
			this.timeTableController.addRoom(roomID, roomCapacity);
			this.timeTableController.saveDB();
			inputData.dispose();
		});

	}
	
	private void ActionDeleteRoom() {
		JFrame inputData = CreateWindow("Delete a room", 500);
		JPanel contentPane = (JPanel) inputData.getContentPane();
		contentPane.setLayout(new FlowLayout());
		
		String[] infoRoom = this.timeTableController.roomsIdToString();
		JList listRoom = new JList(infoRoom);
		listRoom.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listRoom.setSelectedIndex(0);
		
		contentPane.add(new JScrollPane(listRoom));
		
		JButton confirm = new JButton("Apply");
		contentPane.add(confirm);
		
		confirm.addActionListener((event) -> {
			int roomSelected = Integer.parseInt((String) listRoom.getSelectedValue());
			timeTableController.removeRoom(roomSelected);
		});
	}
	
	private void ActionAddGroup() {
		JFrame inputData = CreateWindow("Add a group", 150);
		JPanel contentPane = (JPanel) inputData.getContentPane();
		contentPane.setLayout(null);
		
		JLabel lbGroupID = new JLabel("Group Id:");
		lbGroupID.setBounds(10, 8, 80, 35);
		
		JTextField tfGroupID = new JTextField();
		tfGroupID.setBounds(100, 10, 160, 28);
		
		JButton btConfirm = new JButton("Apply");
		btConfirm.setBounds(100, 80, 100, 25);
		
		contentPane.add(lbGroupID); contentPane.add(tfGroupID); contentPane.add(btConfirm);
		
		//Action bouton
		btConfirm.addActionListener((event) -> {
			int groupID = Integer.parseInt(tfGroupID.getText());
			this.userController.addGroup(this.adminLogin, groupID);
			this.userController.saveDB();
			inputData.dispose();
		});
	}
	
	private void ActionDeleteGroup() {
		
	}
	
	private void ActionManageGroup() {
		
	}
	
	private void ActionAddUser(String rank) {
		//Creation fenetre
		JFrame inputData = CreateWindow("Add a " + rank, 250);
		JPanel contentPane = (JPanel) inputData.getContentPane();
		contentPane.setLayout(null);
		
		JLabel lbLogin = new JLabel("Login:");
		lbLogin.setBounds(10, 8, 80, 35);
		
		JTextField tfLogin= new JTextField();
		tfLogin.setBounds(100, 10, 160, 28);
		
		JLabel lbID = new JLabel("ID:");
		lbID.setBounds(10, 38, 80, 35);
		
		JTextField tfID = new JTextField();
		tfID.setBounds(100, 40, 160, 28);
		
		JLabel lbName = new JLabel("Name:");
		lbName.setBounds(10, 68, 80, 35);
		
		JTextField tfName = new JTextField();
		tfName.setBounds(100, 70, 160, 28);
		
		JLabel lbSurname = new JLabel("Surname:");
		lbSurname.setBounds(10, 98, 80, 35);
		
		JTextField tfSurname = new JTextField();
		tfSurname.setBounds(100, 100, 160, 28);
		
		JLabel lbPwd = new JLabel("Password:");
		lbPwd.setBounds(10, 128, 80, 35);
		
		JPasswordField pfPwd = new JPasswordField();
		pfPwd.setBounds(100, 130, 160, 28);
		
		JButton btConfirm = new JButton("Apply");
		btConfirm.setBounds(100, 175, 100, 25);
		
		contentPane.add(lbName); contentPane.add(tfName);
		contentPane.add(lbSurname); contentPane.add(tfSurname);
		contentPane.add(lbID); contentPane.add(tfID);
		contentPane.add(lbLogin); contentPane.add(tfLogin);
		contentPane.add(lbPwd); contentPane.add(pfPwd);
		contentPane.add(btConfirm);
		
		//Action bouton
		btConfirm.addActionListener((event) -> {
			int id = Integer.parseInt(tfID.getText());
			String login = tfLogin.getText();
			String name = tfName.getText();
			String surname = tfSurname.getText();
			String pwd = pfPwd.getText();
			switch(rank) {
				case "Student":
					this.userController.addStudent(this.adminLogin, login, id, name, surname, pwd);
					break;
				
				case "Admin":
					this.userController.addAdmin(this.adminLogin, login, id, name, surname, pwd);
					break;
					
				case "Teacher":
					this.userController.addTeacher(this.adminLogin, login, id, name, surname, pwd);
					break;
			}
			this.userController.saveDB();
			inputData.dispose();
		});
	}
	
	private void ActionDeleteUser() {
		
	}
	
	private JFrame CreateWindow(String windowName, int windowLength) {
		JFrame window = new JFrame(windowName);
		window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		window.setType(Window.Type.UTILITY);
		window.setResizable(false);
		window.setAlwaysOnTop(true);
		window.setVisible(true);
		window.setSize(300, windowLength);
		window.setLocationRelativeTo(null);		
		return window;
	}
}