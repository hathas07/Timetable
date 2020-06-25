package userModel;

public class Teacher extends User {
	

	private int teacherId ;
	
	/**
	 *  Initialisation d'un utilisateur de type professeur 
	 * @param login login du professeur 
	 * @param firstName Pr�nom du professeur
	 * @param surName Nom de famille du professeur
	 * @param pwd Mot de passe du professeur
	 * @param teacherId identifiant professeur
	 */
	public Teacher(String login, String firstName, String surName, String pwd, int teacherId) {
	
		super(login, firstName, surName, pwd,"Teacher");
		this.teacherId = teacherId;
		
	}

	public int getTeacherId (User user)
	{
		Teacher teacher = (Teacher) user; 
		return teacher.teacherId; 
	}
	
}
