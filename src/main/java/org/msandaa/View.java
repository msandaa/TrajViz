package org.msandaa;

import java.util.Iterator;

import org.msandaa.model.Move;
import org.msandaa.model.Path;
import org.msandaa.model.Position;
import org.msandaa.model.Roadmap;
import org.msandaa.model.Trajectories;
import org.msandaa.model.Trajectory;
import org.msandaa.viewElements.MoveableGroup;
import org.msandaa.viewElements.PathShape;
import org.msandaa.viewElements.PositionShape;
import org.msandaa.viewElements.RotatableGroup;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class View extends Group {

	private MoveableGroup moveableRoot = new MoveableGroup();
	private RotatableGroup rotateableRoot = new RotatableGroup();

	private int drawnTrajektories = 0;

	public View(Roadmap roadmap) {
		Group graph = new Group();
		Iterator<Path> itPaths = roadmap.paths.values().iterator();
		Iterator<Position> itPos = roadmap.positions.values().iterator();
		for (int i = 0; i < roadmap.positions.size(); i++) {
			Position position = itPos.next();
			graph.getChildren().add(new PositionShape(position.name, position.x * 10, -position.y * 10, 4));
		}

		for (int i = 0; i < roadmap.paths.size(); i++) {
			Path path = itPaths.next();
			graph.getChildren().add(new PathShape(path.name, path.startPosition.x * 10, -path.startPosition.y * 10,
					path.endPosition.x * 10, -path.endPosition.y * 10));
		}

		moveableRoot.getChildren().add(rotateableRoot);
		rotateableRoot.getChildren().add(graph);
		this.getChildren().add(moveableRoot);
	}

	public void drawTrajectorys(Trajectories trajectories) {
		Iterator<Trajectory> it = trajectories.map.values().iterator();
		for (int i = 0; it.hasNext(); i++) {
			drawTrajectory(it.next());
		}
	}

	public void drawTrajectory(Trajectory trajectory) {
		for (int j = 0; j < trajectory.moves.size(); j++) {
			Move move = trajectory.moves.get(j);
			Rectangle rectangle = new Rectangle(move.distance * 10, 20);
			rectangle.setFill(Color.LIGHTGREY);
			double alpha = Math.toDegrees(Math.atan2((-move.endPosition.y + move.startPosition.y),
					(move.endPosition.x - move.startPosition.x)));
			Translate translate = new Translate(move.startPosition.x * 10, -move.startPosition.y * 10,
					-20 * drawnTrajektories);
			Rotate rotateX = new Rotate(-90, Rotate.X_AXIS);
			Rotate rotateZ = new Rotate(alpha, Rotate.Z_AXIS);
			rectangle.getTransforms().addAll(translate, rotateZ, rotateX);
			rotateableRoot.getChildren().add(rectangle);
		}
		drawnTrajektories++;
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

}
