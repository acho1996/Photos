package model;

import java.text.ParseException;
import java.io.Serializable;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Kanak Borad
 * @author Anthony Cho
 *
 */
public class Album implements Comparable<Album>, Serializable{
	
	/**
	 * Name of the albums
	 */
	private String Names;
	
	/**
	 * List of photos in album
	 */
	private ArrayList<Photo> photos = new ArrayList<Photo>();
	
	/**
	 * @param calendar 
	 * @return string in MM/dd/yyyy format
	 */
	private String getDateString(Calendar calendar) 
	{
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DATE);
		int year = calendar.get(Calendar.YEAR);
		
		return month + "/" + day + "/" + year;
	}
	
	/**
	 * Album Constructor
	 * @param name Name assigns to the album
	 */
	public Album(String Names) {	//assigns name to string
		this.Names = Names;
	}
	
	/**
	 * @param sets name of the album
	 */
	public void setName(String Names) {	//sets album name
		this.Names = Names;
	}
	
	/**
	 * 
	 * @return gets name of the album
	 */
	public String getName() {	//get album name
		return this.Names;
	}
	
	/**
	 * @param  removes photo from this album
	 */
	public void removePhoto(Photo photo) 
	{
		this.photos.remove(photo);
	}

	
	/**
	 * @param compares to other album
	 * @return 0 as default
	 */
	public int compareTo(Album album) //compares albums
	{
		return 0;
	}
	
	/**
	 * @return list of photos in album
	 */
	public ArrayList<Photo> getPhotos(){	//gets photos
		return this.photos;
	}
	
	/**
	 * @return the album as a string. 
	 */
	public String toString() {
		int NumPhotos = photos.size();
		
		if(NumPhotos == 0) 
		{
			return this.Names;
		}
		
		SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
		
		Calendar Recent = Calendar.getInstance();
		Recent.clear();
			try {
				Recent.setTime(date.parse("12/31/9999"));
			} catch (ParseException e2) 
			{
				e2.printStackTrace();
			}
			Recent.set(Calendar.MILLISECOND, 0);
		Calendar leastRecent = Calendar.getInstance();
		leastRecent.clear();
			try {
				leastRecent.setTime(date.parse("12/31/1000"));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			leastRecent.set(Calendar.MILLISECOND, 0);
		

		Date PicDate = new Date();
		Calendar CalendarCheck = Calendar.getInstance();
		CalendarCheck.clear();
		for(int i = 0; i < NumPhotos; i++) {
			Photo p = photos.get(i);
			
		    try {
		    	PicDate = date.parse(p.getDate());
			} catch(Exception e){
				return this.Names + "  ||  " + "Photos: " + NumPhotos + "    " + "Date Range: " + " unknown ";
			}
		    CalendarCheck.setTime(PicDate);
		    CalendarCheck.set(Calendar.MILLISECOND, 0);
			
			if(CalendarCheck.after(leastRecent)) {
				leastRecent.setTime(CalendarCheck.getTime());
			}
			
			if(CalendarCheck.before(Recent)) {
				Recent.setTime(CalendarCheck.getTime());
			}
		}
		
		String d1 = getDateString(Recent);
		String d2 = getDateString(leastRecent);
		return this.Names + "  ||  " + "Photos: " + NumPhotos + "    " + "Date Range: " + d1 + " - " + d2;
	}
	
}