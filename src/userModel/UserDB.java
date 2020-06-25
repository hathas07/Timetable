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
 * Cette classe gÃ©re la base de donnÃ©es d'utilisateurs. Elle doit permettre de sauvegarder et charger les utilisateurs et les groupes Ã  partir d'un fichier XML. 
 * La structure du fichier XML devra Ãªtre la mÃªme que celle du fichier userDB.xml.
 * @see <a href="../../userDB.xml">userDB.xml</a> 
 * 
 * @author Lila Nickler
 * @version 06/2020
 * 
 */


public class  UserDB {
	
	

	/**
	 * 
	 * Le fichier contenant la base de donnÃ©es.
	 * 
	 */
	
	private String file;
	public Hashtable <String,User> hashUser;
	private Hashtable <Integer,Group> hashGroup;

	
	/**
	 * 
	 * Constructeur de UserDB. 
	 * 
	 * !!!!!!!!!!!! PENSEZ Ã€ AJOUTER UN ADMINISTRATEUR (su par exemple) QUI VOUS PERMETTRA DE CHARGER LA BASE DE DONNÃ‰ES AU DEMARRAGE DE L'APPLICATION !!!!!!
	 * 
	 * @param file
	 * 		Le nom du fichier qui contient la base de donnÃ©es.
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
	 * Chargement de la base de donnée à partir d'un fichier XML
	 */
	public void loadDB ()
	{
		Document document = null ;
		Element rootElt;
		SAXBuilder sxb = new SAXBuilder();
		
		try {

			document = sxb.build(new File(this.getFile()));
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
				String login = currentAdmin.getChildText("login");
				String firstName = currentAdmin.getChildText("firstname"); 
				String surName = currentAdmin.getChildText("surname"); 
				String pwd = currentAdmin.getChildText("pwd");
				int id = Integer.parseInt(currentAdmin.getChildText("adminId"));
				Administrator admin = new Administrator (login,firstName, surName, pwd,id);
				this.addAdmin(admin);
			}
			for(Element currentTeacher : teacherList)
			{
				String login = currentTeacher.getChildText("login");
				String firstName = currentTeacher.getChildText("firstname"); 
				String surName = currentTeacher.getChildText("surname"); 
				String pwd = currentTeacher.getChildText("pwd");
				int id = Integer.parseInt(currentTeacher.getChildText("teacherId"));;
				Teacher teacher = new Teacher (login,firstName, surName, pwd,id);
				this.addTeacher(teacher);
		}
			for(Element currentStudent : studentList)
			{
				String login = currentStudent.getChildText("login");
				String firstName = currentStudent.getChildText("firstname"); 
				String surName = currentStudent.getChildText("surname"); 
				String pwd = currentStudent.getChildText("pwd");
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
	/**
	 * Sauvgarde de la base de donnée dans un ficher XML
	 */
	
	public void saveDB()
	{
		Element rootElt = new Element("TimeTablesDB");
		Document document = new Document(rootElt);
		
		Element Administrators = new Element("Administrators");
		Element Teachers = new Element ("Teachers");
		Element Students = new Element ("Students");
		for (User user : this.hashUser.values())
		{
			if (user.getType(user) == "Administrator")
			{
				Administrator admin = (Administrator) user;
				Element Administrator = new Element ("Administrator");
					Element login = new Element("login");
						login.setText(String.valueOf(admin.getLogin(admin)));
					Administrator.addContent(login);
					Element firstName = new Element ("firstname");
						firstName.setText(String.valueOf(admin.getFirstName(admin)));
					Administrator.addContent(firstName);
					Element surName = new Element ("surname");
						surName.setText(String.valueOf(admin.getSurName(admin)));
					Administrator.addContent(surName);
					Element pwd = new Element ("pwd"); 
						pwd.setText(String.valueOf(admin.getPassword(admin)));
					Administrator.addContent(pwd);
					Element adminId = new Element("adminId");
						adminId.setText(String.valueOf(admin.getAdminId(admin)));
					Administrator.addContent(adminId);
				Administrators.addContent(Administrator);
			}
			if (user.getType(user) == "Teacher")
			{
				Teacher teacher = (Teacher) user;
					Element Teacher = new Element("Teacher");
					Element login = new Element("login");
						login.setText(String.valueOf(teacher.getLogin(teacher)));
					Teacher.addContent(login);
					Element firstName = new Element ("firstname");
						firstName.setText(String.valueOf(teacher.getFirstName(teacher)));
					Teacher.addContent(firstName);
					Element surName = new Element ("surname");
						surName.setText(String.valueOf(teacher.getSurName(teacher)));
					Teacher.addContent(surName);
					Element pwd = new Element ("pwd"); 
						pwd.setText(String.valueOf(teacher.getPassword(teacher)));
					Teacher.addContent(pwd);
					Element teacherId = new Element("teacherId");
						teacherId.setText(String.valueOf(teacher.getTeacherId(teacher)));
					Teacher.addContent(teacherId);
				Teachers.addContent(Teacher);
			}
			if (user.getType(user) == "Student")
			{
				Student student = (Student) user;
					Element Student = new Element("Student");
					Element login = new Element("login");
						login.setText(String.valueOf(student.getLogin(student)));
					Student.addContent(login);
					Element firstName = new Element ("firstname");
						firstName.setText(String.valueOf(student.getFirstName(student)));
					Student.addContent(firstName);
					Element surName = new Element ("surname");
						surName.setText(String.valueOf(student.getSurName(student)));
					Student.addContent(surName);
					Element pwd = new Element ("pwd"); 
						pwd.setText(String.valueOf(student.getPassword(student)));
					Student.addContent(pwd);
					Element studentId = new Element("studentId");
						studentId.setText(String.valueOf(student.getStudentId(student)));
					Student.addContent(studentId);
					Element groupId = new Element ("groupId");
						groupId.setText(String.valueOf(student.getStudentGroup(student)));
					Student.addContent(groupId);
				Students.addContent(Student);
			}
		}
		rootElt.addContent(Administrators);
		rootElt.addContent(Teachers);
		rootElt.addContent(Students);
		
		Element Groups = new Element("Groups");
		for(Group group : this.hashGroup.values())
		{
			Element Group = new Element ("Group");
			Element groupId = new Element("groupId");
				groupId.setText(String.valueOf(group.getGroupId(group)));
			Group.addContent(groupId);
			Element nbStudent = new Element("nbStudent");
			nbStudent.setText(String.valueOf(group.getGroupNb(group)));
			Group.addContent(nbStudent);
			Groups.addContent(Group);
		}
		rootElt.addContent(Groups);
		try{
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, new FileOutputStream(this.getFile()));
		}catch (java.io.IOException e){}
	}
	/**
	 * Permet de récupérer un Administrateur grâce à son login 
	 * @param login 
	 * @return Administrator
	 */
	public Administrator getAdmin (String login)
	{
		return (Administrator) hashUser.get(login);
	}
	
	/**
	 * Permet de récupérer un Etudiant grâce à son login 
	 * @param login
	 * @return Student
	 */
	public Student getStudent (String login)
	{
		Student student = (Student) hashUser.get(login);
		return student; 
	}
	
	/**
	 * Permet de récupérer un Professeur grâce à son login
	 * @param login
	 * @return Teacher
	 */
	public Teacher getTeacher (String login)
	{
		Teacher teacher = (Teacher) hashUser.get(login);
		return teacher; 
	}
	
	/**
	 * Permet de récupérer un Utilisateur grâce à son login
	 * @param login
	 * @return User
	 */
	public User getUser (String login)
	{
		User user = hashUser.get(login);
		return user;
	}
	
	/**
	 * Permet de récupérer un Group grâce à son identifiant 
	 * @param ID
	 * @return Group
	 */
	public Group getGroup (int ID)
	{
		Group group = hashGroup.get(ID);
		return group ;
	}
	
	/**
	 * Renvoie Vrai si le login en paramètre est dans la table des Utilisateurs sinon renvoie Faux 
	 * @param login 
	 * @return boolean 
	 */
	public boolean login_contain(String login)
	{
		boolean result = hashUser.containsKey(login);
		return result; 
	}
	
	/**
	 * Renvoie Vrai si l'identifiant en paramètre est dans la table des Groupes sinon renvoie Faux 
	 * @param id
	 * @return boolean
	 */
	public boolean group_exist(int id)
	{
		return hashGroup.containsKey(id);
	}
	
	/**
	 * Renvoie la taille de la base de donnée des Utilisateur
	 * @return entier 
	 */
	public int size_UserDB ()
	{
		int size = hashUser.size(); 
		return size; 
	}
	
	/**
	 * Renvoie une collection contenu tout les utilisateurs présent dans la table des utilisateurs
	 * @return collection
	 */
	public Collection<User> getUsers ()
	{
		Collection<User> collection = hashUser.values();
		return collection ; 
	}
	
	/**
	 * Renvoie une collection contenu tout les groupes présent dans la table des groupes
	 * @return collection
	 */
	public Collection<Group> getGroups() {
		Collection<Group> collection = hashGroup.values();
		return collection;
	}
	/**
	 * Supprime l'utilisateur correspondant au login en paramètre de la table des utilisateurs
	 * @param login
	 */
	public void removeUser (String login)
	{
		hashUser.remove(login); 
	}
	
	/**
	 * Supprime le groupe correspondant a l'identifiant en paramètre de la table des groupes
	 * @param groupId
	 */
	public void removeGroup (int groupId)
	{
		hashGroup.remove(groupId);
	}
	
	/**
	 * Ajoute l'utilisateur en paramètre dans la table des utilisateurs
	 * @param user
	 */
	public void addUser (User user)
	{
		hashUser.put(user.getLogin(user),user);
	}
	
	/**
	 * Ajoute l'administrateur en paramètre dans la table des utilisateurs
	 * @param admin
	 */
	public void addAdmin (Administrator admin)
	{
		hashUser.put(admin.getLogin(admin),admin);
	}
	
	/**
	 * Ajoute l'étudiant en paramètre dans la table des utilisateurs
	 * @param student
	 */
	public void addStudent(Student student) 
	{
		hashUser.put(student.login, student);	
	}

	/**
	 * Ajoute le professeur en paramètre dans la table des utilisateur
	 * @param teacher
	 */
	public void addTeacher(Teacher teacher) 
	{
		hashUser.put(teacher.login, teacher);
	}
	
	/**
	 * Ajoute le groupe en paramètre dans la table des groupes
	 * @param groupId
	 * @param group
	 */
	public void addGroup (int groupId, Group group)
	{
		hashGroup.put(groupId,group); 
	}
}
	
