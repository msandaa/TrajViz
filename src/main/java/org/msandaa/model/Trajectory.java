package org.msandaa.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trajectory {

	private final String id;
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
			Position startPosition = pointList.get(i).station;
			Position endPosition = pointList.get(i + 1).station;
			int startTime = Integer.parseInt(pointList.get(i).time);
			int endTime = Integer.parseInt(pointList.get(i + 1).time);
			moves.add(new Move(startPosition, endPosition, startTime, endTime));
		}
		return moves;
	}

	@Override
	public String toString() {
		return "Trajectory: " + System.lineSeparator() + "name=" + id + ", trajectory=" + pointList
				+ System.lineSeparator();
	}

}
