package ui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

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
	ProgramManagement management = new ProgramManagement() {
		public void setTime(int time) {
			super.setTime(time);
			sldTime.setValue(time);
		};
	};
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		sldSound.setMax(100);
		
	}
	public void setHertzwithSlider() {
		int value = (int) sldSound.getValue();
		management.setHertz(value);
		management.SetMute(false);
		changeSoundButtonwithHertz();
	}
	public void setMuteW() {
		management.SetMute();
		Boolean isMute = management.getMute();
		if(isMute) putImage(btnSound, "soundlevel0");
		else {
			changeSoundButtonwithHertz();
		}
	}
	public void playSong() {
		management.continueSong();
		Boolean isStop = management.getMediaplayer().isStop();
		if(isStop) putImage(btnPlay, "stop");
		else putImage(btnPlay, "play");
	}
	public void setMedia() {
		management.setMedia(mvPlayer);
		setOpenFile();
	}
	//this method change the 
	private void changeSoundButtonwithHertz() {
		int value = (int) sldSound.getValue();
		String soundlvIcon = management.getSoundLVName(value);
		putImage(btnSound, soundlvIcon);
	}
	private void putImage(Labeled sceneObject, String imagename) {
		ImageView btnimg = (ImageView) sceneObject.getGraphic();
		Image image = new Image(LocationFinder.filetoURL(LocationFinder.IMGfinder(imagename)).toString());
		btnimg.setImage(image);
	}
	private void setOpenFile() {
		sldSound.setValue(50);
		setHertzwithSlider();
		sldTime.setValue(0);
		sldTime.setMax(management.getFullTime());
	}

}
class ProgramManagement{
	private MediaplayerInterface mediaplayer = new MediaplayerFX();
	private Timer mediaTimer = new Timer();
	private int time,timeMediaEnd;
	public static int SOUNDLV0 = 0, SOUNDLV1 = 30, SOUNDLV2 = 70;
	public void continueSong() {
		mediaplayer.stop();
	}
	public void setMedia(MediaView mediaview) {
		timerEnd();
		File file = PathShowerPC.FilelocationPC(FileExtensionsList.videoExt);
		if(file == null) return;
		mediaplayer.play(LocationFinder.filetoURL(file).toString());
		mediaview.setMediaPlayer((MediaPlayer)mediaplayer.getMedia());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timeMediaEnd = mediaplayer.getLenght();
		timerStart();
		
	}
	public void setHertz(int hertz) {
		mediaplayer.setSound(hertz);
	}
	public void SetMute(Boolean isMute) {
		if(mediaplayer.getMute() != isMute) mediaplayer.muteSound();
	}
	public void SetMute() {
		mediaplayer.muteSound();
	}
	public String getSoundLVName(int hertz) {
		if(hertz == SOUNDLV0) return "soundlevel0";
	    else if(hertz < SOUNDLV1) return "soundlevel1";
		else if(SOUNDLV1<hertz && hertz<SOUNDLV2) return "soundlevel2";
		else if(hertz>SOUNDLV2) return "soundlevel3";
		else return "soundlevel0";
	}
	public Boolean getMute() {
		return mediaplayer.getMute();
	}
	public MediaplayerInterface getMediaplayer() {
		return mediaplayer;
	}
	public int getFullTime() {
		return timeMediaEnd;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	private void timerStart() {
		time = 0;
		mediaTimer = new Timer();
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				setTime(time+1);
				if(time>timeMediaEnd) timerEnd();
			}
		};
		getMusicTimer().scheduleAtFixedRate(task, 0, 1000);
	}
	private void timerEnd() {
		mediaTimer.cancel();
		time = 0;
		timeMediaEnd = 0;
	}
	public Timer getMusicTimer() {
		return mediaTimer;
	}
	
}
