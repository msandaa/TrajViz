package org.msandaa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.msandaa.model.Move;
import org.msandaa.model.Path;
import org.msandaa.model.Roadmap;
import org.msandaa.model.Trajectories;
import org.msandaa.model.Trajectory;
import org.msandaa.viewElements.PathShape;
import org.msandaa.viewElements.StationShape;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class Controller extends AnchorPane {

	// Model
	public final Roadmap roadmap;
	private Trajectories trajectories;

	// View of Model
	private View view;

	private List<StationShape> selectedPositions = new ArrayList<>();

	private double previousX;
	private double previousY;

	private ToolbarController toolbar;
	private ChartTime chart = new ChartTime();
	private ChartTime2 chart2 = new ChartTime2();

	@FXML
	public AnchorPane anchorpane;
	@FXML
	public SubScene subscene;
	@FXML
	private PerspectiveCamera cam;

	public Controller(Roadmap roadmap) {
		this.roadmap = roadmap;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("3Dsubscene.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		toolbar = new ToolbarController(this);
		AnchorPane.setTopAnchor(toolbar, 20.0);
		AnchorPane.setLeftAnchor(toolbar, 20.0);
		AnchorPane.setRightAnchor(chart, 20.0);
		AnchorPane.setBottomAnchor(chart, 20.0);
		AnchorPane.setRightAnchor(chart2, 20.0);
		AnchorPane.setBottomAnchor(chart2, 220.0);
		this.getChildren().addAll(toolbar, chart2, chart);
	}

	public void setTrajectories(Trajectories t) {
		trajectories = t;
	}

	public void tryDrawWall(int MovesIn, int MovesOut) {
		view.deleteWall();
		if (selectedPositions.size() == 2) {
			for (Move move : movesBetweenSelectedPositions()) {
				view.drawTrajectory(move, MovesIn, MovesOut);
			}
		}
	}

	public void updateMovesInOut(int MoveIn, int MoveOut) {
		tryDrawWall(MoveIn, MoveOut);
	}

	public void switchOnAverageSpeed(boolean on) {
		if (on) {
			Map<Path, Double> averageSpeedsOfPaths = calculateAverageSpeedOfPaths();
			view.colorizePaths(averageSpeedsOfPaths);
		} else {
			view.decolorizePaths();
		}
	}

	public void deleteAll() {
		view.deleteWall();
		deleteSelectedPositions();
	}

	public void deleteChart() {
		chart.delete();
		chart2.delete();
	}

	private void deleteSelectedPositions() {
		for (StationShape posShape : selectedPositions) {
			posShape.setFill(Color.BLACK);
		}
		selectedPositions.clear();
	}

	@FXML
	public void mouseClicked(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			PickResult pickResult = event.getPickResult();
			Node node = pickResult.getIntersectedNode();
			if (node instanceof StationShape) {
				StationShape posShape = (StationShape) node;
				if (selectedPositions.size() < 2) {
					selectedPositions.add(posShape);
					posShape.setFill(Color.RED);
				}
			} else if (node instanceof PathShape) {
				PathShape pathShape = (PathShape) node;
				Path path = roadmap.getPath(pathShape.id);
				List<Move> moves = movesBetweenStations(path.startStation.id, path.endStation.id);
				chart.draw(moves);
				chart2.draw(moves, toolbar.getMovesIn(), toolbar.getMovesOut());
			}
		} else if (event.getButton() == MouseButton.SECONDARY) {
		}
	}

	@FXML
	public void mousePressed(MouseEvent event) {
		previousX = event.getSceneX();
		previousY = event.getSceneY();
	}

	@FXML
	public void mouseDragged(MouseEvent event) {
		double xDiff, yDiff, currentX, currentY;
		currentX = event.getSceneX();
		currentY = event.getSceneY();
		xDiff = previousX - currentX;
		yDiff = previousY - currentY;
		previousX = currentX;
		previousY = currentY;
		switch (event.getButton()) {
		case PRIMARY:
			view.moveX(-xDiff);
			view.moveY(-yDiff);
			break;
		case SECONDARY:
			view.rotateX(-0.2 * yDiff);
			view.rotateZ(0.2 * xDiff);
			break;
		}
	}

	@FXML
	public void mouseScroll(ScrollEvent event) {
		double rotation = event.getDeltaY();
		view.moveZ(rotation);
	}

	@FXML
	public void initialize() {
		view = new View(roadmap);
		view.setDefaultPosition(subscene.getWidth() / 2, subscene.getHeight() / 2, 0);
		subscene.setRoot(view);
		subscene.setCamera(cam);
		subscene.widthProperty().bind(anchorpane.widthProperty());
		subscene.heightProperty().bind(anchorpane.heightProperty());
	}

	private Map<Path, Double> calculateAverageSpeedOfPaths() {
		Map<Path, Double> averageSpeedsOfPaths = new HashMap<>();
		Map<String, Path> paths = roadmap.paths;
		for (Path path : paths.values()) {
			List<Double> speedsOfMoves = new ArrayList<>();
			for (Trajectory trajectory : trajectories.map.values()) {
				for (Move move : trajectory.moves) {
					if (move.path.startStation == path.startStation && move.path.endStation == path.endStation
							|| move.path.startStation == path.endStation && move.path.endStation == path.startStation) {
						speedsOfMoves.add(move.speedInMpS);
					}
				}
			}
			averageSpeedsOfPaths.put(path, calculateAverage(speedsOfMoves));
		}
		return averageSpeedsOfPaths;
	}

	private double calculateAverage(List<Double> list) {
		double average = 0;
		for (int i = 0; i < list.size(); i++) {
			average = average + list.get(i);
		}
		average = average / list.size();
		return average;
	}

	private List<Move> movesBetweenSelectedPositions() {
		List<Move> moves = new ArrayList<>();
		for (Trajectory trajectory : trajectories.map.values()) {
			for (int j = 0; j < trajectory.moves.size(); j++) {
				Move move = trajectory.moves.get(j);
				String selX1 = selectedPositions.get(0).id;
				String selX2 = selectedPositions.get(1).id;
				String moveX1 = move.path.startStation.id;
				String moveX2 = move.path.endStation.id;
				if (moveX1.equals(selX1) && moveX2.equals(selX2) || moveX1.equals(selX2) && moveX2.equals(selX1)) {
					moves.add(move);
				}
			}
		}
		Collections.sort(moves);
		return moves;
	}

	private List<Move> movesBetweenStations(String startStation, String endStation) {
		List<Move> moves = new ArrayList<>();
		for (Trajectory trajectory : trajectories.map.values()) {
			for (int j = 0; j < trajectory.moves.size(); j++) {
				Move move = trajectory.moves.get(j);
				String moveX1 = move.path.startStation.id;
				String moveX2 = move.path.endStation.id;
				if (moveX1.equals(startStation) && moveX2.equals(endStation)
						|| moveX1.equals(endStation) && moveX2.equals(startStation)) {
					moves.add(move);
				}
			}
		}
		Collections.sort(moves);
		return moves;
	}

}
