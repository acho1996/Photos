package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Kanak Borad
 * @author Anthony Cho
 */
public class User implements Serializable{
	
	/**
	 * Username of user
	 */
	String username;
	
	
	/**
	 * List of this albums of the user
	 */
	public ArrayList<Album> albums = new ArrayList<Album>();
	
	/**
	 * List of  users tags
	 */
	private ArrayList<String> tagTypes = new ArrayList<String>();
	
	/**
	 * @param user gets username
	 */
	public User(String user) {
		this.username = user;
	}
	
	/**
	 * @return username of  user
	 */
	public String toString() {
		return username;
	}
	
	/**
	 * @return username of user
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * @return gets list of Albums
	 */
	public ArrayList<Album> getAlbums(){
		return this.albums;
	}
	
	/**
	 * @return Gets list of tag
	 */
	public ArrayList<String> getTagTypes(){
		return this.tagTypes;
	}
	
	/**
	 * @param type type of tag
	 */
	public void addTagType(String type) {
		this.tagTypes.add(type);
	}
}
