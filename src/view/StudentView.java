package view;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import timeTableController.TimeTableController;
import userController.UserController;

public class StudentView extends JFrame {
	private static final long serialVersionUID = 1L;
	private UserController userController;
	private TimeTableController timeTableController;
	
	protected JMenuBar menuBar;
	JMenu userMenu, helpMenu;
	JMenuItem menuItemLogout, menuItemExit, menuItemHelp;
	int studentGrp; //le groupe de l'élève
	Date currentMonday; //le lundi de la semaine en cours ou lundi prochain si on est le weekend
	int rows=44; //lignes d'emploi du temps : actuellement 11h divisé en 4 créneaux d'un quart d'heure
	int cols=6; //colonnes
	JPanel timeTable = new JPanel(new GridLayout(rows, cols)); //création d'un panel de type grilles
	
	public StudentView(UserController userController,TimeTableController tTController, int grp){
		super("Student Timetable");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //on ne ferme pas directement, on demande la confirmation
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
               
        //CREATE PANEL
        setLayout(new BorderLayout());
        
        currentMonday = getCurrentMonday();
        
        createMenuBar();
        createTimeTable();
        createArrows();
        
        //message de confirmation avant fermeture
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
	
	//creation des boutons pour changer de semaine (précédente ou suivante)
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
	
	//fonction pour acceder a la semaine précédente
	private void createPreviousWeek() {
		//on enleve 7 jours au currentMonday
		int nbrDeJour = -7;
		currentMonday =new Date(currentMonday.getTime() + (1000*60*60*24*nbrDeJour));//incremente la date de "nbrDeJour" jours
		createTimeTable(); //on recharge pour mettre a jour
	}

	//de meme que pour previous week mais avec la semaine suivante
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
		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));// ctrl E pour quitter
		
		this.add(menuBar, BorderLayout.NORTH);
	}
	
	@SuppressWarnings("deprecation")
	private void createTimeTable() {
		double firstHour = 8.0; //heure de debut de l'edt
		int compteur4=0; //compteur permettant de contourner les problèmes de compatibilité entre base 60 et base 10
        int year,month,dayNbr,day,hour,min;
		double heureComplet=0; //heure + min
		Date limitDate1, limitDate2; //date limite permettant de savoir si le créneau appartient a la semaine actuelle
		String heureTxt; //string affiché sur les créneaux
		boolean[] isEmpty = {true,true,true,true,true}; //test pour chaque jour (Lun,Mar,Mer,Jeu,Ven)
		
		timeTable.removeAll();
		timeTable.updateUI();
		
		//date
        timeTableController.loadDB(); 
        Hashtable <Integer, Date> DateBegin = new Hashtable<Integer, Date>();
        Hashtable <Integer, Date> DateEnd = new Hashtable<Integer, Date>(); 
        timeTableController.getBookingsDate(studentGrp, DateBegin, DateEnd); //mise a jour des hashtable avec la BDD selon le groupe de l'étudiant 
        System.out.println("Dates de début:"+DateBegin);
        
   
		
		timeTable.add(new JLabel("<html>Day:<br><br>Hour:</html>", SwingConstants.CENTER));
		
		//jours
		createTimeTableDays();
		
		
		//heures
		for(int i=1; i<=rows-1; i++) {
			if(firstHour==(int)firstHour) {
			timeTable.add(new JLabel(firstHour+"h", SwingConstants.RIGHT));
			}
			else timeTable.add(new JLabel("", SwingConstants.CENTER));
		
			//j correspond au jour
			for(int j=1; j<=cols-1; j++) {	
				JButton btn = new JButton();
				  for(Date date : DateBegin.values()) {
						 //les dates limitent servent a delimiter la semaine actuelle 
					     limitDate1 =new Date(currentMonday.getTime() - (1000*60*60*24*1));//date limite1 correspond a la veille du lundi actuel
					     limitDate2 =new Date(currentMonday.getTime() + (1000*60*60*24*6));//date limite correspond au samedi de la semaine actuelle
					  
						 //on recupere les données du créneau actuel de debut
					     year = date.getYear();
					     month = date.getMonth();
					     dayNbr = date.getDate();
			        	 day = date.getDay();
			        	 hour = date.getHours();
			        	 min = date.getMinutes();
			        	 heureComplet = hour + min*0.01;//heure + min en décimal
			        	 
			        	 //si l'heure correspond et le bon jour de la semaine et la bonne semaine

			        	 if(heureComplet==firstHour && day==j  && date.after(limitDate1) && date.before(limitDate2)) {
			        		isEmpty[j-1]=false; //on modifie le tableau pour indiquer que ce créneau est réservé
			        		
			        		//on affiche l'heure du créneau sur le bouton
			 				heureTxt = String.valueOf(heureComplet+"h");
							btn.setText(heureTxt);
			        	 }
			      }
				  for(Date date : DateEnd.values()) {
					  //de meme que pour dateBegin
					 limitDate1 =new Date(currentMonday.getTime() - (1000*60*60*24*1));
				     limitDate2 =new Date(currentMonday.getTime() + (1000*60*60*24*6));
					  
					 //on recupere les données du créneau actuel de fin
				     year = date.getYear();
				     month=date.getMonth();
				     dayNbr = date.getDate();
		        	 day = date.getDay();
		        	 hour = date.getHours();
		        	 min = date.getMinutes();
		        	 heureComplet = hour + min*0.01;//heure + min en décimal
		        	 
		        	 //si l'heure correspond et le bon jour de la semaine et la bonne semaine
		        	 if(heureComplet==firstHour && day==j  && date.after(limitDate1) && date.before(limitDate2)) {
		        		//on modifie le tableau de verification de créneau pour indiquer que le créneau n'est plus réservé
		        		isEmpty[j-1]=true;
		        		
		        		//on affiche l'heure du créneau sur le bouton
		        		heureTxt = String.valueOf(heureComplet+"h"); 
						btn.setText(heureTxt);
		        	 }
			      }
				 //si le créneau correspond on modifie la couleur du bouton
				 if(isEmpty[j-1]==false) btn.setBackground(Color.BLUE);
				 timeTable.add(btn); //on ajoute un bouton par créneau, sa couleur dépend des valeurs de la hastable
			}
			//compteur permettant de gerer la compatibilité entre la base 60 de l'heure et la base 10
			firstHour=firstHour+0.15;
			compteur4 ++;
			if (compteur4==4) {
				compteur4=0;
				firstHour=firstHour+0.4;
			}
			firstHour=Math.round(firstHour*10000.0)/10000.0; //arrondi pour simplifier les égalités
		}
		
		//ajout du panel au centre
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
	
	//creation des dates au dessus de l'emploi du temps
	@SuppressWarnings("deprecation")
	private void createTimeTableDays() {
		String tempDay="";
		int taille=10; //taille de la date tronquée optimale
		Date tempDate = currentMonday; //on obtient le lundi actuel

		//on ajoute les dates de la semaine qui suivent le lundi actuel
		for(int i=1; i<6; i++) {
			tempDay=String.valueOf(tempDate);
			tempDay=tempDay.substring(0, taille); //on reduit la date pour ne pas avoir l'heure

			timeTable.add(new JLabel(tempDay, SwingConstants.CENTER));
			tempDate=new Date(tempDate.getTime() + (1000*60*60*24));//incremente la date de 1 jour
		}

	}
	
	private void ActionMenuBar(String text) {
		System.out.println("Action:"+text);
		
		//si on se deconnecte: on ferme cette fenetre et on ré-ouvre la fenetre principale
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
