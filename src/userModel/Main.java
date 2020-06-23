package userModel;

import userController.UserController;
/**
 * Cette classe permet de tester les fonctions du contrôleur d'utilisateurs.
 * Elle crée une base de données de 6 utilisateurs et les sauvegarde dans le fichier "usersDB.xml". 
 * 
 * @author Jose Mennesson (Mettre à jour)
 * @version 04/2016 (Mettre à jour)
 * 
 */

//TODO Classe pouvant être modifiée suivant vos besoins

public class Main {
	/**
	 * Fonction principale 
	 * 
	 * @param args
	 * 			Les arguments donnés en entrée du programme.
	 * 
	 */
	public static void main(String[] args) {
						
		final String file="userDB.xml";
		UserController UC=new UserController(file);
		UC.loadDB();
		//UC.usersToString();
		boolean x ;
		x = UC.addAdmin("su","KF",0001,"Kevin", "Flynn","@tron");
		System.out.println(x);
		
		/*UC.addAdmin("su","KR",0002,"Keanu", "Reeves",  "redpill");
		UC.addTeacher("su","GS",1001,"Grand", "Schtroumpf",  "salsepareille");
		UC.addTeacher("su","MF",1002,"Morgan", "Freeman",  "iknowall");
		UC.addStudent("su","BS",2001,"Buffy", "Summers",  "stake");
		UC.addStudent("su","NL",2002,"Nicolas", "Lepetit",  "prout");*/
		UC.associateStudToGroup("su", "BS", 1);
		UC.associateStudToGroup("su", "NL", 2);
		int i = 0;
		while(i<7)
		{
			System.out.println(UC.usersToString()[i]);
			i++;
		}
		
		UC.saveDB();
		
	}
}
