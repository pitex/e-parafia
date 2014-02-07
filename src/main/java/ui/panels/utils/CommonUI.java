package ui.panels.utils;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import ui.InfoTable;

import db.Database;
import db.queries.QueryBuilder;
import db.utils.TableColumns;
import db.utils.Tables;

public class CommonUI {
	public static Dimension BUTTON_DIMENSION = new Dimension(1000,90);
	
	public static Button universalButton(String s) {
		Button b = new Button(s);
		b.setPreferredSize(CommonUI.BUTTON_DIMENSION);
		b.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		b.setBackground(new Color(176, 224, 230));
		return b;
	}
	
	public static GridBagConstraints defaultGridBagConstraints() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(2,2,3,3);
		return c;
	}
	
	public static String[] tablesValuesAsStringArray(Tables[] t) {
		String s[] = new String[t.length];
		for (int i = 0; i < t.length; i++) {
			s[i] = t.toString();
		}
		return s;
	}
	
	public static void selectAllFrom(Tables t) {
		InfoTable table = new InfoTable(Database.executeQuery(new QueryBuilder().
				select(TableColumns.Common.ALL).from(t).build()));
		table.setVisible(true);
	}
	
}
