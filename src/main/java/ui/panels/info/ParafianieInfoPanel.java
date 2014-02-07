package ui.panels.info;

import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class ParafianieInfoPanel extends JPanel {
	public ParafianieInfoPanel() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = CommonUI.defaultGridBagConstraints();
		
		add(all(), c);
		add(pomocnicy(), c);
		add(ministranci(), c);
		add(lektorzy(), c);
		add(szafarze(), c);
		add(zmarli(), c);
	}
	
	private Button all() {
		Button b = CommonUI.universalButton("WSZYSCY ZYJACY PARAFIANIE");
		
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InfoTable table = new InfoTable(Database.executeQuery(new QueryBuilder().
						select(TableColumns.Common.ALL).from(Tables.PARAFIANIE).where("zyje = TRUE")
						.build()));
				table.setVisible(true);
			}
		});
		
		return b;
	}
	
	private Button pomocnicy() {
		Button b = CommonUI.universalButton("POMAGAJACY");
		
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InfoTable table = new InfoTable(Database.executeQuery(new QueryBuilder().
						select(TableColumns.Common.ALL).from(Tables.POMOCNICY).build()));
				table.setVisible(true);
			}
		});
		
		return b;
	}
	
	private Button ministranci() {
		Button b = CommonUI.universalButton("MINISTRANCI");
		
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InfoTable table = new InfoTable(Database.executeQuery(new QueryBuilder().
						select(TableColumns.Common.ALL).from(Tables.MINISTRANCI).build()));
				table.setVisible(true);
			}
		});
		
		return b;
	}
	
	private Button lektorzy() {
		Button b = CommonUI.universalButton("LEKTORZY");
		
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InfoTable table = new InfoTable(Database.executeQuery(new QueryBuilder().
						select(TableColumns.Common.ALL).from(Tables.LEKTORZY).build()));
				table.setVisible(true);
			}
		});
		
		return b;
	}
	
	private Button szafarze() {
		Button b = CommonUI.universalButton("SZAFARZE");
		
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InfoTable table = new InfoTable(Database.executeQuery(new QueryBuilder().
						select(TableColumns.Common.ALL).from(Tables.SZAFARZE).build()));
				table.setVisible(true);
			}
		});
		
		return b;
	}
	
	private Button zmarli() {
		Button b = CommonUI.universalButton("ZMARLI");
		
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InfoTable table = new InfoTable(Database.executeQuery(new QueryBuilder().
						select(TableColumns.Common.ALL).from(Tables.ZMARLI).build()));
				table.setVisible(true);
			}
		});
		
		return b;
	}
}
