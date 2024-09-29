package businessLayer;



import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.mp3.Mp3MetadataReader;
import com.drew.imaging.mp4.Mp4MetadataReader;
import com.drew.imaging.riff.RiffProcessingException;
import com.drew.imaging.wav.WavMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.mp4.Mp4Descriptor;
import com.drew.metadata.mp4.Mp4Directory;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public class MediaplayerFX implements MediaplayerInterface{
	private Media media = null;
	private MediaPlayer mediaplayer = null;
	private File filelocal;
	public MediaplayerFX() {
	}
	@Override
	public void play(String songLocal) {
		// TODO Auto-generated method stub
		setFilelocal(new File(songLocal));
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
	@Override
	public int getLenght() {
		Metadata metadata = null;
		try {
			metadata = ImageMetadataReader.readMetadata(filelocal);
		} catch (ImageProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(metadata == null) return 0;
		Mp4Directory directory = metadata.getFirstDirectoryOfType(Mp4Directory.class);
		Long duration = null;
		try {
			duration = directory.getLong(Mp4Directory.TAG_DURATION);
		} catch (MetadataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Duration second = new Duration(duration);
		return (int) second.toSeconds();
	}
	@Override
	public void setTime(int second) {
		// TODO Auto-generated method stub
		mediaplayer.seek(Duration.seconds(second));
	}
	@Override
	public int getTime() {
		// TODO Auto-generated method stub
		return (int) mediaplayer.getCurrentTime().toSeconds();
	}
	public File getFilelocal() {
		return filelocal;
	}
	public void setFilelocal(File filelocal) {
		if(!filelocal.exists()) {
			String realpath = null;
			try {
				realpath = Paths.get(new URL(filelocal.getPath()).toURI()).toString();
				filelocal = new File(realpath);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.filelocal = filelocal;
	}



}
