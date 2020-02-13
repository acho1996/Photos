package controllers;

import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import model.User;
import model.UserData;

/**
 * @author Kanak Borad
 * @author Anthony Cho
 *
 */
public class AdminController {
	/**
	 * Delegated instance of ConMethods
	 */
	static Controller controller = new Controller();
	
	/**
	 * Delegated instance of UserData
	 */
	static UserData userData = new UserData();
	
	@FXML
	ListView<String> listOfUsers;
	
	@FXML
	Button logout, deleteUser, addUser;
	
	@FXML
	TextField Username;
	
	/**
	 * Shows the list of users in the application
	 */
	ObservableList<String> user = FXCollections.observableArrayList();
	
	@FXML
	void initialize(){
		LoadUserNames();
	}
	
	/**
	  * Removes user and their personal save files from application
	  * @param user username of User to delete from application
	  */
	 public void DeleteUser(String user) {
		 File files = new File(".\\saves\\" + user + ".save");
		 if(!files.exists()) //print error if file does not exist 
		 {
			 controller.showError("The user does not exist.");
			 return;
		 }
		 if(!files.delete())	
		 { 
			 controller.showError("Sorry, unable to delete user.");
			 return;
	     }
	 }
	 
	 /**
	  * Loads usernames from a local save file
	  */
	 public void LoadUserNames() {
		 File file = null;
	     File[] paths;
	      
	     try {  
	         file = new File(".\\saves\\");
	         paths = file.listFiles();
	         for(File path:paths) 
	         {	 
		         String temp = path.toString();
		         if(temp.substring(8, temp.length()-5).equals("stock")) {
		        	 continue;
		         }
		         user.add(temp.substring(8,temp.length()-5));
		         listOfUsers.setItems(user);
	         }
	         
	      } catch(Exception e) {
	    	  controller.showError("Couldn't load user.");
	         return;
	      }
	 }
	
	 /**
	  * Button event handling
	  * @param event Event calls ButtonAction
	  */ 
	 public void Logout(ActionEvent event) {//if logout button is clicked, takes you to login
		 Button logout = (Button) event.getSource();
		 controller.setStage(logout);
		 controller.LogOut(); 
	 }
	 public void AddUser(ActionEvent event) { //if clicked, check if input is "admin", stock" or null, if so prints error
		 Button addUser = (Button) event.getSource();
		 controller.setStage(addUser);
		 String username = Username.getText().toLowerCase();
		 if(username.equals("admin") || username.equals("stock") || username.equals("")) {
			 controller.showError("Invalid user name!");
			 return;
		}
		 else {	//adds user
			 if(!user.contains(username)) {	
				 user.add(username);
				 listOfUsers.setItems(user);
				 User u = new User(username);
				 userData.saveUserData(u);
				 Username.clear(); //clears input box
			 }
			 else { // if duplicate account, prints error 
				 controller.showError("User is already in the system!");
			 }
		 }
	 }
	 
	 public void DeleteUser(ActionEvent event) {
		 Button deleteUser = (Button) event.getSource();
		 controller.setStage(deleteUser); //if delete button is clicked, deletes user form save
		 String selected = listOfUsers.getSelectionModel().getSelectedItem();
		if(selected != null) {
			 DeleteUser(selected);
			 user.remove(selected);
		}
		 Username.clear();
	 }
}