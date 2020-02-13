package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;

import controllers.Controller;

/**
 * @author Kanak Borad
 * @author Anthony Cho
 *
 */
public class UserData {

private static Controller controls = new Controller();
    

	public void GenerateStockUser() {    	   	
    	User u = new User("stock");
    	controls.setUser(u);
    	
    	Album a = new Album("Stock");
    	u.getAlbums().add(a);
    	
    	File testFile = new File(".\\stock_photos\\Stock1.jpg");
    	
    	if(!testFile.exists()) {
    		controls.showError("File DNE");
    	}
    	
    	a.getPhotos().add(setDateValues(new File(".\\stock_photos\\Stock1.jpg")));
    	a.getPhotos().add(setDateValues(new File(".\\stock_photos\\Stock2.jpg")));
    	a.getPhotos().add(setDateValues(new File(".\\stock_photos\\Stock3.jpg")));
    	a.getPhotos().add(setDateValues(new File(".\\stock_photos\\Stock4.jpg")));
    	a.getPhotos().add(setDateValues(new File(".\\stock_photos\\Stock5.jpg")));
    	a.getPhotos().add(setDateValues(new File(".\\stock_photos\\Stock6.jpg")));
    	a.getPhotos().add(setDateValues(new File(".\\stock_photos\\Stock7.jpg")));

    	controls.gotoHomePage();
    }
	
	 /**
	  * @param f File of photo
	  * @return Photo with modified date
	  */
	 private Photo setDateValues(File f) {
		 int m,d,y;
		 long dateLong = f.lastModified();
		 SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		 
		 sdf.applyPattern("MM");
		 m = Integer.parseInt(sdf.format(dateLong));
		 
		 sdf.applyPattern("dd");
		 d = Integer.parseInt(sdf.format(dateLong));
		 
		 sdf.applyPattern("yyyy");
		 y = Integer.parseInt(sdf.format(dateLong));
		 
		 Photo ph = new Photo(f.toURI().toString());
		 ph.setDate(m, d, y);
		 
		 return ph;
	 }
	
	
	/**
	 * @param u User to serialize
	 */
	public void saveUserData(User u) {
		File file = new File(".\\saves\\" + u.getUsername() + ".save");
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(u);
			oos.close();
		}catch (Exception e){
			controls.showError("Unable to export user data.");
			System.exit(1);
		}
	}
    
	/**

	 * @param username attempt to load user data to
	 */
    public void loadData(String username) {
		File file = new File(".\\saves\\" + username + ".save");
		if(!file.exists()) {
			if(username.equals("stock")) {
				GenerateStockUser();
				return;
			}
			controls.setUser(new User(username));
			return;
		}
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			User u = (User) ois.readObject();
			controls.setUser(u);
			ois.close();
		}catch (Exception e){
			controls.showError("Unable to import user data.");
			System.exit(1);
		}
	}
}
