package businessLayer;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class MediaplayerFX implements MediaplayerInterface{
	private Media media = null;
	private MediaPlayer mediaplayer = null;
	public static double MIN_VALUE = 0, MAX_VALUE = 1.0;
	public MediaplayerFX() {
	}
	@Override
	public void play(String songLocal) {
		// TODO Auto-generated method stub
		media = new Media(songLocal);
		mediaplayer = new MediaPlayer(media);
		mediaplayer.play();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		if(mediaplayer == null) return;
		else if(mediaplayer.getStatus() == Status.PAUSED) mediaplayer.play();
		else mediaplayer.pause();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		if(mediaplayer == null) return;
		mediaplayer.stop();
		mediaplayer = null;
	}

	@Override
	public void setSound(int soundlevel) {
		// TODO Auto-generated method stub
		if(mediaplayer == null) return;
		double volume = (double)(soundlevel)/100;
		mediaplayer.setVolume(volume);
	}

	@Override
	public void muteSound() {
		mediaplayer.setMute(!mediaplayer.isMute());
	}

	@Override
	public Object getMedia() {
		return mediaplayer;
	}

	@Override
	public Boolean isStop() {
		// TODO Auto-generated method stub
		if(mediaplayer == null) return true;
		MediaPlayer.Status status =  mediaplayer.getStatus();
		if(MediaPlayer.Status.PAUSED == status) return true;
		else return false;
	}

	@Override
	public Boolean getMute() {
		return mediaplayer.isMute();
		
	}



}
