package ui;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import businessLayer.fileLocationManagement.LocationFinder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MainappControl implements Initializable{
	@FXML
	private Button btnPlay;

	@FXML
	private Button btnPrev;

	@FXML
	private Button btnNext;

	@FXML
	private Button btnSound;

	@FXML
	private MenuBar menubar;

	@FXML
	private MediaView mvPlayer;

	@FXML
	private Slider sldTime;

	@FXML
	private Slider sldSound;
	ProgramManagement management = new ProgramManagement();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	public void playSong() {
		management.playSong();
		putImage(btnPlay, "stop");
	}
	private void putImage(Labeled sceneObject, String imagename) {
		ImageView btnimg = (ImageView) sceneObject.getGraphic();
		Image image = new Image(LocationFinder.filetoURL(LocationFinder.IMGfinder(imagename)).toString());
		btnimg.setImage(image);
	}

}
class ProgramManagement{
	public void playSong() {
		System.out.println("i am playing song");
	}
}
