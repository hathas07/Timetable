package view;

import java.awt.*;

import javax.naming.LimitExceededException;
import javax.swing.*;

import java.awt.event.*;
import java.sql.Savepoint;
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

public class TeacherView extends JFrame {
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
	JPanel top = new JPanel(new GridLayout(4, 1));
	JPanel bookPanel = new JPanel();
	JPanel unbookPanel = new JPanel();
    Hashtable <Integer, Date> DateBegin = new Hashtable<Integer, Date>();
    Hashtable <Integer, Date> DateEnd = new Hashtable<Integer, Date>(); 
	Date bookDateBegin= new Date();
	Date bookDateEnd= new Date();
	String userLogin="";
	String[] groups= {""};
	JComboBox<String> groupBox= new JComboBox<>(groups);


	
	public TeacherView(UserController userController,TimeTableController tTController, String login){
		super("Student Timetable");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(1800, 800);
        setVisible(true);
        setLocationRelativeTo(null);
        this.userController = userController;
        this.timeTableController = tTController;
        userLogin=login;
        
        String[] userInfo =  userController.usersToString();
        for(String user : userInfo) {System.out.println(user);}
        
        String[] roomInfo = tTController.roomsToString();
        for(String room : roomInfo) {System.out.println(room);}
               
        //CREATE PANEL
        setLayout(new BorderLayout());
        
        currentMonday = getCurrentMonday();
		groups= userController.groupsIdToString();

        
        createTop();
        createTimeTable();
        createArrows();
        
        
        this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				timeTableController.saveDB();
				int choix = JOptionPane.showConfirmDialog(TeacherView.this, "Etes-vous sur de vouloir quitter ?",
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
	
	private void createTop() {
		JPanel groupPanel = new JPanel();
		JPanel boutons = new JPanel();
		
		JLabel groupLabel = new JLabel("Choose the group of your students :   ", SwingConstants.RIGHT);
		JLabel hourLabel = new JLabel("From: ");
		JLabel to = new JLabel("To: ");
		JLabel h1 = new JLabel("h");
		JLabel h2 = new JLabel("h");
		JLabel space = new JLabel("     ");
		JLabel dayLab = new JLabel("Day number: ");
		JLabel monthLab = new JLabel("Month number: ");
		JLabel yearLab = new JLabel("Year: ");
		JLabel roomLab = new JLabel("Room: ");
		JLabel unbookLabel = new JLabel("ID to unbook: ");
		
		JTextField idTxt = new JTextField(5);

		
		JMenuBar menuBar = createMenuBar();
		
		JButton book = new JButton("Book a time slot");
		JButton unbook = new JButton("Unbook a time slot");
		JButton cancel = new JButton("Cancel");
		JButton cancel2 = new JButton("Cancel");
		JButton bookValidation = new JButton("Booking validation");
		JButton unbookValidation = new JButton("Unbooking validation");
		JButton refresh = new JButton("Refresh");



		groups= userController.groupsIdToString();
		groupBox= new JComboBox<>(groups);
		
		String[] hoursTabBegin = {"8","9","10","11","12","13","14","15","16","17"};
		String[] hoursTabEnd = {"8","9","10","11","12","13","14","15","16","17","18"};
		String[] dayTab = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
		String[] monthTab = {"1","2","3","4","5","6","7","8","9","10","11","12"};
		String[] yearTab = {"2020","2021","2022"};
		String[] roomTab = timeTableController.roomsIdToString();
		JComboBox<String> hoursBegin= new JComboBox<>(hoursTabBegin);
		JComboBox<String> hoursEnd= new JComboBox<>(hoursTabEnd);
		String[] minTab = {"00","15","30","45"};
		JComboBox<String> minBegin= new JComboBox<>(minTab);
		JComboBox<String> minEnd= new JComboBox<>(minTab);
		JComboBox<String> day= new JComboBox<>(dayTab);
		JComboBox<String> month= new JComboBox<>(monthTab);
		JComboBox<String> year= new JComboBox<>(yearTab);
		JComboBox<String> room= new JComboBox<>(roomTab);

		


		//F5
		top.removeAll();
		top.updateUI();
		
		//event
		book.addActionListener((event) -> {
			top.remove(unbookPanel);
			top.add(bookPanel);
			top.updateUI();
			});
		unbook.addActionListener((event) -> {
			top.remove(bookPanel);
			top.add(unbookPanel);
			top.updateUI();
			});
		cancel.addActionListener((event) -> {
		//	bookPanel.remove(bookValidation);
			top.remove(bookPanel);
			top.updateUI();
			createTimeTable();

			});
		cancel2.addActionListener((event) -> {
			//bookPanel.remove(unbookValidation);
			top.remove(unbookPanel);
			top.updateUI();
			createTimeTable();

			});
		refresh.addActionListener((event) -> {
			top.updateUI();
			createTimeTable();
			});
		bookValidation.addActionListener((event) -> {
			int bookGrp, bookRoom;
			bookRoom = Integer.parseInt(room.getSelectedItem().toString());
			bookGrp = Integer.parseInt(groupBox.getSelectedItem().toString());
			bookDateBegin=setDate(day.getSelectedItem().toString(),month.getSelectedItem().toString(),year.getSelectedItem().toString(),hoursBegin.getSelectedItem().toString(),minBegin.getSelectedItem().toString());
			bookDateEnd=setDate(day.getSelectedItem().toString(),month.getSelectedItem().toString(),year.getSelectedItem().toString(),hoursEnd.getSelectedItem().toString(),minEnd.getSelectedItem().toString());

			timeTableController.addBooking(bookGrp, DateBegin.size()+10, userLogin, bookDateBegin, bookDateEnd, bookRoom);
			
			top.remove(bookPanel);
			top.updateUI();

			
			timeTableController.saveDB(); 
			createTimeTable();
		});
		unbookValidation.addActionListener((event) -> {
			int bookGrp, bookID;
			bookID=Integer.parseInt(idTxt.getText());
			System.out.println(bookID);
			bookGrp = Integer.parseInt(groupBox.getSelectedItem().toString());
			
			
			timeTableController.removeBook(bookGrp, bookID);
			
			top.remove(unbookPanel);
			top.updateUI();

			
			timeTableController.saveDB(); 
			createTimeTable();
		});


		
		//structure
		boutons.add(book);
		boutons.add(unbook);
		boutons.add(refresh);
		
		groupPanel.add(groupLabel);
		groupPanel.add(groupBox);
		
		
		
		bookPanel.add(dayLab);
		bookPanel.add(day);
		bookPanel.add(monthLab);
		bookPanel.add(month);
		bookPanel.add(yearLab);
		bookPanel.add(year);

		bookPanel.add(hourLabel);
		bookPanel.add(hoursBegin);
		bookPanel.add(h1);
		bookPanel.add(minBegin);
		bookPanel.add(to);
		bookPanel.add(hoursEnd);
		bookPanel.add(h2);
		bookPanel.add(minEnd);
		bookPanel.add(roomLab);
		bookPanel.add(room);
		bookPanel.add(space);
		bookPanel.add(cancel);
		bookPanel.add(bookValidation);
		
		unbookPanel.add(unbookLabel);
		unbookPanel.add(idTxt);
		unbookPanel.add(cancel2);
		unbookPanel.add(unbookValidation);


		
		
		top.add(menuBar);
		top.add(groupPanel);
		top.add(boutons);
		//top.add(bookPanel);
		this.add(top,BorderLayout.NORTH);
	}
	
	
	
	//test fonction
	@SuppressWarnings("deprecation")
	private Date setDate(String dateNbr, String month, String year, String hours, String minutes) {
		Date date=new Date();
		date.setDate(Integer.parseInt(dateNbr));
		date.setMonth(Integer.parseInt(month)-1); //0-11
		date.setYear(Integer.parseInt(year)-1900); //1900+year
		date.setHours(Integer.parseInt(hours));
		date.setMinutes(Integer.parseInt(minutes));

		return date;
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

	private JMenuBar createMenuBar() {
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
		
		return menuBar;
	}
	
	@SuppressWarnings("deprecation")
	private void createTimeTable() {
		double firstHour = 8.0; //heure de debut de l'edt
		int compteur4=0;
		int grpID=1;
        int year,month,dayNbr,day,hour,min;
		int currentMondayNbr = currentMonday.getDate();
		int currentMonth = currentMonday.getMonth();
		int currentYear = currentMonday.getYear();
		double heureComplet=0;
		Date limitDate1, limitDate2;
		String heureTxt;
		String[] id;
		boolean[] isEmpty = {true,true,true,true,true}; //test pour chaque jour
		
		grpID=Integer.parseInt(groupBox.getSelectedItem().toString());
		timeTable.removeAll();
		timeTable.updateUI();

		
		//date
        timeTableController.loadDB(); 
        DateBegin.clear();
        DateEnd.clear();
        timeTableController.getBookingsDate(grpID, DateBegin, DateEnd); //remplacer le 1 !
        System.out.println(DateBegin);
     //   System.out.println(DateEnd);
        timeTableController.saveDB(); 

   
		
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
				int index=0;
				  for(Date date : DateBegin.values()) {
					     limitDate1 =new Date(currentMonday.getTime() - (1000*60*60*24*1));
					     limitDate2 =new Date(currentMonday.getTime() + (1000*60*60*24*6));
					  
					     id=timeTableController.booksIdToString(grpID);
					   //  System.out.println("id: "+id[index]);
					     
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
							btn.setText("ID:"+id[index]+"   "+heureTxt);
			        	 }
			        	 index++;
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
			timeTableController.saveDB();
			new MainFrame(userController, timeTableController);
			JOptionPane.showMessageDialog(TeacherView.this, "Disconnection");
			this.dispose();
					
		}else if(text.equals("Help")){
			JOptionPane.showMessageDialog(TeacherView.this, "You can only read your timetable");
		}else {
			this.dispose();
		}
	}
	
}
