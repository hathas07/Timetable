package userController;



import userModel.*;
/**
 * Cette classe est le contrôleur d'utilisateurs que vous devez implémenter. 
 * Elle contient un attribut correspondant à la base de données utilisateurs que vous allez créer.
 * Elle contient toutes les fonctions de l'interface IUserController que vous devez implémenter.
 * 
 * @author Jose Mennesson (Mettre à jour)
 * @version 04/2016 (Mettre à jour)
 * 
 */

//

public class UserController implements IUserController
{
	
	/**
	 * Contient une instance de base de données d'utilisateurs
	 * 
	 */
	private UserDB userDB=null;
	private User user = null; 
	private Administrator admin = null; 
	private Student student = null; 
	private Teacher teacher = null ; 
	private Group group = null; 
	
	/**
	 * Constructeur de controleur d'utilisateurs créant la base de données d'utilisateurs
	 * 
	 * @param userfile
	 * 		Fichier XML contenant la base de données d'utilisateurs
	 */
	public UserController(String userfile){
		UserDB userDB=new UserDB(userfile);
		this.setUserDB(userDB);
	}

	@Override
	public String getUserName(String userLogin) {

		User user = userDB.getUser(userLogin);
		return user.getFirstName(user);
	}

	@Override
	public String getUserClass(String userLogin, String userPwd) {
		
		String result =""; 
		try {
			
		User user = userDB.getUser(userLogin);
		if (user.getPassword(user) == userPwd)
		{
			result = user.getType(user);
		}
		
		} catch (Exception e) {
			
			System.out.println(e);
		}
		
		return result;
	}

	@Override
	public int getStudentGroup(String studentLogin) {
		
		student = (Student) userDB.getStudent(studentLogin);
		return student.getStudentGroup(student);
	}

	@Override
	public boolean addAdmin(String adminLogin, String newAdminlogin, int adminID, String firstname, String surname,
			String pwd) {
		
		boolean result =false ; 
		admin = userDB.getAdmin(adminLogin);
		try {
			if (admin.getType(admin)== "Administrator")
			{
				Administrator admini = new Administrator (newAdminlogin,firstname,surname,pwd,adminID);
				if (userDB.login_contain(newAdminlogin) == false)
				{
					admin.createAdmin(admini,userDB); 
					result = true; 
				}
			}
			
		} catch (Exception e)
		{
			System.out.println(e);
		}
		return result;
	}

	@Override
	public boolean addTeacher(String adminLogin, String newteacherLogin, int teacherID, String firstname,
			String surname, String pwd) {
		
		boolean result =false ; 
		admin =(Administrator) userDB.getAdmin(adminLogin);
		
		try {
			if (admin.getType(admin) == "Administrator")
			{
				teacher = new Teacher (newteacherLogin,firstname,surname,pwd,teacherID);
				if (userDB.login_contain(newteacherLogin) == false)
				{
					admin.createTeacher(teacher,userDB); 
					result = true; 
				}
			}
			
		} catch (Exception e)
		{
			System.out.println(e);
		}
		return result;
	}

	@Override
	public boolean addStudent(String adminLogin, String newStudentLogin, int studentID, String firstname,
			String surname, String pwd) {
		boolean result = false ; 
		admin =(Administrator) userDB.getAdmin(adminLogin);
		
		try {
			if (admin.getType(admin) == "Administrator")
			{
				student = new Student (newStudentLogin,firstname,surname,pwd,studentID,-1);
				if (userDB.login_contain(newStudentLogin) == false)
				{
					admin.createStudent(student,userDB); 
					result = true; 
				}
			}
			
		} catch (Exception e)
		{
			System.out.println(e);
		}
		return result;
	}

	@Override
	public boolean removeUser(String adminLogin, String userLogin) {
		
		admin =(Administrator) userDB.getAdmin(adminLogin);
		boolean result = false;
		try {
				if (user.getType(admin) == "Administrator")
				{
					userDB.removeUser(userLogin);
					result = true;
				} 
		}
		catch (Exception e) 
				{
					System.out.println(e);
				}
		
		return result;
	}

	@Override
	public boolean addGroup(String adminLogin, int groupId) {
		admin = userDB.getAdmin(adminLogin);
		boolean result = false;
		try {
				if (user.getType(admin) == "Administrator")
				{
					Group group = new Group (groupId,0);
					admin.createGroup(group);
					result = true ;
				}
		}
		catch (Exception e) 
				{
					System.out.println(e);
				}
		return result;
	}

	@Override
	public boolean removeGroup(String adminLogin, int groupId) {
		admin = userDB.getAdmin(adminLogin);
		boolean result = false;
		try {
				if (admin.getType(admin) == "Administrator")
				{
					userDB.removeGroup(groupId);
					result = true;
				} 
		}
		catch (Exception e) 
				{
					System.out.println(e);
				}
		
		return result;
	} 

	@Override
	public boolean associateStudToGroup(String adminLogin, String studentLogin, int groupId) {
		admin = userDB.getAdmin(adminLogin);
		boolean result = false;
		try {
				if (admin.getType(admin) == "Administrator")
				{

					if (userDB.group_exist(groupId)== false)
					{
						group = new Group (groupId,0);
						userDB.addGroup(groupId, group);
					}else 
					{
						group = userDB.getGroup(groupId);

					}
					student = userDB.getStudent(studentLogin);
					student.addStudentToGroup(student,group);
					result = true;
				} 
		}
		catch (Exception e) 
				{
					System.out.println(e);
				}
		
		return result;
	}

	@Override
	public String[] usersToString() {
	
		String[] tab = new String[userDB.size_UserDB()]; 
		try {
			int i = 0; 
			for (User user : userDB.getUsers())
			{
				user = userDB.getUser(user.getLogin(user));
				if (admin.getType(user) == "Administrator")
				{
					tab[i] = new StringBuilder("Login : "+user.getLogin(user)+" | First Name : "+user.getFirstName(user) +"|Sur Name: " +user.getPassword(user)+ "| Type: "+user.getType(user) + "|Administrator ID: "+ admin.getAdminId(user)).toString();
				}
				
				if (user.getType(user) == "Teacher")
				{
					tab[i] = new StringBuilder("Login : "+user.getLogin(user)+" | First Name : "+user.getFirstName(user) +"|Sur Name: " +user.getPassword(user)+ "| Type: "+user.getType(user) + "|Teacher ID: "+ teacher.getTeacherId(user)).toString();
				}
				if (user.getType(user) == "Student")
				{
					tab[i] = new StringBuilder("Login : "+user.getLogin(user)+" | First Name : "+user.getFirstName(user) +"|Sur Name: " +user.getPassword(user)+ "| Type: "+user.getType(user) + "|Student ID: "+ student.getStudentId(user)+ "|Student Group ID: "+ student.getStudentGroup(user)).toString();
				}
				i++; 
				
			}
		} catch (Exception e)
		
		{
			System.out.println(e);
		}
		
		return tab;
	}

	@Override
	public String[] usersLoginToString() {
		String[] tab = new String[userDB.size_UserDB()]; 
		try {
			int i = 0; 
			for (User user : userDB.getUsers())
			{
				user = userDB.getUser(user.getLogin(user));
				tab[i] = new StringBuilder("Login: "+user.getLogin(user)).toString();
				i++; 
			}
		} catch (Exception e)
		
		{
			System.out.println(e);
		}
		
		return tab;
	}

	@Override
	public String[] studentsLoginToString() {
		String[] tab = new String[userDB.size_UserDB()]; 
		try {
			int i = 0; 
			for (User user : userDB.getUsers())
			{
				user = userDB.getUser(user.getLogin(user));
				if (user.getType(user)== "Student")
				{
					tab[i] = new StringBuilder("Login: "+user.getLogin(user)).toString();
					i++; 
				}	
			}
		} catch (Exception e)
		
		{
			System.out.println(e);
		}
		
		return tab;
	}

	@Override
	public String[] groupsIdToString() {
		String[] tab = new String[userDB.size_UserDB()]; 
		try {
			int i = 0; 
			for (Group group : userDB.getGroups())
			{
				group = userDB.getGroup(group.getGroupId(group));

					tab[i] = new StringBuilder("Group ID: "+group.getGroupId(group)).toString();
					i++; 	
			}
		} catch (Exception e)
		
		{
			System.out.println(e);
		}
		
		return tab;
	}

	@Override
	public String[] groupsToString() {
		String[] tab = new String[userDB.size_UserDB()]; 
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

	@Override
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

	@Override
	public boolean saveDB() {
		// TODO Auto-generated method stub
		return false;
	}

	public UserDB getUserDB() {
		return userDB;
	}

	public void setUserDB(UserDB userDB) {
		this.userDB = userDB;
	}
	
	

}

