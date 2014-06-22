package cs213.photoAlbum.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * @author Imran
 * Object contains a name for the album, the number of photos in the album, and the photos themselves in an ArrayList.
 */
public class album implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6969193343500661208L;
	
	/**
	 * name: Name of each photo album
	 * albumContents: Stores the photos in each album
	 */
	public String name;
    public ArrayList<photo> albumContents;
	
	/**
	 * @param p : photo to be added.
	 * @param newCaption: caption to be added.
	 * 
	 */
	public void addPhoto(photo p, String newCaption){
		
		photo pOld = findPhoto(p);
		if(pOld!=null)
			p = pOld;
		
		if(albumContents.contains(p)){
			System.out.println("Photo " + p.name + " already exists in album " + name);
			return;
		}
		albumContents.add(p);
		if(!p.albumName.contains(name)){
			p.albumName.add(name);
		}
		p.caption = newCaption;
		try {
			p.writePhoto(p);
		} catch (IOException e) {
		}
		System.out.println("Added photo " + p.getName() + ":");
		System.out.println(p.getCaption() + " - Album: "+ name + "\n");

	}
	
	
	/**
	 * @param p : photo to be removed
	 */
	public void removePhoto(photo p){
		if(!albumContents.contains(p)){
			System.out.println("Photo " + p.name + " does not exist in album " + name );
			return;
		}
		p.removeAlbum(name);
		albumContents.remove(p);
		System.out.println("Removed photo:\n" + p.getName() + " - From album " + name + "\n");
		return;
	}
	
	/**
	 * @param nu : New caption to use
	 * @param p : Photo to use new caption nu
	 *
	 */
	public void recaptcha(String nu, photo p){
		if(!albumContents.contains(p)){
			System.out.println("Photo " + p.name + " does not exist in album " + name  + "\n");
			return;
		}
		int photoNum = albumContents.lastIndexOf(p);
		albumContents.get(photoNum).caption = nu;
		System.out.println("Photo " + p.name + " caption changed to: " + nu + "\n");
		return;
	}
	
	

	/**
	 * @param name : name of new album
	 * 
	 */
	public album(String name){
		this.name = name;
		albumContents = new ArrayList<photo>();
	}
	
	/**
	 * Lists photos.
	 */
	public void listPhoto(){
		if (albumContents.isEmpty())
			System.out.println(name+" is empty.");
		else{
			System.out.println("Photos for album " + name);
			for(int i=0; i<albumContents.size();i++){
				System.out.println(albumContents.get(i).getName());
			}
		}
		return;
	}
	
	/**
	 * @param photoName: photo to be moved.
	 * @param oldAlbumName: old album name.
	 * @return
	 */
	public photo movePhoto(photo photoName, String oldAlbumName){
		if(!albumContents.contains(photoName)){
			return null;
		}
		photoName.removeAlbum(name);
		photo ret = albumContents.get(albumContents.indexOf(photoName));
		albumContents.remove(photoName);
		return ret;
		
	}
	
	/**
	 * @param photoName: name of the photo.
	 * @return photo object with the photo name.
	 */
	public photo getPhoto(String photoName){
		
		for(int i = 0; i < albumContents.size(); i++){
			if(albumContents.get(i).name.equals(photoName)){
				return albumContents.get(i);
			}
		}
		System.out.println("photo not found");
		return null;
		
	}

	/**
	 * @param p: photo to find.
	 * @return photo object.
	 */
	public photo findPhoto(photo p){
		photo target = null;
		try {
			target = p.readPhoto(p.name);
		} catch (ClassNotFoundException | IOException e) {
		}
		return target;
	}
	
	public String toString(){
		return name;
	}

}
