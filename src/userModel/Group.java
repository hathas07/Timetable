package userModel;

public class Group {
	
	protected int groupId ;
	protected int studentNb ;
	
	
	/**
	 * Initialisation d'un groupe d�finit par un identifiant et un nombre d'�tudiant
	 * @param groupId
	 * @param studentNb
	 */
	public Group ( int groupId, int studentNb)
	{
		this.groupId = groupId; 
		this.studentNb = studentNb; 
	}
	
	/**
	 * Renvoie l'identifiant du groupe en param�tre
	 * @param group
	 * @return entier
	 */
	public int getGroupId ( Group group)
	{
		return group.groupId; 
	}

	/**
	 * Renvoie le nombre d'�l�ves pr�sent dans le groupe en param�tre 
	 * @param group
	 * @return entier 
	 */
	public int getGroupNb(Group group) {
		return group.studentNb;
	}

}
