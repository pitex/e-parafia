package ui.panels.info;

import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

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
public class KsiezaInfoPanel extends JPanel {

	public KsiezaInfoPanel() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = CommonUI.defaultGridBagConstraints();
		add(all(),c);
		add(activities(),c);
		add(selectedInfo(),c);
	}
	
	private Button all() {
		Button b = CommonUI.universalButton("LISTA KAPLANOW");
		
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				InfoTable table = new InfoTable(Database.executeQuery(new QueryBuilder().
						select(TableColumns.Common.ALL).from(Tables.KAPLANI).build()));
				table.setVisible(true);
			}
		});
		
		return b;
	}
	
	private Button activities() {
		Button b = CommonUI.universalButton("AKTYWNOSCI KAPLANOW");
		
		return b;
	}

	private Button selectedInfo() {
		Button b = CommonUI.universalButton("WYBRANE INFORMACJE O KAPLANACH");
		
		return b;
	}
}
