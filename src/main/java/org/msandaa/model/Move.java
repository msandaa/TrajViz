package org.msandaa.model;

public class Move {

	public final Position startPosition;
	public final Position endPosition;
	public final int startTime;
	public final int endTime;
	public final double distance;
	public final double timedifferenz;
	public final double speed;

	public Move(Position startPosition, Position endPosition, int startTime, int endTime) {
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.startTime = startTime;
		this.endTime = endTime;
		distance = Math
				.sqrt(Math.pow(endPosition.x - startPosition.x, 2) + Math.pow(startPosition.y - endPosition.y, 2));
		timedifferenz = endTime - startTime;
		speed = distance / timedifferenz;
	}

	@Override
	public String toString() {
		return "Move [startPosistion=" + startPosition + ", endPosition=" + endPosition + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", distance=" + distance + ", timedifferenz=" + timedifferenz + ", speed="
				+ speed + "]";
	}

}
