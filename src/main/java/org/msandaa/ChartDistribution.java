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

public class ChartDistribution extends StackPane {

	@FXML
	CategoryAxis xAxis;
	@FXML
	NumberAxis yAxis;
	@FXML
	StackedBarChart<String, Number> barChart;

	private final XYChart.Series<String, Number> series0 = new XYChart.Series<String, Number>();
	private final XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
	private final XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
	private final XYChart.Series<String, Number> series3 = new XYChart.Series<String, Number>();
	private final XYChart.Series<String, Number> series4 = new XYChart.Series<String, Number>();
	private final XYChart.Series<String, Number> series5 = new XYChart.Series<String, Number>();
	private final XYChart.Series<String, Number> series6 = new XYChart.Series<String, Number>();

	public ChartDistribution() {
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
		System.out.println(movesBetweenStations.size());
		Map<String, List<Move>> moves = new HashMap<>();
		for (Move move : movesBetweenStations) {
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			String hrmin = format.format(move.startTime);
			if (!moves.containsKey(hrmin)) {
				moves.put(hrmin, new ArrayList<Move>());
			}
			moves.get(hrmin).add(move);
		}
		double s1 = 0, s2 = 0, s3 = 0, s4 = 0, s5 = 0, s6 = 0;
		String category;
		for (Map.Entry<String, List<Move>> entry : moves.entrySet()) {
			List<Move> movesList = entry.getValue();
			s1 = 0;
			s2 = 0;
			s3 = 0;
			s4 = 0;
			s5 = 0;
			s6 = 0;
			for (Move move : movesList) {
				double speed = move.speedInMpS;
				if (speed <= 0.5) {
					s1++;
				} else if (speed <= 1) {
					s2++;
				} else if (speed <= 1.5) {
					s3++;
				} else if (speed <= 2.0) {
					s4++;
				} else if (speed <= 2.5) {
					s5++;
				} else {
					s6++;
				}
			}
			category = entry.getKey();
			series0.getData().add(new XYChart.Data<String, Number>(category, 0));
			series1.getData().add(new XYChart.Data<String, Number>(category, s1));
			series2.getData().add(new XYChart.Data<String, Number>(category, s2));
			series3.getData().add(new XYChart.Data<String, Number>(category, s3));
			series4.getData().add(new XYChart.Data<String, Number>(category, s4));
			series5.getData().add(new XYChart.Data<String, Number>(category, s5));
			series6.getData().add(new XYChart.Data<String, Number>(category, s6));
		}

		xAxis.setCategories(FXCollections.<String>observableArrayList(moves.keySet()));
		barChart.getData().addAll(series0, series1, series2, series3, series4, series5, series6);
	}

	public void delete() {
		xAxis.getCategories().clear();
		series0.getData().clear();
		series1.getData().clear();
		series2.getData().clear();
		series3.getData().clear();
		series4.getData().clear();
		series5.getData().clear();
		series6.getData().clear();
		barChart.getData().clear();
	}

}
