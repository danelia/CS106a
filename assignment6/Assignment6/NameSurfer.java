/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;

import java.awt.event.*;

import javax.swing.*;
//I didn't write many comments because it was already explained what method did what.
public class NameSurfer extends Program implements NameSurferConstants {
	
	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public void init() {
		tf = new JTextField(10);
		graph = new NameSurferGraph();
		add(graph);
		add(new JLabel("Enter Name"), SOUTH);
		add(tf, SOUTH);
		add(new JButton("Graph"), SOUTH);
		add(new JButton("Clear"), SOUTH);
		addActionListeners();
		tf.addActionListener(this);

		names = new NameSurferDataBase(NAMES_DATA_FILE);
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so
	 * you will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Graph") || e.getSource() == tf) {
			NameSurferEntry entry = names.findEntry(tf.getText());
			if (entry != null) {
				graph.addEntry(entry);
				graph.update();
				tf.setText("");
			} else {
				tf.setText("");
			}
		} else if (cmd.equals("Clear")) {
			graph.clear();
			graph.update();
			tf.setText("");
		}
	}

	//instance variables.
	private JTextField tf;
	private NameSurferGraph graph;
	private NameSurferDataBase names;

}
