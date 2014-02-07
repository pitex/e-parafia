package ui.panels.edit;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import db.utils.TableColumns;
import db.utils.Tables;

import ui.AddingDialog;
import ui.panels.utils.CommonUI;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class WydarzeniaModificationPanel extends JPanel {
	public WydarzeniaModificationPanel() {
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = CommonUI.defaultGridBagConstraints();

		add(remove(), c);
		add(addIntencja(), c);
		add(addChrzest(), c);
		add(addKomunia(), c);
		add(addBierzmowanie(), c);
		add(addSlub(), c);
		add(addPogrzeb(), c);
		add(addWizyta(), c);
	}
	
	private Button remove() {
		Button b = CommonUI.universalButton("USUŃ WYDARZENIE");
		
		return b;
	}
	
	private Button addIntencja() {
		Button b = CommonUI.universalButton("DODAJ INTENCJĘ MSZALNĄ");
		
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddingDialog dialog = new AddingDialog(
						(Frame)WydarzeniaModificationPanel.this.getTopLevelAncestor(),
						Tables.INTENCJE_MSZALNE, TableColumns.IntencjeMszalne.values());
				dialog.setVisible(true);
			}
		});
		
		return b;
	}
	
	private Button addChrzest() {
		Button b = CommonUI.universalButton("DODAJ CHRZEST");
		
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddingDialog dialog = new AddingDialog(
						(Frame)WydarzeniaModificationPanel.this.getTopLevelAncestor(),
						Tables.CHRZTY, TableColumns.Chrzty.values());
				dialog.setVisible(true);
			}
		});
		
		return b;
	}
	
	private Button addKomunia() {
		Button b = CommonUI.universalButton("DODAJ KOMUNIE ŚWIĘTA");
		
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddingDialog dialog = new AddingDialog(
						(Frame)WydarzeniaModificationPanel.this.getTopLevelAncestor(),
						Tables.PIERWSZE_KOMUNIE, TableColumns.PierwszeKomunie.values());
				dialog.setVisible(true);
			}
		});
		
		return b;
	}
	
	private Button addBierzmowanie() {
		Button b = CommonUI.universalButton("DODAJ BIERZMOWANIE");
		
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddingDialog dialog = new AddingDialog(
						(Frame)WydarzeniaModificationPanel.this.getTopLevelAncestor(),
						Tables.BIERZMOWANIA, TableColumns.Bierzmowania.values());
				dialog.setVisible(true);
			}
		});
		
		return b;
	}

	private Button addSlub() {
		Button b = CommonUI.universalButton("DODAJ ŚLUB");
		
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddingDialog dialog = new AddingDialog(
						(Frame)WydarzeniaModificationPanel.this.getTopLevelAncestor(),
						Tables.SLUBY, TableColumns.Sluby.values());
				dialog.setVisible(true);
			}
		});
		
		return b;
	}
	
	private Button addPogrzeb() {
		Button b = CommonUI.universalButton("DODAJ POGRZEB");
		
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddingDialog dialog = new AddingDialog(
						(Frame)WydarzeniaModificationPanel.this.getTopLevelAncestor(),
						Tables.POGRZEBY, TableColumns.Pogrzeby.values());
				dialog.setVisible(true);
			}
		});
		
		return b;
	}
	
	private Button addWizyta() {
		Button b = CommonUI.universalButton("DODAJ WIZYTĘ DUSZPASTERSKĄ");
		
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AddingDialog dialog = new AddingDialog(
						(Frame)WydarzeniaModificationPanel.this.getTopLevelAncestor(),
						Tables.WIZYTY_DUSZPASTERSKIE, TableColumns.WizytyDuszpasterskie.values());
				dialog.setVisible(true);
			}
		});
		
		return b;
	}
}
