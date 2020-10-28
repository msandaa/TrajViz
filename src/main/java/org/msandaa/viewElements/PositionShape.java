package org.msandaa.viewElements;

import org.msandaa.model.Station;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class PositionShape extends Circle {

	public final String id;

	public PositionShape(String id) {
		super();
		this.id = id;
	}

	public PositionShape(String id, double centerX, double centerY, double radius, Paint fill) {
		super(centerX, centerY, radius, fill);
		this.id = id;
	}

	public PositionShape(String id, double centerX, double centerY, double radius) {
		super(centerX, centerY, radius);
		this.id = id;
	}

	public PositionShape(String id, double radius, Paint fill) {
		super(radius, fill);
		this.id = id;
	}

	public PositionShape(String id, double radius) {
		super(radius);
		this.id = id;
	}

	public PositionShape(Station position) {
		this(position.id, position.x * 10, -position.y * 10, 5);
	}

}
