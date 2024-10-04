package businessLayer;



import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

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
import com.drew.metadata.mp3.Mp3Directory;
import com.drew.metadata.mp4.Mp4Descriptor;
import com.drew.metadata.mp4.Mp4Directory;

import entitiesLayer.FileExtensionsList;
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
	public void stop(Boolean isPaused) {
		// TODO Auto-generated method stub
		if(mediaplayer == null) return;
		else if(isPaused) mediaplayer.pause();
		else mediaplayer.play();
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
	public Boolean isMediaEnd() {
		// TODO Auto-generated method stub
		if(mediaplayer == null) return true;
		MediaPlayer.Status status =  mediaplayer.getStatus();
		if(MediaPlayer.Status.STOPPED == status) return true;
		else return false;
	}

	@Override
	public Boolean getMute() {
		return mediaplayer.isMute();
		
	}
	@Override
	public long getLenght() {
		Metadata metadata = null;
		String fileExt = getExtension(filelocal.getPath());
		try {
			metadata = ImageMetadataReader.readMetadata(filelocal);
		} catch (ImageProcessingException e) {
			// TODO Auto-generated catch block
			if(!FileExtensionsList.checkType(FileExtensionsList.audioExt, fileExt)) return 0;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Long duration = null;
		try {
			switch (fileExt) {
			case ".mp4":
				Mp4Directory directory = metadata.getFirstDirectoryOfType(Mp4Directory.class);
				duration = directory.getLong(Mp4Directory.TAG_DURATION);
				break;
			case ".mp3":
				  AudioFile audioFile = null;
				try {
					audioFile = AudioFileIO.read(filelocal);
				} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
						| InvalidAudioFrameException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return 0;
				}
				  duration = (long) audioFile.getAudioHeader().getTrackLength();
				  duration *= 1000;
				break;	
			default:
				break;
			}

		} catch (MetadataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return duration;
	}
	private String getExtension(String filepath) {
		int index = filepath.indexOf('.');
		String ext = filepath.substring(index, filepath.length());
		return ext;
	}
	@Override
	public void setTime(long second) {
		// TODO Auto-generated method stub
		mediaplayer.seek(Duration.millis(second));
	}
	@Override
	public long getCurrentTime() {
		// TODO Auto-generated method stub
		return (long) mediaplayer.getCurrentTime().toMillis();
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
	@Override
	public Boolean isAudio() {
		// TODO Auto-generated method stub
		String path = filelocal.getPath();
		String ext = giveExt(path);
		switch (ext) {
		case ".mp3": {
			return true;
		}
		case ".mp4": {
		}
		case ".wav": {
			return false;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + ext);
		}
	}
	private String giveExt(String path) {
		int index = path.indexOf('.');
		return path.substring(index, path.length());
	}




}
