/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants,
		ComponentListener {

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		entries = new ArrayList<NameSurferEntry>();
	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		entries.clear();
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note
	 * that this method does not actually draw the graph, but simply stores the
	 * entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		entries.add(entry);
	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of
	 * entries. Your application must call update after calling either clear or
	 * addEntry; update is also called whenever the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		initializeDiagram();
		for (int i = 0; i < entries.size(); i++) {
			drawDiagram(entries.get(i), diagramColor(i));
		}
	}
	
	//initializes diagram, draws vertical lines, horizontals lines and years.
	private void initializeDiagram() {
		drawVerticals();
		drowHorizonts();
		insertYears();
	}

	//draws horizontal lines for diagram.
	private void drowHorizonts() {
		double a = getHeight();
		double b = getWidth();
		GLine line1 = new GLine(0, GRAPH_MARGIN_SIZE, b, GRAPH_MARGIN_SIZE);
		GLine line2 = new GLine(0, a - GRAPH_MARGIN_SIZE, b, a
				- GRAPH_MARGIN_SIZE);
		add(line1);
		add(line2);
	}
	
	//draws vertical lines for diagram.
	private void drawVerticals() {
		for (int i = 0; i < NDECADES; i++) {
			double x1 = (getWidth() / NDECADES) * i;
			double y1 = 0;
			double x2 = x1;
			double y2 = getHeight();
			GLine vertical = new GLine(x1, y1, x2, y2);
			add(vertical);
		}
	}

	//adds years at the bottom of diagram.
	private void insertYears() {
		int year = START_DECADE;
		for (int i = 0; i < NDECADES; i++) {
			String decade = "" + year;
			GLabel years = new GLabel(decade);
			double x = (getWidth() / NDECADES) * i;
			double y = getHeight();
			add(years, x, y);
			year += 10;
		}
	}
	
	//it will draw diagram when the name is entered.
	private void drawDiagram(NameSurferEntry entry, Color c) {
		//we use for loop because we need to add 12 lines for the diagram.
		for (int i = 0; i < NDECADES - 1; i++) {
			int point1 = entry.getRank(i);
			int point2 = entry.getRank(i + 1);
			double x1 = (getWidth() / NDECADES) * i;
			double y1 = 0;
			double x2 = x1 + (getWidth() / NDECADES);
			double y2 = 0;
			if (point1 != 0 && point2 != 0) {
				y1 = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE * 2)
						* point1 / MAX_RANK;
				y2 = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE * 2)
						* point2 / MAX_RANK;
			} else if (point1 == 0 && point2 == 0) {
				y1 = getHeight() - GRAPH_MARGIN_SIZE;
				y2 = getHeight() - GRAPH_MARGIN_SIZE;
			} else if (point1 == 0 && point2 != 0) {
				y1 = getHeight() - GRAPH_MARGIN_SIZE;
				y2 = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE * 2)
						* point2 / MAX_RANK;
			} else if (point1 != 0 && point2 == 0) {
				y1 = GRAPH_MARGIN_SIZE + (getHeight() - GRAPH_MARGIN_SIZE * 2)
						* point1 / MAX_RANK;
				y2 = getHeight() - GRAPH_MARGIN_SIZE;
			}
			//here we use for loop again because we need to add 13 GLabels.
			for (int j = 0; j < NDECADES; j++) {
				String rank = "";
				int point = entry.getRank(j);
				double x = (getWidth() / NDECADES) * j;
				double y = 0;
				if (point != 0) {
					rank += " " + point;
					y = GRAPH_MARGIN_SIZE
							+ (getHeight() - GRAPH_MARGIN_SIZE * 2) * point
							/ MAX_RANK;
				} else if(point==0) {
					rank += " *";
					y = getHeight() - GRAPH_MARGIN_SIZE;
				}
				GLabel name = new GLabel(entry.getName() + rank);
				name.setColor(c);
				add(name, x, y);
			}
			GLine diagram = new GLine(x1, y1, x2, y2);
			diagram.setColor(c);
			add(diagram);
		}
	}

	// diagram's color will be changing with the rainbow's colors. I can. That's
	// why.
	private Color diagramColor(int i) {
		i = i % 7;
		switch (i) {
		case 0:
			return Color.RED;
		case 1:
			return Color.ORANGE;
		case 2:
			return Color.YELLOW;
		case 3:
			return Color.GREEN;
		case 4:
			return Color.BLUE;
		case 5:
			return Color.CYAN;
		}
		return Color.PINK;
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		update();
	}

	public void componentShown(ComponentEvent e) {
	}

	//instance variables.
	private ArrayList<NameSurferEntry> entries;
}
