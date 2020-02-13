package controllers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.Photo;
import model.Tag;

/**
 * @author Kanak Borad
 * @author Anthony Cho
 *
 */
public class AlbumController {

	private static Controller controls = new Controller();
	
	@FXML
	Button logout, back, deletePhoto, addPhoto, movePhoto, editCaption, manageTags, viewDisplay;
	
	@FXML
	ListView<Photo> thumbnail;
	
	@FXML
	ListView<Tag> tagList;
	
	@FXML
	ImageView photoDisplay;
	
	@FXML
	Text albumName, caption, date;
	
	

	private static final int thumbnail_sizeX = 50;
	private static final int thumbnail_sizeY = 50;
	
	
	/**
	 * Observable list of photos
	 */
	ObservableList<Photo> photos = FXCollections.observableArrayList();
	
	/**
	 * Observable list of tags
	 */
	ObservableList<Tag> tags = FXCollections.observableArrayList();
	
	
	@FXML
	void initialize() {
		photos.clear();
		
		ArrayList<Photo> phs = controls.getActiveAlbum().getPhotos();
		for(int i = 0; i < phs.size(); i++) {
			Photo ph = phs.get(i);
			File pFile = new File(ph.getPath().substring(6, ph.getPath().length()));
			if(pFile.exists()) {
				photos.add(ph);
			}else {
				controls.getActiveAlbum().getPhotos().remove(ph);
			}
		}
		
		albumName.setText("Photos in: " + controls.getActiveAlbum().getName());
		
		thumbnail.getSelectionModel().selectedItemProperty().addListener( (photo, oldPhoto, newPhoto) ->
    	changeAction(newPhoto)
    	);
		
		 RefreshAlbum();
		 if(photos.contains(controls.getActivePhoto())){
			 thumbnail.getSelectionModel().select(controls.getActivePhoto());
		 }
		 else if(photos.size() > 0) {
			thumbnail.getSelectionModel().select(0);
		 }
		
	}
	 
	 /**
	  * Method called by listener upon photo selection change
	  * @param photo new photo selected
	  */
	 private void changeAction(Photo photo) {
		 if(photos.size() == 0) {
			 photoDisplay.setVisible(false);
			 caption.setText("");
			 date.setText("");
			 tagList.setItems(null);
		 }
		 
		     if(photo != null) {
				 photoDisplay.setVisible(true);
				 photoDisplay.setImage(new Image(photo.getPath()));
				 caption.setText(photo.getCaption());
				 date.setText("Date taken: " + photo.getDate());
				 tags.setAll(photo.getTags());
				 tagList.setItems(tags);
		     } 
	 }
	 

	 /**
	 * @param event handles logout
	 */
	public void Logout(ActionEvent event) {
		 Button logout = (Button) event.getSource();
		 controls.setStage(logout);
		 controls.LogOut();
		 return;
	 }
	 
	 /**
	 * @param event handles back button
	 */
	public void Back(ActionEvent event) {
		 Button back = (Button) event.getSource();
		 controls.setStage(back);
		 controls.gotoHomePage();
		 return;
	 }
	 
	 /**
	 * @param event handles AddPhoto
	 */
	public void AddPhoto(ActionEvent event) {
		 Button addPhoto = (Button) event.getSource();
		 controls.setStage(addPhoto);
		 File selected_file = controls.fileSelect("Select a file to add to your album");
		 if(selected_file == null) {
			 return;
		}
		Photo ph = setDateValues(selected_file);	 
		controls.getActiveAlbum().getPhotos().add(ph);
		photos.clear();
		photos.addAll(controls.getActiveAlbum().getPhotos());
		thumbnail.setItems(photos);
		thumbnail.getSelectionModel().select(photos.size()-1);
		 RefreshAlbum(); 
	}
	 
	 /**
	 * @param event handles DeletePhoto
	 */
	public void DeletePhoto(ActionEvent event) {
		 Photo selectedPhoto = thumbnail.getSelectionModel().getSelectedItem();
		 Button deletePhoto = (Button) event.getSource();
		 controls.setStage(deletePhoto);
		 if(selectedPhoto == null) {
			 controls.showError("No photo selected.");
			 return;
		}
		 Alert alert = new Alert(AlertType.CONFIRMATION,"Are you sure you want to delete?", ButtonType.YES, ButtonType.CANCEL);
	     Optional<ButtonType> response = alert.showAndWait();
		 if (response.get() == ButtonType.CANCEL) {
			 return;
		 }
		
		int index = photos.indexOf(selectedPhoto);
		controls.getActiveAlbum().removePhoto(selectedPhoto);
		photos.setAll(controls.getActiveAlbum().getPhotos());
		thumbnail.setItems(photos);
		if(index < photos.size()) {
			thumbnail.getSelectionModel().select(index);
		}else if(index == photos.size()) {
			thumbnail.getSelectionModel().select(index - 1);
		}
		 RefreshAlbum(); 
	 }
	 
	 /**
	 * @param event handles movePhoto
	 */
	public void MovePhoto(ActionEvent event) {
		 Photo selectedPhoto = thumbnail.getSelectionModel().getSelectedItem();
		 Button movePhoto = (Button) event.getSource();
		 controls.setStage(movePhoto);
		 if(selectedPhoto == null) {
			 controls.showError("No photo selected.");
			 return;
		 }else if(controls.getUser().getAlbums().size() < 2) {
			 controls.showError("No album to move/copy to.");
			 return;
		 }
		 controls.setActivePhoto(selectedPhoto);
		 controls.changeScene("ManageLocation.fxml", 300, 400, false);
		 RefreshAlbum(); 

	 }
	 
	 /**
	 * @param event handles Edit caption button
	 */
	public void EditCaption(ActionEvent event) {
		 Photo selectedPhoto = thumbnail.getSelectionModel().getSelectedItem();
		 Button editCaption = (Button) event.getSource();
		 controls.setStage(editCaption);
		 if(selectedPhoto == null) {
			 controls.showError("No photo selected.");
			 return;
		 }
		 String newCaption = controls.inputBox("Caption Edit", "Edit the caption", selectedPhoto.getCaption());
		 if(newCaption != null) {
			 selectedPhoto.setCaption(newCaption);
			 caption.setText(selectedPhoto.getCaption());
		 }
		 RefreshAlbum(); 
	 }
	 
	 /**
	 * @param event handles manage tags
	 */
	public void ManageTags(ActionEvent event) {
		 Photo selectedPhoto = thumbnail.getSelectionModel().getSelectedItem();
		 Button manageTags = (Button) event.getSource();
		 controls.setStage(manageTags);
		 if(selectedPhoto == null) {
			 controls.showError("No photo selected.");
			 return;
		 }
		 controls.setActivePhoto(selectedPhoto);
		 controls.changeScene("ManageTags.fxml", 318, 460, false);
		 RefreshAlbum(); 
	 }
	 
	 /**
	 * @param event handles view display
	 */
	public void ViewDisplay(ActionEvent event) {
		 Button viewDisplay = (Button) event.getSource();
		 controls.setStage(viewDisplay);
		 if(photos.size() == 0) {
			 controls.showError("No photos to view.");
			 return;
		 }
		 controls.changeScene("DisplayPhotos.fxml", 655, 450, false);
		 RefreshAlbum(); 
	 }
	 
	 
	 /**
	  * Refreshes the album to ensure new data is displayed
	  */
	 private void RefreshAlbum() {
		 thumbnail.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>(){
			 
			@Override
			public ListCell<Photo> call(ListView<Photo> param) {
				ListCell<Photo> thumbs = new ListCell<Photo>() {
					
					@Override
					protected void updateItem(Photo photo, boolean b) {
						super.updateItem(photo, b);
						if(photo != null) {
							Image thumb = new Image(photo.getPath());
							ImageView imgView = new ImageView(thumb);
							
							imgView.setFitWidth(thumbnail_sizeX);
							imgView.setFitHeight(thumbnail_sizeY);
							setGraphic(imgView);
							setText(photo.getCaption());
						}
					}
				};
				
				return thumbs;
			}
		 });
		 
		 ObservableList<Photo> temp = FXCollections.observableArrayList();
		 temp.addAll(controls.getActiveAlbum().getPhotos());
		 
		 photos = temp;
		 thumbnail.setItems(photos);
	 }
	 
	 /**
	  * @param f File of photo
	  * @return modified date
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
}
