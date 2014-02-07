package ui;

import db.utils.QueryPair;
import db.utils.TableColumns.TableColumn;
import db.utils.Tables;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 *         Editing item in the database.
 */
public class EditDialog extends AbstractEditDialog {

    public EditDialog(Frame owner, Tables table, List<QueryPair> pairs, TableColumn... names) {
        super(owner, table, names);

        createComponents(pairs);
    }

    private void createComponents(List<QueryPair> pairs) {
        int x = pairs.get(0).getKey().toString().toLowerCase().equals("id")?1:0;
        for (int i=0; i<pairs.size(); i++) {
            textFields.get(i).setText(pairs.get(i+x).getValue());
        }

        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                submitData();
            }
        });
    }

    private void submitData() {

    }
}
