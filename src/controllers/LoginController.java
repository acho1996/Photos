package controllers;


import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import model.User;
import javafx.event.ActionEvent;
import model.UserData;

/**
 * @author Kanak Borad
 * @author Anthony Cho
 *
 */
public class LoginController{

	/**
	 * instance of Controller
	 */
	static Controller Controller = new Controller();
	
	/**
	 * instance of UserData
	 */
	static UserData UserData = new UserData();
	
	/**
	 * initialize controller
	 */
	@FXML
	void initialize() {	
	}	
	
	@FXML
	TextField unameText;
	
	@FXML
	Button loginButton;
	
	public boolean findUsername(String User) {	
		//searches for user in save
		 File files = null;
	     File[] paths;
	      
	     try {  
	    	 files = new File(".\\saves\\");
	         paths = files.listFiles();
	         for(File path:paths) {	 
		         String temp = path.toString();
		         if(temp.substring(8, temp.length()-5).equals(User)) {
		        	 return true;
		         }
	         }
	         
	      } catch(Exception e) {	//pop up error if not found
	    	  Controller.showError("There was an error in finding the user.");
	         System.exit(1);
	      }
	     
	     return false;
	 }
   
	/**
	 * Button event handling
	 * @param event Event calls ButtonAction
	 */
    public void Login(ActionEvent event) {
    	Button loginButton = (Button) event.getSource();
    	Controller.setStage(loginButton);    	
   		String user = unameText.getText().toLowerCase();
    	Controller.setUser(new User(user));
    	switch(user) {
    	case "":	//if empty
    		return;
    	case "admin":	//if admin, go to Admin page
    		Controller.gotoAdminHome();
    		return;
    	default:	// if can't find username, or "stock", show error
    		/*	Load User's photos	*/	
    		if(!user.equals("stock") && !findUsername(user)) {
    			//if input is stock or username in system, show error
    			Controller.showError("User not found.");
    			return;
    		}
    		UserData.loadData(user);
       		Controller.gotoHomePage();	//goes to homepage
    		return;
    	}	
    }    
}