package ui;

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

import static db.Database.executeQuery;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 *         Adding item to the database.
 */
public class AddingDialog extends AbstractEditDialog {

    public AddingDialog(Frame owner, Tables table,
                        TableColumn... names) {
        super(owner, table, names);

        createComponents();
    }

    private void createComponents() {
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    submitData();
                    showMessageDialog(AddingDialog.this, "Dodano!", "EUREKA!", INFORMATION_MESSAGE);
                    dispose();
                } catch (SQLException e1) {
                    showErrorMessage(e1);
                }
            }
        });
    }

    private void submitData() throws SQLException {
        List<QueryPair> pairList = new ArrayList<>(names.size());

        for (int i = 0; i < names.size(); i++) {
            pairList.add(new QueryPair(names.get(i), textFields.get(i).getText()));
        }

        QueryBuilder builder = new QueryBuilder();
        executeQuery(builder.insertInto(table).values(pairList).build());
    }

    private void showErrorMessage(Exception e) {
        e.printStackTrace();
        showMessageDialog(this, e.getMessage(), "ERROR", ERROR_MESSAGE);
    }
}
