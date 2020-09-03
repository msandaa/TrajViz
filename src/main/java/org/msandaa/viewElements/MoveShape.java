package org.msandaa.viewElements;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class MoveShape extends Rectangle {

	public final String id;

	public MoveShape(String id) {
		super();
		this.id = id;
	}

	public MoveShape(String id, double x, double y, double width, double height) {
		super(x, y, width, height);
		this.id = id;
	}

	public MoveShape(String id, double width, double height, Paint fill) {
		super(width, height, fill);
		this.id = id;
	}

	public MoveShape(String id, double width, double height) {
		super(width, height);
		this.id = id;
	}

}
