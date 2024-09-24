package entitiesLayer;

public class FileExtensionsList {
	/*i am using this file to determine which file extensions i am supporting 
	 * on this program
	 */
	//example audioExt is for audiofiles i am supporting
	public static final FileExtensions audioExt = new FileExtensions(
			"Audio files",
			new String[] {"mp3"});
	public static final FileExtensions photoExt = new FileExtensions(
			"Image files", 
			new String[]{"png","jpg","jpeg"});
	public static Boolean checkType(FileExtensions types, String extension) {
	    for (String string : types.getExtensions()) {
			if(string.equals(extension)) return true;
		}
	    return false;
	}
}
