package org.msandaa.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.msandaa.model.DataPoint;
import org.msandaa.model.Path;
import org.msandaa.model.Position;
import org.msandaa.model.Roadmap;
import org.msandaa.model.Trajectories;
import org.msandaa.model.Trajectory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Deserializer {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private Deserializer() {
	}

	public static class DesPoint {

		public double x;
		public double y;

		@Override
		public String toString() {
			return ("x = " + x + "  " + "y = " + y);
		}

	}

	public static class DesPath {

		public String a;
		public String b;

		@Override
		public String toString() {
			return ("a = " + a + "  " + "b = " + b);
		}

	}

	public static class DesPointTraj {
		public String station;
		public String time;

		@Override
		public String toString() {
			return ("station = " + station + "  " + "time = " + time);
		}
	}

	public static Roadmap fileToRoadmap(final File file)
			throws FileInputException, FileMappingException, FileTransformException {
		String fExtension = getFileExtension(file);
		switch (fExtension) {
		case "json":
			return jsonToRoadmap(fileToString(file));
		default:
			throw new FileInputException.FiletypeNotProvided(file.getName(), fExtension);
		}
	}

	public static Roadmap jsonToRoadmap(final String json) throws FileMappingException {
		return deserializeRoadmap(json);
	}

	public static Trajectories jsonToTrajectorys(Roadmap roadmap, String json)
			throws FileMappingException, JsonMappingException, JsonProcessingException {
		return deserializeTrajectories(roadmap, json);
	}

	private static Roadmap deserializeRoadmap(final String json) throws FileMappingException {
		try {
			MAPPER.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
			MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

			JsonNode jsonNode = MAPPER.readTree(json);
			JsonNode posNode = jsonNode.get("positions");
			JsonNode pathNode = jsonNode.get("paths");
			Set<String> keyNotFound = new HashSet<>();

			if (posNode == null) {
				keyNotFound.add("positions");
			}
			if (pathNode == null) {
				keyNotFound.add("paths");
			}
			if (!keyNotFound.isEmpty()) {
				throw new FileMappingException.KeyNotFound(keyNotFound);
			}
			Map<String, Position> positions = mappingPositions(posNode.toString());
			Map<String, Path> paths = mappingPaths(positions, pathNode.toString());

			MAPPER.disable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
			MAPPER.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			return new Roadmap(positions, paths);
		} catch (JsonProcessingException e) {
			throw new FileMappingException(e);
		}
	}

	private static Map<String, Position> mappingPositions(final String jsonPos) throws FileMappingException {
		try {
			Map<String, DesPoint> points = MAPPER.readValue(jsonPos, new TypeReference<Map<String, DesPoint>>() {
			});
			Map<String, Position> positions = new HashMap<>(points.size());
			points.forEach((key, value) -> positions.put(key, new Position(key, value.x, value.y)));
			return positions;
		} catch (JsonProcessingException e) {
			throw new FileMappingException(e);
		}
	}

	private static Map<String, Path> mappingPaths(final Map<String, Position> positions, final String jsonPath)
			throws FileMappingException {
		try {
			List<DesPath> desPaths = MAPPER.readValue(jsonPath, new TypeReference<List<DesPath>>() {
			});
			Map<String, Path> paths = new HashMap<>(desPaths.size());
			Set<String> posNotFound = new HashSet<>();
			for (DesPath desPath : desPaths) {
				Position startPosition = positions.get(desPath.a);
				Position endPosition = positions.get(desPath.b);
				if (startPosition == null) {
					posNotFound.add(desPath.a);
				}
				if (endPosition == null) {
					posNotFound.add(desPath.b);
				}
				if (startPosition != null && endPosition != null) {
					Path nPath = new Path(startPosition, endPosition);
					paths.put(nPath.name, nPath);
				}
			}
			if (!posNotFound.isEmpty()) {
				throw new FileMappingException.PositionNotFound(posNotFound);
			}
			return paths;
		} catch (JsonProcessingException e) {
			throw new FileMappingException(e);
		}
	}

	private static Trajectories deserializeTrajectories(Roadmap roadmap, String json)
			throws JsonMappingException, JsonProcessingException {
		Iterator<Map.Entry<String, JsonNode>> it = MAPPER.readTree(json).fields();
		Map<String, Trajectory> trajectories = new HashMap<>();
		while (it.hasNext()) {
			Map.Entry<String, JsonNode> entry = it.next();
			JsonNode node = entry.getValue();
			ArrayList<DesPointTraj> deserializerPoints = MAPPER.readValue(node.toString(),
					new TypeReference<ArrayList<DesPointTraj>>() {
					});
			ArrayList<DataPoint> trajectory = new ArrayList<>();
			for (int i = 0; i < deserializerPoints.size(); i++) {
				trajectory.add(new DataPoint(roadmap.positions.get(deserializerPoints.get(i).station),
						deserializerPoints.get(i).time));
			}
			trajectories.put(entry.getKey(), new Trajectory(entry.getKey(), trajectory));
		}
		return new Trajectories(trajectories);
	}

	public static String fileToString(File file) throws FileInputException {
		try {
			return readString(file);
		} catch (IOException e) {
			throw new FileInputException(e);
		}
	}

	public static class DeserializerException extends Exception {

		public DeserializerException(Exception e) {
			super(e);
		}

		public DeserializerException(String msg) {
			super(msg);
		}

	}

	public static class FileTransformException extends DeserializerException {

		public FileTransformException(Exception e) {
			super(e);
		}

	}

	public static class FileInputException extends DeserializerException {

		public FileInputException(Exception e) {
			super(e);
		}

		public FileInputException(String msg) {
			super(msg);
		}

		public static class FiletypeNotProvided extends FileInputException {

			public final String fileName;
			public final String fileType;

			public FiletypeNotProvided(String fName, String fType) {
				super("Error by reading the file: " + fName + System.lineSeparator() + "The filetype " + fType
						+ " is not providet.");
				fileName = fName;
				fileType = fType;
			}

		}

	}

	public static class FileMappingException extends DeserializerException {

		public FileMappingException(Exception e) {
			super(e);
		}

		public FileMappingException(String msg) {
			super(msg);
		}

		public static class PositionNotFound extends FileMappingException {

			public final Set<String> positionsNotFound;

			public PositionNotFound(Set<String> positionsNotFounded) {
				super("Positions not found: " + System.lineSeparator() + positionsNotFounded.toString());
				positionsNotFound = positionsNotFounded;
			}

		}

		public static class KeyNotFound extends FileMappingException {

			public final Set<String> keyNotFound;

			public KeyNotFound(Set<String> keyNotFound) {
				super("Keys in File are expected but not found: " + keyNotFound);
				this.keyNotFound = keyNotFound;
			}

		}

	}

	public static String getFileExtension(final File file) {
		String filename = file.getName();
		int i = filename.lastIndexOf('.');
		return (i >= 0 && filename.length() > i) ? filename.substring(i + 1) : "";
	}

	public static String readString(final File file) throws IOException {
		try (Stream<String> stream = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
			return stream.collect(Collectors.joining("\n"));
		}
	}

}
