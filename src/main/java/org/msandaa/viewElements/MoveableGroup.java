package org.msandaa.viewElements;

import javafx.scene.Group;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class MoveableGroup extends Group{
	
	private Transform translate = new Translate();

	public void setDefaultPosition(double x,double y, double z) {
		this.getTransforms().clear();
		this.getTransforms().addAll(new Translate(x, y, z));
	}
	
	public void moveX(double distance) {
		translate = translate.createConcatenation(new Translate(distance, 0, 0));
		this.getTransforms().clear();
		this.getTransforms().addAll(translate);
	}

	public void moveY(double distance) {
		translate = translate.createConcatenation(new Translate(0, distance, 0));
		this.getTransforms().clear();
		this.getTransforms().addAll(translate);
	}

	public void moveZ(double distance) {
		translate = translate.createConcatenation(new Translate(0, 0, distance));
		this.getTransforms().clear();
		this.getTransforms().addAll(translate);
	}

}
