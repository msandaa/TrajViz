package org.msandaa.model;

public class DataPoint {

	public final Position station;
	public final String time;

	public DataPoint(Position station, String time) {
		this.station = station;
		this.time = time;
	}

	@Override
	public String toString() {
		return "DataPoint [station=" + station + ", time=" + time + "]";
	}

}
