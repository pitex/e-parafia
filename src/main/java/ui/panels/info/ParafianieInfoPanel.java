package ui.panels.info;

import db.Database;
import db.queries.QueryBuilder;
import db.utils.Tables;
import ui.InfoDialog;
import ui.panels.utils.CommonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import static db.utils.TableColumns.Common.ALL;
import static db.utils.Tables.PARAFIANIE;

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
                ResultSet rs;

                try {
                    rs = Database.executeQueryWithResult(new QueryBuilder()
                            .select(ALL).from(PARAFIANIE).where("zyje = TRUE")
                            .build());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    return;
                }

                InfoDialog table = new InfoDialog(PARAFIANIE, rs);
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
                ResultSet rs;

                try {
                    rs = Database.executeQueryWithResult(new QueryBuilder()
                            .select(ALL).from(Tables.POMOCNICY).build());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    return;
                }

                InfoDialog table = new InfoDialog(PARAFIANIE, rs);
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
                ResultSet rs;

                try {
                    rs = Database.executeQueryWithResult(new QueryBuilder()
                            .select(ALL).from(Tables.MINISTRANCI).build());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    return;
                }

                InfoDialog table = new InfoDialog(PARAFIANIE, rs);
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
                ResultSet rs;

                try {
                    rs = Database.executeQueryWithResult(new QueryBuilder()
                            .select(ALL).from(Tables.LEKTORZY).build());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    return;
                }

                InfoDialog table = new InfoDialog(PARAFIANIE, rs);
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
                ResultSet rs;

                try {
                    rs = Database.executeQueryWithResult(new QueryBuilder()
                            .select(ALL).from(Tables.SZAFARZE).build());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    return;
                }

                InfoDialog table = new InfoDialog(PARAFIANIE, rs);
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
                ResultSet rs;

                try {
                    rs = Database.executeQueryWithResult(new QueryBuilder()
                            .select(ALL).from(Tables.ZMARLI).build());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    return;
                }

                InfoDialog table = new InfoDialog(PARAFIANIE, rs, false);
                table.setVisible(true);
            }
        });

        return b;
    }
}
