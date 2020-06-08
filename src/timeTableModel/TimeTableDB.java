package timeTableModel;
/**
 * 
 * Cette classe gére la base de données d'emplois du temps. Elle doit permettre de sauvegarder et charger les emplois du temps ainsi que les salles à partir d'un fichier XML. 
 * La structure du fichier XML devra être la même que celle du fichier TimeTableDB.xml.
 * @see <a href="../../TimeTableDB.xml">TimeTableDB.xml</a> 
 * 
 * @author Jose Mennesson (Mettre à jour)
 * @version 04/2016 (Mettre à jour)
 * 
 */

import java.io.File;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

//TODO Classe à modifier

public class TimeTableDB {
	/**
	 * 
	 * Le fichier contenant la base de données.
	 * 
	 */
	private String file;
	public Hashtable<Integer, TimeTable> TimeDB ;
	public Hashtable<Integer, Room> RoomDB;
	/**
	 * 
	 * Constructeur de TimeTableDB. 
	 * 
	 * @param file
	 * 		Le nom du fichier qui contient la base de données.
	 */
	public TimeTableDB(String file){
		//TODO	À modifier	
		super();	
		this.setFile(file);
		this.TimeDB = new Hashtable<Integer, TimeTable>();
		this.RoomDB = new Hashtable<Integer, Room>();
	}
	/**
	 * Getter de file
	 * 
	 * @return 
	 * 		Le nom du fichier qui contient la base de données.
	 */
	public String getFile() {
		return file;
	}
	/**
	 * Setter de file
	 * 
	 * @param file
	 * 		Le nom du fichier qui contient la base de données.
	 */
	public void setFile(String file) {
		this.file = file;
	}
	
	public void AddTimeTable(TimeTable timeTable) {
		this.TimeDB.put(timeTable.getGroupId(), timeTable);
	}
	
	public TimeTable GetTimeTable(int GroupId) {
		return this.TimeDB.get(GroupId);
	}
	
	public void RemoveTimeTable(int GroupId) {
		this.TimeDB.remove(GroupId);
	}
	
	public void AddRoom(Room room) {
		this.RoomDB.put(room.getRoomId(), room);
	}
	
	public Room GetRoom(int RoomId) {
		return this.RoomDB.get(RoomId);
	}
	
	public void RemoveRoom(int RoomId) {
		this.RoomDB.remove(RoomId);
	}
	
	public void PrintDB() {
		org.jdom2.Document document = null;
		SAXBuilder sxb = new SAXBuilder();
		try {
			document = sxb.build(new File(this.getFile()));
		}catch(Exception e) {System.out.println(e);}
		
		try {
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, System.out);
		}catch (java.io.IOException e) {System.out.println(e);}
	}
	
	public Date StringToDate(String DateS) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		try{date = formatter.parse(DateS);}
		catch(ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public void loadDB() {
		org.jdom2.Document document = null;
		Element rootElt;
		SAXBuilder sxb = new SAXBuilder();
		try {
			document = sxb.build(new File(this.getFile()));
		}catch(Exception e) {System.out.println(e);}
		if(document !=null) {
			
			rootElt = document.getRootElement();
			List<Element> RoomList = rootElt.getChild("Rooms").getChildren("Room");
			List<Element> TimeTableList = rootElt.getChild("TimeTables").getChildren("TimeTable");
			
			for(Element CurrentRoom : RoomList) {
				int CurrentRoomId = Integer.parseInt(CurrentRoom.getChildText("RoomId"));
				int CurrentCapacity = Integer.parseInt(CurrentRoom.getChildText("Capacity"));
				Room Currentroom = new Room(CurrentRoomId, CurrentCapacity);
				this.AddRoom(Currentroom);
			}
			
			for(Element CurrentTimeTable : TimeTableList) {
				int CurrentTimeTableId = Integer.parseInt(CurrentTimeTable.getChildText("GroupId"));
				TimeTable CurrentTimetable = new TimeTable(CurrentTimeTableId);
				this.AddTimeTable(CurrentTimetable);
				
				List<Element> BookList = CurrentTimeTable.getChild("Books").getChildren("Book");
				for(Element CurrentBook : BookList) {
					int CurrentBookId = Integer.parseInt(CurrentBook.getChildText("BookingId"));
					String CurrentBookLogin = CurrentBook.getChildText("Login");
					Date CurrentBookDateBegin = StringToDate(CurrentBook.getChildText("DateBegin"));
					Date CurrentBookDateEnd = StringToDate(CurrentBook.getChildText("DateEnd"));
					int CurrentRoomId = Integer.parseInt(CurrentBook.getChildText("RoomId"));
					
					Booking Currentbook = new Booking(CurrentBookId, CurrentBookLogin, CurrentBookDateBegin,CurrentBookDateEnd,CurrentRoomId);
					CurrentTimetable.AddBooking(Currentbook);
				}
			}
	
		}
	}
}
