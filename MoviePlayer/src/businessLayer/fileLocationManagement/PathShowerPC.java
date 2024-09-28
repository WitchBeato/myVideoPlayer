package businessLayer.fileLocationManagement;

import java.io.File;

import entitiesLayer.FileExtensions;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class PathShowerPC{
    public static File FilelocationPC(FileExtensions extension){
        FileChooser chooser = new FileChooser();
        String newExtensions[] = extension.getExtensions();
        chooser.setTitle("select a file!");
    	if(newExtensions[0].charAt(0) != '*') {
            for (int i = 0; i < extension.getExtensions().length; i++) {
            	newExtensions[i] = "*" + "." + newExtensions[i];
            }
    	}
        ExtensionFilter filter = new ExtensionFilter(extension.getExplanation(), newExtensions);
        chooser.getExtensionFilters().add(filter);

        File selectedDirectory = chooser.showOpenDialog(new Stage());   
        return selectedDirectory;

    }
}