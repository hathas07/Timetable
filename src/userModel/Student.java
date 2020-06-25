package userModel;

public class Student extends User {
	
	private int studentId ;
	private int groupId ;
	
	/**
	 * Initialisation d'un utilisateur de type �tudiant  
	 * @param login login de l'�tudaint
	 * @param firstName Pr�nom de l'�tudiant
	 * @param surName Nom de famille de l'�tudiant
	 * @param pwd Mot de passe de l'�tudiant
	 * @param studentId identifiant �tudiant
	 * @param groupId Identifiant de groupe de l'�tudiant 
	 */
	public Student(String login, String firstName, String surName, String pwd, int studentId, int groupId) {
		
		super(login, firstName, surName, pwd, "Student");
		this.studentId = studentId ;
		this.groupId = groupId ; 
				
	}
	
	/**
	 * Renvoie l'identifiant de l'�tudiant en param�tre 
	 * @param user
	 * @return entier
	 */
	public int getStudentId (User user)
	{
		Student student = (Student) user; 
		return student.studentId; 
	}
	
	/**
	 * Renvoie l'identifiant de group de l'�tudiant en param�tre 
	 * @param user
	 * @return entier
	 */
	public int getStudentGroup (User user)
	{
		Student student = (Student) user; 
		return student.groupId; 
	}
	
	/**
	 * Permet d'ajouter l'�tudiant en param�tre au group en param�tre 
	 * @param student
	 * @param group
	 */
	public void addStudentToGroup (Student student, Group group)
	{
		Group grp = new Group(0,0); // instance de group
		student.groupId = grp.getGroupId(group) ;
		group.studentNb ++;  
	}

}
