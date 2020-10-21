package org.msandaa.viewElements;

import java.util.List;

import org.msandaa.model.Move;
import org.msandaa.model.Trajectory;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class TrajectoryShape extends Group {

	private String id;

	public TrajectoryShape(String id, Trajectory trajectory) {
		this.id = id;
		build(trajectory);
	}

	public TrajectoryShape(String id, Trajectory trajectory, int moveElement, int moveIns, int moveOuts) {
		this.id = id;
		build(trajectory, moveElement, moveIns, moveOuts);
	}

	private void build(Trajectory trajectory, int moveElement, int moveIns, int moveOuts) {
		List<Move> moves = trajectory.moves;
		getChildren().add(new MoveShape(moves.get(moveElement)));
		for (int i = 1; i <= moveIns; i++) {
			try {
				getChildren().add(new MoveShape(moves.get(moveElement - i)));
			} catch (IndexOutOfBoundsException e) {
			}
		}
		for (int j = 1; j <= moveOuts; j++) {
			try {
				getChildren().add(new MoveShape(moves.get(moveElement + j)));
			} catch (IndexOutOfBoundsException e) {
			}
		}
	}

	private void build(Trajectory trajectory) {
		for (Move move : trajectory.moves) {
			getChildren().add(new MoveShape(move));
		}
	}

	private Paint calculateColor(double timeDiff) {
		if (timeDiff < 5) {
			return Color.LIGHTGREY;
		} else if (timeDiff < 10) {
			return Color.GREEN;
		} else if (timeDiff < 15) {
			return Color.YELLOW;
		} else if (timeDiff < 20) {
			return Color.RED;
		}
		return Color.BLACK;
	}

}
