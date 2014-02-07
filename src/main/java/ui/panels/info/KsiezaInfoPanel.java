package ui.panels.info;

import db.Database;
import db.queries.QueryBuilder;
import db.utils.TableColumns;
import ui.InfoDialog;
import ui.panels.utils.CommonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import static db.utils.TableColumns.Common.ALL;
import static db.utils.Tables.AKTYWNOSCI_KAPLANOW;
import static db.utils.Tables.KAPLANI;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class KsiezaInfoPanel extends JPanel {

    public KsiezaInfoPanel() {
        setLayout(new GridBagLayout());

        GridBagConstraints c = CommonUI.defaultGridBagConstraints();
        add(all(), c);
        add(activities(), c);
    }

    private Button all() {
        Button b = CommonUI.universalButton("LISTA KAPLANOW");

        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet rs;

                try {
                    rs = Database.executeQueryWithResult(new QueryBuilder()
                            .select(ALL).from(KAPLANI).build());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    return;
                }

                InfoDialog table = new InfoDialog(KAPLANI, rs);
                table.setVisible(true);
            }
        });

        return b;
    }

    private Button activities() {
        Button b = CommonUI.universalButton("AKTYWNOSCI KAPLANOW");

        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                ResultSet rs;

                try {
                    rs = Database.executeQueryWithResult(new QueryBuilder()
                            .select(TableColumns.Common.ALL).from(AKTYWNOSCI_KAPLANOW).build());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    return;
                }

                InfoDialog table = new InfoDialog(AKTYWNOSCI_KAPLANOW, rs, false);
                table.setVisible(true);
            }
        });

        return b;
    }
}
