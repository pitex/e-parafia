package ui.panels.edit;

import db.utils.TableColumns;
import db.utils.Tables;
import ui.AddingDialog;
import ui.panels.utils.CommonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class ParafianieModificationPanel extends JPanel {
    public ParafianieModificationPanel() {
        setLayout(new GridBagLayout());

        GridBagConstraints c = CommonUI.defaultGridBagConstraints();

        add(addElement(), c);
    }

    private Button addElement() {
        Button b = CommonUI.universalButton("DODAJ PARAFIANINA");

        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AddingDialog dialog = new AddingDialog(
                        (Frame) ParafianieModificationPanel.this.getTopLevelAncestor(),
                        Tables.PARAFIANIE,
                        TableColumns.Parafianie.values()
                );
                dialog.setVisible(true);
            }
        });

        return b;
    }
}
