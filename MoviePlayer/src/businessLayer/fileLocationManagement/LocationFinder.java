package businessLayer.fileLocationManagement;

import java.io.File;

import javax.swing.text.html.parser.Entity;

import businessLayer.DebugSettings;
import entitiesLayer.FileExtensions;


public class LocationFinder {
	private static String local = (DebugSettings.debugMode) ? 
			System.getProperty("user.dir")+"\\src":
			System.getProperty("user.dir");
	private static String UIlocal = local+"\\ui";
	private static String IMGlocal = local+"\\img";
	public static File FXMLfinder(String filename) {
		return FileFinder(filename+".fxml", UIlocal);
	}
	public static File IMGfinder(String filename) {
		FileExtensions imgExtensions = entitiesLayer.FileExtensionsList.photoExt;
		for (String extension : imgExtensions.getExtensions()) {
			File file = FileFinder(filename+"."+extension, UIlocal);
			if(file != null) return file;
		}
		return null;
	}
	private static File FileFinder(String filename, String filelocal) {
		String filelocation = filelocal+"\\"+filename;
		File file = new File(filelocation);
		if(file.exists()) return file;
		else return null;
	}
}
