package timeTableController;

import java.util.Date;
import java.util.Hashtable;
import java.util.Set;

import timeTableModel.*;
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
		String TeacherLogin = "";
		try {
			TeacherLogin = this.tTDB.GetTimeTable(timeTableId).GetBooking(bookId).getLogin();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return TeacherLogin;
	}

	@Override
	public String[] roomsIdToString() {
		Hashtable <Integer, Room> roomDB = this.tTDB.getRoomDB();
		String [] TabRoomsId = new String[roomDB.size()];
		int i = 0;
		for(int roomid : roomDB.keySet()) {
			TabRoomsId[i] = String.valueOf(roomid);
			i++;
		}
		return TabRoomsId;
	}

	@Override
	public String[] roomsToString() {
		Hashtable <Integer, Room> roomDB = this.tTDB.getRoomDB();
		String [] TabRooms = new String[roomDB.size()];
		int i = 0;
		for(Room room : roomDB.values()) {
			TabRooms[i] = new StringBuilder("Room : "+String.valueOf(room.getRoomId())+" | Capacity : "+String.valueOf(room.getCapacity())).toString();
			i++;
		}
		return TabRooms;
	}

	@Override
	public String[] timeTablesIDToString() {

		Hashtable <Integer, TimeTable> TimeTables = this.tTDB.getTimeDB();
		String [] TabTimeTablesID = new String[TimeTables.size()];
		int i = 0;
		for(int timeTablesid : TimeTables.keySet()) {
			TabTimeTablesID[i] = String.valueOf(timeTablesid);
			i++;
		}
		return TabTimeTablesID;
	}

	@Override
	public String[] booksIdToString(int timeTableId) {
		
		Hashtable <Integer, Booking> Bookings = this.tTDB.getTimeDB().get(timeTableId).getBookingDB();
		String [] BookingsId = new String[Bookings.size()];
		int i = 0;
		for(int Bookingsid : Bookings.keySet()) {
			BookingsId[i] = String.valueOf(Bookingsid);
			i++;
		}
		return BookingsId;
	}

	@Override
	public boolean addRoom(int roomId, int capacity) {
		boolean result = true;
		try {
			Room room = new Room(roomId, capacity);
			this.tTDB.AddRoom(room);
		}
		catch(Exception e) {
			System.out.println(e);
			result = false;
		}
		return result;
	}

	@Override
	public boolean removeRoom(int roomId) {
		boolean result = true;
		try {
			this.tTDB.RemoveRoom(roomId);
		}
		catch(Exception e) {
			System.out.println(e);
			result = false;
		}
		return result;
	}

	@Override
	public int getRoom(int timeTableId, int bookId) {
		int RoomId = 0;
		try {
			RoomId = this.tTDB.GetTimeTable(timeTableId).GetBooking(bookId).getRoomId();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return RoomId;
	}

	@Override
	public boolean addTimeTable(int timeTableId) {
		boolean result = true;
		try {
			TimeTable timeTable = new TimeTable(timeTableId);
			this.tTDB.AddTimeTable(timeTable);
		}
		catch(Exception e) {
			System.out.println(e);
			result = false;
		}
		return result;
	}

	@Override
	public boolean removeTimeTable(int timeTableId) {
		boolean result = true;
		try {
			this.tTDB.RemoveTimeTable(timeTableId);
		}
		catch(Exception e) {
			System.out.println(e);
			result = false;
		}
		return result;
	}

	@Override
	public boolean addBooking(int timeTableId, int bookingId, String login, Date dateBegin, Date dateEnd, int roomId) {
		boolean result = true;
		try {
			Booking booking = new Booking(bookingId,login,dateBegin,dateEnd,roomId);
			this.tTDB.GetTimeTable(timeTableId).AddBooking(booking);
		}
		catch(Exception e) {
			System.out.println(e);
			result = false;
		}
		return result;
	}

	@Override
	public void getBookingsDate(int timeTableId, Hashtable<Integer, Date> dateBegin, Hashtable<Integer, Date> dateEnd) {
		try {
			for(Booking booking: this.tTDB.GetTimeTable(timeTableId).getBookingDB().values()) {
				dateBegin.put(booking.getBookingId(), booking.getDateBegin());
				dateEnd.put(booking.getBookingId(), booking.getDateEnd());
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public boolean removeBook(int timeTableId, int bookId) {
		boolean result = true;
		try {
			this.tTDB.GetTimeTable(timeTableId).RemoveBooking(bookId);
		}
		catch(Exception e) {
			System.out.println(e);
			result = false;
		}
		return result;
	}

	@Override
	public int getBookingsMaxId(int timeTableId) {
		int max = 0;
		try {
			Set<Integer> BookingList =  this.tTDB.getTimeDB().get(timeTableId).getBookingDB().keySet();
			for(int bookingId : BookingList) {
				if(bookingId >= max) {
					max = bookingId;
				}
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return max;
	}

	@Override
	public boolean saveDB() {
		boolean result = true;
		try {
			this.tTDB.saveDB();
		}catch(Exception e) {
			System.out.print("La base de donn�es n'a pas pu �tre sauvegard� correctement : " +e);
			result = false;
		}
		return result;
	}

	@Override
	public boolean loadDB() {
		boolean result = true;
		try {
			this.tTDB.loadDB();
		}catch(Exception e) {
			System.out.print("La base de donn�es n'a pas pu �tre charg� correctement : " +e);
			result = false;
		}
		return result;
	}
	
	

}
