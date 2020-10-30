package org.msandaa.viewElements;

import java.util.List;

import org.msandaa.model.Move;
import org.msandaa.model.Trajectory;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class TrajectoryShape extends Group {

	public final String id;

	public TrajectoryShape(Trajectory trajectory) {
		this.id = trajectory.id;
		for (Move move : trajectory.moves) {
			getChildren().add(new MoveShape(move));
		}
	}

	public TrajectoryShape(Move move, int moveIns, int moveOuts) {
		this.id = move.trajectory.id;
		List<Move> moves = move.trajectory.moves;
		getChildren().add(new MoveShape(moves.get(move.trajectoryPart)));
		for (int i = 1; i <= moveIns; i++) {
			try {
				getChildren().add(new MoveShape(moves.get(move.trajectoryPart - i)));
			} catch (IndexOutOfBoundsException e) {
			}
		}
		for (int j = 1; j <= moveOuts; j++) {
			try {
				getChildren().add(new MoveShape(moves.get(move.trajectoryPart + j)));
			} catch (IndexOutOfBoundsException e) {
			}
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
