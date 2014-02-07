package ui;

import db.utils.TableColumns.TableColumn;
import db.utils.Tables;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 *         Editing item in the database.
 */
public class EditDialog extends AbstractEditDialog {

    public EditDialog(Frame owner, Tables table,
                      TableColumn... names) {
        super(owner, table, names);

        createComponents();
    }

    private void createComponents() {
        //TODO: current data from table showed in the questionnaire

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
