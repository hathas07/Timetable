package userModel;

public class Group {
	
	protected int groupId ;
	protected int studentNb ;
	
	
	/**
	 * Initialisation d'un groupe définit par un identifiant et un nombre d'étudiant
	 * @param groupId
	 * @param studentNb
	 */
	public Group ( int groupId, int studentNb)
	{
		this.groupId = groupId; 
		this.studentNb = studentNb; 
	}
	
	/**
	 * Renvoie l'identifiant du groupe en paramètre
	 * @param group
	 * @return entier
	 */
	public int getGroupId ( Group group)
	{
		return group.groupId; 
	}

	/**
	 * Renvoie le nombre d'élèves présent dans le groupe en paramètre 
	 * @param group
	 * @return entier 
	 */
	public int getGroupNb(Group group) {
		return group.studentNb;
	}

}
