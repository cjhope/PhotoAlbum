package cs213.photoAlbum.model;

import java.io.Serializable;
import java.util.*;

/**
 * @author Michael
 *
 */
public class user implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5020725678063661452L;
	/**uniqueID: string, used to login.
	 * fullName: user's full name.
	 * albumNum: Number of albums the user has.
	 */
	public String uniqueID;
	public String fullName;
	
	public ArrayList<album> albumNames;
	
	/**
	 * @param albumName- Name of album to be added.
	 * Adds an album.
	 */
	public String addAlbum(String albumName){
		String message;
		for(int i = 0; i < albumNames.size(); i++){
			if(albumNames.get(i).name.equals(albumName)){
				message = "album already exists for user :"+albumName;
			System.out.println("album already exists for user " +  this.uniqueID + ":");
			System.out.println(albumName);
			return message;
			}
		}
		album album1 = new album(albumName);
		albumNames.add(album1);
		message = "created album for user " +  this.uniqueID + ":"+albumName;
		System.out.println("created album for user " +  this.uniqueID + ":");
		System.out.println(albumName);
		return message;
		
	}
	
	/**
	 * @param photoName: Name of the photo.
	 * @param oldAlbumName: Name of the old album.
	 * @param newAlbumName: Name of the new album.
	 */
	public void movePhotos(String photoName, String oldAlbumName, String newAlbumName){
		boolean seenOld = false, seenNew = false;
		int j = -1, k = -1;
		for(int i = 0; i < albumNames.size(); i++){
			if(albumNames.get(i).name.equals(oldAlbumName)){
				seenOld = true;
				j = i;
			}
			if(albumNames.get(i).name.equals(newAlbumName)){
				seenNew = true;
				k = i;
			}
		}
		if(seenOld && seenNew){
			photo tempPhoto = albumNames.get(j).getPhoto(photoName);
			System.out.println(tempPhoto.albumName.toString());
			photo check =	albumNames.get(j).movePhoto(tempPhoto, oldAlbumName);
			if(check != null){
				check.addAlbum(newAlbumName);
				
				albumNames.get(k).albumContents.add(check);
				
				System.out.println("Moved photo " +  photoName + ":\n" + photoName + " - from album " + 
									oldAlbumName + " to " + newAlbumName + "\n");
			}
		}else{
			System.out.println("Photo " + photoName + " does not exist in album "+oldAlbumName + "\n");
		}
	}
	
	/**
	 * @param albumName
	 */
	public void listPhotos(String albumName){
		for(int i = 0; i < albumNames.size(); i++){
			if(albumNames.get(i).name.equals(albumName)){
				albumNames.get(i).listPhoto();
				return;
			}
		}
		System.out.println("album does not exist for user " +  this.uniqueID + ":");
		System.out.println(albumName + "\n");
		return;
		
	}
	
	/**
	 * @param albumName- Name of album to be deleted.
	 * Deletes a user's album.
	 */
	public void deleteAlbum(String albumName){
		for(int i = 0; i < albumNames.size(); i++){
			if(albumNames.get(i).name.equals(albumName)){
				albumNames.remove(albumNames.get(i));
				System.out.println("deleted album for user " +  this.uniqueID + ":");
				System.out.println( albumName+ "\n");
				return;
			}
		}
		System.out.println("album does not exist for user " +  this.uniqueID + ":");
		System.out.println(albumName+ "\n");
		return;
	}
	
	/**
	 * @param oldName- Name of the old album that will be changed.
	 * @param newName- New name of the album.
	 * Renames an album.
	 */
	public void renameAlbum(String oldName, String newName){
		for(int i = 0; i < albumNames.size(); i++){
			if(albumNames.get(i).name.equals(oldName)){
				albumNames.remove(albumNames.get(i));
				album album1 = new album(newName);
				albumNames.add(album1);
				System.out.println("edited album for user " +  this.uniqueID + ":");
				System.out.println("changed " + oldName + " to "  + newName + "\n");
				return;
				
					}
			}
		
		
			System.out.println("album does not exist for user " +  this.uniqueID + ":");
			System.out.println(oldName+ "\n");

}
	/**
	 * Lists albums in commandline.
	 */
	public void listAlbums(){
		int i = 0;
		if(albumNames.size() == 0){
			System.out.println("no albums exist for user " +  this.uniqueID + ":");
			return;
		}
		while(i < albumNames.size()){
			System.out.println(albumNames.get(i).name 
					+ " number of photos:" + albumNames.get(i).albumContents.size());
			i++;
		}
	}
	
	/**
	 * @param albumName: name of the album.
	 * @return object of the desired album.
	 */
	public album getAlbum(String albumName){
		for(int i = 0; i < albumNames.size(); i++){
			if(albumNames.get(i).name.equals(albumName)){
				return albumNames.get(i);
					}
			}
		return null;
	}

	
	/**
	 * @param uniqueID- The user's unique ID (String, used to login).
	 * @param name- The user's full name.
	 * Constructor for each user
	 */
	public user(String uniqueID, String name){
		this.uniqueID = uniqueID;
		this.fullName = name;
		albumNames = new ArrayList();
		
	}
	
	public String toString(){
		return uniqueID+" - "+fullName;
		
	}
	
}
