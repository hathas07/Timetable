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
	
	public int getGroupId ( Group group)
	{
		return group.groupId; 
	}

	public int getGroupNb(Group group) {
		return group.studentNb;
	}

}
