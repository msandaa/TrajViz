package org.msandaa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.msandaa.model.Move;
import org.msandaa.model.Path;
import org.msandaa.model.Roadmap;
import org.msandaa.model.Trajectory;
import org.msandaa.viewElements.GraphShape;
import org.msandaa.viewElements.MoveShape;
import org.msandaa.viewElements.MoveableGroup;
import org.msandaa.viewElements.RotatableGroup;
import org.msandaa.viewElements.TrajectoryShape;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;

public class View extends Group {

	private MoveableGroup moveableRoot = new MoveableGroup();
	private RotatableGroup rotateableRoot = new RotatableGroup();

	private GraphShape graph;
	private Map<String, Node> listOfTrajectoryShapes = new HashMap<>();
	private int drawnTrajektories = 0;

	public View(Roadmap roadmap) {
		graph = new GraphShape(roadmap);
		moveableRoot.getChildren().add(rotateableRoot);
		rotateableRoot.getChildren().add(graph);
		this.getChildren().add(moveableRoot);
	}

	public void drawTrajectory(Trajectory trajectory) {
		TrajectoryShape trajectoryShape = new TrajectoryShape(trajectory.id, trajectory);
		listOfTrajectoryShapes.put(trajectory.id, trajectoryShape);
		trajectoryShape.getTransforms().addAll(new Translate(0, 0, -20 * drawnTrajektories));
		rotateableRoot.getChildren().add(trajectoryShape);
		updateDrawnTrajectorys();
	}

	public void drawMoves(List<Move> moves) {
		for (Move move : moves) {
			MoveShape moveShape = new MoveShape(move);
			listOfTrajectoryShapes.put(move.id, moveShape);
			moveShape.getTransforms().addAll(new Translate(0, 20 * drawnTrajektories, 0));
			rotateableRoot.getChildren().add(moveShape);
			updateDrawnTrajectorys();
		}
	}

	public void drawWall(Trajectory trajectory, int moveElement, int moveIns, int moveOuts) {
		TrajectoryShape trajectoryShape = new TrajectoryShape(trajectory.id, trajectory, moveElement, moveIns,
				moveOuts);
		listOfTrajectoryShapes.put(trajectory.id, trajectoryShape);
		trajectoryShape.getTransforms().addAll(new Translate(0, 0, -20 * drawnTrajektories));
		rotateableRoot.getChildren().add(trajectoryShape);
		updateDrawnTrajectorys();
	}

	public void colorizePaths(Map<Path, Double> averageSpeeds) {
		graph.colorizePaths(averageSpeeds);

	}

	public void decolorizePaths() {
		graph.decolorizePaths();
	}

	public int getNumberOfDrawnTrajecotries() {
		return drawnTrajektories;
	}

	public void moveX(double distance) {
		moveableRoot.moveX(distance);
	}

	public void moveY(double distance) {
		moveableRoot.moveY(distance);
	}

	public void moveZ(double distance) {
		moveableRoot.moveZ(distance);
	}

	public void rotateX(double x) {
		rotateableRoot.rotateX(x);
	}

	public void rotateZ(double z) {
		rotateableRoot.rotateZ(z);
	}

	public void setDefaultPosition(double x, double y, double z) {
		this.getTransforms().clear();
		this.getTransforms().addAll(new Translate(x, y, z));
	}

	public void deleteWall() {
		for (Node trajectoryShape : listOfTrajectoryShapes.values()) {
			rotateableRoot.getChildren().remove(trajectoryShape);
		}
		listOfTrajectoryShapes.clear();
		updateDrawnTrajectorys();
	}

	public static Color speedToColor(Double speed) {
		if (0 <= speed && speed < 0.0005) {
			return Color.RED;
		} else if (0.0005 <= speed && speed < 0.001) {
			return Color.YELLOW;
		} else if (0.001 <= speed && speed < 0.002) {
			return Color.GREEN;
		} else {
			return Color.LIGHTGRAY;
		}
	}

	private void updateDrawnTrajectorys() {
		drawnTrajektories = listOfTrajectoryShapes.size();
	}

}
