package org.msandaa;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.msandaa.model.Move;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ChartTime2 extends AnchorPane {

	@FXML
	private Label startTimeLabel;
	@FXML
	private CategoryAxis yAxis;
	@FXML
	private NumberAxis xAxis;
	@FXML
	private StackedBarChart<Number, String> barChart;

	List<XYChart.Series<Number, String>> series = new ArrayList<XYChart.Series<Number, String>>();

	public ChartTime2() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chart2.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		barChart.setAnimated(false);
		barChart.setLegendVisible(false);
		barChart.setCategoryGap(1);
		yAxis.setTickLabelsVisible(false);
		for (int i = 0; i < 10; i++) {
			XYChart.Series<Number, String> s = new XYChart.Series<Number, String>();
			series.add(s);
		}
	}

	public void draw(List<Move> movesBetweenStations, int movesIn, int movesOut) {
		delete();
		String category;
		List<String> categories = new ArrayList<String>();
		List<List<Move>> listMovesInToOut = listMovesInToOut(movesBetweenStations, movesIn, movesOut);
		setStartTime(listMovesInToOut);
		int timeOffSet = timeOffset(listMovesInToOut);
		long firstMoveStartTime = listMovesInToOut.get(0).get(0).startTime.getTime();
		for (List<Move> movesInToOut : listMovesInToOut) {
			category = movesInToOut.get(0).trajectory.id;
			categories.add(category);
			setTransparentSerie((movesInToOut.get(0).startTime.getTime() - firstMoveStartTime) / 1000 + timeOffSet,
					category);
			setSeries(movesInToOut, category);
		}
		Collections.reverse(categories);
		yAxis.setCategories(FXCollections.<String>observableArrayList(categories));
		barChart.getData().addAll(series);
	}

	private void setSeries(List<Move> movesInToOut, String category) {
		for (int i = 0; i < movesInToOut.size(); i++) {
			Move move = movesInToOut.get(i);
			XYChart.Data<Number, String> data = new XYChart.Data<Number, String>(move.timedifferenzInMilliSecs / 1000,
					category);
			series.get(i + 1).getData().add(data);
			data.nodeProperty().addListener(new ChangeListener<Node>() {
				@Override
				public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
					if (node != null) {
						node.setStyle(getcolor(move.speedInMpS));
					}
				}
			});
		}
	}

	private void setTransparentSerie(long time, String category) {
		series.get(0).getData().add(new XYChart.Data<Number, String>(time, category));
	}

	private List<List<Move>> listMovesInToOut(List<Move> movesBetweenStations, int movesIn, int movesOut) {
		ArrayList<List<Move>> listMovesInToOut = new ArrayList<List<Move>>();
		for (Move move : movesBetweenStations) {
			List<Move> movesInToOut = new ArrayList<Move>();
			for (int i = -movesIn; i <= movesOut; i++) {
				try {
					movesInToOut.add(move.trajectory.moves.get(move.trajectory.moves.indexOf(move) + i));
				} catch (IndexOutOfBoundsException e) {
				}
			}
			listMovesInToOut.add(movesInToOut);
		}
		return listMovesInToOut;
	}

	private void setStartTime(List<List<Move>> listMovesBetweenStations) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String hrmin = format.format(listMovesBetweenStations.get(0).get(0).startTime.getTime());
		startTimeLabel.setText("start time: " + hrmin);
	}

	private String getcolor(double speed) {
		if (0 < speed && speed < 0.5) {
			return "-fx-bar-fill: red;";
		} else if (speed < 1) {
			return "-fx-bar-fill: #FF8000;";
		} else if (speed < 1.5) {
			return "-fx-bar-fill: yellow;";
		} else if (speed < 2.0) {
			return "-fx-bar-fill: #80FF00;";
		} else if (speed < 2.5) {
			return "-fx-bar-fill: lime;";
		} else {
			return "-fx-bar-fill: lightgrey;";
		}
	}

	public void delete() {
		yAxis.getCategories().clear();
		for (XYChart.Series<Number, String> s : series) {
			s.getData().clear();
		}
		barChart.getData().clear();
	}

	public int timeOffset(List<List<Move>> listMovesInToOut) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(listMovesInToOut.get(0).get(0).startTime);
		int second = cal.get(Calendar.SECOND);
		return second;
	}

}
