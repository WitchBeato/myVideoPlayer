package entitiesLayer;

public class FileExtensions {
	/* this class work for determine specific area of files type images supported 
	 * in this program 
	 */
	private String explanation;
	private String[] extensions;
	public FileExtensions(String explanation, String[] extension) {
		// TODO Auto-generated constructor stub
		setExplanation(explanation);
		setExtensions(extension);
	}
	
	public String[] getExtensions() {
		return extensions;
	}
	public void setExtensions(String[] extensions) {
		this.extensions = extensions;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
}
