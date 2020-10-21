package org.msandaa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.msandaa.model.Move;
import org.msandaa.model.Path;
import org.msandaa.model.Roadmap;
import org.msandaa.model.Trajectories;
import org.msandaa.model.Trajectory;
import org.msandaa.viewElements.PositionShape;

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
	private Roadmap roadmap;
	private Trajectories trajectories;

	// View of Model
	private View view;

	private List<PositionShape> selectedPositions = new ArrayList<>();

	private double previousX;
	private double previousY;

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
		ToolbarController toolbar = new ToolbarController(this);
		AnchorPane.setTopAnchor(toolbar, 20.0);
		AnchorPane.setLeftAnchor(toolbar, 20.0);
		this.getChildren().add(toolbar);
	}

	public void setTrajectories(Trajectories t) {
		trajectories = t;
	}

	public void tryDrawWall(int MovesIn, int MovesOut) {
		if (selectedPositions.size() == 2) {
			for (Map.Entry<Trajectory, Integer> entry : trajectorypPartsBetweenSelectedPositions().entrySet()) {
				view.drawWall(entry.getKey(), entry.getValue(), MovesIn, MovesOut);
			}
		}
	}

	public void updateMovesInOut(int MoveIn, int MoveOut) {
		view.deleteWall();
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

	private void deleteSelectedPositions() {
		for (PositionShape posShape : selectedPositions) {
			posShape.setFill(Color.BLACK);
		}
		selectedPositions.clear();
	}

	@FXML
	public void mouseClicked(MouseEvent event) {
		// System.out.println(anchorpane.getHeight() + " anchor " +
		// anchorpane.getWidth());
		// System.out.println(subscene.getHeight() + " subscene " +
		// subscene.getWidth());
		if (event.getButton() == MouseButton.PRIMARY) {
			PickResult pickResult = event.getPickResult();
			Node node = pickResult.getIntersectedNode();
			if (node instanceof PositionShape) {
				PositionShape posShape = (PositionShape) node;
				if (selectedPositions.size() < 2) {
					selectedPositions.add(posShape);
					posShape.setFill(Color.RED);
				}
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
		// System.out.println(anchorpane.getHeight() + " anchor " +
		// anchorpane.getWidth());
		// System.out.println(subscene.getHeight() + " subscene " +
		// subscene.getWidth());
		// subscene.heightProperty().bind(anchorpane.heightProperty());
		// subscene.widthProperty().bind(anchorpane.widthProperty());
	}

	private Map<Path, Double> calculateAverageSpeedOfPaths() {
		Map<Path, Double> averageSpeedsOfPaths = new HashMap<>();
		Map<String, Path> paths = roadmap.paths;
		for (Path path : paths.values()) {
			List<Double> speedsOfMoves = new ArrayList<>();
			for (Trajectory trajectory : trajectories.map.values()) {
				for (Move move : trajectory.moves) {
					if (move.startPosition == path.startPosition && move.endPosition == path.endPosition
							|| move.startPosition == path.endPosition && move.endPosition == path.startPosition) {
						speedsOfMoves.add(move.speed);
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

	private Map<Trajectory, Integer> trajectorypPartsBetweenSelectedPositions() {
		HashMap<Trajectory, Integer> trajectoriesMap = new HashMap<>();
		for (Trajectory trajectory : trajectories.map.values()) {
			for (int j = 0; j < trajectory.moves.size(); j++) {
				Move move = trajectory.moves.get(j);
				String selX1 = selectedPositions.get(0).id;
				String selX2 = selectedPositions.get(1).id;
				String moveX1 = move.startPosition.name;
				String moveX2 = move.endPosition.name;
				if (moveX1.equals(selX1) && moveX2.equals(selX2) || moveX1.equals(selX2) && moveX2.equals(selX1)) {
					trajectoriesMap.put(trajectory, j);
				}
			}
		}
		return trajectoriesMap;
	}
}
