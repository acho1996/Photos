package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Photo;

/**
 * @author Kanak Borad
 * @author Anthony Cho
 *
 */
public class DisplayController {
	
	/**
	 * Delegated instance of Controller
	 */
	static Controller controller = new Controller();
	
	@FXML
	ImageView DisplayImage;
	
	@FXML
	Button back, prevPhoto, nextPhoto;
	
	/**
	 * Initialize the controller by populating to photosToDisplay with photos from currently active album
	 */
	@FXML
	void initialize() {
		PhotosDisplay.setAll(controller.getActiveAlbum().getPhotos());
		prevPhoto.setDisable(true);
		
		if(PhotosDisplay.size() < 2) 
		{
			nextPhoto.setDisable(true);
		}
		
		DisplayImage.setImage(new Image(PhotosDisplay.get(imageI).getPath()));
	}
	
	/**
	 * Index of images in the list
	 */
	int imageI = 0;
	
	/**
	 * Observable list of photos which the user can view in the slideshow
	 */
	ObservableList<Photo> PhotosDisplay = FXCollections.observableArrayList();
		
	/**
	  * Disables next photo or previous photo buttons based on current index of the image being shown
	  */
	 private void checkIndex() 
	 {
		 if(imageI == PhotosDisplay.size()-1) 
		 { 
			 nextPhoto.setDisable(true); 
			 }
		 else 
		 { 
			 nextPhoto.setDisable(false); 
		 }
		 
		 if(imageI == 0) 
		 { 
			 prevPhoto.setDisable(true); 
		 }
		 else 
		 { 
			 prevPhoto.setDisable(false); 
		 }
	 }

	
	 /**
	 * @param event handles back
	 */
	public void Back(ActionEvent event) { 
		 Button back = (Button) event.getSource();
		 controller.setStage(back);
		 controller.gotoAlbumContent();
	 }
	
	 /**
	 * @param event handles next photo
	 */
	public void NextPhoto(ActionEvent event) { 
		 Button nextPhoto = (Button) event.getSource();
		 controller.setStage(nextPhoto);
		 imageI ++;	//increments by 1 to switch between pictures
		 DisplayImage.setImage(new Image(PhotosDisplay.get(imageI).getPath()));
		 checkIndex();
	}
	
	/**
	 * @param event handles prev photo
	 */
	public void PrevPhoto(ActionEvent event) { 
		 Button prevPhoto = (Button) event.getSource();
		 controller.setStage(prevPhoto);
		 imageI --;	//decrements by 1 to switch between pictures
		 DisplayImage.setImage(new Image(PhotosDisplay.get(imageI).getPath()));
		 checkIndex();
	}
 }
