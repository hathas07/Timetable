package timeTableController;

import java.util.Date;
import java.util.Hashtable;

import timeTableModel.TimeTableDB;
/**
 * Cette classe est le contrôleur d'emplois du temps que vous devez implémenter. 
 * Elle contient un attribut correspondant à la base de données d'emplois du temps que vous allez créer.
 * Elle contient toutes les fonctions de l'interface ITimeTableController que vous devez implémenter.
 * 
 * @author Jose Mennesson (Mettre à jour)
 * @version 04/2016 (Mettre à jour)
 * 
 */

//TODO Classe à modifier

public class TimeTableController implements ITimeTableController{

	/**
	 * Contient une instance de base de données d'emplois du temps
	 * 
	 */
	TimeTableDB tTDB;
	/**
	 * Constructeur de controleur d'emplois du temps créant la base de données d'emplois du temps
	 * 
	 * @param tTfile
	 * 		Fichier XML contenant la base de données d'emplois du temps
	 */
	public TimeTableController(String tTfile) {
		TimeTableDB tTDB=new TimeTableDB(tTfile);
		this.tTDB=tTDB;
	}

	@Override
	public String getTeacherLogin(int timeTableId, int bookId) {
		String TeacherLogin = tTDB.GetTimeTable(timeTableId).GetBooking(bookId).getLogin();
		return TeacherLogin;
	}

	@Override
	public String[] roomsIdToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] roomsToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] timeTablesIDToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] booksIdToString(int timeTableId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addRoom(int roomId, int capacity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeRoom(int roomId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRoom(int timeTableId, int bookId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean addTimeTable(int timeTableId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeTimeTable(int timeTableId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addBooking(int timeTableId, int bookingId, String login, Date dateBegin, Date dateEnd, int roomId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void getBookingsDate(int timeTableId, Hashtable<Integer, Date> dateBegin, Hashtable<Integer, Date> dateEnd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeBook(int timeTableId, int bookId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getBookingsMaxId(int timeTableId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean saveDB() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadDB() {
		boolean bool = true;
		try {
			tTDB.loadDB();
		}catch(Exception e) {
			System.out.print("La base de donn�es n'a pas pu �tre charg� correctement.");
			bool = false;
		}
		return bool;
	}
	
	

}
