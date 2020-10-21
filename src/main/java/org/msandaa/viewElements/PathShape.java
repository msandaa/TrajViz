package org.msandaa.viewElements;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class PathShape extends Line {

	public final String id;

	public PathShape(String id) {
		super();
		this.id = id;
	}

	public PathShape(String id, double startX, double startY, double endX, double endY) {
		super(startX, startY, endX, endY);
		this.id = id;
	}

	public void setColor(Color color) {
		setStroke(color);
	}

}
