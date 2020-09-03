package org.msandaa;

import java.io.File;
import java.net.URISyntaxException;

import org.msandaa.model.Roadmap;
import org.msandaa.model.Trajectories;
import org.msandaa.util.Deserializer;
import org.msandaa.util.Deserializer.FileInputException;
import org.msandaa.util.Deserializer.FileMappingException;
import org.msandaa.util.Deserializer.FileTransformException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	@FXML
	private BorderPane borderpane;

	private Roadmap roadmap;
	private Trajectories trajectories;

	private Controller controller;

	@Override
	public void start(Stage stage) throws Exception {
		borderpane = FXMLLoader.load(getClass().getResource("main2.fxml"));
		Scene scene = new Scene(borderpane);
		scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
		stage.setTitle("TrajViz");
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void readGraph(ActionEvent event)
			throws FileInputException, FileMappingException, FileTransformException, URISyntaxException {
		File file = new File(this.getClass().getResource("graph2.json").toURI());
		roadmap = Deserializer.fileToRoadmap(file);
		controller = new Controller(roadmap);
		borderpane.setCenter(controller);
	}

	@FXML
	void readTrajectories(ActionEvent event) throws JsonMappingException, FileMappingException, JsonProcessingException,
			FileInputException, URISyntaxException {
		File file = new File(this.getClass().getResource("trajectorys2.json").toURI());
		trajectories = Deserializer.jsonToTrajectorys(roadmap, Deserializer.fileToString(file));
		controller.setTrajectories(trajectories);
	}

	@FXML
	void drawAll(ActionEvent event) {
		controller.drawTrajectorys();
	}

	@FXML
	void drawSelected(ActionEvent event) {
		controller.tryDrawTrajectory();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
