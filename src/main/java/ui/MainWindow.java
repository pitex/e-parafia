package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;

import javax.swing.*;

import db.queries.QueryBuilder;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class MainWindow extends JFrame {
	
	private JTabbedPane informationPanel() {
		JTabbedPane tabs = new JTabbedPane();
		
		tabs.add("PARAFIANIE", InformationPanels.parafianie());
		tabs.add("WYDARZENIA", InformationPanels.wydarzenia());
		tabs.add("KSIEZA", InformationPanels.ksieza());
		tabs.add("FINANSE", InformationPanels.finanse());
		
		return tabs;
	}
	
	private JTabbedPane modificationPanel() {
		JTabbedPane tabs = new JTabbedPane();
		
		tabs.add("PARAFIANIE", ModificationPanels.parafianie());
		tabs.add("WYDARZENIA", ModificationPanels.wydarzenia());
		tabs.add("KSIEZA", ModificationPanels.ksieza());
		tabs.add("FINANSE", ModificationPanels.finanse());		
		
		return tabs;		
	}
	
	private JTabbedPane mainWindowTabs() {
		JTabbedPane tabs = new JTabbedPane();
		
		tabs.add("INFORMACJE", informationPanel());
		
		tabs.add("MODYFIKACJE", modificationPanel());
		
		return tabs;
	}
	
	public MainWindow(String title) {
		super(title);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		// defaultowa maksymalizacja
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().add(new JLabel(), BorderLayout.CENTER);
		setVisible(true);
		
		setContentPane(mainWindowTabs());
	}
	
	public static void main(String[] args) {
		MainWindow mainWindow = new MainWindow("e-parafia welcome to!");
	}
}
