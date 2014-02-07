package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTable;

/**
 * 
 * @author Katarzyna Janocha, Michał Piekarz
 * Table showing selected rows.
 *
 */
public class InfoTable extends JTable {
	
	private JButton editButton;
	private JButton removeButton;
	
	public InfoTable(Object[][] rowData, String[] names) {
		super(rowData, names);
		
		createComponents();
	}
	
	private void createComponents() {
		editButton = new JButton("EDYTUJ");
		removeButton = new JButton("USUŃ");
		
		editButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				edit();
			}
		});
		
		removeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				remove();
			}
		});
	}
	
	private void edit() {
		
	}
	
	private void remove() {
		
	}
}
