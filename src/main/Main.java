package main;

import timeTableController.TimeTableController;
import userController.UserController;
import view.MainFrame;

public class Main {
	
	private static void createAndShowUI(UserController userController,TimeTableController tTController) {
		new MainFrame(userController,tTController);
	}
	
	public static void main(String args[]){
		final String userfile="userDB.xml";
		final String tTfile="timeTableDB.xml";
	    UserController userController=new UserController(userfile);
	    TimeTableController tTController=new TimeTableController(tTfile);
		java.awt.EventQueue.invokeLater(new Runnable() {
	         public void run() {
	            createAndShowUI(userController,tTController);
	         }
	      });
	}
}
