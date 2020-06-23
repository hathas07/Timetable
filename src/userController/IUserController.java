package userController;

/**
 * Cette classe est l'interface du contrôleur que vous devez implémenter. 
 * Elle contient un certains nombre de fonctions qui sont utilisées dans l'interface graphique. 
 * Elle permet à cette dernière d'être indépendante par rapport à votre implémentation de la gestion des utilisateurs.
 * Cette classe doit être implémentée par la classe UserController qui devra implémenter les fonctions décrites ci-dessous.
 * 
 * @author Jose Mennesson
 * @version 04/2016
 */

//TODO Classe à ne pas modifier

public interface IUserController{ 
	/**
	 * Fonction permettant de récupérer le nom et le prénom de l'utilisateur à partir de son login
	 * @param userLogin
	 * 		Le login de l'utilisateur
	 * @return
	 * 		Une chaine de caractère contenant le prénom et le nom de l'utilisateur
	 */
	public String getUserName(String userLogin);
	/**
	 * Fonction permettant de récupérer la classe de l'utilisateur à partir de son login et de son mot de passe. 
	 * Elle renvoie :
	 * 			- "" si l'utilisateur n'est pas reconnu (vérification du login et mdp).
	 * 			- "Student" si l'utilisateur est un étudiant 
	 *			- "Teacher" si l'utilisateur est un professeur
	 *			- "Administrator" si l'utilisateur est un administrateur 
	 * @param userLogin
	 * 		Le login de l'utilisateur
	 * @param userPwd
	 * 		Le mot de passe de l'utilisateur
	 * @return
	 * 		Une chaine de caractère contenant la classe de l'utilisateur
	 */
	public String getUserClass(String userLogin, String userPwd);
	/**
	 * Fonction permettant de récupérer l'identifiant de groupe de l'étudiant à partir de son login. Elle renvoie l'identifiant du groupe de l'étudiant s'il existe et -1 sinon.
	 * @param studentLogin
	 * 		Le login de l'étudiant
	 * @return
	 * 		L'identifiant de groupe de l'étudiant 
	 */
	public int getStudentGroup(String studentLogin);
	/**
	 * Fonction permettant d'ajouter un administrateur. Elle renvoie true si l'administrateur a été créé et false sinon. 
	 * Cette fonction devra tester si l'administrateur existe déjà ou non, puis elle devra le sauvegarder dans la base de donnée.
	 * @param adminLogin
	 * 				Le login de l'administrateur qui va créer le nouvel administrateur.
	 * @param newAdminlogin
	 * 				Le login du nouvel administrateur.
	 * @param adminID
	 * 				L'identifiant du nouvel administrateur.
	 * @param firstname
	 * 				Le prénom du nouvel administrateur.
	 * @param surname
	 * 				Le nom du nouvel administrateur.
	 * @param pwd
	 * 				Le mot de passe du nouvel administrateur.
	 * @return
	 * 		Un boolean indiquant si l'administrateur a bien été créé
	 */
	public boolean addAdmin(String adminLogin, String newAdminlogin,int adminID, String firstname, String surname, String pwd);
	/**
	 * Fonction permettant d'ajouter un professeur. Elle renvoie true si le professeur a été créé et false sinon. 
	 * Cette fonction devra tester si le professeur existe déjà ou non, puis elle devra le sauvegarder dans la base de donnée.
	 * @param adminLogin
	 * 				Le login de l'administrateur qui va créer le nouveau professeur.
	 * @param newteacherLogin
	 * 				Le login du nouveau professeur.
	 * @param teacherID
	 * 				L'identifiant du nouveau professeur.
	 * @param firstname
	 * 				Le prénom du nouveau professeur.
	 * @param surname
	 * 				Le nom du nouveau professeur.
	 * @param pwd
	 * 				Le mot de passe du nouveau professeur.
	 * @return
	 * 		Un boolean indiquant si le nouveau professeur a bien été créé
	 */
	public boolean addTeacher(String adminLogin, String newteacherLogin,int teacherID, String firstname, String surname, String pwd);
	/**
	 * Fonction permettant d'ajouter un étudiant. Elle renvoie true si l'étudiant a été créé et false sinon. 
	 * Cette fonction devra tester si l'étudiant existe déjà ou non, puis elle devra le sauvegarder dans la base de donnée.
	 * @param adminLogin
	 * 				Le login de l'administrateur qui va créer le nouvel étudiant.
	 * @param newStudentLogin
	 * 				Le login du nouvel étudiant.
	 * @param studentID
	 * 				L'identifiant du nouvel étudiant.
	 * @param firstname
	 * 				Le prénom du nouvel étudiant.
	 * @param surname
	 * 				Le nom du nouvel étudiant.
	 * @param pwd
	 * 				Le mot de passe du nouvel étudiant.
	 * @return
	 * 		Un boolean indiquant si le nouvel étudiant a bien été créé
	 */
	public boolean addStudent(String adminLogin, String newStudentLogin,int studentID, String firstname, String surname, String pwd);
	/**
	 * Fonction permettant de supprimer un utilisateur. Elle renvoie true si l'utilisateur a été supprimé et false sinon. 
	 * Cette fonction devra tester si l'utilisateur existe ou non, puis elle devra le retirer de la base de donnée. 
	 * !!!!!! Si c'est un étudiant, il faudra penser à le retirer du groupe auquel il appartient. !!!
	 * @param adminLogin
	 * 				Le login de l'administrateur qui va supprimer l'utilisateur.
	 * @param userLogin
	 * 				Le login d'utilisateur à supprimer.
	 * @return
	 * 		Un boolean indiquant si l'utilisateur a bien été supprimé.
	 */
	public boolean removeUser(String adminLogin, String userLogin);
	/**
	 * Fonction permettant d'ajouter un groupe. Elle renvoie true si le groupe a été ajouté et false sinon. 
	 * Cette fonction devra tester si le groupe existe déjà ou non, puis elle devra le créer et le sauvegarder dans la base de donnée. 
	 * @param adminLogin
	 * 				Le login de l'administrateur qui va créer le groupe.
	 * @param groupId
	 * 				L'identifiant du groupe à créer.
	 * @return
	 * 		Un boolean indiquant si le groupe a été créé.
	 */
	public boolean addGroup(String adminLogin, int groupId);
	/**
	 * Fonction permettant de supprimer un groupe. Elle renvoie true si le groupe a été supprimé et false sinon. 
	 * Cette fonction devra tester si le groupe existe ou non, puis elle devra le retirer de la base de donnée. 
	 * !!!!!! Pensez à retirer tous les étudiants de ce groupe !!!
	 * @param adminLogin
	 * 				Le login de l'administrateur qui va supprimer le groupe.
	 * @param groupId
	 * 				Identifiant du groupe à supprimer.
	 * @return
	 * 		Un boolean indiquant si le groupe a bien été supprimé.
	 */
	public boolean removeGroup(String adminLogin, int groupId);
	/**
	 * Fonction permettant d'associer un étudiant à un groupe. Elle renvoie true si l'association a été réalisée et false sinon. 
	 * Cette fonction devra tester si l'étudiant et le groupe existent ou non, puis elle devra sauvegarder la base de donnée. 
	 * @param adminLogin
	 * 				Le login de l'administrateur qui va associer un étudiant à un groupe.
	 * 
	 * @param studentLogin
	 * 				Login de l'étudiant
	 * @param groupId
	 * 				Identifiant du groupe.
	 * @return
	 * 		Un boolean indiquant si l'association a bien été réalisée.
	 */
	public boolean associateStudToGroup(String adminLogin, String studentLogin, int groupId);
	/**
	 * Fonction permettant de récupérer toutes les informations des utilisateurs sous la forme d'un 
	 * tableau de chaînes de caractères où chaque ligne contient toutes les informations d'un utilisateur.
	 * 
	 * @return
	 * 		Un tableau de String contenant toutes les infos de tous les utilisateurs.
	 */
	public String[] usersToString();
	/**
	 * Fonction permettant de récupérer les logins des utilisateurs sous la forme d'un 
	 * tableau de chaînes de caractères où chaque ligne contient le login d'un utilisateur.
	 * 
	 * @return
	 * 		Un tableau de String contenant le login de tous les utilisateurs.
	 */
	public String[] usersLoginToString();
	/**
	 * Fonction permettant de récupérer les logins des étudiants sous la forme d'un 
	 * tableau de chaînes de caractères où chaque ligne contient le login d'un étudiant.
	 * 
	 * @return
	 * 		Un tableau de String contenant le login de tous les étudiants.
	 */
	public String[] studentsLoginToString();
	/**
	 * Fonction permettant de récupérer les identifiants des groupes sous la forme d'un 
	 * tableau de chaînes de caractères où chaque ligne contient l'identifiant d'un groupe.
	 * 
	 * @return
	 * 		Un tableau de String contenant l'identifiant de tous les groupes.
	 */
	public String[] groupsIdToString();
	/**
	 * Fonction permettant de récupérer toutes les informations des groupes sous la forme d'un 
	 * tableau de chaînes de caractères où chaque ligne contient les informations d'un groupe.
	 * 
	 * @return
	 * 		Un tableau de String contenant toutes les informations de tous les groupes.
	 */
	public String[] groupsToString();
	
	/**
	 * Fonction chargeant la base de donnée contenue dans un fichier XML.
	 * @return
	 * 		Un boolean indiquant si le chargement a bien été réalisée.
	 */
	public boolean loadDB();
	/**
	 * Fonction sauvegardant la base de donnée dans un fichier XML.
	 * @return
	 * 		Un boolean indiquant si la sauvegarde a bien été réalisée.
	 */
	public boolean saveDB();

}