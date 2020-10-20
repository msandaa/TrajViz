package org.msandaa;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.msandaa.model.Path;
import org.msandaa.model.Roadmap;
import org.msandaa.model.Trajectories;
import org.msandaa.model.Trajectory;
import org.msandaa.viewElements.GraphShape;
import org.msandaa.viewElements.MoveableGroup;
import org.msandaa.viewElements.RotatableGroup;
import org.msandaa.viewElements.TrajectoryShape;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;

public class View extends Group {

	private MoveableGroup moveableRoot = new MoveableGroup();
	private RotatableGroup rotateableRoot = new RotatableGroup();

	private Map<String, TrajectoryShape> trajectoryShapes = new HashMap<>();
	private GraphShape graph;
	private int drawnTrajektories = 0;

	public View(Roadmap roadmap) {
		graph = new GraphShape(roadmap);
		moveableRoot.getChildren().add(rotateableRoot);
		rotateableRoot.getChildren().add(graph);
		this.getChildren().add(moveableRoot);
	}

	public void drawTrajectories(Trajectories trajectories) {
		Iterator<Trajectory> it = trajectories.map.values().iterator();
		for (int i = 0; it.hasNext(); i++) {
			drawTrajectory(it.next());
		}
	}

	public void drawTrajectory(Trajectory trajectory) {
		TrajectoryShape trajectoryShape = new TrajectoryShape(trajectory.id, this, trajectory);
		trajectoryShapes.put(trajectory.id, trajectoryShape);
		rotateableRoot.getChildren().add(trajectoryShape);
		updateDrawnTrajectorys();
	}

	private void updateDrawnTrajectorys() {
		drawnTrajektories = trajectoryShapes.size();
	}

	public void colorPaths(Map<Path, Double> avSpeeds) {
		graph.color(avSpeeds);
	}

	public int getDrawnTrajecotries() {
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

	public void deleteAll() {
		Iterator<TrajectoryShape> it = trajectoryShapes.values().iterator();
		for (int i = 0; it.hasNext(); i++) {
			TrajectoryShape trajectoryShape = it.next();
			rotateableRoot.getChildren().remove(trajectoryShape);
		}
		trajectoryShapes.clear();
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
}
