package me.coley.recaf.graph;

import me.coley.recaf.workspace.Workspace;
import org.objectweb.asm.ClassReader;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Graph implementation based off of Recaf workspaces as providers for vertex data.
 *
 * @param <V>
 * 		Type of vertex data.
 *
 * @author Matt
 */
public abstract class WorkspaceGraph<V extends Vertex<ClassReader>> implements Graph<ClassReader, V> {
	/**
	 * Workspace to use for generating vertices from.
	 */
	private final Workspace workspace;

	/**
	 * Constructs a graph from the given workspace.
	 *
	 * @param workspace
	 * 		Workspace to pull classes from.
	 */
	public WorkspaceGraph(Workspace workspace) {
		this.workspace = workspace;
	}

	@Override
	public Set<V> vertices() {
		return getWorkspace().getPrimaryClassReaders().stream()
					.map(reader -> getVertex(reader))
					.collect(Collectors.toSet());
	}

	/**
	 * @return Input associated with the current hierarchy map.
	 */
	public Workspace getWorkspace() {
		return workspace;
	}

	/**
	 * @param name
	 * 		Class name.
	 *
	 * @return Class vertex of matching class.
	 */
	public V getVertexByName(String name) {
		if (getWorkspace().hasClass(name)) {
			ClassReader key = getWorkspace().getClassReader(name);
			return getVertexFast(key);
		}
		return null;
	}
}
