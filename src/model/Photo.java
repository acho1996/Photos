package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * @author Kanak Borad
 * @author Anthony Choz
 *
 */
public class Photo implements Serializable{
	
	/**
	 * Caption of photo
	 */
	private String caption = "";
	
	/**
	 * Path of image file
	 */
	private String path;
	
	/**
	 * List of tags
	 */
	private ArrayList<Tag> tags = new ArrayList<Tag>();
	
	/**
	 * last modified date of photo
	 */
	private Calendar calendar = Calendar.getInstance();
	
	/**
	 * @param path path of image
	 */
	public Photo(String path) {
		this.path = path;
	}
	
	/**
	 * @param month sets month
	 * @param day sets day
	 * @param year sets year
	 */
	public void setDate(int month, int day, int year) {
		this.calendar.clear();
		this.calendar.set(year, month-1, day);
		this.calendar.set(Calendar.MILLISECOND, 0);
	}
	
	/**
	 * @return date in MM/dd/yyyy format
	 */
	public String getDate() {
		int m = this.calendar.get(Calendar.MONTH) + 1;
		int d = this.calendar.get(Calendar.DATE);
		int y = this.calendar.get(Calendar.YEAR);
		return m + "/" + d + "/" + y;
	}
	
	/**
	 * @return Calendar of photo
	 */
	public Calendar getCalendar() {
		return this.calendar;
	}
	
	/**
	 * Gets path
	 * @return file path
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * @param path sets path of file
	 */
	public void setPath(String path) {
		this.path = "file://" + path.replace('\\', '/');
	}
	
	/**
	 * @return Caption of current photo
	 */
	public String getCaption() {
		return this.caption;
	}
	
	/**
	 * @param caption sets string as caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	/**
	 * @return ArrayList of tags of photo
	 */
	public ArrayList<Tag> getTags(){
		return this.tags;
	}
}
