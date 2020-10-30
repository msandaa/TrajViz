package org.msandaa.viewElements;

import org.msandaa.model.Station;

import javafx.scene.shape.Circle;

public class StationShape extends Circle {

	public final String id;

	public StationShape(Station position) {
		super(position.x * 10, -position.y * 10, 5);
		this.id = position.id;
	}

}
