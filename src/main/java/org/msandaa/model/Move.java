package org.msandaa.model;

import java.util.Date;

public class Move {

	public final String id;
	public final Position startPosition;
	public final Position endPosition;
	public final Date startTime;
	public final Date endTime;
	public final double distance;
	public final long timedifferenz;
	public final double speed;

	public Move(Position startPosition, Position endPosition, Date startTime, Date endTime) {
		id = id(startPosition.name, endPosition.name);
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.startTime = startTime;
		this.endTime = endTime;
		distance = Math
				.sqrt(Math.pow(endPosition.x - startPosition.x, 2) + Math.pow(startPosition.y - endPosition.y, 2));
		timedifferenz = endTime.getTime() - startTime.getTime();
		System.out.println(timedifferenz);
		speed = distance / timedifferenz;
	}

	private String id(String startPosition, String endPosition) {
		return startPosition + " - " + endPosition;
	}

	@Override
	public String toString() {
		return "Move [startPosistion=" + startPosition + ", endPosition=" + endPosition + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", distance=" + distance + ", timedifferenz=" + timedifferenz + ", speed="
				+ speed + "]";
	}

}
