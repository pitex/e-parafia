package ui;

import db.utils.TableColumns.TableColumn;
import db.utils.Tables;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    }
}
