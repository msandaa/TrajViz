package org.msandaa.model;

import java.util.Date;

public class DataPoint {

	public final Position station;
	public final Date time;

	public DataPoint(Position station, Date time) {
		this.station = station;
		this.time = time;
	}

	@Override
	public String toString() {
		return "DataPoint [station=" + station + ", time=" + time + "]";
	}

}
