package controllers;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import model.Album;
import model.User;

/**
 * @author Kanak Borad
 * @author Anthony Cho
 *
 */
public class NonAdminController{
	
	/**
	 *  instance of Controller
	 */
	static Controller controller = new Controller();
	
	@FXML
	ListView<Album> listOfAlbums;
	
	@FXML
	Button viewAlbum, addAlbum, editName, deleteAlbum, searchPhotos, logout;
	
	
	/**
	 * Current user 
	 */
	static User User;
	
	
	/**
	 * list of the user's albums
	 */
	ObservableList<Album> Album = FXCollections.observableArrayList();
	
	
	/**
	 * Initialize controller by setting the current user and adding the user's albums to albums list
	 */
	@FXML
	void initialize() {	//adds album to current user
		User = controller.getUser();
		Album.addAll(User.getAlbums());
		listOfAlbums.setItems(Album);
		listOfAlbums.setOnMouseClicked(new EventHandler<MouseEvent>() 
				{
		    @Override
		    public void handle(MouseEvent click) 
		    {
		        if (click.getClickCount() == 2) 
		        {
		        	Album ChosenAlbum = listOfAlbums.getSelectionModel().getSelectedItem();
		        	if(ChosenAlbum != null) 
		        	{
		        		controller.setActiveAlbum(ChosenAlbum);
		        		controller.gotoAlbumContent();
			    	}
		        }
		    }
		});
		
	}
	
	 /**
	  * Refresh the album list to make sure the new data is being displayed
	  */
	 private void Refresh() //refreshes window
	 {	
		 Album.clear();
		 Album.addAll(User.getAlbums());
		 FXCollections.sort(Album);
		 listOfAlbums.setItems(Album);
	 }
	 
	 /**
	  * Determines whether albums with the same names already exists
	  * @param AlbumName Name of the new album the user is attempting to create
	  * @return Returns true if an album is found with the same name, otherwise it is false
	  */
	 private boolean ExistingAlbum(String AlbumName) {
		 for(int i = 0; i < Album.size(); i++) {
			 if(Album.get(i).getName().equals(AlbumName)) {
				 return true;
			 }
		 }
		 return false;
	 }
	
	 /**
	 * @param event handle viewAlbum
	 */
	public void ViewAlbum(ActionEvent event) { 
		Button viewAlbum = (Button) event.getSource();
		controller.setStage(viewAlbum);
	    Album selectedAlbum = listOfAlbums.getSelectionModel().getSelectedItem();
	   	if(selectedAlbum != null) 
	   	{
	   		controller.setActiveAlbum(selectedAlbum);
	   		controller.gotoAlbumContent();
	   	}
	   	Refresh();
	 }
	
	 /**
	 * @param event handle addAlbum
	 */
	public void AddAlbum(ActionEvent event) { 
		Button addAlbum = (Button) event.getSource();
		controller.setStage(addAlbum);
	    String album_name = controller.inputBox("Add Album", "Please enter the name of the new album");	//generates pop up
	    if(album_name != null) 
	    {
	    	if(ExistingAlbum(album_name)) 	//if there input name already exists, show error
	    	{
	    		controller.showError("The album with the name '" + album_name + "' already exists.");
	    		return;
	    	}
	    	if(album_name.equals("")) //if no input, show error
	    	{
	    		controller.showError("The album name cannot be blank.");
		   		return;
		   	}
	    	User.albums.add(new Album(album_name));
	    	Refresh();
	    }
	 }
	
	
	    /**
	     * @param event handle event
	     */
	    public void DeleteAlbum(ActionEvent event) { 
			Button deleteAlbum = (Button) event.getSource();
			controller.setStage(deleteAlbum);
		    Album selectedAlbum = listOfAlbums.getSelectionModel().getSelectedItem();
			if(selectedAlbum == null) {	//shows error if no album was clicked on
				controller.showError("There was no album selected.");
				return;
			}
			Alert alert = new Alert(AlertType.CONFIRMATION,"You sure you want to delete: "
				+ selectedAlbum.getName() + "?", ButtonType.YES, ButtonType.CANCEL);
	      		Optional<ButtonType> response = alert.showAndWait();
	        if (response.get() == ButtonType.CANCEL) {
	        	 return;
	        }	
	        User.albums.remove(selectedAlbum);
	        Refresh();
	    }
	    
	    
	    /**
	     * @param event handle editName
	     */
	    public void EditName(ActionEvent event) { 
			Button editName = (Button) event.getSource();
			controller.setStage(editName);
		    Album selectedAlbum = listOfAlbums.getSelectionModel().getSelectedItem();
	    	if(selectedAlbum == null) {	//shows error if no album was clicked on
    		controller.showError("There was no album selected.");
    		return;
    	}
    		String newName = controller.inputBox("Rename Album","Please enter a new name for album: " + selectedAlbum.getName(), selectedAlbum.getName());
    		if(newName != null) {
    			if(ExistingAlbum(newName)) {
    				controller.showError("The album with the name '" + newName + "' already exists.");
	    			return;
	    		}
    			selectedAlbum.setName(newName);
    		}
    		Refresh();
	    }  
	    
	    
	    /**
	     * @param event handle logout
	     */
	    public void Logout(ActionEvent event) { 
			Button logout = (Button) event.getSource();
			controller.setStage(logout);
	    	controller.LogOut();
	    }
	    
	    
	    /**
	     * @param event handle searchPhotos
	     */
	    public void SearchPhotos(ActionEvent event) { 
			Button searchPhotos = (Button) event.getSource();
			controller.setStage(searchPhotos);
	    	controller.gotoSearchPhoto();
	    	return;
	    }
	 }