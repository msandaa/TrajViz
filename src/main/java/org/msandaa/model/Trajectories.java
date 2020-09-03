package org.msandaa.model;

import java.util.Collections;
import java.util.Map;

public class Trajectories {

	public Map<String, Trajectory> map;

	public Trajectories(Map<String, Trajectory> trajectorys) {
		map = Collections.unmodifiableMap(trajectorys);
	}

	@Override
	public String toString() {
		return "Trajectories: " + System.lineSeparator() + map;
	}

}