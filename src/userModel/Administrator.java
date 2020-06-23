package userModel;

public class Administrator extends User{
	
	private int adminId ;
	
	private UserDB userDB;


	
	
	
	/**
	 * Initialisation d'un utilisateur de type administrateur
	 * @param login login de l'admin
	 * @param firstName Prénom de l'admin 
	 * @param surName Nom de famille de l'admmin
	 * @param pwd Mot de passe de l'admin
	 * @param adminId Identifiant administrateur
	 */
	public Administrator (String login, String firstName, String surName, String pwd, int adminId)
	{
		super(login, firstName, surName, pwd,"Administrator");
		this.adminId = adminId ;
	
	}
	/**
	 * On créer un nouvel étudiant dans la base de donnée des utilisateurs 
	 * @param student L'étudiant a ajouté 
	 */
	public void createStudent (Student student,UserDB userDB)
	{
		userDB.addStudent(student);
	}
	
	/**
	 * On créer un nouvel professeur dans la base de donnée des utilisateurs
	 * @param teacher Le professeur a ajouté 
	 */
	public void createTeacher (Teacher teacher,UserDB userDB)
	{
		userDB.addTeacher(teacher);
	}
	
	/**
	 * On créer un nouvel administrateur dans la base de donnée des utilisateurs
	 * @param admin L'administrateur a ajouté 
	 */
	public void createAdmin (Administrator admin, UserDB userDB) 
	{
		userDB.addUser(admin);	
	}
	
	/**
	 * On crée un nouveau groupe dans la base de donnée des groupes 
	 * @param group Le groupe a ajouté 
	 */
	public void createGroup  (Group group) 
	{
		userDB.addGroup(group.groupId, group);
	}
	
	/**
	 * On ajoute un étudiant a un groupe 
	 * @param student L'étudiant a ajouté au groupe 
	 * @param group Le groupe 
	 */

	public int getAdminId ()
	{
		return this.adminId;
		
	}
	
	public int getAdminId (User user)
	{
		Administrator admin = (Administrator) user; 
		return admin.adminId; 
	}
	
}