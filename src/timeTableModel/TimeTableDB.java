package timeTableModel;
/**
 * 
 * Cette classe gÃ©re la base de donnÃ©es d'emplois du temps. Elle doit permettre de sauvegarder et charger les emplois du temps ainsi que les salles Ã  partir d'un fichier XML. 
 * La structure du fichier XML devra Ãªtre la mÃªme que celle du fichier TimeTableDB.xml.
 * @see <a href="../../TimeTableDB.xml">TimeTableDB.xml</a> 
 * 
 * @author Jose Mennesson (Mettre Ã  jour)
 * @version 04/2016 (Mettre Ã  jour)
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

//TODO Classe Ã  modifier

public class TimeTableDB {
	/**
	 * 
	 * file = Le fichier contenant la base de données.
	 * TimeDB = Une hashtable contenant les emplois du temps.
	 * RoomDB = Une hashtable contenant les rooms.
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
	 * 		Le nom du fichier qui contient la base de donnÃ©es.
	 */
	public TimeTableDB(String file){
		//TODO	Ã€ modifier	
		super();	
		this.setFile(file);
		this.TimeDB = new Hashtable<Integer, TimeTable>();
		this.RoomDB = new Hashtable<Integer, Room>();
	}
	
	/**
	 * Getter de file
	 * 
	 * @return 
	 * 		Le nom du fichier qui contient la base de donnÃ©es.
	 */
	public String getFile() {
		return file;
	}
	
	/**
	 * Setter de file
	 * 
	 * @param file
	 * 		Le nom du fichier qui contient la base de donnÃ©es.
	 */
	public void setFile(String file) {
		this.file = file;
	}
	
	/**
	 * Ajout d'un emploi du temps dans la DB
	 * 
	 * @param timeTable
	 * 		L'emploi du temps que l'on souhaite ajouter.
	 */
	public void AddTimeTable(TimeTable timeTable) {
		this.TimeDB.put(timeTable.getGroupId(), timeTable);
	}
	
	/**
	 * Retourne l'emploi du temps qui correspond à GroupId.
	 * 
	 * @param GroupId
	 * 		L'identifiant du groupe correspondant à l'emploi du temps recherché.
	 */
	public TimeTable GetTimeTable(int GroupId) {
		return this.TimeDB.get(GroupId);
	}
	
	/**
	 * Supprime un emploi du temps de la hashtable
	 * 
	 * @param GroupId
	 * 		L'identifiant du groupe lié à l'emploi du temps que l'on veut supprimer.
	 */
	public void RemoveTimeTable(int GroupId) {
		this.TimeDB.remove(GroupId);
	}
	
	/**
	 * Ajout d'une salle dans la DB
	 * 
	 * @param room
	 * 		La salle que l'on veut ajouter.
	 */
	public void AddRoom(Room room) {
		this.RoomDB.put(room.getRoomId(), room);
	}
	
	/**
	 * Retourne la salle qui correspond à RoomId.
	 * 
	 * @param RoomId
	 * 		L'identifiant de la salle recherché.
	 */
	public Room GetRoom(int RoomId) {
		return this.RoomDB.get(RoomId);
	}
	
	/**
	 * Supprime une salle de la hashtable
	 * 
	 * @param RoomId
	 * 		L'identifiant de la salle à supprimer.
	 */
	public void RemoveRoom(int RoomId) {
		this.RoomDB.remove(RoomId);
	}
	
	/**
	 * Getter de RoomDB
	 * 
	 * @return 
	 * 		La hashtable contenant les rooms.
	 */
	public Hashtable<Integer, Room> getRoomDB() {
		return this.RoomDB;
	}
	
	/**
	 * Getter de TimeDB
	 * 
	 * @return 
	 * 		La hashtable contenant les emplois du temps.
	 */
	public Hashtable<Integer, TimeTable> getTimeDB() {
		return this.TimeDB;
	}
	
	/**
	 * Fonction permettant d'afficher dans la console le contenue du fichier XML
	 * 
	 */
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
	
	/**
	 * Convertion d'un format string à un format Date
	 * 
	 * @param DateS
	 * 		Une date au format String.
	 * @return
	 * 		Une date au format Date.
	 */
	public Date StringToDate(String DateS) {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		try{date = formatter.parse(DateS);}
		catch(ParseException e) {
			System.out.print(e);
		}
		return date;
	}
	
	/**
	 * 
	  * Convertion d'un format Date à un format String
	 * 
	 * @param Date
	 * 		Une date au format Date.
	 * @return
	 * 		Une date au format String.
	 */
	public String DateToString(Date date) { 
		String DateS = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		
		try{DateS = formatter.format(date);}
		catch(Exception e) {
			System.out.print(e);
		}
		
		return DateS;
	}
	
	/**
	 * Chargement de la DB contenue dans le fichier XML
	 * 
	 *  
	 */
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
	
	/**
	 * 
	 * Sauvegarde de la DB dans le fichier XML
	 *  
	 */
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
