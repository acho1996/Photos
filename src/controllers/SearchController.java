package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import model.Album;
import model.Photo;
import model.Tag;

/**
 * @author Kanak Borad
 * @author Anthony Cho
 *
 */
public class SearchController {
	
	static Controller controls = new Controller();
	
	
	@FXML
	Button back, searchButton;

	@FXML
	DatePicker startDate, endDate;

	@FXML
	ComboBox<String> tagComb1, tagComb2;
	
	@FXML
	TextField tagVal1, tagVal2, searchAlbumText;
	
	@FXML
	ToggleButton or;
	
	@FXML
	ToggleGroup searchBy;
	
	@FXML
	RadioButton rangeOfDate, tagsSearch;
	
	@FXML
	Text dateText1, dateText2, tagText1, tagText2, tagText3, tagText4;
	
	/**
	 * The list of tag types for the user
	 */
	ObservableList<String> tagTypes = FXCollections.observableArrayList();
	
	/**
	 * Initialize the controller
	 */
	@FXML
	void initialize()
	{
		or.setSelected(false);
		setStateDateRange(true);
		
		or.setOnMouseClicked(new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent click) 
		    {
		       if(or.isSelected()) {
		    	   or.setText("And");
		       }else {
		    	   or.setText("Or");
		       }
		    }
		});
		
		searchBy.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
		    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

		         if (searchBy.getSelectedToggle().equals(rangeOfDate)) {
		        	 setStateDateRange(true);
		         }else if(searchBy.getSelectedToggle().equals(tagsSearch)) {
		        	 setStateDateRange(false);
		         }

		     } 
		});
		
		tagTypes.addAll(controls.getUser().getTagTypes());
		tagComb1.setItems(tagTypes);
		tagComb2.setItems(tagTypes);
		tagComb1.setEditable(false);
		tagComb2.setEditable(false);
	}
	
	/**
     * Determines whether or not an album with a specified name already exists
     * @param name Name to search for in existing albums
     * @return True if album with specified names already exists, otherwise false
     */
    private boolean AlbumExists(String name) {
		 for(int i = 0; i < controls.getUser().getAlbums().size(); i++) {
			 if(controls.getUser().getAlbums().get(i).getName().equals(name)) {
				 return true;
			 }
		 }
		 return false;
	 }
    
    /**
     * Returns list of photos that fall within the specified date range //
     * @param startCal Start date
     * @param endCal End date
     * @return ArrayList of photos that fall within the date range inclusively
     */
    private ArrayList<Photo> matchByDate(Calendar startCal, Calendar endCal){
    	ArrayList<Photo> found = new ArrayList<Photo>();
    	
    	ArrayList<Album> albums = controls.getUser().getAlbums();
    	
    	for(int i = 0; i < albums.size(); i++) {
    		ArrayList<Photo> photos = albums.get(i).getPhotos();
    		
    		for(int j = 0; j < photos.size(); j++) {
    			Calendar c = photos.get(j).getCalendar();
    			if(c.equals(startCal) || c.equals(endCal) || (c.before(endCal) && c.after(startCal))) {
    				found.add(photos.get(j));
    			}
    		}
    	}
    	return found;
    }
	
	/**
	 * Sets the field visibilities based on the search method at hand
	 * @param b is True if searching by date range, and false if searching by tags
	 */
	private void setStateDateRange(boolean b) 
	{	//searches by date range
		dateText1.setVisible(b);
		dateText2.setVisible(b);
		startDate.setVisible(b);
		endDate.setVisible(b);
		
		or.setVisible(!b);
		tagText1.setVisible(!b);
		tagText2.setVisible(!b);
		tagText3.setVisible(!b);
		tagText4.setVisible(!b);
		tagComb1.setVisible(!b);
		tagComb2.setVisible(!b);
		tagVal1.setVisible(!b);
		tagVal2.setVisible(!b);
		
		if(b) 
		{
			tagComb1.setValue("");
			tagComb2.setValue("");
			tagVal1.setText("");
			tagVal2.setText("");
		}
		else 
		{
			startDate.setValue(null);
			endDate.setValue(null);
		}	
	}
    

    /**
     * @param event handles back button
     */
    public void Back(ActionEvent event) {
    	Button back = (Button) event.getSource();
    	controls.setStage(back);
    	controls.gotoHomePage(); 
    }
    
    /**
     * @param event handles search button
     */
    public void SearchPhotos(ActionEvent event) {
    	Button searchButton = (Button) event.getSource();
    	controls.setStage(searchButton);{	 	 //if click search, run through
		if(searchBy.getSelectedToggle().equals(rangeOfDate)) {
			 if(startDate.getValue() == null || endDate.getValue() == null) {
				 controls.showError("Add a start and a end date.");	//error stating add start and end date
				 return;
			 }
			 
			 if(startDate.getValue().isAfter(endDate.getValue())) {
				 controls.showError("Ensure start date is before end date.");
				 return;	//error stating to ensure start date is before the end date
			}
			 
		 }else {
			 if((tagComb1.getValue().equals("") && tagComb2.getValue().equals(""))) {
				 controls.showError("Please enter valid search fields.");
				 return;	//error stating to add valid search fields
			 }
			 
			 if(!tagComb1.getValue().equals("") && tagVal1.getText().equals("")) {
				 controls.showError("Please enter valid tag value");
				 return;	//error stating enter valid tag value
			 }
			 
			 if(!tagComb2.getValue().equals("") && tagVal2.getText().equals("")) {
				 controls.showError("Please enter valid tag value");
				 return;	//error stating enter second valid tag value
			 }
		 }
		 		 
		if(searchAlbumText.getText() == null || searchAlbumText.getText().equals("")) {
			 controls.showError("Please enter a name for the album.");
			 return;	//error stating enter a name of the album
		 }
		 
		 if(AlbumExists(searchAlbumText.getText())) {
			 controls.showError("Album with this name already exists.");
			 return;	//error stating there is a duplicate account
		 }
		 
		 Album newAlbum = new Album(searchAlbumText.getText());
		 
		 if(startDate.getValue() != null && endDate.getValue() != null) {	//if both values are not empty

				String StringSDate = startDate.getValue().toString();
				String StringEDate = endDate.getValue().toString();
				
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
				Date sDate = new Date();
				Date eDate = new Date();
				try {
					sDate = date.parse(StringSDate);
					eDate = date.parse(StringEDate);
				} catch (ParseException e) {
					controls.showError("Sorry, unable to parse dates.");
					return;
				}
					
				Calendar SCalendar = Calendar.getInstance();
				SCalendar.clear();
				Calendar ECalendar = Calendar.getInstance();
				ECalendar.clear();
				
				SCalendar.setTime(sDate);
				SCalendar.set(Calendar.MILLISECOND, 0);
				ECalendar.setTime(eDate);
				ECalendar.set(Calendar.MILLISECOND, 0);
				
				
				ArrayList<Photo> match = matchByDate(SCalendar, ECalendar);
				
				if(match.size() == 0) 
				{
					controls.showError("Sorry, there was no matches found.");
					return;
				}
				
				for(int i = 0; i < match.size(); i++) {
					newAlbum.getPhotos().add(controls.makeCopyOfPhoto(match.get(i)));
				}
				
				
				}else 
				{
				 
				 ArrayList<Tag> SearchTags = new ArrayList<Tag>();
				 
				 ArrayList<Photo> MatchTag1 = new ArrayList<Photo>();
				 ArrayList<Photo> MatchTag2 = new ArrayList<Photo>();
				 
				 if(!tagComb1.getValue().equals("") 
					 && !tagVal1.getText().equals("")) 
				 {
					 Tag Tag1 = new Tag(tagComb1.getValue(), tagVal1.getText());
					 SearchTags.add(Tag1);
					 MatchTag1 = matchByTag(Tag1);
				 }
				 
				 if(!tagComb2.getValue().equals("") 
						 && !tagVal2.getText().equals("")) 
				 {
					 Tag Tag2 = new Tag(tagComb2.getValue(), tagVal2.getText());
					 SearchTags.add(Tag2);
					 MatchTag2 = matchByTag(Tag2);
				 }			 
				if(SearchTags.size() == 1) {
					if(MatchTag1.size() > 0) {
						for(int i = 0; i < MatchTag1.size(); i++) {
							newAlbum.getPhotos().add(controls.makeCopyOfPhoto(MatchTag1.get(i)));
						}
					}else if(MatchTag2.size() > 0) {
						for(int i = 0; i < MatchTag2.size(); i++) {
							newAlbum.getPhotos().add(controls.makeCopyOfPhoto(MatchTag2.get(i)));
						}
					}	
				}else {
				
				if(or.getText().equals("And")) {
					for(int i = 0; i < MatchTag1.size(); i++) {
						Photo p = MatchTag1.get(i);
						
						if(MatchTag2.contains(p)) {
							
							newAlbum.getPhotos().add(controls.makeCopyOfPhoto(p));
						}
					}
					
				}else {
					
					for(int i = 0; i < MatchTag2.size(); i++) {
						Photo p = MatchTag2.get(i);
						
						if(!MatchTag1.contains(p)) {
							MatchTag1.add(p);
						}
					}
					for(int i = 0; i < MatchTag1.size(); i++) {
						newAlbum.getPhotos().add(controls.makeCopyOfPhoto(MatchTag1.get(i)));
					}
				}
				}
				}
				if(newAlbum.getPhotos().size() == 0) {
					controls.showError("Sorry, no Matches Found.");
					return;
				}
				
				controls.getUser().getAlbums().add(newAlbum);
				controls.setActiveAlbum(newAlbum);
				controls.gotoAlbumContent();
				return;		 
		 }
    }
    
    
	    /**
	     * Searches for photos with the specified tag
	     * @param searchTag Tag to search for
	     * @return List of photos that contain the specified tag
	     */
	    private ArrayList<Photo> matchByTag(Tag searchTag){
	    	ArrayList<Photo> found = new ArrayList<Photo>();
	    	
	    	ArrayList<Album> albums = controls.getUser().getAlbums();
	    	
	    	for(int i = 0; i < albums.size(); i++) {
	    		ArrayList<Photo> photos = albums.get(i).getPhotos();
	    		
	    		for(int j = 0; j < photos.size(); j++) {
	    			ArrayList<Tag> tags = photos.get(j).getTags();
	    			
	    			for(int k = 0; k < tags.size(); k++) {
	    				Tag tag = tags.get(k);
	    				
	    				if(tag.getKey().equals(searchTag.getKey())) {
	    					String[] split_tag = tag.getValue().split(",");
	    					
	    					for(int l = 0; l < split_tag.length; l++) {
	    						if(split_tag[l].equals(searchTag.getValue())) {
	    							found.add(photos.get(j));
	    						}
	    					}
	    				}
	    			}
	    		}
	    	}
	    	return found;
	    }
	}