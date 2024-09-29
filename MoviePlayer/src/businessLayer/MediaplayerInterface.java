package businessLayer;

public interface MediaplayerInterface {
	public void play(String songLocal);
	public void stop();
	public Boolean isStop();
	public void close();
	public void setSound(int soundlevel);
	public void muteSound();
	public Boolean getMute();
	public int getLenght();
	public void setTime(int second);
	public int getTime();
	public Object getMedia();
}
