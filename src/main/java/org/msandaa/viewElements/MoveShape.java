package org.msandaa.viewElements;

import org.msandaa.model.Move;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class MoveShape extends Rectangle {

	public final String id;

	public MoveShape(String id) {
		super();
		this.id = id;
	}

	public MoveShape(String id, double x, double y, double width, double height) {
		super(x, y, width, height);
		this.id = id;
	}

	public MoveShape(String id, double width, double height, Paint fill) {
		super(width, height, fill);
		this.id = id;
	}

	public MoveShape(String id, double width, double height) {
		super(width, height);
		this.id = id;
	}

	public MoveShape(Move move) {
		super(move.distance * 10, 20);
		this.id = move.id;
		build(move);
	}

	private void build(Move move) {
		double timeDiff = move.timedifferenz / 1000;
		setFill(calculateColor(timeDiff));
		double alpha = Math.toDegrees(
				Math.atan2((-move.endPosition.y + move.startPosition.y), (move.endPosition.x - move.startPosition.x)));
		Translate translate = new Translate(move.startPosition.x * 10, -move.startPosition.y * 10, 0);
		Rotate rotateX = new Rotate(-90, Rotate.X_AXIS);
		Rotate rotateZ = new Rotate(alpha, Rotate.Z_AXIS);
		getTransforms().addAll(translate, rotateZ, rotateX);
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
