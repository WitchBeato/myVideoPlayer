package businessLayer;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaplayerFX implements MediaplayerInterface{
	private Media media = new Media(null);
	private MediaPlayer mediaplayer = null;
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
		if(mediaplayer != null) mediaplayer.pause();
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



}
