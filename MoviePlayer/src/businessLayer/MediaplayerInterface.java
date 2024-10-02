package businessLayer;

public interface MediaplayerInterface {
	public void play(String songLocal);
	public void stop(Boolean isPaused);
	public Boolean isStop();
	public void close();
	public void setSound(int soundlevel);
	public void muteSound();
	public Boolean getMute();
	public long getLenght();
	public void setTime(long second);
	public long getCurrentTime();
	public Object getMedia();
}
