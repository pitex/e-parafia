package ui.panels.info;

import java.awt.Button;
import java.awt.Choice;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JPanel;

import db.Database;
import db.queries.QueryBuilder;
import db.utils.TableColumns;
import db.utils.Tables;

import ui.InfoTable;
import ui.panels.utils.CommonUI;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class FinanseInfoPanel extends JPanel {
	public FinanseInfoPanel() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = CommonUI.defaultGridBagConstraints();
		
		add(all(), c);
		add(ofiaryOkazje(), c);
	}
	
	private Button all() {
		Button b = CommonUI.universalButton("WSZYSTKIE OFIARY");
		
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InfoTable table = new InfoTable(Database.executeQuery(new QueryBuilder().
						select(TableColumns.Common.ALL).from(Tables.OFIARY).build()));
				table.setVisible(true);
			}
		});
		
		return b;
	}
	
	private Choice specMoney() {
		final Choice c = new Choice();
		
		final String[] key ={"CHRZEST", "SLUB", "POGRZEB", "WIZYTA", "INTENCJA"};

		for (int i = 0; i < key.length; i++) {
			c.add(key[i]);
		}
		
		c.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				String selected = c.getSelectedItem();
				InfoTable table = new InfoTable(Database.executeQuery(new QueryBuilder()
					.select(TableColumns.Common.ALL).from(Tables.OFIARY)
					.where("TYP = " + selected).build()));
				table.setVisible(true);
			}
		});
		
		return c;
	}
	
	private Button ofiaryOkazje() {
		Button b = CommonUI.universalButton("OFIARY Z KONKRETNYCH OKAZJI");
		
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Choice c = specMoney();
				c.setVisible(true);
			}
		});
		
		return b;
	}
}
