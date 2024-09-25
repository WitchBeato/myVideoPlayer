package businessLayer;

public interface MediaplayerInterface {
	public void play(String songLocal);
	public void stop();
	public Boolean isStop();
	public void close();
	public void setSound(int soundlevel);
	public void muteSound();
	public Object getMedia();
}
