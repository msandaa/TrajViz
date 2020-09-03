package org.msandaa.viewElements;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;

public class RotatableGroup extends Group {

	private Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
	private Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
	private Rotate rotateZ = new Rotate(0, Rotate.Z_AXIS);

	public RotatableGroup() {
		getTransforms().addAll(rotateX, rotateY, rotateZ);
	}

	public void rotateX(double angle) {
		rotateX.setAngle(angle + rotateX.getAngle());
	}

	public void rotateY(double angle) {
		rotateY.setAngle(angle + rotateY.getAngle());
	}
	
	public void rotateZ(double angle) {
		rotateZ.setAngle(angle + rotateZ.getAngle());
	}
	
}
