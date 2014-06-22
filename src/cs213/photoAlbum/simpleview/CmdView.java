package cs213.photoAlbum.simpleview;

import cs213.photoAlbum.model.user;
import cs213.photoAlbum.control.*;

public class CmdView {
	
	static Control con = new Control();
	
	
	/**
	 * @param args: Main method.
	 */
	public static void main(String[] args){
		if(args.length == 0){
			System.out.println("Please enter a command when running this program. Exiting program.");
			return;
		}
	
		con.readUsers();
		
		String command = args[0];
		switch (command.toLowerCase()){
			case "listusers":
				listusers();
				break;
			case "adduser":
				if(args.length<3)
					System.out.println("Please input user id and full name When creating a new user. Exiting program");
				else{
					adduser(args[1], args[2]);
					System.out.println("Created user.");
				}
				break;
			case "deleteuser":
				if(args.length<2)
					System.out.println("Please specify a user to delete by their user id. Exiting program");
				else
					deleteuser(args[1]);
				break;
			case "login":
				if(args.length<2)
					System.out.println("Please specify a user to login as. Exiting program");
				else
					login(args[1]);
				break;
			default:
				System.out.println("Command not recognized. Exiting program");
				break;
		}
		return;
	}

	/**
	 * Lists users.
	 */
	public static void listusers(){
		Control.listUsers();
		return;
	}
	
	/**
	 * @param uid: String of user id.
	 * @param fullName: user's full name.
	 * @return
	 */
	public static user adduser(String uid, String fullName){
		Control.addUser(uid, fullName);
		return null;
	}
	
	/**
	 * @param uid: String of user id.
	 */
	public static void deleteuser(String uid){
		Control.deleteUser(uid);
		return;
	}
	
	/**
	 * @param uid: String of user id.
	 */
	public static void login(String uid){
		Control.login(uid);
		return;
	}
	
}
