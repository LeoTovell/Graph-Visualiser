package com.leo.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.leo.algorithms.Algorithm.DijkstrasAlgorithm;
import com.leo.algorithms.Algorithm.KruskalsAlgorithm;
import com.leo.algorithms.Assets.LButton;
import com.leo.algorithms.Assets.LExpandableMenu;
import com.leo.algorithms.Assets.LMenuGroup;
import com.leo.algorithms.Assets.Resources;


public class Graph {

	String name = "Graph";
	ArrayList<Vertex> vertexList = new ArrayList<>();
	ShapeRenderer sr;
	SpriteBatch batch;
	Stage stage;
	private LButton 
	addVertexButton, 
	addEdgeButton, 
	removeVertexButton, 
	removeEdgeButton, 
	highlightNeighboursButton, 
	vertexInfoButton, 
	resetColorsButton, 
	getAllNeighboursButton,
	kruskalsAlgorithmButton,
	DijkstrasAlgorithmButton,
	loadGraphButton,
	saveGraphButton,
	toggleFullscreenButton;
	
	private LExpandableMenu
	functionButtonMenu,
	debugButtonMenu,
	algorithmButtonMenu,
	saveLoadMenu;
	
	private ArrayList<LButton> editButtons;
	private LMenuGroup UIMenus;
	private BitmapFont font;
	private int VERTEX_RADIUS;
	private Random r;
	private boolean[] buttonToggles = {false, false, false, false, false, false, false, false, false, false, false};
	private Vertex addSource, addDestination, edgeSource, edgeDestination, startVertex, endVertex;
	private boolean fullScreen = true;
	
	public Graph(String name) {
		this.name = name;
		@SuppressWarnings("unused")
		Resources resources = new Resources();
		sr = Resources.sr;
		batch = Resources.batch;
		stage = Resources.stage;
		font = Resources.font;
		font.setColor(Color.WHITE);
		VERTEX_RADIUS = 15;
		r = new Random();
		
		createUI(); // Create the UI
		createDefaultVertices(); // Create the default set of vertices and edges.
		
	}
	
	public void createUI() {
		functionButtonMenu = new LExpandableMenu(0, Gdx.graphics.getHeight(), 200, 250, Color.BLUE);
		functionButtonMenu.setTitle("Functions");
		
		debugButtonMenu = new LExpandableMenu(0, Gdx.graphics.getHeight() - 300, 200, 250, Color.BLUE);
		debugButtonMenu.setTitle("Debug");
		
		algorithmButtonMenu = new LExpandableMenu(0, Gdx.graphics.getHeight() - 600, 200, 250, Color.BLUE);
		algorithmButtonMenu.setTitle("Algorithms");
		
		saveLoadMenu = new LExpandableMenu(0, 0, 200, 250, Color.BLUE);
		saveLoadMenu.setTitle("Save/Load Graph");
		
		addVertexButton = new LButton("Add Vertex", Gdx.graphics.getWidth()-520, 10, 120, 30, Color.WHITE, ShapeType.Filled);
		addVertexButton.setClickedColor(Color.GREEN);

		addEdgeButton = new LButton("Add Edge", Gdx.graphics.getWidth()-390, 10, 120, 30, Color.WHITE, ShapeType.Filled);
		addEdgeButton.setClickedColor(Color.GREEN);
		
		removeVertexButton = new LButton("Remove Vertex", Gdx.graphics.getWidth()-260, 10, 120, 30, Color.WHITE, ShapeType.Filled);
		removeVertexButton.setClickedColor(Color.RED);
		
		removeEdgeButton = new LButton("Remove Edge", Gdx.graphics.getWidth()-130, 10, 120, 30, Color.WHITE, ShapeType.Filled);
		removeEdgeButton.setClickedColor(Color.RED);
		
		highlightNeighboursButton = new LButton("Highlight Neighbours", Gdx.graphics.getWidth()-650, 10, 150, 30, Color.WHITE, ShapeType.Filled);
		highlightNeighboursButton.setClickedColor(Color.PURPLE);
		
		vertexInfoButton = new LButton("Vertex Info", Gdx.graphics.getWidth()-780, 10, 120, 30, Color.WHITE, ShapeType.Filled);
		vertexInfoButton.setClickedColor(Color.PURPLE);
		
		resetColorsButton = new LButton("Reset Colours", Gdx.graphics.getWidth()-910, 10, 120, 30, Color.WHITE, ShapeType.Filled);
		
		getAllNeighboursButton = new LButton("Print All Neighbours", Gdx.graphics.getWidth()-1040, 10, 120, 30, Color.WHITE, ShapeType.Filled);
		
		kruskalsAlgorithmButton = new LButton("'Kruskals'", 0, 0, 120, 30, Color.WHITE, ShapeType.Filled);
		kruskalsAlgorithmButton.setClickedColor(Color.PINK);
		
		DijkstrasAlgorithmButton = new LButton("'Dijkstras'", 0, 0, 120, 30, Color.WHITE, ShapeType.Filled);
		DijkstrasAlgorithmButton.setClickedColor(Color.PINK);
		
		toggleFullscreenButton = new LButton("Toggle Fullscreen", 0, 0, 120, 30, Color.WHITE, ShapeType.Filled);
		
		functionButtonMenu.addElements(addVertexButton, addEdgeButton, removeVertexButton, removeEdgeButton);
		debugButtonMenu.addElements(highlightNeighboursButton, vertexInfoButton, resetColorsButton, getAllNeighboursButton, toggleFullscreenButton);
		algorithmButtonMenu.addElements(DijkstrasAlgorithmButton, kruskalsAlgorithmButton);
		
		editButtons = new ArrayList<>();
		editButtons.add(addVertexButton);
		editButtons.add(addEdgeButton);
		editButtons.add(removeVertexButton);
		editButtons.add(removeEdgeButton);
		editButtons.add(highlightNeighboursButton);
		editButtons.add(vertexInfoButton);
		editButtons.add(resetColorsButton);
		editButtons.add(getAllNeighboursButton);
		editButtons.add(DijkstrasAlgorithmButton);
		editButtons.add(kruskalsAlgorithmButton);
		editButtons.add(toggleFullscreenButton);
		
		UIMenus = new LMenuGroup();
		UIMenus.addMenu(functionButtonMenu);
		UIMenus.addMenu(algorithmButtonMenu);
		UIMenus.addMenu(debugButtonMenu);
		UIMenus.addMenu(saveLoadMenu);
	}
	
	public void createDefaultVertices() {
		String[] verticesToMake = {"a", "b", "c", "d", "1", "2", "3", "4"};
		for(String vertex: verticesToMake) {
			this.addVertex(vertex);
		}
		
		for(int i = 0; i < 0; i++) {
			this.addVertex(String.valueOf(i));
		}
		
		this.addEdge("a", "b", r.nextInt(12) + 1); // Minimum 1 - 13 Maximum
		this.addEdge("b", "c", r.nextInt(12) + 1);
		this.addEdge("c", "d", r.nextInt(12) + 1);
		this.addEdge("a", "3", r.nextInt(12) + 1);
		this.addEdge("c", "2", r.nextInt(12) + 1);
		this.addEdge("c", "1", r.nextInt(12) + 1);
		this.addEdge("3", "4", r.nextInt(12) + 1);
		this.addEdge("d", "3", r.nextInt(12) + 1);
		
		for(Vertex vertex: this.getVertexes()) {
			int x = r.nextInt(Gdx.graphics.getWidth() - 250) + 225;
			int y = r.nextInt(Gdx.graphics.getHeight() - 50) + 25;
						
			while(coordinatesCollide(x, y, vertex)) {
				x = r.nextInt(Gdx.graphics.getWidth() - 250) + 225;
				y = r.nextInt(Gdx.graphics.getHeight() - 50) + 25;
			}

			vertex.setX(x);
			vertex.setY(y);
			vertex.setRadius(VERTEX_RADIUS);
			}
		}
	
	public boolean coordinatesCollide(int x, int y, Vertex currentVertex) {
		if(x > UIMenus.getMenus().get(0).getDimensions()[0] + 50) {
			for(Vertex vertex: this.getVertexes()) {
				if(vertex != currentVertex & vertex.hasCoords()) {
					int dx = vertex.getX() - x;
					int dy = vertex.getY() - y;
					int distance = (int) Math.hypot(dx, dy);
					if(distance < 75) {
						return true;
					}}}}
		
		return false;
	}
	
	public boolean addVertex(String name) {
		Vertex existingVertex = findVertex(name);
		if(Objects.isNull(existingVertex)) {
			Vertex vertex = new Vertex(name);
			this.vertexList.add(vertex);
			return true;
		}
		else {
			System.out.println("Vertex with name: " + name + ", already exists, process aborted.");			
		}
		return false;
	}
	
	public Vertex addVertex(int x, int y) {
		if(x > UIMenus.getMenus().get(0).getDimensions()[0] + 50) {
			// Name building
			String alphabet = "abcdefghijklmnopqrstuvwxyz";
			StringBuilder sb = new StringBuilder(3);
			for(int j = 0; j < 3; j++) {
				int index = (int)(alphabet.length() * Math.random());
				sb.append(alphabet.charAt(index));}
			String newName = sb.toString();
			
			// Creating Vertex
			boolean vertexCreated = addVertex(newName);
			if(vertexCreated) {
				Vertex newVertex = findVertex(newName);
				newVertex.setPosition(x, y);
				newVertex.setRadius(VERTEX_RADIUS);
				return newVertex;
			}
			
			// Error handling
			else System.out.println("Vertex already exists, try clicking again.");
			}
		return null;
	}
	
	public void removeVertex(Vertex vertex) {
		ArrayList<Edge> edgesToDelete = new ArrayList<>();
		for(Vertex vertexToCheck: this.getVertexes()) { // Get all verticies in graph.
			for(Edge edge: vertexToCheck.getEdges()) { // Get each edge
				if(edge.getEndVertex() == vertex) { // If edge is attached to the vertex we are deleting
					edgesToDelete.add(edge); // Add to list to be removed after loop.
				}}};
		
		// Removes all vertexes as above.
		for(Edge edge: edgesToDelete) this.removeEdge(edge.getStartVertex(), edge.getEndVertex());
		this.vertexList.remove(vertex); // Vertex edges are deleted along with self.
	}
	
	public void addEdge(Vertex source, Vertex destination, Integer weight) {
		source.addEdge(destination, weight, this.getVertexes());
	}
	
	public Vertex findVertex(String data) {
		Vertex result = null;
		for(Vertex vertex: this.vertexList) {
			if(vertex.getName().equals(data)) {
				result = vertex;
			}
		}
		return result;
	}
	
	public void addEdge(String source, String destination, Integer weight) {
		boolean startFailed = false;
		boolean endFailed = false;
		int callersLineNumber = new Exception().getStackTrace()[1].getLineNumber();
		
		// Attempts to find start vertex, returns false if not found.
		Vertex start = findVertex(source);
		if(Objects.isNull(start)) {
			startFailed = true;
		}
		
		// Attempts to find end vertex, returns false if not found.
		Vertex end = findVertex(destination);
		if(Objects.isNull(end)) {
			endFailed = true;			
		}
		
		// Creates Edge if both vertices are found.
		if(!(startFailed) & !(endFailed)) {
			start.addEdge(end, weight, this.getVertexes());
		}
		
		// Error handling.
		if(startFailed) System.out.println("Source vertex: " + source + ", not found. Edge not added. | Line: " + callersLineNumber);
		if(endFailed) System.out.println("Destination vertex: " + destination + ", not found. Edge not added. | Line: " + callersLineNumber);
	}

	public void removeEdge(Vertex source, Vertex destination) {
		source.removeEdge(destination);
		destination.removeEdge(source);
		//Try both - quick get around to doing validation in non-directional
	}
	
	public Edge getEdge(Vertex start, Vertex end) {
		for(Vertex vertex: this.getVertexes()) {
			for(Edge edge: vertex.getEdges()) {
				if(edge.getStartVertex() == start && edge.getEndVertex() == end || edge.getStartVertex() == end && edge.getEndVertex() == start) {
					return edge;
				}
			}
		}
		return null;
	}
	
	public void clearGraph() {
		// Removes all vertexes and therefore edges, clearing the graph
		this.getVertexes().clear();
	}
	
	public HashMap<Vertex, ArrayList<Vertex>> getAllNeighbours() { //NEEDS FIXING
		HashMap<Vertex, ArrayList<Vertex>> allNeighbours = new HashMap<>();
		for(Vertex i: this.getVertexes()) {
			ArrayList<Vertex> neighbours = new ArrayList<>();
			for(Edge edge: i.getEdges()) {
				neighbours.add(edge.getStartVertex());
			}
			for(Vertex j: this.getVertexes()) {
				for(Edge edge: j.getEdges()) {
					if(edge.getEndVertex() == i) {
						if(!(neighbours.contains(j))) {
						neighbours.add(j);
						}
					}
				}
			}
			allNeighbours.put(i, neighbours);
		}
		return allNeighbours;
		
	}
	
	public void draw() {
		// Calls update on all elements before drawing.
		this.update();
		
		// Draws the edges.
		for(Vertex vertex: this.getVertexes()) {
			for(Edge edge: vertex.getEdges()){
				edge.draw(sr);
			}}
		
		// Draws the scene2d inputs (if any)
		stage.draw();
		
		// Draws the menu buttons
//		for(LButton button: editButtons) {
//			button.draw(sr, batch, editButtons);
//		}
		
		// Draws the menus
		UIMenus.render(Resources.sr, Resources.batch);
		
		// Draws the Vertices
		Color previousColor = Resources.font.getColor();
		Resources.font.setColor(Color.WHITE);
		for(Vertex vertex: this.getVertexes()) {
			vertex.draw(sr, font, batch);
		}
		Resources.font.setColor(previousColor);
	}
	
	public void update() {
		// Updates toggled button values.
		for(int i = 0; i < editButtons.size(); i++) {
			buttonToggles[i] = editButtons.get(i).getToggled();
		}
		
		// Acts out stage
		stage.act(Gdx.graphics.getDeltaTime());
		
		// Checks for vertex movement via dragging.
		for(Vertex vertex: this.getVertexes()) {
			vertex.checkDragged();
		}
		
		UIMenus.update();
	
		// Add Vertex method
		if(buttonToggles[0]) {
			if(Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
				this.addVertex(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
			}
		}
		
		Vertex toRemove = null;
		for(Vertex vertex: this.getVertexes()) {
			// Add Edge method
			if(buttonToggles[1]) {
				if(vertex.checkClicked()) {
					if(addSource == null) { addSource = vertex; vertex.setColor(Color.PURPLE);}
					else if(addDestination == null) { addDestination = vertex; vertex.setColor(Color.PURPLE);}
					}
				if(addSource != null & addDestination != null) {
					addEdge(addSource, addDestination, r.nextInt(15));
					addSource.setColor(Color.FIREBRICK);
					addDestination.setColor(Color.FIREBRICK);
					addSource = addDestination = null;
				}
			}
			
			// Remove Vertex method
			else if(buttonToggles[2]) {
				if(vertex.checkClicked()) {
					toRemove = vertex;
			}}
			
			// Remove Edge method
			else if(buttonToggles[3]) {
				if(vertex.checkClicked()) {
					if(edgeSource == null) {edgeSource = vertex; vertex.setColor(Color.CHARTREUSE);}
					else if(edgeDestination == null) {edgeDestination = vertex; vertex.setColor(Color.CHARTREUSE);}
					}
				if(edgeSource != null & edgeDestination != null) {
					removeEdge(edgeSource, edgeDestination);
					edgeSource.setColor(Color.FIREBRICK);
					edgeDestination.setColor(Color.FIREBRICK);
					edgeSource = edgeDestination = null;
				}}
			
			// Highlight Neighbours method
			else if(buttonToggles[4]) {
				if(vertex.checkClicked()) {
					for(Vertex i: vertex.getNeighbours()) {
						i.setColor(Color.BLUE);
					}
					vertex.setColor(Color.FIREBRICK);	
				}
			}
			
			// Print Vertex Info method
			else if(buttonToggles[5]) { //Print info
				if(vertex.checkClicked()) {
					System.out.println("---------------");
					System.out.println("Vertex: '" + vertex.getName() + "' Information");
					System.out.println("X, Y: " + Integer.toString(vertex.getX()) + ", " + Integer.toString(vertex.getY()));
					System.out.println("Color: " + vertex.getColor().toString());
					System.out.println("--- Edges ---");
					for(Edge edge: vertex.getEdges()) {
						System.out.println(edge);
					}}}
			
			// Reset Colours method
			else if(buttonToggles[6]) {
				vertex.setColor(Color.FIREBRICK);
				for(Edge edge: vertex.getEdges()) edge.setColor(edge.defaultColor);
				resetColorsButton.setToggled(false); //Make click button (not toggle)
			}
			
			// Print All Neighbours method
			else if(buttonToggles[7]) {
				HashMap<Vertex, ArrayList<Vertex>> neighbourMap = getAllNeighbours();
				neighbourMap.entrySet().forEach(entry ->{
					System.out.println("-----------------");
					System.out.println(entry.getKey().getName() + " Neighbours:");
					for(Vertex v: entry.getValue()) {
						System.out.println(v.getName());
					}
				});
				getAllNeighboursButton.setToggled(false); //Make click button (not toggle)
			}
		
			// Dijkstra's Algorithm method
			else if(buttonToggles[8]) {
				if(vertex.checkClicked()) {
					if(startVertex == null) { startVertex = vertex; vertex.setColor(Color.PURPLE);}
					else if(endVertex == null) {
						endVertex = vertex; vertex.setColor(Color.PURPLE);
						}
					}
				
				// Execute Dijkstra's.
				if(startVertex != null & endVertex != null) {					
					ArrayList<Vertex> path = DijkstrasAlgorithm.apply(this, startVertex, endVertex);
					for(int i = 0; i < path.size(); i++) {
						if(i+1 < path.size()) {
							this.getEdge(path.get(i), path.get(i + 1)).setColor(Color.GREEN);
						}}

					endVertex.setColor(Color.FIREBRICK);
					startVertex.setColor(Color.FIREBRICK);
					endVertex = startVertex = null;
				}}
			
			// Kruskal's Algorithm method
			else if(buttonToggles[9]) {
				KruskalsAlgorithm.apply(this);
				kruskalsAlgorithmButton.setToggled(false);
				buttonToggles[9] = false;
			}
			
			else if(buttonToggles[10]) {
				if(Gdx.graphics.isFullscreen()) {
					Gdx.graphics.setWindowedMode(1280, 720);
				}
				else Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
//				toggleFullscreenButton.setToggled(false);
//				buttonToggles[10] = false;
			}
		
		}

		// Removes vertices as added above.
		if(toRemove != null) this.removeVertex(toRemove);

	}
	
	// Getters/Setters
	
	public ArrayList<LButton> getEditButtons(){
		return editButtons;
	}
	
	public ArrayList<Vertex> getVertexes(){
		return this.vertexList;
	}
}
