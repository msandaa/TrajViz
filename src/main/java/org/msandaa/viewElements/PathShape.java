package org.msandaa.viewElements;

import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class PathShape extends Path {

	private static final double defaultArrowHeadSize = 5.0;

	public final String id;

	public PathShape(String id, double startX, double startY, double endX, double endY, double arrowHeadSize) {
		super();
		this.id = id;
		strokeProperty().bind(fillProperty());
		setFill(Color.BLACK);

		double alpha = Math.atan2((endY - startY), (endX - startX));
		double y_shorting = Math.sin(alpha) * 5;
		double x_shorting = Math.cos(alpha) * 5;

		endX = endX - x_shorting;
		endY = endY - y_shorting;

		// Line
		getElements().add(new MoveTo(startX, startY));
		getElements().add(new LineTo(endX, endY));

		// ArrowHead
		double angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		// point1
		double x1 = (-1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
		double y1 = (-1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;
		// point2
		double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
		double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;

		getElements().add(new LineTo(x1, y1));
		getElements().add(new LineTo(x2, y2));
		getElements().add(new LineTo(endX, endY));
	}

	public PathShape(String id, double startX, double startY, double endX, double endY) {
		this(id, startX, startY, endX, endY, defaultArrowHeadSize);
	}

	public void setColor(Color color) {
		setFill(color);
	}

}
