package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;

import java.util.List;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import timeTableController.TimeTableController;
import userController.UserController;

public class AdminView extends JFrame {

	private static final long serialVersionUID = 1L;
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
		JFrame inputData = CreateWindow("Delete room(s)", 250);
		JPanel contentPane = (JPanel) inputData.getContentPane();
		contentPane.setLayout(new FlowLayout());
		
		String[] infoRoom = this.timeTableController.roomsIdToString();
		JList<String> listRoom = new JList<String>(infoRoom);
		listRoom.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listRoom.setPreferredSize(new Dimension(100, 200));
		
		contentPane.add(new JScrollPane(listRoom));
		
		JButton confirm = new JButton("Apply");
		contentPane.add(confirm);
		
		confirm.addActionListener((event) -> {
			
			List<String> roomSelected = new ArrayList<String>();
			roomSelected = listRoom.getSelectedValuesList();
			
			for(String room : roomSelected) {
				int roomINT = Integer.parseInt(room);
				timeTableController.removeRoom(roomINT);
			}
			
			inputData.dispose();
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
		JFrame inputData = CreateWindow("Delete group(s)", 250);
		JPanel contentPane = (JPanel) inputData.getContentPane();
		contentPane.setLayout(new FlowLayout());
		
		String[] infoGroup = this.userController.groupsIdToString();
		JList<String> listGroup = new JList<String>(infoGroup);
		listGroup.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listGroup.setPreferredSize(new Dimension(100, 200));
		
		contentPane.add(new JScrollPane(listGroup));
		
		JButton confirm = new JButton("Apply");
		contentPane.add(confirm);
		
		confirm.addActionListener((event) -> {
			
			List<String> groupsSelected = new ArrayList<String>();
			groupsSelected = listGroup.getSelectedValuesList();
			
			for(String group : groupsSelected) {
				int groupINT = Integer.parseInt(group);
				this.userController.removeGroup(this.adminLogin, groupINT);
			}
			
			inputData.dispose();
		});
	}
	
	private void ActionManageGroup() {
		JFrame inputData = CreateWindow("Associate student to a group", 500);
		JPanel contentPane = (JPanel) inputData.getContentPane();
		contentPane.setLayout(new FlowLayout());
		
		JLabel lbStudent = new JLabel("Student selected:");
		contentPane.add(lbStudent);
		
		String[] infoUser = this.userController.studentsLoginToString();
		JList<String> listUsers = new JList<String>(infoUser);
		listUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listUsers.setPreferredSize(new Dimension(100, 200));
		contentPane.add(new JScrollPane(listUsers));
		
		JLabel lbGroup = new JLabel("Group selected:");
		contentPane.add(lbGroup);
		
		String[] infoGroup = this.userController.groupsIdToString();
		JList<String> listGroup = new JList<String>(infoGroup);
		listGroup.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listGroup.setPreferredSize(new Dimension(100, 200));
		contentPane.add(new JScrollPane(listGroup));
		
		JButton confirm = new JButton("Apply");
		contentPane.add(confirm);
		
		confirm.addActionListener((event) -> {
			int selectedGroup = Integer.parseInt(listGroup.getSelectedValue());
			String selectedStudent = listUsers.getSelectedValue();
			this.userController.associateStudToGroup(this.adminLogin, selectedStudent, selectedGroup);
			inputData.dispose();
		});
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
		JFrame inputData = CreateWindow("Delete user(s)", 250);
		JPanel contentPane = (JPanel) inputData.getContentPane();
		contentPane.setLayout(new FlowLayout());
		
		String[] infoUser = this.userController.usersLoginToString();
		JList<String> listUsers = new JList<String>(infoUser);
		listUsers.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		listUsers.setPreferredSize(new Dimension(100, 200));
		
		contentPane.add(new JScrollPane(listUsers));
		
		JButton confirm = new JButton("Apply");
		contentPane.add(confirm);
		
		confirm.addActionListener((event) -> {
			
			List<String> usersSelected = new ArrayList<String>();
			usersSelected = listUsers.getSelectedValuesList();
			
			for(String user : usersSelected) {
				if(this.adminLogin != user) {this.userController.removeUser(this.adminLogin, user);}
			}
			
			inputData.dispose();
		});
		
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