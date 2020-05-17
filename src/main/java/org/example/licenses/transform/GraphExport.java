package org.example.licenses.transform;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * Exports graph edges for visualization.
 * 
 * @author 33a1cc8d616a72f953d8e15274194bcd5aac2b78fbe6b4a4d1a911e0f2ef00cd
 */
public class GraphExport {

	public static final String SEPARATOR = ";";
	private List<Edge> edges = new LinkedList<>();

	public class Edge {
		String source;
		String target;
		String type;

		public Edge(String source, String target, String type) {
			this.source = source;
			this.target = target;
			this.type = type;
		}
	}

	public GraphExport export(File file) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		for (Edge edge : edges) {
			stringBuilder.append(edge.source);
			stringBuilder.append(SEPARATOR);
			stringBuilder.append(edge.target);
			stringBuilder.append(SEPARATOR);
			stringBuilder.append(edge.type);
			stringBuilder.append(System.lineSeparator());
		}
		FileUtils.write(file, stringBuilder, StandardCharsets.UTF_8);
		return this;
	}

	public GraphExport addEdge(String source, String target, String type) {
		edges.add(new Edge(source, target, type));
		return this;
	}

}