package ui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import businessLayer.DebugSettings;
import businessLayer.MediaplayerFX;
import businessLayer.MediaplayerInterface;
import businessLayer.fileLocationManagement.LocationFinder;
import businessLayer.fileLocationManagement.PathShowerPC;
import entitiesLayer.FileExtensionsList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

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
	private AnchorPane videoPane;

	@FXML
	private Slider sldSound;
	@FXML 
	private Label lblCurrent;
	@FXML 
	private Label lblFulltime;
	
	private static int  MILISECOND = 1000;
	
	ProgramManagement management = new ProgramManagement() {
		@Override
		public void setTime(long second) {
			super.setTime(second);
			if(!getIsPaused())sldTime.setValue(second);
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					lblCurrent.setText(management.milisecondtoString(second));
				}
			});

		};
		@Override
		public void setIsPaused(Boolean isPaused) {
			super.setIsPaused(isPaused);
			if(isPaused) putImage(btnPlay, "stop");
			else putImage(btnPlay, "play");
		};
		@Override
		public void mediaEnd() {
			super.mediaEnd();
			putImage(btnPlay, "replay");
		};
	};
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		sldSound.setMax(100);
		sldTime.valueChangingProperty().addListener((obs, oldVal, newVal) -> {
		        setHertzwithSlider();
		});
		sldTime.valueChangingProperty().addListener((obs, oldVal, newVal) -> {
		    if (!newVal) {
		        long value = (long) sldTime.getValue();
		        management.seekTime(value);
		    }
		});
		
	}
	public void setHertzwithSlider() {
		int value = (int) sldSound.getValue();
		management.setHertz(value);
		management.SetMute(false);
		changeSoundButtonwithHertz();
	}
	public void setTimewithSlider() {
		long second = (long) sldTime.getValue();
		management.seekTime(second);
	}
	public void continueTimer() {
		management.setIsPaused(false);
	}
	public void sldTimeClicked() {
		setTimewithSlider();
		management.setIsPaused(false);
	}
	public void pauseTimer() {
		management.setIsPaused(true);
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
		if(management.isMediaEnd()) {
			management.seekTime(0);
		}
		try {
			management.continueorstopSong();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void setMedia() {
		management.setMedia(mvPlayer);
		setOpenFile();
	}
	private final int timeForward = 10*MILISECOND;
	public void btnNext() {
		management.forwardTime(timeForward);
	}
	public void btnPrev() {
		management.forwardTime(-timeForward);
	}
	public void closeApp() {
		System.exit(0);
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
		long fulltime = management.getFullTime();
		sldTime.setMax(fulltime);
		lblFulltime.setText(management.milisecondtoString(fulltime));
	}

}
class ProgramManagement{
	private MediaplayerInterface mediaplayer = new MediaplayerFX();
	private Timer mediaTimer = new Timer();
	private Boolean isPaused = false;
	private long timeMediaEnd;
	public static int SOUNDLV0 = 0, SOUNDLV1 = 30, SOUNDLV2 = 70;
	public void continueorstopSong() throws InterruptedException {
		setIsPaused(!getIsPaused());
	}
	public void setMedia(MediaView mediaview) {
		mediaEnd();
		File file = PathShowerPC.FilelocationPC(FileExtensionsList.videoExt);
		if(file == null) return;
		mediaplayer.play(LocationFinder.filetoURL(file).toString());
		mediaview.setMediaPlayer((MediaPlayer)mediaplayer.getMedia());
		setIsPaused(false);
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
	public long getFullTime() {
		return timeMediaEnd;
	}
	public long getTime() {
		long current = mediaplayer.getCurrentTime();
		return current;
	}
	public void setTime(long second) {

		if(timeMediaEnd<second) second = timeMediaEnd;
		else if(second < 0) second = 0;
	}
	public void seekTime(long second) {
		mediaplayer.setTime(second);
		setIsPaused(false);
		mediaplayer.stop(false);
	}
	//it can got in time to previous seconds or forward seconds
	public void forwardTime(long second) {
		long newTime = second + (int)(getTime());
		seekTime(newTime);

	}
	public Timer getMusicTimer() {
		return mediaTimer;
	}
	public Boolean getIsPaused() {
		return isPaused;
	}
	public void setIsPaused(Boolean isPaused) {
		this.isPaused = isPaused;
		mediaplayer.stop(isPaused);
	}
	public String milisecondtoString(long milisecond) {
		int second = (int)(milisecond/1000);
		int minute = second/60;
		int hour = minute/60;
		second = second % 60;
		minute = minute % 60;
		String hourString;
		if(hour == 0) {
			hourString = "";
		}
		else {
			hourString = timetoClockString(hour);
			if(hour<10) {
				hourString = hourString.substring(1, hourString.length())+":";
			}
		}
		String text = hourString+timetoClockString(minute)+":"+timetoClockString(second);
		return text;
	}
	private String timetoClockString(int time) {
		String text;
		if(time < 10) {
			text = "0" + time;
		}
		else {
			text = Integer.toString(time);
		}
		return text;
	}
	public void mediaEnd() {
		
	}
	public Boolean isMediaEnd() {
		return timeMediaEnd == getTime();
	}
	private void timerStart() {
		mediaTimer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(getIsPaused()) return;
				setTime(mediaplayer.getCurrentTime());
				if(getTime()>=timeMediaEnd) {
					mediaEnd();
				}
			}
		};
		getMusicTimer().scheduleAtFixedRate(task, 0, 100);
	}


	
}
