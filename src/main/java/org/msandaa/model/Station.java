package org.msandaa.model;

public class Station extends GraphPoint {

	public final String id;

	public Station(String id, double x, double y) {
		super(x, y);
		this.id = id;
	}

}
