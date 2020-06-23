package userModel;

public class Student extends User {
	
	private int studentId ;
	private int groupId ;
	/**
	 * Initialisation d'un utilisateur de type étudiant  
	 * @param login login de l'étudaint
	 * @param firstName Prénom de l'étudiant
	 * @param surName Nom de famille de l'étudiant
	 * @param pwd Mot de passe de l'étudiant
	 * @param studentId identifiant étudiant
	 * @param groupId Identifiant de groupe de l'étudiant 
	 */

	public Student(String login, String firstName, String surName, String pwd, int studentId, int groupId) {
		
		super(login, firstName, surName, pwd, "Student");
		this.studentId = studentId ;
		this.groupId = groupId ; 
				
	}
	
	public int getStudentId (User user)
	{
		Student student = (Student) user; 
		return student.studentId; 
	}
	
	
	public int getStudentGroup (User user)
	{
		Student student = (Student) user; 
		return student.groupId; 
	}
	
	public void addStudentToGroup (Student student, Group group)
	{
		Group grp = new Group(0,0); 
		student.groupId = grp.getGroupId(group) ;
		group.studentNb ++;  
	}

}
