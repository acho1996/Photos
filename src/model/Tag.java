package model;

import java.io.Serializable;

/**
 * @author Kanak Borad
 * @author Anthony Cho
 *
 */
public class Tag implements Comparable<Tag>, Serializable{
	/**
	 * The 'key' string value
	 */
	private String key;
	
	/**
	 * The 'value' string value
	 */
	private String value;
	
	/**
	 * Tag constructor
	 * @param key of pair
	 * @param value of pair
	 */
	public Tag(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * @return key of tag
	 */
	public String getKey() {
		return this.key;
	}
	
	/**
	 * @return value of tag
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * @param key 
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * @param value 
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 *@return joins tag in proper format
	 */
	public String toString() {
		return this.key + "\t:\t" + this.value;
	}
	
	/**
	 * comparator implements compareTo
	 * @param t compares to tag t
	 * @return 0 default
	 */
	public int compareTo(Tag t) {
		return 0;
	}
}
