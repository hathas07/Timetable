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
	
	/**
	 * Renvoie le login de l'utilisateurs en paramètre
	 * @param user
	 * @return String
	 */
	public String getLogin (User user)
	{
		return user.login; 
	}
	
	/**
	 * Renvoie le Prénom de l'utilisateur en paramètre 
	 * @param user
	 * @return
	 */
	public String getFirstName (User user)
	{
		return user.firstName; 
	}
	
	/**
	 * Renvoie le nom de famille de l'utilisateur en paramètre
	 * @param user
	 * @return
	 */
	public String getSurName (User user)
	{
		return user.surName; 
	}
	
	/**
	 * Renvoie le mot de passe de l'utilisateur en paramètre
	 * @param user
	 * @return
	 */
	public String getPassword (User user)
	{
		return user.pwd; 
	}
	
	/**
	 * Renvoie le type de l'utilisateur en paramètre
	 * @param user
	 * @return
	 */
	public String getType (User user)
	{
		return user.type; 
	}
	

}
