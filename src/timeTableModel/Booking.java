package timeTableModel;

import java.util.Date;

public class Booking {
	/**
	 * 
	 *
	 * 
	 */
	private int BookingId;
	private String Login;
	private Date DateBegin;
	private Date DateEnd;
	private int RoomId;
	
	
	public Booking(int BookingId, String Login, Date DateBegin, Date DateEnd, int RoomId){
		this.setBookingId(BookingId);	
		this.setLogin(Login);
		this.setDateBegin(DateBegin);
		this.setDateEnd(DateEnd);
		this.setRoomId(RoomId);
	}
	
	public int getBookingId() {
		return BookingId;
	}
	public String getLogin() {
		return Login;
	}
	public Date getDateBegin() {
		return DateBegin;
	}
	public Date getDateEnd() {
		return DateEnd;
	}
	public int getRoomId() {
		return RoomId;
	}

	public void setBookingId(int BookingId) {
		this.BookingId = BookingId;
	}
	public void setLogin(String Login) {
		this.Login = Login;
	}
	public void setDateBegin(Date DateBegin) {
		this.DateBegin = DateBegin;
	}
	public void setDateEnd(Date DateEnd) {
		this.DateEnd = DateEnd;
	}
	public void setRoomId(int RoomId) {
		this.RoomId = RoomId;
	}
}