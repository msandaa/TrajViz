package org.msandaa;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

import org.msandaa.util.Deserializer;
import org.msandaa.util.Deserializer.FileInputException;
import org.msandaa.util.Deserializer.FileMappingException;
import org.msandaa.util.Deserializer.FileTransformException;
import org.msandaa.viewElements.CustomFileChooser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuController extends MenuBar {

	private Stage stage;
	private Controller controller;

	public MenuController(Stage stage) {
		this.stage = stage;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@FXML
	void readGraph(ActionEvent event)
			throws FileInputException, FileMappingException, FileTransformException, URISyntaxException {
		File file = CustomFileChooser.showOpenDialog(stage);
		if (file == null) {
			return;
		}
		controller = new Controller(Deserializer.fileToRoadmap(file));
		((BorderPane) stage.getScene().getRoot()).setCenter(controller);
	}

	@FXML
	void readTrajectories(ActionEvent event) throws JsonMappingException, FileInputException, FileMappingException,
			FileTransformException, JsonProcessingException, ParseException {
		File file = CustomFileChooser.showOpenDialog(stage);
		if (file == null) {
			return;
		}
		controller.setTrajectories(Deserializer.fileToTrajectories(controller.roadmap, file));
	}

}
