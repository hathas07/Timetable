package view;

import java.awt.*;

import javax.naming.LimitExceededException;
import javax.swing.*;

import java.awt.event.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.Set;

import timeTableController.ITimeTableController;
import timeTableController.TimeTableController;
import userController.UserController;

public class StudentView extends JFrame {
	private UserController userController;
	private TimeTableController timeTableController;
	
	protected JMenuBar menuBar;
	JMenu userMenu, helpMenu;
	JMenuItem menuItemLogout, menuItemExit, menuItemHelp;
	int studentGrp;
	Date currentMonday;
	int rows=44;
	int cols=6;
	JPanel timeTable = new JPanel(new GridLayout(rows, cols));
	
	public StudentView(UserController userController,TimeTableController tTController, int grp){
		super("Student Timetable");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(1800, 800);
        setVisible(true);
        setLocationRelativeTo(null);
        this.userController = userController;
        this.timeTableController = tTController;
        
        String[] userInfo =  userController.usersToString();
        for(String user : userInfo) {System.out.println(user);}
        
        String[] roomInfo = tTController.roomsToString();
        for(String room : roomInfo) {System.out.println(room);}
        
        studentGrp=grp; //on recupere le grp de l'eleve qui s'est connecté
        
       // System.out.println(String.valueOf(studentGrp));

               
        //CREATE PANEL
        setLayout(new BorderLayout());
        
        currentMonday = getCurrentMonday();
        
        
        
        createMenuBar();
        createTimeTable();
        createArrows();
        
        
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
	
	private void createArrows() {
		JPanel arrows = new JPanel(new GridLayout(2, 2));
		JButton leftArr = new JButton("<-");
		JButton rightArr = new JButton("->");
		leftArr.addActionListener((event) -> createPreviousWeek());
		rightArr.addActionListener((event) -> createNextWeek());
		
		arrows.add(new JLabel("Previous week", SwingConstants.CENTER));
		arrows.add(new JLabel("Next week", SwingConstants.CENTER));
		arrows.add(leftArr);
		arrows.add(rightArr);
		this.add(arrows,BorderLayout.SOUTH);
	}
	
	private void createPreviousWeek() {
		// TODO Auto-generated method stub
		int nbrDeJour = -7;
		currentMonday =new Date(currentMonday.getTime() + (1000*60*60*24*nbrDeJour));//incremente la date de "nbrDeJour" jours
		createTimeTable();
	}

	private void createNextWeek() {
		// TODO Auto-generated method stub
		int nbrDeJour = 7;
		currentMonday =new Date(currentMonday.getTime() + (1000*60*60*24*nbrDeJour));//incremente la date de "nbrDeJour" jours
		createTimeTable();
	}

	private void createMenuBar() {
        menuBar = new JMenuBar();     
        userMenu = new JMenu("User");
        helpMenu = new JMenu("Help");
        menuBar.add(userMenu); 
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
		menuItemLogout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK));// ctrl L pour se log
		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
		
		this.add(menuBar, BorderLayout.NORTH);
	}
	
	@SuppressWarnings("deprecation")
	private void createTimeTable() {
		double firstHour = 8.0; //heure de debut de l'edt
		int compteur4=0;
        int year,month,dayNbr,day,hour,min;
		int currentMondayNbr = currentMonday.getDate();
		int currentMonth = currentMonday.getMonth();
		int currentYear = currentMonday.getYear();
		double heureComplet=0;
		Date limitDate1, limitDate2;
		String heureTxt;
		boolean[] isEmpty = {true,true,true,true,true}; //test pour chaque jour
		
		timeTable.removeAll();
		timeTable.updateUI();

		
		//date
        timeTableController.loadDB(); 
      //  userController.loadDB();
        Hashtable <Integer, Date> DateBegin = new Hashtable<Integer, Date>();
        Hashtable <Integer, Date> DateEnd = new Hashtable<Integer, Date>(); 
        timeTableController.getBookingsDate(studentGrp, DateBegin, DateEnd); 
        System.out.println(DateBegin);
     //   System.out.println(DateEnd);
        
   
		
		timeTable.add(new JLabel("<html>Day:<br><br>Hour:</html>", SwingConstants.CENTER));
		
		//jours
		createTimeTableDays();
		
		
		//heures
		for(int i=1; i<=rows-1; i++) {
			if(firstHour==(int)firstHour) {
			timeTable.add(new JLabel(firstHour+"h", SwingConstants.RIGHT));
			}
			else timeTable.add(new JLabel("", SwingConstants.CENTER));
		
			for(int j=1; j<=cols-1; j++) {	
				JButton btn = new JButton();
				  for(Date date : DateBegin.values()) {
					     limitDate1 =new Date(currentMonday.getTime() - (1000*60*60*24*1));
					     limitDate2 =new Date(currentMonday.getTime() + (1000*60*60*24*6));
					  
					     year = date.getYear();
					     month=date.getMonth();
					     dayNbr = date.getDate();
			        	 day = date.getDay();
			        	 hour = date.getHours();
			        	 min = date.getMinutes();
			        	 heureComplet = hour + min*0.01;//heure + min en décimal
			        	 //modifier condition pour changement de mois (format date)
			        	 if(heureComplet==firstHour && day==j  && date.after(limitDate1) && date.before(limitDate2)) {
			        		isEmpty[j-1]=false;
			 				heureTxt = String.valueOf(heureComplet+"h");
							btn.setText(heureTxt);
			        	 }
			      }
				  for(Date date : DateEnd.values()) {
					  	 limitDate1 =new Date(currentMonday.getTime() - (1000*60*60*24*1));
					     limitDate2 =new Date(currentMonday.getTime() + (1000*60*60*24*6));
					  
					     year = date.getYear();
					     month=date.getMonth();
					     dayNbr = date.getDate();
			        	 day = date.getDay();
			        	 hour = date.getHours();
			        	 min = date.getMinutes();
			        	 heureComplet = hour + min*0.01;//heure + min en décimal
			        	 if(heureComplet==firstHour && day==j  && date.after(limitDate1) && date.before(limitDate2)) {
			        		isEmpty[j-1]=true;
			        		heureTxt = String.valueOf(heureComplet+"h");
							btn.setText(heureTxt);
			        	 }
			      }
	
				if(isEmpty[j-1]==false) btn.setBackground(Color.BLUE);
				timeTable.add(btn);
			}
			
			firstHour=firstHour+0.15;
			compteur4 ++;
			if (compteur4==4) {
				compteur4=0;
				firstHour=firstHour+0.4;
			}
			firstHour=Math.round(firstHour*10000.0)/10000.0; //arrondi
		}

		this.add(timeTable, BorderLayout.CENTER);
	}
	
	
	//trouve le lundi de la semaine en cours ou de la semaine prochaine si c'est le weekend
	@SuppressWarnings("deprecation")
	private Date getCurrentMonday() {
		Date today, currentMonday = new Date();
		int day=1; //jour de base lundi
		Calendar calendar = Calendar.getInstance();
		
		today = calendar.getTime(); //jour actuel	
		day = today.getDay();


		if(day>0 && day<6) { //si le jour actuel est entre lundi et vendredi
			calendar.add(Calendar.DAY_OF_YEAR, -(day-1));
			currentMonday=calendar.getTime();
		}else if(day==0) {
			calendar.add(Calendar.DAY_OF_YEAR, 1);// si dimanche, retourne le lendemain
			currentMonday=calendar.getTime();
		}else if(day==6) {
			calendar.add(Calendar.DAY_OF_YEAR, 2);// si samedi, retourne le surlendemain
			currentMonday=calendar.getTime();
		}else System.out.println("problème de date");
		
		return currentMonday;
	}
	
	@SuppressWarnings("deprecation")
	private void createTimeTableDays() {
		String tempDay="";
		int taille=10; //taille de la date
		Date tempDate = currentMonday;

		for(int i=1; i<6; i++) {
			tempDay=String.valueOf(tempDate);
			tempDay=tempDay.substring(0, taille); //on reduit la date pour ne pas avoir l'heure

			timeTable.add(new JLabel(tempDay, SwingConstants.CENTER));
			tempDate=new Date(tempDate.getTime() + (1000*60*60*24));//incremente la date de 1 jour
		}

	}
	
	private void ActionMenuBar(String text) {
		System.out.println(text);
		if(text.equals("Logout")) {
			new MainFrame(userController, timeTableController);
			JOptionPane.showMessageDialog(StudentView.this, "Disconnection");
			this.dispose();
					
		}else if(text.equals("Help")){
			JOptionPane.showMessageDialog(StudentView.this, "You can only read your timetable");
		}else {
			this.dispose();
		}
	}
	
}
