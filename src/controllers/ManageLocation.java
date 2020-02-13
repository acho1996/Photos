package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import model.Album;
import model.Photo;

/**
 * @author Kanak Borad
 * @author Anthony Cho
 *
 */
public class ManageLocation {

	private static Controller controls = new Controller();
	
	@FXML
	ListView<Album> listOfAlbums;
	
	@FXML
	Button back, copy;
	
	@FXML
	CheckBox deleteBox;
	
	/**
	 * List of albums
	 */
	ObservableList<Album> targetAlbums = FXCollections.observableArrayList();
	
	
	/**
	 * Initializes controller with a mouse click
	 */
	@FXML
	void initialize() {
		targetAlbums.addAll(controls.getUser().getAlbums());
		targetAlbums.remove(controls.getActiveAlbum());
		
		listOfAlbums.setItems(targetAlbums);
		
		deleteBox.setOnMouseClicked(new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent click) {
		       if(deleteBox.isSelected()) {
		    	   copy.setText("Move");
		       }else {
		    	   copy.setText("Copy");
		       }
		    }
		});
		
		listOfAlbums.setCellFactory(new Callback<ListView<Album>, ListCell<Album>>(){
			 
			@Override
			public ListCell<Album> call(ListView<Album> param) {
				ListCell<Album> albums = new ListCell<Album>() {
					
					@Override
					protected void updateItem(Album album, boolean b) {
						super.updateItem(album, b);
						if(album != null) {
							setText(album.getName());
						}
					}
				};
				
				return albums;
			}
		 });
	}

	 /**
	 * @param event handles Back
	 */
	public void Back(ActionEvent event) {
		 Button back = (Button) event.getSource();
		 controls.setStage(back);
		 controls.gotoAlbumContent();
	 }
	 
	 /**
	 * @param event handles Copy
	 */
	public void Copy(ActionEvent event) {
		 Button copy = (Button) event.getSource();
		 controls.setStage(copy);
		 controls.gotoAlbumContent();
		 Album selected_album = listOfAlbums.getSelectionModel().getSelectedItem();
			 if(selected_album == null) {
			 controls.showError("No target album selected.");
			 return;
		 }
		 
		 Photo toMove = controls.getActivePhoto();
 
		 selected_album.getPhotos().add(controls.makeCopyOfPhoto(toMove));
		 
		 if(deleteBox.isSelected()) {
			 controls.getActiveAlbum().getPhotos().remove(toMove);
		 }
		 controls.gotoAlbumContent();
	 }
 }

