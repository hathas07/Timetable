package view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import timeTableController.TimeTableController;
import userController.UserController;

public class TeacherView extends JFrame{
	private UserController userController;
	private TimeTableController timeTableController;
	
	protected JMenuBar menuBar;
	JMenu userMenu, modifyMenu, helpMenu;
	JMenuItem menuItemLogout, menuItemExit, menuItemModify,menuItemHelp;
	
	public TeacherView(UserController userController,TimeTableController tTController){
		super("Teacher Timetable");
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
				int choix = JOptionPane.showConfirmDialog(TeacherView.this, "Etes-vous sur de vouloir quitter ?",
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
        modifyMenu = new JMenu("Modify");
        helpMenu = new JMenu("Help");
        menuBar.add(userMenu); 
        menuBar.add(modifyMenu);
        menuBar.add(helpMenu);
        
        //sous boutons menu user
        menuItemLogout = new JMenuItem("Logout");
        userMenu.add(menuItemLogout);
        userMenu.addSeparator();
        menuItemExit = new JMenuItem("Exit");
        userMenu.add(menuItemExit);
        menuItemModify = new JMenuItem("Modify Timetables");
        modifyMenu.add(menuItemModify);
        menuItemHelp = new JMenuItem("Help");
        helpMenu.add(menuItemHelp);
        
        //evenements
        menuItemLogout.addActionListener((event) -> ActionMenuBar(menuItemLogout.getText()));
        menuItemExit.addActionListener((event) -> ActionMenuBar(menuItemExit.getText()));
        menuItemHelp.addActionListener((event) -> ActionMenuBar((menuItemHelp.getText())));
        
        userMenu.setMnemonic('U');//ALT U pour ouvrir le menu user
        modifyMenu.setMnemonic('V');
		menuItemLogout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK));// ctrl L pour se log
		
		this.add(menuBar, BorderLayout.NORTH);
	}
	
	
	
	private void ActionMenuBar(String text) {
		System.out.println(text);
		if(text.equals("Logout")) {
			new MainFrame(userController, timeTableController);
			JOptionPane.showMessageDialog(TeacherView.this, "Disconnection");
			this.dispose();
					
		}else if(text.equals("Help")){
			JOptionPane.showMessageDialog(TeacherView.this, "No help sorry....");
		}else {
			this.dispose();
		}
	}
}
