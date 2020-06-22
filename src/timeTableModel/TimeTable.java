package timeTableModel;

import java.util.Hashtable;

public class TimeTable {
	/**
	 * 
	 * GroupId = l'identifiant du groupe affilié à cet emploi du temps (= TimeTableID).
	 * BookingDB = Une hashtable contenant les reservations d'un emploi du temps.
	 * 
	 */
	private int GroupId;
	private Hashtable<Integer, Booking> BookingDB ;
	
	/**
	 * 
	 * Constructeur de TimeTable. 
	 * 
	 * @param L'identifiant du groupe pour lequel on creé un emploi du temps.
	 * 		
	 */
	public TimeTable(int GroupId){
		
		this.setGroupId(GroupId);
		this.BookingDB = new Hashtable<Integer, Booking>();
	}
	
	/**
	 * Getter de GroupId
	 * 
	 * @return 
	 * 		L'identifiant du groupe.
	 */
	public int getGroupId() {
		return GroupId;
	}
	
	/**
	 * Setter de GroupId
	 * 
	 * @param GroupId
	 * 		
	 */
	public void setGroupId(int GroupId) {
		this.GroupId = GroupId;
	}
	
	/**
	 * Ajout d'un booking dans la hashtable BookingDB.
	 * 
	 * @param booking
	 * 		La réservation que l'on souhaite ajouter.
	 * 		
	 */
	public void AddBooking(Booking booking) {
		this.BookingDB.put(booking.getBookingId(), booking);
	}
	
	/**
	 * Retourne la réservation présente dans la hashtable à partir de son identifiant.
	 * 
	 * @param bookingId
	 * 		L'identifiant de la réservation que l'on souhaite retrouver.
	 * 
	 * @return 
	 * 		La réservation correspondant à l'id BoonkingId.
	 * 		
	 */
	public Booking GetBooking(int BookingId) {
		return this.BookingDB.get(BookingId);
	}
	
	/**
	 * Supprime une réservation de la hashtable.
	 * 
	 * @param BookingId
	 * 		L'identifiant de la réservation que l'on souhaite supprimer.
	 * 		
	 */
	public void RemoveBooking(int BookingId) {
		this.BookingDB.remove(BookingId);
	}
	
	/**
	 * Getter de BookingDB
	 * 
	 * @return 
	 * 		La hashtable comprennant les réservations.
	 */
	public Hashtable<Integer, Booking> getBookingDB() {
		return BookingDB;
	}
}	
