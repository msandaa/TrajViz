package org.msandaa.viewElements;

import org.msandaa.model.Move;
import org.msandaa.model.Trajectory;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class TrajectoryShape extends Group {

	private String id;

	public TrajectoryShape(String id, Trajectory trajectory) {
		this.id = id;
		build(trajectory);
	}

	private void build(Trajectory trajectory) {
		for (int j = 0; j < trajectory.moves.size(); j++) {
			Move move = trajectory.moves.get(j);

			Rectangle rectangle = new MoveShape(move.id, move.distance * 10, 20);
			double timeDiff = move.timedifferenz / 1000;

			rectangle.setFill(calculateColor(timeDiff));

			double alpha = Math.toDegrees(Math.atan2((-move.endPosition.y + move.startPosition.y),
					(move.endPosition.x - move.startPosition.x)));
			// Translate translate = new Translate(move.startPosition.x * 10,
			// -move.startPosition.y * 10,
			// -20 * view.getDrawnTrajecotries());
			Translate translate = new Translate(move.startPosition.x * 10, -move.startPosition.y * 10, 0);
			Rotate rotateX = new Rotate(-90, Rotate.X_AXIS);
			Rotate rotateZ = new Rotate(alpha, Rotate.Z_AXIS);
			rectangle.getTransforms().addAll(translate, rotateZ, rotateX);
			getChildren().add(rectangle);
		}
	}

	private Paint calculateColor(double timeDiff) {
		if (timeDiff < 5) {
			return Color.LIGHTGREY;
		} else if (timeDiff < 10) {
			return Color.GREEN;
		} else if (timeDiff < 15) {
			return Color.YELLOW;
		} else if (timeDiff < 20) {
			return Color.RED;
		}
		return Color.BLACK;
	}

}
