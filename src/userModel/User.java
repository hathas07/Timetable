package userModel;

public class User {
	
	protected String login ; 
	private String firstName ;
	private String surName; 
	private String pwd ;
	private String type ; 
	

	
	
	/**
	 * Initialisation d'un utilisateur
	 * @param login
	 * @param firstName
	 * @param surName
	 * @param pwd 
	 */
	
	public User ( String login, String firstName, String surName, String pwd, String type)
	{
		this.login = login ;
		this.firstName= firstName;
		this.surName = surName ; 
		this.pwd = pwd ;
		this.type = type;
		
	}
	
	public String getLogin (User user)
	{
		return user.login; 
	}
	
	public String getFirstName (User user)
	{
		return user.firstName; 
	}
	
	public String getSurName (User user)
	{
		return user.surName; 
	}
	
	public String getPassword (User user)
	{
		return user.pwd; 
	}
	public String getType (User user)
	{
		return user.type; 
	}
	

}
