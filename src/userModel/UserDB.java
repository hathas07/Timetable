package userModel;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


/**
 * 
 * Cette classe gére la base de données d'utilisateurs. Elle doit permettre de sauvegarder et charger les utilisateurs et les groupes à partir d'un fichier XML. 
 * La structure du fichier XML devra être la même que celle du fichier userDB.xml.
 * @see <a href="../../userDB.xml">userDB.xml</a> 
 * 
 * @author Lila Nickler
 * @version 06/2020
 * 
 */


public class  UserDB {
	
	

	/**
	 * 
	 * Le fichier contenant la base de données.
	 * 
	 */
	
	private String file;
	public Hashtable <String,User> hashUser;
	private Hashtable <Integer,Group> hashGroup;

	
	/**
	 * 
	 * Constructeur de UserDB. 
	 * 
	 * !!!!!!!!!!!! PENSEZ À AJOUTER UN ADMINISTRATEUR (su par exemple) QUI VOUS PERMETTRA DE CHARGER LA BASE DE DONNÉES AU DEMARRAGE DE L'APPLICATION !!!!!!
	 * 
	 * @param file
	 * 		Le nom du fichier qui contient la base de données.
	 */
	public UserDB(String file){
		super();
		this.setFile(file);
		hashUser = new Hashtable <String,User>();	
		hashGroup = new Hashtable <Integer, Group>();
		
		Administrator su = new Administrator("su","Super","User","PWD",0);
		this.addAdmin(su);
		
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
	public void loadDB ()
	{
		Document document = null ;
		Element rootElt;
		SAXBuilder sxb = new SAXBuilder();
		String nameDB = this.getFile();
		
		try {
			document = sxb.build(new File(nameDB));
		}catch(Exception e) 
		{
			System.out.println(e);
		}
		if(document !=null) 
		{
			rootElt = document.getRootElement();
			List<Element> adminList = rootElt.getChild("Administrators").getChildren("Administrator");
			List <Element> teacherList = rootElt.getChild("Teachers").getChildren("Teacher");
			List <Element> studentList = rootElt.getChild("Students").getChildren("Student");
			List<Element> groupList = rootElt.getChild("Groups").getChildren("Group");
			
			for(Element currentAdmin : adminList)
			{
				String login = currentAdmin.getChildText("Login");
				String firstName = currentAdmin.getChildText("firstName"); 
				String surName = currentAdmin.getChildText("surName"); 
				String pwd = currentAdmin.getChildText("password");
				int id = Integer.parseInt(currentAdmin.getChildText("adminId"));;
				Administrator admin = new Administrator (login,firstName, surName, pwd,id);
				this.addAdmin(admin);
			}
			for(Element currentTeacher : teacherList)
			{
				String login = currentTeacher.getChildText("Login");
				String firstName = currentTeacher.getChildText("firstName"); 
				String surName = currentTeacher.getChildText("surName"); 
				String pwd = currentTeacher.getChildText("password");
				int id = Integer.parseInt(currentTeacher.getChildText("teacherId"));;
				Teacher teacher = new Teacher (login,firstName, surName, pwd,id);
				this.addTeacher(teacher);
		}
			for(Element currentStudent : studentList)
			{
				String login = currentStudent.getChildText("Login");
				String firstName = currentStudent.getChildText("firstName"); 
				String surName = currentStudent.getChildText("surName"); 
				String pwd = currentStudent.getChildText("password");
				int groupID = Integer.parseInt(currentStudent.getChildText("groupId"));
				int id = Integer.parseInt(currentStudent.getChildText("studentId"));
				Student student = new Student (login,firstName, surName, pwd,id,groupID);
				this.addStudent(student);
			}
			for(Element currentGroup : groupList)
			{
				int groupId = Integer.parseInt(currentGroup.getChildText("groupId"));
				int nbStudent = Integer.parseInt(currentGroup.getChildText("nbStudent"));
				Group group = new Group (groupId,nbStudent);
				this.addGroup(groupId, group);
			}
		}
	}
	
	public Administrator getAdmin (String login)
	{
		return (Administrator) hashUser.get(login);
	}
	
	public Student getStudent (String login)
	{
		Student student = (Student) hashUser.get(login);
		return student; 
	}
	
	public Teacher getTeacher (String login)
	{
		Teacher teacher = (Teacher) hashUser.get(login);
		return teacher; 
	}
	
	public User getUser (String login)
	{
		User user = hashUser.get(login);
		return user;
	}
	
	public Group getGroup (int ID)
	{
		Group group = hashGroup.get(ID);
		return group ;
	}
	
	public boolean login_contain(String login)
	{
		boolean result = hashUser.containsKey(login);
		return result; 
	}
	public boolean group_exist(int id)
	{
		return hashGroup.containsKey(id);
	}
	
	public int size_UserDB ()
	{
		int size = hashUser.size(); 
		return size; 
	}
	
	public Collection<User> getUsers ()
	{
		Collection<User> collection = hashUser.values();
		return collection ; 
	}
	public Collection<Group> getGroups() {
		Collection<Group> collection = hashGroup.values();
		return collection;
	}
	
	public void removeUser (String login)
	{
		hashUser.remove(login); 
	}
	
	public void removeGroup (int groupId)
	{
		hashGroup.remove(groupId);
	}
	
	public void addUser (User user)
	{
		hashUser.put(user.getLogin(user),user);
	}
	
	public void addAdmin (Administrator admin)
	{
		hashUser.put(admin.getLogin(admin),admin);
	}
	
	public void addGroup (int groupId, Group group)
	{
		hashGroup.put(groupId,group); 
	}

	public void addStudent(Student student) 
	{
		hashUser.put(student.login, student);	
	}

	public void addTeacher(Teacher teacher) 
	{
		hashUser.put(teacher.login, teacher);
	}

	
}
	
