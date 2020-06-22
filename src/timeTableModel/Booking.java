package timeTableModel;

import java.util.Date;

public class Booking {
	/**
	 * 
	 * BookingId = l'identifiant de la réservation.
	 * Login = l'identifiant du professeur réservant la salle.
	 * DateBegin = la date du début de la réservation (au format date).
	 * DateEnd = la date de fin de la réservation (au format date).
	 * RoomId = l'identifiant de la salle reservé.
	 * 
	 */
	private int BookingId;
	private String Login;
	private Date DateBegin;
	private Date DateEnd;
	private int RoomId;
	
	/**
	 * 
	 * Constructeur de Booking. 
	 * 
	 * @param L'identifiant de la réservation, le login du professeur réservant la salle, la date de début, la date de fin , l'identifiant de la salle
	 * 		
	 */
	public Booking(int BookingId, String Login, Date DateBegin, Date DateEnd, int RoomId){
		this.setBookingId(BookingId);	
		this.setLogin(Login);
		this.setDateBegin(DateBegin);
		this.setDateEnd(DateEnd);
		this.setRoomId(RoomId);
	}
	
	/**
	 * Getter de BookingId
	 * 
	 * @return 
	 * 		L'identifiant de la réservation.
	 */
	public int getBookingId() {
		return BookingId;
	}
	
	/**
	 * Getter de Login
	 * 
	 * @return 
	 * 		L'identifiant du professeur réservant la salle.
	 */
	public String getLogin() {
		return Login;
	}
	
	/**
	 * Getter de DateBegin
	 * 
	 * @return 
	 * 		La date du début de la réservation (au format date).
	 */
	public Date getDateBegin() {
		return DateBegin;
	}
	
	/**
	 * Getter de DateEnd
	 * 
	 * @return 
	 * 		La date de fin de la réservation (au format date).
	 */
	public Date getDateEnd() {
		return DateEnd;
	}
	
	/**
	 * Getter de RoomId
	 * 
	 * @return 
	 * 		L'identifiant de la room réservé.
	 */
	public int getRoomId() {
		return RoomId;
	}
	
	/**
	 * Setter de BookingId
	 * 
	 * @param BookingId
	 * 		
	 */
	public void setBookingId(int BookingId) {
		this.BookingId = BookingId;
	}
	
	/**
	 * Setter de Login
	 * 
	 * @param Login
	 * 		
	 */
	public void setLogin(String Login) {
		this.Login = Login;
	}
	
	/**
	 * Setter de DateBegin
	 * 
	 * @param DateBegin
	 * 		
	 */
	public void setDateBegin(Date DateBegin) {
		this.DateBegin = DateBegin;
	}
	
	/**
	 * Setter de DateEnd
	 * 
	 * @param DateEnd
	 * 		
	 */
	public void setDateEnd(Date DateEnd) {
		this.DateEnd = DateEnd;
	}
	
	/**
	 * Setter de RoomId
	 * 
	 * @param RoomId
	 * 		
	 */
	public void setRoomId(int RoomId) {
		this.RoomId = RoomId;
	}
}