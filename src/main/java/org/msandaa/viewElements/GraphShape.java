package org.msandaa.viewElements;

import java.util.Map;

import org.msandaa.View;
import org.msandaa.model.Path;
import org.msandaa.model.Roadmap;
import org.msandaa.model.Station;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public class GraphShape extends Group {

	public GraphShape(Roadmap roadmap) {
		for (Station position : roadmap.positions.values()) {
			getChildren().add(new StationShape(position));
		}
		for (Path path : roadmap.paths.values()) {
			getChildren().add(new PathShape(path));
		}
	}

	public void colorizePaths(Map<Path, Double> averageSpeedOfPaths) {
		for (Path path : averageSpeedOfPaths.keySet()) {
			String pathId = path.id;
			for (Node node : getChildren()) {
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

	public void decolorizePaths() {
		for (Node node : getChildren()) {
			if (node instanceof PathShape) {
				PathShape pathShape = (PathShape) node;
				pathShape.setColor(Color.BLACK);
			}
		}
	}

}
