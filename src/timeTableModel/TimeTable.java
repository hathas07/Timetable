package timeTableModel;

import java.util.Hashtable;

public class TimeTable {
	
	private int GroupId;
	public Hashtable<Integer, Booking> BookingDB ;
	
	public TimeTable(int GroupId){
		
		this.setGroupId(GroupId);
		this.BookingDB = new Hashtable<Integer, Booking>();
	}
	
	public int getGroupId() {
		return GroupId;
	}
	
	public void setGroupId(int GroupId) {
		this.GroupId = GroupId;
	}
	
	public void AddBooking(Booking booking) {
		this.BookingDB.put(booking.getBookingId(), booking);
	}
	
	public Booking GetBooking(int BookingId) {
		return this.BookingDB.get(BookingId);
	}
	
	public void RemoveBooking(int BookingId) {
		this.BookingDB.remove(BookingId);
	}
}