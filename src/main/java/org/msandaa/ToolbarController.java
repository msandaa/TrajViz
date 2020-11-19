package org.msandaa;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class ToolbarController extends VBox {

	private Controller controller;
	@FXML
	private CheckBox headmapCheckBox;
	@FXML
	private CheckBox wallCheckBox;
	@FXML
	private Slider sliderMovesIn;
	@FXML
	private Slider sliderMovesOut;

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
		addChangeListener();
		headmapCheckBox.setDisable(true);
		wallCheckBox.setDisable(true);
	}

	@FXML
	public void headmapCheckBox(ActionEvent event) {
		controller.switchOnAverageSpeed(headmapCheckBox.isSelected());
	}

	@FXML
	public void wallCheckBox(ActionEvent event) {
		if (wallCheckBox.isSelected())
			controller.tryDrawWall((int) sliderMovesIn.getValue(), (int) sliderMovesOut.getValue());
		else
			controller.deleteWall();
	}

	@FXML
	public void delete(ActionEvent event) {
		controller.deleteAll();
	}

	@FXML
	void deleteChart(ActionEvent event) {
		controller.deleteChart();
	}

	@FXML
	public void drawWall(ActionEvent event) {
		controller.tryDrawWall((int) sliderMovesIn.getValue(), (int) sliderMovesOut.getValue());
	}

	public void headmapCheckboxSetEnable(boolean b) {
		headmapCheckBox.setDisable(!b);
	}

	public void wallCheckboxSetEnable(boolean b) {
		wallCheckBox.setSelected(false);
		wallCheckBox.setDisable(!b);
	}

	public boolean wallCheckBoxIsSelected() {
		return wallCheckBox.isSelected();
	}

	public int getMovesIn() {
		return (int) sliderMovesIn.getValue();
	}

	public int getMovesOut() {
		return (int) sliderMovesOut.getValue();
	}

	private void addChangeListener() {
		sliderMovesIn.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				controller.updateMovesInOut(new_val.intValue(), (int) sliderMovesOut.getValue());
			}
		});
		sliderMovesOut.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				controller.updateMovesInOut((int) sliderMovesIn.getValue(), new_val.intValue());
			}
		});
	}

}
