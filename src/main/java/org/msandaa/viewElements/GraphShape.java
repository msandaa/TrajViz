package org.msandaa.viewElements;

import java.util.Iterator;
import java.util.Map;

import org.msandaa.View;
import org.msandaa.model.Path;
import org.msandaa.model.Position;
import org.msandaa.model.Roadmap;
import org.msandaa.model.Trajectories;

import javafx.scene.Group;
import javafx.scene.Node;

public class GraphShape extends Group {

	public GraphShape(Roadmap roadmap) {

		Iterator<Path> itPaths = roadmap.paths.values().iterator();
		Iterator<Position> itPos = roadmap.positions.values().iterator();
		for (int i = 0; i < roadmap.positions.size(); i++) {
			Position position = itPos.next();
			getChildren().add(new PositionShape(position.name, position.x * 10, -position.y * 10, 4));
		}

		for (int i = 0; i < roadmap.paths.size(); i++) {
			Path path = itPaths.next();
			getChildren().add(new PathShape(path.name, path.startPosition.x * 10, -path.startPosition.y * 10,
					path.endPosition.x * 10, -path.endPosition.y * 10));
		}

	}

	public void color(Map<Path, Double> averageSpeedOfPaths) {

		Iterator<Path> itPath = averageSpeedOfPaths.keySet().iterator();

		while (itPath.hasNext()) {
			Path path = itPath.next();
			String pathId = path.name;
			Iterator<Node> itNodes = getChildren().iterator();
			while (itNodes.hasNext()) {
				Node node = itNodes.next();
				if (node instanceof PathShape) {
					PathShape pathShape = (PathShape) node;
					String pathShapeId = pathShape.id;
					if (pathShapeId.equals(pathId)) {
						pathShape.setColor(View.speedToColor(averageSpeedOfPaths.get(path)));
					}
				}
			}
		}
	}

	private void calculateColorForPath(Trajectories t, Roadmap roadmap) {

	}

	public void deleteColor() {

	}

}
