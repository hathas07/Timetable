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
import java.io.FileOutputStream;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.List;

import org.jdom2.Document;
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
	private Hashtable<Integer, TimeTable> TimeDB ;
	private Hashtable<Integer, Room> RoomDB;
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
	
	public Hashtable<Integer, Room> getRoomDB() {
		return this.RoomDB;
	}
	
	public Hashtable<Integer, TimeTable> getTimeDB() {
		return this.TimeDB;
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
			System.out.print(e);
		}
		return date;
	}
	
	public String DateToString(Date date) { 
		String DateS = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		
		try{DateS = formatter.format(date);}
		catch(Exception e) {
			System.out.print(e);
		}
		
		return DateS;
	}
 
	
	public void loadDB() {
		Document document = null;
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
	
	public void saveDB() {
		Element rootElt = new Element("TimeTablesDB");
		Document document = new Document(rootElt);
		
			Element Rooms = new Element("Rooms");
				for(Room room : this.getRoomDB().values()) {
					Element Room = new Element("Room");
						Element RRoomId = new Element("RoomId");
							RRoomId.setText(String.valueOf(room.getRoomId()));
						Room.addContent(RRoomId);
						Element Capacity = new Element("Capacity");
							Capacity.setText(String.valueOf(room.getCapacity()));
						Room.addContent(Capacity);
					Rooms.addContent(Room);
				}
			rootElt.addContent(Rooms);
		
			Element TimeTables = new Element("TimeTables");
				for(TimeTable timeTable : this.getTimeDB().values()) {
					Element TimeTable = new Element("TimeTable");
						Element GroupId = new Element("GroupId");
							GroupId.setText(String.valueOf(timeTable.getGroupId()));
						TimeTable.addContent(GroupId);
						Element Books = new Element("Books");
							for(Booking booking : timeTable.getBookingDB().values()) {
								Element Book = new Element("Book");
									Element BookingId = new Element("BookingId");
										BookingId.setText(String.valueOf(booking.getBookingId()));
									Book.addContent(BookingId);
									Element Login = new Element("Login");
										Login.setText(booking.getLogin());
									Book.addContent(Login);
									Element DateBegin = new Element("DateBegin");
										DateBegin.setText(this.DateToString(booking.getDateBegin()));
									Book.addContent(DateBegin);
									Element DateEnd = new Element("DateEnd");
										DateEnd.setText(this.DateToString(booking.getDateEnd()));
									Book.addContent(DateEnd);
									Element TRoomId = new Element("RoomId");
										TRoomId.setText(String.valueOf(booking.getRoomId()));
									Book.addContent(TRoomId);
								Books.addContent(Book);
							}
						TimeTable.addContent(Books);
					TimeTables.addContent(TimeTable);
				}
			rootElt.addContent(TimeTables);
		
		
		try{
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, new FileOutputStream(this.getFile()));
		}catch (java.io.IOException e){}
	}
}
