package timeTableModel;

public class Room {
	/**
	 * 
	 * RoomId = l'identifiant de la room.
	 * Capacity = la capacit� de la room.
	 * 
	 */
	private int RoomId;
	private int Capacity;
	
	/**
	 * 
	 * Constructeur de Room. 
	 * 
	 * @param L'identifiant de la room, la capacit� de la room
	 * 		
	 */
	public Room(int RoomId, int Capacity){
		
		this.setRoomId(RoomId);
		this.setCapacity(Capacity);
	}
	
	/**
	 * Getter de RoomId
	 * 
	 * @return 
	 * 		L'identifiant de la room.
	 */
	public int getRoomId() {
		return RoomId;
	}
	
	/**
	 * Getter de Capacity
	 * 
	 * @return 
	 * 		La capacit� de la room.
	 */	
	public int getCapacity() {
		return Capacity;
	}
	
	/**
	 * Setter de RoomId
	 * 
	 * @param RoomId
	 * 		L'identifiant de la room.
	 */
	public void setRoomId(int RoomId) {
		this.RoomId = RoomId;
	}
	
	/**
	 * Setter de Capacity
	 * 
	 * @param RoomId
	 * 		La capacit� de la room.
	 */
	public void setCapacity(int Capacity) {
		this.Capacity = Capacity;
	}
}