package main;

import java.io.File;

import businessLayer.DebugSettings;
import businessLayer.fileLocationManagement.LocationFinder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application{
	public static void main(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		File mainapp = LocationFinder.FXMLfinder("mainapp");
        loader.setLocation(mainapp.toURI().toURL());
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        setStage(primaryStage);
        primaryStage.show();
        System.out.println("oynuyor");
	}
	private void setStage(Stage stage) {
		stage.setResizable(DebugSettings.isResizable);
		stage.setTitle(DebugSettings.appName);
		File logolocation = LocationFinder.IMGfinder("logo");
		stage.getIcons().add(new Image(LocationFinder.filetoURL(logolocation).toString()));
	}
}
