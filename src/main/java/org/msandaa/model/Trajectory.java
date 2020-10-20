package org.msandaa.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trajectory {

	public final String id;
	private final List<DataPoint> pointList;
	public final List<Move> moves;

	public Trajectory(String id, List<DataPoint> pointList) {
		this.id = id;
		this.pointList = Collections.unmodifiableList(pointList);
		moves = Collections.unmodifiableList(calculateMoves());
	}

	private ArrayList<Move> calculateMoves() {
		ArrayList<Move> moves = new ArrayList<>();
		for (int i = 0; i < pointList.size() - 1; i++) {
			DataPoint startPoint = pointList.get(i);
			DataPoint endPoint = pointList.get(i + 1);
			moves.add(new Move(startPoint.station, endPoint.station, startPoint.time, endPoint.time));
		}
		return moves;
	}

	@Override
	public String toString() {
		return "Trajectory: " + System.lineSeparator() + "name=" + id + ", trajectory=" + pointList
				+ System.lineSeparator();
	}

}
