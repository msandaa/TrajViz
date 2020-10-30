package org.msandaa;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.msandaa.model.Move;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

public class ChartTime extends StackPane {

	@FXML
	CategoryAxis xAxis;
	@FXML
	NumberAxis yAxis;
	@FXML
	StackedBarChart<String, Number> barChart;

	private final XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
	private final XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
	private final XYChart.Series<String, Number> series3 = new XYChart.Series<String, Number>();
	private final XYChart.Series<String, Number> series4 = new XYChart.Series<String, Number>();
	private final XYChart.Series<String, Number> series5 = new XYChart.Series<String, Number>();

	public ChartTime() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chart1.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		barChart.setAnimated(false);
		barChart.setLegendVisible(false);
	}

	public void draw(List<Move> movesBetweenStations) {
		delete();
		Map<String, List<Move>> moves = new HashMap<>();
		for (Move move : movesBetweenStations) {
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			String hrmin = format.format(move.startTime);
			if (!moves.containsKey(hrmin)) {
				moves.put(hrmin, new ArrayList<Move>());
			}
			moves.get(hrmin).add(move);
		}
		int i = 0;
		String category;
		for (Map.Entry<String, List<Move>> entry : moves.entrySet()) {
			List<Move> movesList = entry.getValue();
			double s1 = 0, s2 = 0, s3 = 0, s4 = 0, s5 = 0;
			for (Move move : movesList) {
				double speed = move.speedInMpS;
				if (speed < 0.5) {
					s1++;
				} else if (speed < 1) {
					s2++;
				} else if (speed < 1.5) {
					s3++;
				} else if (speed < 2.0) {
					s4++;
				} else if (speed < 2.5) {
					s5++;
				}
			}
			category = entry.getKey();
			series1.getData().add(new XYChart.Data<String, Number>(category, s1));
			series2.getData().add(new XYChart.Data<String, Number>(category, s2));
			series3.getData().add(new XYChart.Data<String, Number>(category, s3));
			series4.getData().add(new XYChart.Data<String, Number>(category, s4));
			series5.getData().add(new XYChart.Data<String, Number>(category, s5));
			i++;
		}

		xAxis.setCategories(FXCollections.<String>observableArrayList(moves.keySet()));
		barChart.getData().addAll(series1, series2, series3, series4, series5);
	}

	public void delete() {
		xAxis.getCategories().clear();
		series1.getData().clear();
		series2.getData().clear();
		series3.getData().clear();
		series4.getData().clear();
		series5.getData().clear();
		barChart.getData().clear();
	}

}
