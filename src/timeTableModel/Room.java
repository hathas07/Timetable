package timeTableModel;

public class Room {
	/**
	 * 
	 * Le fichier contenant la base de données.
	 * 
	 */
	private int RoomId;
	private int Capacity;
	/**
	 * 
	 * Constructeur de TimeTableDB. 
	 * 
	 * @param file
	 * 		Le nom du fichier qui contient la base de données.
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