package org.msandaa.model;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Roadmap {

	public final Map<String, Station> positions;
	public final Map<String, Path> paths;

	public Roadmap(Map<String, Station> positions, Map<String, Path> paths) {
		this.positions = Collections.unmodifiableMap(positions);
		this.paths = Collections.unmodifiableMap(paths);
	}

	public Station getPosition(String name) {
		return positions.get(name);
	}

	public Path getPath(String name) {
		return paths.get(name);
	}

	public boolean hasPosition(String name) {
		return positions.containsKey(name);
	}

	public boolean hasPath(String name) {
		return paths.containsKey(name);
	}

	@Override
	public String toString() {
		StringBuilder bld = new StringBuilder();
		for (Station position : positions.values()) {
			bld.append(position.toString() + System.lineSeparator());
		}
		for (String roadmapPath : paths.keySet()) {
			bld.append(roadmapPath + System.lineSeparator());
		}
		return bld.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(positions, paths);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Roadmap)) {
			return false;
		}
		Roadmap that = (Roadmap) o;
		return positions.equals(that.positions) && paths.equals(that.paths);
	}

}
