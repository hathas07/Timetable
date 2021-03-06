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
 * @author Rapha�l Delecluse
 * @version 06/2020
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
		
		//On charge la DB � la cr�ation du controler 
		this.loadDB();
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
			
			//On regarde si la roomId existe d�j� dans la DB
			for(int roomid: this.tTDB.getRoomDB().keySet()) {
				if(roomid == roomId) {
					throw new Exception("Impossible d'inserer l'emploi du temps : "+roomId+" existe d�j�");
				}
			}
			Room room = new Room(roomId, capacity);
			this.tTDB.AddRoom(room);
			
			//On sauvegarde � chaque modification
			this.saveDB();
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
			//Si l'id n'existe pas dans la hashtable, alors elle restera inchang� donc pas besoin de chercher si elle existe
			this.tTDB.RemoveRoom(roomId);
			
			//On supprime toute les r�servations qui avaient lieu dans cette room :
			for(TimeTable timetable : this.tTDB.getTimeDB().values()) {
				for(Booking book : timetable.getBookingDB().values()) {
					if(book.getRoomId() == roomId) {
						this.removeBook(timetable.getGroupId(), book.getBookingId());
					}
				}
			}
			
			//On sauvegarde � chaque modification
			this.saveDB();
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
			
			//On regarde si la timeTableId existe d�j� dans la DB
			for(int timetableid: this.tTDB.getTimeDB().keySet()) {
				if(timetableid == timeTableId) {
					throw new Exception("Impossible d'inserer l'emploi du temps : "+timeTableId+" existe d�j�");
				}
			}
			TimeTable timeTable = new TimeTable(timeTableId);
			this.tTDB.AddTimeTable(timeTable);
			
			//On sauvegarde � chaque modification
			this.saveDB();
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
			
			//Pareil ici, si l'id n'existe pas ce n'est pas grave
			this.tTDB.RemoveTimeTable(timeTableId);
			
			//On sauvegarde � chaque modification
			this.saveDB();
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
			//On v�rifie que la date de d�but est bien inf�rieur � la date de fin 
			if(dateEnd.compareTo(dateBegin) < 0) {
				throw new Exception("Impossible d'inserer la reservation : La date de fin doit �tre strictement supp�rieur � la date de d�but.");
			}
			for(Booking booking: this.tTDB.GetTimeTable(timeTableId).getBookingDB().values()) {
				//On cherche si deux r�sevations se chechauvent pour un groupe
				if(dateBegin.compareTo(booking.getDateBegin()) >= 0  && dateBegin.compareTo(booking.getDateEnd()) < 0 ||
					dateEnd.compareTo(booking.getDateBegin()) > 0 && dateEnd.compareTo(booking.getDateEnd()) <= 0) {
					throw new Exception("Impossible d'inserer la reservation : Deux reservations se chevauchent");
				}
				
				//On regarde si la bookingId existe d�j� dans la DB
				if(booking.getBookingId() == bookingId) {
					throw new Exception("Impossible d'inserer la reservation : "+bookingId+" existe d�j�");
				}
			}
			
			Booking booking = new Booking(bookingId,login,dateBegin,dateEnd,roomId);
			this.tTDB.GetTimeTable(timeTableId).AddBooking(booking);
			
			//On sauvegarde � chaque modification
			this.saveDB();
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
			//Pareil ici, si l'id n'existe pas ce n'est pas grave
			this.tTDB.GetTimeTable(timeTableId).RemoveBooking(bookId);
			
			//On sauvegarde � chaque modification
			this.saveDB();
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
