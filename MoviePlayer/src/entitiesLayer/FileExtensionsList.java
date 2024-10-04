package entitiesLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class FileExtensionsList {
	/*i am using this file to determine which file extensions i am supporting 
	 * on this program
	 */
	//example audioExt is for audiofiles i am supporting
	public static final FileExtensions audioExt = new FileExtensions(
			"Audio files",
			new String[] {"mp3"});
	public static final FileExtensions videoExt = new FileExtensions(
			"Video files",
			new String[] {"mp4","wav"});
	public static final FileExtensions mediaExt = utilizeExtList(
			"MediaFiles", 
			new FileExtensions[] {audioExt,videoExt}
			);
			
	public static final FileExtensions photoExt = new FileExtensions(
			"Image files", 
			new String[]{"png","jpg","jpeg"});
	public static Boolean checkType(FileExtensions types, String extension) {
		String newExtension = null;
		if(extension.charAt(0) == '.') newExtension = extension.substring(1, extension.length());
		else newExtension = extension;
	    for (String string : types.getExtensions()) {
			if(string.equals(newExtension)) return true;
		}
	    return false;
	}
	public static FileExtensions utilizeExtList(String newText, FileExtensions[] extensionlist) {
		ArrayList<String> list = new ArrayList<>();
		for (FileExtensions fileextension : extensionlist) {
			String array[] = fileextension.getExtensions();
			list.addAll(Arrays.asList(array));
		}
		FileExtensions newFileExtList = new FileExtensions(
				newText, 
				list.toArray(new String[list.size()]));
		return newFileExtList;
	}
}
