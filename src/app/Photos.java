package app;
	
import java.io.File;
import java.util.Optional;
import controllers.Controller;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

/**
 * @author Kanak Borad
 * @author Anthony Cho
 *
 */
public class Photos extends Application {
	private static Controller controller = new Controller(); //change!
	
	public static void main(String[] args) {	//launch application 
		launch(args);
	}
	
	@Override
	public void start(Stage Stage) {
		File save = new File(".\\saves\\");

		if (!save.exists()) {	//if the save doesn't exist
		    try{
		    	save.mkdir();	//attempts to save
		    } 
		    catch(SecurityException se){	//error pop up
		    	controller.showError("Couldn't create directory for the sav file");
		    }        
		}
		
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/view/Login.fxml"));	//generates pop up
			Scene window = new Scene(root,265,350);
			Stage.setScene(window);
			Stage.setResizable(false);
			Stage.setTitle("Log in");
			Stage.show();
		} 
		
		catch(Exception e) {
			controller.showError("Couldn't start application");	//application error, shows error pop up
			System.exit(1);
		}
		
		Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    
			@Override
		    public void handle(WindowEvent winEvent){
		    	Stage exitStage = (Stage) winEvent.getSource();
		    	if(exitStage.getTitle() == null || exitStage.getTitle() != "Log in") {
		    		if(!controller.getUser().getUsername().equals("admin")) {
			    	Alert alert = new Alert(AlertType.CONFIRMATION,"Do you want to save your changes?", 
			    			ButtonType.YES, ButtonType.NO);	//asks to save or not
				     Optional<ButtonType> feedback = alert.showAndWait();
					 if (feedback.get() == ButtonType.YES) {
						 controller.LogOut();
							 return;
					 }
		    		}
		    	}
		        Platform.exit();
		        System.exit(0);
		    }});
	}	
}