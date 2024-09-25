package ui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import businessLayer.MediaplayerFX;
import businessLayer.MediaplayerInterface;
import businessLayer.fileLocationManagement.LocationFinder;
import businessLayer.fileLocationManagement.PathShowerPC;
import entitiesLayer.FileExtensionsList;
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
		management.continueSong();
		if(management.getMediaplayer().isStop()) putImage(btnPlay, "stop");
		else putImage(btnPlay, "play");
	}
	public void setMedia() {
		management.setMedia(mvPlayer);
	}
	private void putImage(Labeled sceneObject, String imagename) {
		ImageView btnimg = (ImageView) sceneObject.getGraphic();
		Image image = new Image(LocationFinder.filetoURL(LocationFinder.IMGfinder(imagename)).toString());
		btnimg.setImage(image);
	}

}
class ProgramManagement{
	private MediaplayerInterface mediaplayer = new MediaplayerFX();
	public void continueSong() {
		mediaplayer.stop();
	}
	public void setMedia(MediaView mediaview) {
		File file = PathShowerPC.FilelocationPC(FileExtensionsList.videoExt);
		if(file == null) return;
		mediaplayer.play(LocationFinder.filetoURL(file).toString());
		mediaview.setMediaPlayer((MediaPlayer)mediaplayer.getMedia());
	}
	public MediaplayerInterface getMediaplayer() {
		return mediaplayer;
	}
}
