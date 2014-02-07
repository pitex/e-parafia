package ui;

import db.Database;
import db.queries.QueryBuilder;
import db.utils.QueryPair;
import db.utils.TableColumns.TableColumn;
import db.utils.Tables;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
                submitData();
            }
        });
    }

    private void submitData() {
        List<QueryPair> pairList = new ArrayList<>(names.size());

        for (int i = 0; i < names.size(); i++) {
            pairList.add(new QueryPair(names.get(i), textFields.get(i).getText()));
        }

        QueryBuilder builder = new QueryBuilder();
        Database.executeQuery(builder.insertInto(table).values(pairList).build());
    }
}
