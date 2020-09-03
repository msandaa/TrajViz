package org.msandaa.model;

import java.util.Objects;

public class Path {

	public final String name;
	public final Position startPosition;
	public final Position endPosition;

	public Path(Position startPosition, Position endPosition) {
		this.startPosition = Objects.requireNonNull(startPosition);
		this.endPosition = Objects.requireNonNull(endPosition);
		name = startPosition.name + " - " + endPosition.name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, startPosition, endPosition);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Path)) {
			return false;
		}
		Path that = (Path) o;
		return startPosition.equals(that.startPosition) && endPosition.equals(that.endPosition);
	}

}
