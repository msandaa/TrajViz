package org.msandaa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.msandaa.model.Move;
import org.msandaa.model.Roadmap;
import org.msandaa.model.Trajectories;
import org.msandaa.model.Trajectory;
import org.msandaa.viewElements.PositionShape;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class Controller extends AnchorPane {

	private Roadmap roadmap;
	private Trajectories trajectories;

	private View view;

	private ArrayList<PositionShape> selected = new ArrayList<>();

	private double previousX;
	private double previousY;

	@FXML
	private SubScene subscene;
	@FXML
	private PerspectiveCamera cam;

	public Controller(Roadmap roadmap) {
		this.roadmap = roadmap;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("3Dsubscene.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setTrajectories(Trajectories t) {
		trajectories = t;
	}

	public void drawTrajectorys() {
		view.drawTrajectorys(trajectories);
	}

	public void tryDrawTrajectory() {
		if (selected.size() == 2) {
			ArrayList<Trajectory> trajectorys = trajektoriesBetweenSelectedPositions();
			for (int i = 0; i < trajectorys.size(); i++) {
				Trajectory t = trajectorys.get(i);
				view.drawTrajectory(t);
			}
		}
	}

	@FXML
	void mouseClicked(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY) {
			PickResult pickResult = event.getPickResult();
			Node node = pickResult.getIntersectedNode();
			if (node instanceof PositionShape) {
				PositionShape posShape = (PositionShape) node;
				selected.add(posShape);
				posShape.setFill(Color.RED);
			}
		} else if (event.getButton() == MouseButton.SECONDARY) {
			for (int i = 0; i < selected.size(); i++) {
				PositionShape posShape = selected.get(i);
				posShape.setFill(Color.BLACK);
			}
			selected.clear();
		}
	}

	@FXML
	void mousePressed(MouseEvent event) {
		previousX = event.getSceneX();
		previousY = event.getSceneY();
	}

	@FXML
	void mouseDragged(MouseEvent event) {
		double xDiff, yDiff, currentX, currentY;
		currentX = event.getSceneX();
		currentY = event.getSceneY();
		xDiff = previousX - currentX;
		yDiff = previousY - currentY;
		previousX = currentX;
		previousY = currentY;
		switch (event.getButton()) {
		case PRIMARY:
			view.moveX(-xDiff);
			view.moveY(-yDiff);
			break;
		case SECONDARY:
			view.rotateX(-0.2 * yDiff);
			view.rotateZ(0.2 * xDiff);
			break;
		}
	}

	@FXML
	void mouseScroll(ScrollEvent event) {
		double rotation = event.getDeltaY();
		view.moveZ(rotation);
	}

	@FXML
	public void initialize() {
		view = new View(roadmap);
		view.setDefaultPosition(subscene.getWidth() / 2, subscene.getHeight() / 2, 0);
		subscene.setRoot(view);
		subscene.setCamera(cam);
	}

	private ArrayList<Trajectory> trajektoriesBetweenSelectedPositions() {
		ArrayList<Trajectory> trajectoriesList = new ArrayList<Trajectory>();
		Iterator<Trajectory> it = trajectories.map.values().iterator();
		for (int i = 0; it.hasNext(); i++) {
			Trajectory traj = it.next();
			for (int j = 0; j < traj.moves.size(); j++) {
				Move move = traj.moves.get(j);
				String selX1 = selected.get(0).id;
				String selX2 = selected.get(1).id;
				String moveX1 = move.startPosition.name;
				String moveX2 = move.endPosition.name;
				if (moveX1.equals(selX1) && moveX2.equals(selX2) || moveX1.equals(selX2) && moveX2.equals(selX1)) {
					trajectoriesList.add(traj);
				}
			}
		}
		return trajectoriesList;
	}

}
