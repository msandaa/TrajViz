package org.msandaa;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.msandaa.model.Move;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
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

	private final XYChart.Series<Number, String> series0 = new XYChart.Series<Number, String>();
	private final XYChart.Series<Number, String> series1 = new XYChart.Series<Number, String>();
	private final XYChart.Series<Number, String> series2 = new XYChart.Series<Number, String>();
	private final XYChart.Series<Number, String> series3 = new XYChart.Series<Number, String>();
	private final XYChart.Series<Number, String> series4 = new XYChart.Series<Number, String>();
	private final XYChart.Series<Number, String> series5 = new XYChart.Series<Number, String>();
	private final XYChart.Series<Number, String> series6 = new XYChart.Series<Number, String>();

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
	}

	public void draw(List<Move> movesBetweenStations) {
		delete();
		Date startTimeFirstMove = movesBetweenStations.get(0).startTime;
		int timeOffSet = timeOffset(startTimeFirstMove);
		long startTime = startTimeFirstMove.getTime();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String hrmin = format.format(startTime);
		startTimeLabel.setText("start time: " + hrmin);
		List<String> categories = new ArrayList<String>();
		long moveStartTime;
		long moveEndTime;
		long timediffSer1;
		for (Move move : movesBetweenStations) {
			categories.add(move.trajectory.id);
			String category = move.trajectory.id;
			moveStartTime = move.startTime.getTime();
			moveEndTime = move.startTime.getTime();
			timediffSer1 = moveStartTime - startTime;
			series0.getData().add(new XYChart.Data<Number, String>(timediffSer1 / 1000 + timeOffSet, category));
			double speed = move.speedInMpS;
			if (0 < speed && speed < 0.5) {
				add(series1, move, category);
			} else if (speed < 1) {
				add(series2, move, category);
			} else if (speed < 1.5) {
				add(series3, move, category);
			} else if (speed < 2.0) {
				add(series4, move, category);
			} else if (speed < 2.5) {
				add(series5, move, category);
			} else {
				add(series6, move, category);
			}
		}
		Collections.reverse(categories);
		yAxis.setCategories(FXCollections.<String>observableArrayList(categories));
		System.out.println(series0.getData());
		System.out.println(series1.getData());
		System.out.println(series2.getData());
		System.out.println(series3.getData());
		System.out.println(series4.getData());
		System.out.println(series5.getData());
		System.out.println(series6.getData());
		barChart.getData().addAll(series0, series1, series2, series3, series4, series5, series6);

	}

	private void add(Series<Number, String> series, Move move, String category) {
		series.getData().add(new XYChart.Data<Number, String>(move.timedifferenzInMilliSecs / 1000, category));
	}

	public void delete() {
		yAxis.getCategories().clear();
		series0.getData().clear();
		series1.getData().clear();
		series2.getData().clear();
		series3.getData().clear();
		series4.getData().clear();
		series5.getData().clear();
		series6.getData().clear();
		barChart.getData().clear();
	}

	public int timeOffset(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int second = cal.get(Calendar.SECOND);
		return second;
	}

}
