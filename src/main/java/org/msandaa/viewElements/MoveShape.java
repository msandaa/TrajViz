package org.msandaa.viewElements;

import org.msandaa.View;
import org.msandaa.model.Move;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class MoveShape extends Group {

	public final String id;

	public MoveShape(Move move) {
		this.id = move.id;
		build(move);
	}

	private void build(Move move) {
		double speed = move.speedInMpS;
		for (int i = 0; i < move.path.pathPoints.size() - 1; i++) {
			double startX = move.path.pathPoints.get(i).x;
			double startY = -move.path.pathPoints.get(i).y;
			double endX = move.path.pathPoints.get(i + 1).x;
			double endY = -move.path.pathPoints.get(i + 1).y;
			Rectangle rectangle = new Rectangle(Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(startY - endY, 2)) * 10,
					20);
			double alpha = Math.toDegrees(Math.atan2((endY - startY), (endX - startX)));
			Translate translate = new Translate(startX * 10, startY * 10, 0);
			Rotate rotateX = new Rotate(-90, Rotate.X_AXIS);
			Rotate rotateZ = new Rotate(alpha, Rotate.Z_AXIS);
			rectangle.getTransforms().addAll(translate, rotateZ, rotateX);
			rectangle.setFill(View.speedToColor(speed));
			getChildren().add(rectangle);
		}
	}

}
