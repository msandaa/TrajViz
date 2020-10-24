package org.msandaa.viewElements;

import org.msandaa.model.PathGuidePoint;

import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class PathShape extends Path {

	private static final double defaultArrowHeadSize = 5.0;

	public final String id;

	public PathShape(org.msandaa.model.Path path) {
		super();
		this.id = path.id;
		// strokeProperty().bind(fillProperty());
		// setFill(Color.BLACK);

		double startX = path.startPosition.x * 10;
		double startY = -path.startPosition.y * 10;
		double endX = path.endPosition.x * 10;
		double endY = -path.endPosition.y * 10;

		getElements().add(new MoveTo(startX, startY));

		// draw guidelines
		for (PathGuidePoint guidePoint : path.guidePoints) {
			getElements().add(new LineTo(guidePoint.x * 10, -guidePoint.y * 10));
			startX = guidePoint.x * 10;
			startY = -guidePoint.y * 10;
		}

		// short the endElement
		double alpha = Math.atan2((endY - startY), (endX - startX));
		double yShorting = Math.sin(alpha) * 5;
		double xShorting = Math.cos(alpha) * 5;
		endX = endX - xShorting;
		endY = endY - yShorting;

		getElements().add(new LineTo(endX, endY));

		// ArrowHead
		double angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		// point1
		double x1 = (-1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * defaultArrowHeadSize + endX;
		double y1 = (-1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * defaultArrowHeadSize + endY;
		// point2
		double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * defaultArrowHeadSize + endX;
		double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * defaultArrowHeadSize + endY;

		getElements().add(new LineTo(x1, y1));
		getElements().add(new MoveTo(endX, endY));
		getElements().add(new LineTo(x2, y2));

	}

	public void setColor(Color color) {
		setStroke(color);
	}

}
