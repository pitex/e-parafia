package ui;

import db.Database;
import db.queries.QueryBuilder;
import db.utils.QueryPair;
import db.utils.TableColumns.TableColumn;
import db.utils.Tables;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 *         Editing item in the database.
 */
public class EditDialog extends AbstractEditDialog {

    private final QueryPair id;

    public EditDialog(Frame owner, Tables table, List<QueryPair> pairs, TableColumn... names) {
        super(owner, table, names);

        this.id = pairs.get(0);

        createComponents(pairs);
    }

    private void createComponents(List<QueryPair> pairs) {
        int x = pairs.get(0).getKey().toString().toLowerCase().equals("id") ? 1 : 0;
        for (int i = 0; i < pairs.size(); i++) {
            textFields.get(i).setText(pairs.get(i + x).getValue());
        }

        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                submitData();
            }
        });
    }

    private void submitData() {
        List<QueryPair> pairList = new ArrayList<>(names.size());

        for (int i = 0; i < names.size(); i++) {
            pairList.add(new QueryPair(names.get(i), textFields.get(i).getText()));
        }

        QueryBuilder qb = new QueryBuilder();

        String query = qb.update(table).set(pairList).where(id.getKey() + " = " + id.getFormattedValue()).build();

        try {
            Database.executeQuery(query);
            showMessageDialog(this, "Zmieniono!", "EUREKA!", INFORMATION_MESSAGE);
            dispose();
        } catch (SQLException e) {
            e.printStackTrace();
            showMessageDialog(this, e.getMessage(), "ERROR", ERROR_MESSAGE);
        }
    }
}
