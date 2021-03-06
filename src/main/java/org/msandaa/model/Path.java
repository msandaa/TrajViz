package org.msandaa.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Path {

	public final String id;
	public final Station startStation;
	public final Station endStation;
	public final List<PathGuidePoint> guidePoints;
	public final List<GraphPoint> pathPoints;
	public final double distanceInM;

	public Path(Station startPosition, Station endPosition, List<PathGuidePoint> crossPoints) {
		this.startStation = Objects.requireNonNull(startPosition);
		this.endStation = Objects.requireNonNull(endPosition);
		this.guidePoints = crossPoints;
		id = startPosition.id + " - " + endPosition.id;
		pathPoints = new ArrayList<>();
		pathPoints.add(startPosition);
		pathPoints.addAll(guidePoints);
		pathPoints.add(endPosition);
		distanceInM = calculateDistance();
	}

	private double calculateDistance() {
		double distance = 0;
		for (int i = 0; i < pathPoints.size() - 1; i++) {
			double startX = pathPoints.get(i).x;
			double startY = pathPoints.get(i).y;
			double endX = pathPoints.get(i + 1).x;
			double endY = pathPoints.get(i + 1).y;
			distance += Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(startY - endY, 2));
		}
		return distance;
	}

	@Override
	public String toString() {
		return id;
	}

	// Autogenerated
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(distanceInM);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((endStation == null) ? 0 : endStation.hashCode());
		result = prime * result + ((guidePoints == null) ? 0 : guidePoints.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pathPoints == null) ? 0 : pathPoints.hashCode());
		result = prime * result + ((startStation == null) ? 0 : startStation.hashCode());
		return result;
	}

	// Autogenerated
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Path other = (Path) obj;
		if (Double.doubleToLongBits(distanceInM) != Double.doubleToLongBits(other.distanceInM))
			return false;
		if (endStation == null) {
			if (other.endStation != null)
				return false;
		} else if (!endStation.equals(other.endStation))
			return false;
		if (guidePoints == null) {
			if (other.guidePoints != null)
				return false;
		} else if (!guidePoints.equals(other.guidePoints))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pathPoints == null) {
			if (other.pathPoints != null)
				return false;
		} else if (!pathPoints.equals(other.pathPoints))
			return false;
		if (startStation == null) {
			if (other.startStation != null)
				return false;
		} else if (!startStation.equals(other.startStation))
			return false;
		return true;
	}

}
