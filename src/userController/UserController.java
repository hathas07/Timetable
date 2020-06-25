package userController;



import userModel.*;
/**
 * Cette classe est le contr√¥leur d'utilisateurs que vous devez impl√©menter. 
 * Elle contient un attribut correspondant √† la base de donn√©es utilisateurs que vous allez cr√©er.
 * Elle contient toutes les fonctions de l'interface IUserController que vous devez impl√©menter.
 * 
 * @author Lila Nickler
 * @version 06/2020
 * 
 */

//

public class UserController implements IUserController
{
	
	/**
	 * Contient:
	 * une instance de base de donn√©es d'utilisateurs
	 * une instance d'utilisateur
	 * une instance d'adminstrateur
	 * une instance de professeur 
	 * une instance d'Ètudiant
	 * une instance de groupe
	 */
	private UserDB userDB=null;
	private User user = null; 
	private Administrator admin = null; 
	private Student student = null; 
	private Teacher teacher = null ; 
	private Group group = null; 
	
	/**
	 * Constructeur de controleur d'utilisateurs cr√©ant la base de donn√©es d'utilisateurs
	 * 
	 * @param userfile
	 * 		Fichier XML contenant la base de donn√©es d'utilisateurs
	 */
	public UserController(String userfile){
		UserDB userDB=new UserDB(userfile);
		this.setUserDB(userDB);
		
		this.loadDB();
	}

	public String getUserName(String userLogin) {

		User user = userDB.getUser(userLogin);
		return user.getFirstName(user);
	}
	
	public String getUserClass(String userLogin, String userPwd) {
		
		String result =""; 
		try {
			
		User user = userDB.getUser(userLogin);
		if (user.getPassword(user).equals(userPwd))
		{
			result = user.getType(user);
		}
		
		} catch (Exception e) {
			
			System.out.println(e);
		}
		
		return result;
	}

	public int getStudentGroup(String studentLogin) {
		
		student = (Student) userDB.getStudent(studentLogin);
		return student.getStudentGroup(student);
	}
	public boolean addAdmin(String adminLogin, String newAdminlogin, int adminID, String firstname, String surname,
			String pwd) {
		
		boolean result =false ; 
		admin = userDB.getAdmin(adminLogin);
		try {
			if (admin.getType(admin)== "Administrator") // Si la personne qui souhaite ajouter un nouvel utilisateur est bien un administrateur
			{
				Administrator admini = new Administrator (newAdminlogin,firstname,surname,pwd,adminID);
				if (userDB.login_contain(newAdminlogin) == false) // Si le login n'existe pas dÈj‡ 
				{
					admin.createAdmin(admini,userDB); 
					result = true; 
					
					this.saveDB();
				}
			}
			
		} catch (Exception e)
		{
			System.out.println(e);
		}
		return result;
	}

	public boolean addTeacher(String adminLogin, String newteacherLogin, int teacherID, String firstname,
			String surname, String pwd) {
		
		boolean result =false ; 
		admin =(Administrator) userDB.getAdmin(adminLogin);
		
		try {
			if (admin.getType(admin) == "Administrator") // Si la personne qui souhaite ajouter un nouvel utilisateur est bien un administrateur
			{
				Teacher teacher = new Teacher (newteacherLogin,firstname,surname,pwd,teacherID);
				if (userDB.login_contain(newteacherLogin) == false) // Si le login n'existe pas dÈj‡
				{
					admin.createTeacher(teacher,userDB); 
					result = true; 
					
					this.saveDB();
				}
			}
			
		} catch (Exception e)
		{
			System.out.println(e);
		}
		return result;
	}

	public boolean addStudent(String adminLogin, String newStudentLogin, int studentID, String firstname,
			String surname, String pwd) {
		boolean result = false ; 
		admin =(Administrator) userDB.getAdmin(adminLogin);
		
		try {
			if (admin.getType(admin) == "Administrator")// Si la personne qui souhaite ajouter un nouvel utilisateur est bien un administrateur
			{
				student = new Student (newStudentLogin,firstname,surname,pwd,studentID,-1);
				if (userDB.login_contain(newStudentLogin) == false) // Si le login n'existe pas dÈj‡
				{
					
					admin.createStudent(student,userDB); 
					result = true; 
					
					this.saveDB();
				}
			}
			
		} catch (Exception e)
		{
			System.out.println(e);
		}
		return result;
	}

	public boolean removeUser(String adminLogin, String userLogin) {
		
		admin =(Administrator) userDB.getAdmin(adminLogin);
		boolean result = false;
		try {
				if (admin.getType(admin) == "Administrator") // Si la personne qui souhaite supprimer un utilisateur est bien un administrateur
				{
					userDB.removeUser(userLogin);
					result = true;
					
					this.saveDB();
				} 
		}
		catch (Exception e) 
				{
					System.out.println(e);
				}
		
		return result;
	}

	public boolean addGroup(String adminLogin, int groupId) {
		admin = userDB.getAdmin(adminLogin);
		boolean result = false;
		try {
				if (admin.getType(admin) == "Administrator") // Si la personne qui souhaite ajouter un nouveau groupe est bien un administrateur
				{
					Group group = new Group (groupId,0);
					userDB.addGroup(group);
					result = true ;
					
					this.saveDB();
				}
		}
		catch (Exception e) 
				{
					System.out.println(e);
				}
		return result;
	}

	public boolean removeGroup(String adminLogin, int groupId) { 
		admin = userDB.getAdmin(adminLogin);
		boolean result = false;
		try {
				if (admin.getType(admin) == "Administrator")// Si la personne qui souhaite supprimer un nouveau groupe est bien un administrateur
				{
					userDB.removeGroup(groupId);
					result = true;
					
					this.saveDB();
				} 
		}
		catch (Exception e) 
				{
					System.out.println(e);
				}
		
		return result;
	} 

	public boolean associateStudToGroup(String adminLogin, String studentLogin, int groupId) {
		admin = userDB.getAdmin(adminLogin);
		boolean result = false;
		try {
				if (admin.getType(admin) == "Administrator") // Si la personne qui souhaite faire l'action est bien un administrateur
				{

					if (userDB.group_exist(groupId)== false)
					{
						group = new Group (groupId,0);
						userDB.addGroup(group);
					}else 
					{
						group = userDB.getGroup(groupId);

					}
					student = userDB.getStudent(studentLogin);
					student.addStudentToGroup(student,group);
					result = true;
					
					this.saveDB();
				} 
		}
		catch (Exception e) 
				{
					System.out.println(e);
				}
		
		return result;
	}

	public String[] usersToString() {
	
		String[] tab = new String[userDB.size_UserDB()]; 
		try {
			int i = 0; 
			for (User user : userDB.getUsers())
			{
				user = userDB.getUser(user.getLogin(user));
				if (user.getType(user) == "Administrator") // Si l'utilisateur actuel est un administrateur
				{
					tab[i] = new StringBuilder("Login : "+user.getLogin(user)+" | First Name : "+user.getFirstName(user) +"|Sur Name: " +user.getPassword(user)+ "| Type: "+user.getType(user) + "|Administrator ID: "+ ((Administrator) user).getAdminId(user)).toString();
				}
				if (user.getType(user) == "Teacher")// Si l'utilisateur actuel est un professeur
				{
					tab[i] = new StringBuilder("Login : "+user.getLogin(user)+" | First Name : "+user.getFirstName(user) +"|Sur Name: " +user.getPassword(user)+ "| Type: "+user.getType(user) + "|Teacher ID: "+ ((Teacher) user).getTeacherId(user)).toString();
				}
				if (user.getType(user) == "Student")// Si l'utilisateur actuel est un Ètudiant
				{
					tab[i] = new StringBuilder("Login : "+user.getLogin(user)+" | First Name : "+user.getFirstName(user) +"|Sur Name: " +user.getPassword(user)+ "| Type: "+user.getType(user) + "|Student ID: "+ ((Student) user).getStudentId(user)+ "|Student Group ID: "+ ((Student) user).getStudentGroup(user)).toString();
				}
				i++; 
				
			}
		} catch (Exception e)
		
		{
			System.out.println(e);
		}
		
		return tab;
	}

	public String[] usersLoginToString() {
		String[] tab = new String[userDB.size_UserDB()]; 
		try {
			int i = 0; 
			for (User user : userDB.getUsers())
			{
				user = userDB.getUser(user.getLogin(user));
				tab[i] = new StringBuilder(user.getLogin(user)).toString();
				i++; 
			}
		} catch (Exception e)
		
		{
			System.out.println(e);
		}
		
		return tab;
	}

	public String[] studentsLoginToString() {
		String[] tab = new String[userDB.size_UserDB()]; 
		try {
			int i = 0; 
			for (User user : userDB.getUsers())
			{
				user = userDB.getUser(user.getLogin(user));
				if (user.getType(user)== "Student")
				{
					tab[i] = new StringBuilder(user.getLogin(user)).toString();
					i++; 
				}	
			}
		} catch (Exception e)
		
		{
			System.out.println(e);
		}
		
		return tab;
	}

	public String[] groupsIdToString() {
		String[] tab = new String[userDB.size_UserDB()]; 
		try {
			int i = 0; 
			for (Group group : userDB.getGroups())
			{
				group = userDB.getGroup(group.getGroupId(group));

					tab[i] = String.valueOf(group.getGroupId(group));
					i++; 	
			}
		} catch (Exception e)
		
		{
			System.out.println(e);
		}
		
		return tab;
	}

	public String[] groupsToString() {
		String[] tab = new String[userDB.getGroups().size()]; 
		try {
			int i = 0; 
			for (Group group : userDB.getGroups())
			{
				group = userDB.getGroup(group.getGroupId(group));

					tab[i] = new StringBuilder("Group ID: "+group.getGroupId(group) + " |Number of students: "+ group.getGroupNb(group)).toString();
					i++; 	
			}
		} catch (Exception e)
		
		{
			System.out.println(e);
		}
		
		return tab;
	}

	public boolean loadDB() {

		boolean result = true;
		try {
			userDB.loadDB();
		}catch(Exception e) {
			System.out.println("The database could not be loaded properly: " + e);
			result = false;
		}
		return result;
	}

	public boolean saveDB() {
		boolean result = true;
		try {
			userDB.saveDB();
		}catch(Exception e) {
			System.out.println("The database could not be saved properly: " + e);
			result = false;
		}
		return result;
	}

	public UserDB getUserDB() {
		return userDB;
	}

	public void setUserDB(UserDB userDB) {
		this.userDB = userDB;
	}

}

