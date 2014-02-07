package ui.panels.info;

import db.Database;
import db.queries.QueryBuilder;
import ui.InfoDialog;
import ui.panels.utils.CommonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import static db.utils.TableColumns.Common.ALL;
import static db.utils.Tables.OFIARY;

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
                ResultSet rs;

                try {
                    rs = Database.executeQueryWithResult(new QueryBuilder().
                            select(ALL).from(OFIARY).build());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    return;
                }

                InfoDialog table = new InfoDialog(OFIARY, rs, false);
                table.setVisible(true);
            }
        });

        return b;
    }

    private Choice specMoney() {
        final Choice c = new Choice();

        final String[] key = {"CHRZEST", "SLUB", "POGRZEB", "WIZYTA", "INTENCJA"};

        for (String aKey : key) {
            c.add(aKey);
        }

        c.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent arg0) {
                ResultSet rs;
                String selected = c.getSelectedItem();

                try {
                    rs = Database.executeQueryWithResult(new QueryBuilder()
                            .select(ALL).from(OFIARY).where("TYP = " + selected).build());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    return;
                }

                InfoDialog table = new InfoDialog(OFIARY, rs, false);
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
