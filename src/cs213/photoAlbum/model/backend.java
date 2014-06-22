package cs213.photoAlbum.model;

import java.io.*;
import java.util.ArrayList;

/**
 * @author Imran
 *
 */
public class backend {

	public static final String storDir = "../data";
	public static final String storArrL = "0000UserList.dat";
	
	
	/**
	 * usr: User stored in memory.
	 */
	private user usr;
	public static ArrayList<user> userList = new ArrayList<user>();
	
	/**
	 * @param usr: usr to be added.
	 */
	public void addUser(user usr){
		userList.add(usr);
		return;
	}
	
	/**
	 * @param uid: id of user.
	 * @return user object that matches the id.
	 */
	public user findUser(String uid){
		int i = 0;
		for(; i < userList.size(); i++){
			if(userList.get(i).uniqueID.equals(uid)){
				try {
					return readUser(uid);
				} catch (ClassNotFoundException| IOException e) {
					System.out.println("Could not load user "+uid+".");
				}
			}
		}
		System.out.println("User "+uid+" not found.");
		return null;
	}
	
	/**
	 * @param usr: usr to be constructed.
	 */
	public void setUsr(user usr) {
		this.usr = usr;
	}

	/**
	 * @param uid : user id to read into memory
	 * @return usr :user object usr made from items in user file specified by uid.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public user readUser(String uid) throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(storDir+File.separator+uid));
		user ret = (user) ois.readObject();
		ois.close();
		return ret;
	}
	
	/**
	 * @param fullName 
	 * @param uid 
	 * @param uid : user id to specify user object to be written onto disk  
	 */
	public String writeUser(String uid, String fullName){
		for(int i=0; i<userList.size(); i++){
			if(userList.get(i).uniqueID.equals(uid)){
				return "User "+uid+" already exists.";
			}
		}
		user nu = new user(uid, fullName);
		try {
			serializeUser(nu);
		} catch (IOException e) {
			System.out.println("Could not write user "+uid+" to disk.");
		}
		userList.add(nu);
		return "Successfully added user "+uid+".";
	}
	
	/**
	 * @param uid : user id to specify user file to be deleted
	 */
	public String deleteUser(String uid){
		int i = 0;
		String ret;
		for(; i < userList.size(); i++){
			if(userList.get(i).uniqueID.equals(uid)){
				userList.remove(i);
				ret = "Removed user "+uid+".";
				System.out.println(ret);
				try {
					writeList();
				} catch (IOException e) {
					System.out.println("Could not update user list.");
				}
				return ret;
			}
		}
		ret = "User "+uid+" not found.";
		System.out.println(ret);
		return ret;
	}
	
	/**
	 * List of the user files in data folder.
	 */
	public String listUsers(){
		String uList = "";
		if(userList.isEmpty())
			System.out.println("No users to list.");
		else
			for(int i = 0; i < userList.size(); i++){
				System.out.println(userList.get(i).uniqueID);
				uList = uList.concat(userList.get(i).uniqueID+"\n");
			}
		return uList;
	}
	
	/**
	 * @throws IOException
	 */
	public void writeList() throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(storDir+File.separatorChar+storArrL));
		oos.writeObject(userList);
		oos.close();
	}
	
	@SuppressWarnings("unchecked")
	public void readList() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(storDir+File.separator+storArrL));
		userList = (ArrayList<user>) ois.readObject();
		ois.close();
	}
	
	/**
	 * @param usr: user to be serialized.
	 * @throws IOException
	 */
	public void serializeUser(user usr)throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(storDir+File.separatorChar+usr.uniqueID));
		oos.writeObject(usr);
		oos.close();
	}

	/**
	 * @return user object.
	 */
	public user getUsr() {
		return usr;
	}
	
}
