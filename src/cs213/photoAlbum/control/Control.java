package cs213.photoAlbum.control;

import cs213.photoAlbum.model.*;

import java.text.*;
import java.util.*;
import java.io.*;


/**
 * @author Michael & Imran
 *
 */
public class Control {
	
	/**
	 * back: backend object to manipulate user data.
	 */
	static backend back = new backend();
	
	
	/**
	 * @param user1: user to be added.
	 * 
	 */
	public static String addUser(String uid, String fullName){
		String ret = back.writeUser(uid, fullName);
		if(!ret.equals("User "+uid+" already exists."))
			try {
				back.writeList();
			} catch (IOException e) {
				System.out.println("Could not write user list.");
			}
		return ret;
	}
	
	public void setUser(user set){
		back.setUsr(set);
		return;
	}
	
	public void serUser(){
		try {
			back.serializeUser(back.getUsr());
		} catch (IOException e) {
			System.out.println("Could not update user "+back.getUsr().uniqueID+".");
		}
		
		try {
			back.writeList();
		} catch (IOException e) {
			System.out.println("Could not update user list.");
		}
		
		return;
	}
	
	/**
	 * @param user1: user to be deleted
	 */
	public static String deleteUser(String uid){
		String ret = back.deleteUser(uid);
		if(!ret.equals("User "+uid+" not found."))
			try {
				back.writeList();
			} catch (IOException e) {
				System.out.println("Could not write user list.");
			}
		return ret;
		
	}
	
	/**
	 * @param uid: String of user ID.
	 * @return user object.
	 */
	public static user findUser(String uid){
		user log = back.findUser(uid);
		return log;
	}
	
	/**
	 * Lists all of the users that are saved.
	 */
	public static String listUsers(){
		String uList = back.listUsers();
		if (uList.isEmpty())
			return "No users to list.";
		return uList;
	}
		
	/**
	 * Reads users into memory.
	 */
	public void readUsers(){
		try {
			back.readList();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Could not find user list file.");
		}
		return;
	}
	
	/**
	 * @return new user usr created by data input from user.
	 */
public static void login(String uid){
	user log = findUser(uid);
	if(log == null)
		return;
	
	back.setUsr(log);
	
	Scanner in = new Scanner(System.in);
	String input = "";
	
	
	while(!input.equals("logout")){
		
		input = in.nextLine();
		StringTokenizer st = new StringTokenizer(input, " ");
		String temp = st.nextToken(), temp2 = "";
		
		
		if(!temp.toLowerCase().equals("getphotosbydate")){
		while(st.hasMoreTokens()){
			temp2 += st.nextToken()+" ";
		}}
		temp2= temp2.trim();
		StringTokenizer st2 = new StringTokenizer(temp2, "\"");
		
		
		if(temp.toLowerCase().equals("addphoto")){
		
			if(st2.countTokens() != 5){
				System.out.println("Error: Invalid number of arguments");
				continue;
			}
			String photoName = st2.nextToken();
		
			
			File photoFile = new File(photoName);
			if(!photoFile.exists()){
				System.out.println(photoFile.getName() + " does not exist.");
				continue;
			}
			photoName = photoFile.getName();
			Date lastModified = new Date(photoFile.lastModified());
			st2.nextToken();
			String caption = st2.nextToken();
			st2.nextToken();
			String albumName = st2.nextToken();
			
			photo tempPhoto = new photo(photoName, caption, albumName, lastModified, photoFile);
			album tempAlbum = log.getAlbum(albumName);
			
			if(tempAlbum == null){
				System.out.println("Error: Album does not exist for user:" + log.uniqueID);
				continue;
			}
			tempAlbum.addPhoto(tempPhoto, caption);
			
		}else if(temp.toLowerCase().equals("listphotos")){
			if(st2.countTokens() != 1){
				System.out.println("Error: Invalid number of arguments");
				continue;
		}
			String albumName = st2.nextToken();
			
			album tempAlbum = log.getAlbum(albumName);
			
			if(tempAlbum == null){
				System.out.println("Error: Album does not exist for user:" + log.uniqueID);
				continue;
			}
			tempAlbum.listPhoto();
			
		}else if(temp.toLowerCase().equals("removephoto")){
			if(st2.countTokens() != 3){
				System.out.println("Error: Invalid number of arguments");
				continue;
		}
			String photoName = st2.nextToken();
			st2.nextToken();
			String albumName = st2.nextToken();
			
			album tempAlbum = log.getAlbum(albumName);
			if(tempAlbum == null){
				System.out.println("Error: Album does not exist for user:" + log.uniqueID);
				continue;
			}
			photo tempPhoto = tempAlbum.getPhoto(photoName);
			if(tempPhoto == null){
				System.out.println("Error: Photo does not exist for user:" + log.uniqueID);
				continue;
			}
			tempAlbum.removePhoto(tempPhoto);
			continue;
			
		}else if(temp.toLowerCase().equals("createalbum")){
			if(st2.countTokens() != 1){
				System.out.println("Error: Invalid number of arguments");
				continue;
		}
			String albumName = st2.nextToken();
			
			log.addAlbum(albumName);
			
		}
		else if(temp.toLowerCase().equals("deletealbum")){
			if(st2.countTokens() != 1){
				System.out.println("Error: Invalid number of arguments");
				continue;
		}
			String albumName = st2.nextToken();
			
			log.deleteAlbum(albumName);
			
			
		}
		else if(temp.toLowerCase().equals("listalbums")){
				if(st2.countTokens() != 0){
					System.out.println("Error: Invalid number of arguments");
					continue;
		}
				log.listAlbums();
			
		}
		else if(temp.toLowerCase().equals("movephoto")){
			
				if(st2.countTokens() != 5){
					System.out.println("Error: Invalid number of arguments");
					continue;
		}
				String photoName = st2.nextToken();
				st2.nextToken();
				String oldAlbumName = st2.nextToken();
				st2.nextToken();
				String newAlbumName = st2.nextToken();
				
				log.movePhotos(photoName, oldAlbumName, newAlbumName);
				
	}else if(temp.toLowerCase().equals("addtag")){
		if(st2.countTokens() != 3){
			System.out.println("Error: Invalid number of arguments");
			continue;
			}
		String photoName = st2.nextToken();
		String type = st2.nextToken();
		type = type.substring(1,type.length()-1);
		String value = st2.nextToken();
		
		Iterator<album> it = log.albumNames.iterator();
		photo tempPhoto;
		album albumIt;
		while(it.hasNext()){
			albumIt = (album) it.next();
			tempPhoto = albumIt.getPhoto(photoName);
			if( tempPhoto != null){
				tempPhoto.addTag(type, value);
			}
		}

	}else if(temp.toLowerCase().equals("deletetag")){
		if(st2.countTokens() != 3){
			System.out.println("Error: Invalid number of arguments");
			continue;
			}
		String photoName = st2.nextToken();
		String type = st2.nextToken();
		type = type.substring(1,type.length()-1);
		String value = st2.nextToken();
		
		Iterator<album> it = log.albumNames.iterator();
		photo tempPhoto;
		album albumIt;
		while(it.hasNext()){
			albumIt = (album) it.next();
			tempPhoto = albumIt.getPhoto(photoName);
			if( tempPhoto != null){
				tempPhoto.removeTag(type, value);
			}
		}
	}else if(temp.toLowerCase().equals("listphotoinfo")){
		if(st2.countTokens() != 1){
			System.out.println("Error: Invalid number of arguments");
			continue;
			}
		
		String photoName = st2.nextToken();
		
		Iterator<album> it = log.albumNames.iterator();
		photo tempPhoto, infoPhoto = new photo ("", "", "",new Date(0L), null);
		
		album albumIt;
		ArrayList<String> albumList = new ArrayList<String>();
		int i = 0;
		String caption = "";
		Calendar listcal = null;
		
		
		while(i < log.albumNames.size()){
			albumIt = log.albumNames.get(i);
			tempPhoto = albumIt.getPhoto(photoName);
			if( tempPhoto != null){
				albumList.add(log.albumNames.get(i).name);
			
				infoPhoto = tempPhoto;
			
				caption = tempPhoto.getCaption();
				
				listcal = tempPhoto.getCal();
				
				
			}
			i++;
		}
		
		if(albumList.isEmpty()){
			System.out.println("Photo " + photoName + " does not exist");
			continue;
		}
		System.out.println("Photo file name: " + photoName);
		
		i = 0;
		String albums = "";
		while(i < albumList.size()){
			if(i == albumList.size()-1){
			    albums += albumList.get(i);
			    break;
			}
			albums += (albumList.get(i) + ", ");
			i++;
		}
		System.out.println("Albums: " + albums);
		System.out.println("Date: "+listcal.getTime());
		System.out.println("Caption: " + caption);
		System.out.println("Tags: ");
		infoPhoto.listTags();
		

	}else if(temp.toLowerCase().equals("getphotosbydate")){
		

		String start = st.nextToken();
		String end = st.nextToken();		
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
	    Date startDate;
	    Date endDate;
	    try {
	        startDate = df.parse(start);
	        endDate = df.parse(end);
	    } catch (ParseException e) {
	    	System.out.println("Please input date in a MM/DD/YYYY format.");
	    	continue;
	    }
	    
	    start = df.format(startDate);
	    end = df.format(endDate);
	    
	    ArrayList<photo> goodTime = new ArrayList<photo>();
		
	    boolean add=false;
	    int i = 0;
	    album tempAlbum;
	    photo tempPhoto;
		while(i<log.albumNames.size()){
			tempAlbum = log.albumNames.get(i);
			int j = 0;
			while(j < tempAlbum.albumContents.size()){
				tempPhoto = tempAlbum.albumContents.get(j);
				if(tempPhoto.getCal().getTime().after(startDate)&&tempPhoto.getCal().getTime().before(endDate) && !goodTime.contains(tempPhoto))
					goodTime.add(tempPhoto);
				j++;
			}
			i++;
		}
		
		if(goodTime.isEmpty())
			System.out.println("No photos found for that date range.");
		else{
			System.out.println("Photos for user "+log.uniqueID+" in range "+start+" to "+end+":");
			for(int count = 0; count < goodTime.size(); count++){
				System.out.println(goodTime.get(count).name+"- caption: "+goodTime.get(count).getCaption() + " - Album: " + goodTime.get(count).albumName.toString()
						+ " - Date: " + goodTime.get(count).getCal().getTime());
			}
		}
		
	}else if(temp.toLowerCase().equals("getphotosbytag")){
	
		
		ArrayList<photo> photoswithTag = new ArrayList<photo>();
		ArrayList<String> values = new ArrayList<String>();
		
	while(st2.hasMoreTokens()){
		String parse = st2.nextToken();
		parse = parse.trim();
		if(parse.contains(":") ||parse.isEmpty())
			continue;
		values.add(parse);
		
			int i = 0;
			album tempAlbum;
			photo tempPhoto;
			
			boolean add=false;
			while(i<log.albumNames.size()){
				tempAlbum = log.albumNames.get(i);
				int j = 0;
				while(j < tempAlbum.albumContents.size()){
					tempPhoto = tempAlbum.albumContents.get(j);
					for(int k=0; k<values.size(); k++){
						if(!tempPhoto.tagMap.containsValue(values.get(k))){
							add = false;
							break;
						}
						add = true;
					/*if(tempPhoto.tagMap.containsValue(value)){
						photoswithTag.add(tempPhoto);*/
					}
					if(add && !photoswithTag.contains(tempPhoto))
						photoswithTag.add(tempPhoto);
					j++;
				}
				i++;
			}
			
		
		
		
			
		}
	if(photoswithTag.isEmpty()){
		System.out.println("No photos with tag exist");
		continue;
	}
	
	
		int i = 0;
		String value = "";
		while (i < values.size()){
			value += values.get(i) + " ";
			i++;
			
		}
		i = 0;
		System.out.println("Photos for user " + log.uniqueID +" containing: " + value.toString());
		while(i < photoswithTag.size()){
			System.out.println(photoswithTag.get(i).name + " - Album: " + photoswithTag.get(i).albumName.toString()
								+ " - Date: " + photoswithTag.get(i).getCal().getTime());
			i++;
		}
	}else if(input.equals("logout")) {
		break;}
	else{
		System.out.println("Command not recognized. Please input an appropriate command.");
	}
			
}
	
	try {
		back.serializeUser(back.getUsr());
	} catch (IOException e) {
		System.out.println("Could not update user "+back.getUsr().uniqueID+".");
	}
	
	try {
		back.writeList();
	} catch (IOException e) {
		System.out.println("Could not update user list.");
	}
	
	return;
}

/**
 * @param usr user object
 * @param tag tag
 * @return arraylist of photos
 */
public ArrayList<photo> getPhotosByTag(user usr, String tag){
	
	ArrayList<photo> photoswithTag = new ArrayList<photo>();
	ArrayList<String> values = new ArrayList<String>();
	

	if(tag.contains(":")){
		
		String tags[] = tag.split(":");
		String type = tags[0];
		String value = tags[1];
		
		type = type.trim();
		value = value.trim();
		
		int i = 0;
		album tempAlbum;
		photo tempPhoto;
		
		boolean add=false;
		while(i<usr.albumNames.size()){
			tempAlbum = usr.albumNames.get(i);
			int j = 0;
			while(j < tempAlbum.albumContents.size()){
				tempPhoto = tempAlbum.albumContents.get(j);
				
					if(!tempPhoto.tagMap.containsValue(value)){
						add = false;
						break;
					}
					else{
					add = true;
					
				}
				if(add && !photoswithTag.contains(tempPhoto))
					photoswithTag.add(tempPhoto);
				j++;
			}
			i++;
		}
		if(photoswithTag.isEmpty()){
			return null;
		}
		return photoswithTag;
		
		}else{
			
			String value = tag;
			int i = 0;
			album tempAlbum;
			photo tempPhoto;
			
			boolean add=false;
			while(i<usr.albumNames.size()){
				tempAlbum = usr.albumNames.get(i);
				int j = 0;
				while(j < tempAlbum.albumContents.size()){
					tempPhoto = tempAlbum.albumContents.get(j);
					
						if(!tempPhoto.tagMap.containsValue(value)){
							add = false;
							break;
						}
						else{
						add = true;
						
					}
					if(add && !photoswithTag.contains(tempPhoto))
						photoswithTag.add(tempPhoto);
					j++;
				}
				i++;
			}
			if(photoswithTag.isEmpty()){
				return null;
			}
			return photoswithTag;
			
		}
	}
		

/**
 * @param usr user object
 * @param dates dates
 * @return arraylist of photos
 */
public ArrayList<photo> getPhotosByDate(user usr, String dates){
    ArrayList<photo> goodTime = new ArrayList<photo>();

	StringTokenizer st = new StringTokenizer(dates, " ");	
	
	String start = st.nextToken();
	String end = st.nextToken();
	
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
    Date startDate;
    Date endDate;
    try {
        startDate = df.parse(start);
        endDate = df.parse(end);
    } catch (ParseException e) {
    	System.out.println("Please input date in a MM/DD/YYYY format.");
    	return null;
    }
    
    start = df.format(startDate);
    end = df.format(endDate);
    
	
    boolean add=false;
    int i = 0;
    album tempAlbum;
    photo tempPhoto;
	while(i<usr.albumNames.size()){
		tempAlbum = usr.albumNames.get(i);
		int j = 0;
		while(j < tempAlbum.albumContents.size()){
			tempPhoto = tempAlbum.albumContents.get(j);
			if(tempPhoto.getCal().getTime().after(startDate)&&tempPhoto.getCal().getTime().before(endDate) && !goodTime.contains(tempPhoto))
				goodTime.add(tempPhoto);
			j++;
		}
		i++;
	}
	
		if(goodTime.isEmpty()){
			System.out.println("No photos found for that date range.");
			return null;
		}
		else{
		System.out.println("Photos for user "+usr.uniqueID+" in range "+start+" to "+end+":");
		return goodTime;
			}
	
}
	
	
}