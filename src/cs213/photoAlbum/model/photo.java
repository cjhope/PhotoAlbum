package cs213.photoAlbum.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import javax.swing.ImageIcon;

/**
 * @author Michael
 *
 */
public class photo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8841631458889041722L;
	
	public static final String storDir = "../data";

	
	/**name: photo name.
	 * caption: photo caption.
	 * cal: calendar.
	 * tags: ArrayList to store tag types and values.
	 */
	public String name;
	String caption;
	public ArrayList<String> albumName;
	Calendar cal;
	File photoFile;

	public HashMap<String, String> tagMap;
	
	/**
	 * @return Returns the photo name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name: Name of the photo.
	 * @param caption: Caption of the photo.
	 * @param albumName: ArrayList containing albums the photo is in.
	 * @param lastModified: Date when last modified.
	 */
	public photo(String name, String caption, String albumName, Date lastModified, File phoFile){
		this.name = name;
		this.caption = caption;
		this.albumName = new ArrayList<String>();
		this.albumName.add(albumName);
		this.photoFile = phoFile;
		cal = new GregorianCalendar();
		cal.setTime(lastModified);
		cal.set(Calendar.MILLISECOND,0);
		tagMap = new HashMap<String, String>();	
		try {
			writePhoto(this);
		} catch (IOException e) {		}
	}
	
	/**
	 * @return returns the caption of the photo.
	 */
	public String getCaption() {
		return caption;
	}
	
	/**
	 * @param alName: Adds an album to the albumName arraylist.
	 */
	public void addAlbum(String alName){
		if(albumName.contains(alName)){
			return;
		}
		albumName.add(alName);
		try {
			writePhoto(this);
		} catch (IOException e) {
		}
	}
	
	/**
	 * @param alName: Removes an album from the albumname arraylist.
	 */
	public void removeAlbum(String alName){
		albumName.remove(alName);
		try {
			writePhoto(this);
		} catch (IOException e) {
		}
	}
	
	/**
	 * @return returns the calendar.
	 */
	public Calendar getCal() {
		return cal;
	}
	/**
	 * @param cal: Sets Calendar parameter.
	 */
	public void setCal(Calendar cal) {
		this.cal = cal;
		return;
	}
	
	/**
	 * @param tag: Tag to be added.
	 * Adds a tag to photo's list of tags.
	 */
	public void addTag(String type, String value){
		if(tagMap.containsKey(type)){
			tagMap.put(type, tagMap.get(type).concat(";" + value));
			return;
		}
		tagMap.put(type, value);
		
		try {
			writePhoto(this);
		} catch (IOException e) {
		}
		return;
	}
	
	/**
	 * @param tag: Tag to be removed.
	 * Removes a photo's tag.
	 */
	public void removeTag(String type, String value){
		if(tagMap.containsKey(type)){
			
			if(tagMap.get(type).contains(value)){
				String temp = tagMap.get(type);
				if(tagMap.get(type).startsWith(value)){
					if(value.length()==temp.length()){
						tagMap.remove(type);
					}else{
						tagMap.put(type, temp.substring(value.length()+1, temp.length()));
					}
				}else{
					
					int i = tagMap.get(type).indexOf(value);
					String temp2 = tagMap.put(type, temp.substring(0, i));
					tagMap.put(type, temp.substring(0, i-1) + temp.substring(i+value.length(), temp.length()));
				
				}
			}else{System.out.println("Tag value not found");}
		}else{System.out.println("Tag type not found");}
		
		try {
			writePhoto(this);
		} catch (IOException e) {
		}	
	}
	
	/**
	 * @param oldTag: Tag that is being edited.
	 * @param newTag: What tag should be changed to.
	 * Edits a tag.
	 */
	public void editTag(String type, String oldTagValue, String newTagValue){
		if(tagMap.containsKey(type)){
			if(tagMap.get(type).contains(oldTagValue)){
				tagMap.put(type, tagMap.get(type).replace(oldTagValue, newTagValue));
			}else{System.out.println("Tag value not found");}
		}else{System.out.println("Tag type not found");}
		try {
			writePhoto(this);
		} catch (IOException e) {
		}
		return;
	}
	
	/**
	 * Lists a photo's tags in the command line.
	 */
	public String listTags(){
		ArrayList<String> retList = new ArrayList<String>();
		int i = 0;
		Set keys = tagMap.keySet();
		Iterator<String> it = keys.iterator();
		if(tagMap.isEmpty()){
			return "There are no tags for the photo.";
		}
		while( it.hasNext()){
			String type =  it.next();
			String value =  tagMap.get(type);
			StringTokenizer tok = new StringTokenizer(value, ";");
			while(tok.hasMoreTokens()){
				String toAdd = type +":"+ tok.nextToken();
				System.out.println(toAdd);
				retList.add(toAdd);
			}
		}
		return retList.toString();
	}
	

	
	/**
	 * @param albums: Lists photo info. of the album.
	 */
	public void photoInfo(ArrayList<String> albums){
		System.out.println("Photo file name:" + name);
		System.out.println("Albums: " );
		while(!albums.isEmpty()){
			String temp = albums.get(0);
			System.out.print("");
		}

	}
	
	/**
	 * @param p
	 * @throws IOException
	 */
	public void writePhoto(photo p) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(storDir+File.separatorChar+name+".dat"));
		oos.writeObject(p);
		oos.close();
	}

	/**
	 * @param name: name of photo to read.
	 * @return : Photo object that is read.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public photo readPhoto(String name) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(storDir+File.separator+name+".dat"));
		photo loaded = (photo) ois.readObject();
		ois.close();
		return loaded;
	}
	
	public ImageIcon getIcon(){
		ImageIcon icon = new ImageIcon(photoFile.getPath());
		return icon;
	}
	
	public String toString(){
		return name;
	}
	
	public photo clone(){
		photo ret = new photo(name, caption, "", cal.getTime(), photoFile);
		return ret;
	}
	
}
