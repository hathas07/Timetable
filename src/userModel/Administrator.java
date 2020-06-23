package userModel;

public class Administrator extends User{
	
	private int adminId ;
	
	private UserDB userDB;


	
	
	
	/**
	 * Initialisation d'un utilisateur de type administrateur
	 * @param login login de l'admin
	 * @param firstName Pr�nom de l'admin 
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
	 * On cr�er un nouvel �tudiant dans la base de donn�e des utilisateurs 
	 * @param student L'�tudiant a ajout� 
	 */
	public void createStudent (Student student,UserDB userDB)
	{
		userDB.addStudent(student);
	}
	
	/**
	 * On cr�er un nouvel professeur dans la base de donn�e des utilisateurs
	 * @param teacher Le professeur a ajout� 
	 */
	public void createTeacher (Teacher teacher,UserDB userDB)
	{
		userDB.addTeacher(teacher);
	}
	
	/**
	 * On cr�er un nouvel administrateur dans la base de donn�e des utilisateurs
	 * @param admin L'administrateur a ajout� 
	 */
	public void createAdmin (Administrator admin, UserDB userDB) 
	{
		userDB.addUser(admin);	
	}
	
	/**
	 * On cr�e un nouveau groupe dans la base de donn�e des groupes 
	 * @param group Le groupe a ajout� 
	 */
	public void createGroup  (Group group) 
	{
		userDB.addGroup(group.groupId, group);
	}
	
	/**
	 * On ajoute un �tudiant a un groupe 
	 * @param student L'�tudiant a ajout� au groupe 
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