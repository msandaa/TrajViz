package org.msandaa;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class ToolbarController extends VBox {

	private Controller controller;

	public ToolbarController(Controller controller) {
		this.controller = controller;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("toolbar.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

//	@FXML
//	void colorGREENClicked(Event...) {
//		controller.hideAttribute()
//	}

	@FXML
	void showAll(ActionEvent event) {
		controller.drawTrajectorys();
	}

	@FXML
	void showAverageSpeed(ActionEvent event) {
		controller.calculateAverageSpeed();
	}

	@FXML
	void showSelected(ActionEvent event) {
		controller.tryDrawTrajectory();
	}

	@FXML
	void deleteAll(ActionEvent event) {
		controller.deleteAll();
	}
}
