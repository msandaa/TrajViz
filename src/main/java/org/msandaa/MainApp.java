package org.msandaa;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	@FXML
	private BorderPane borderpane;

	@Override
	public void start(Stage stage) throws Exception {
		borderpane = FXMLLoader.load(getClass().getResource("main.fxml"));
		borderpane.setTop(new MenuController(stage));
		Scene scene = new Scene(borderpane);
		scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
		stage.setTitle("TrajViz");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
