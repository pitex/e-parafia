package ui;

import db.utils.Tables;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static db.utils.TableColumns.TableColumn;
import static java.awt.GridBagConstraints.*;
import static java.util.Arrays.asList;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 *         <p/>
 *         This is the main adding and editing class,
 *         which means pressing all buttons responsible for adding or changing item in the database
 *         results in showing this dialog. The user has to fill it.
 */
public abstract class AbstractEditDialog extends JDialog {

    protected Tables table;
    protected List<TableColumn> names;
    protected List<JTextField> textFields;
    protected JButton submitButton;
    protected JButton cancelButton;

    public AbstractEditDialog(Frame owner, Tables table, TableColumn... names) {
        super(owner);

        this.table = table;
        this.names = new ArrayList<>(asList(names));
        this.textFields = new ArrayList<>();

        setModalityType(ModalityType.APPLICATION_MODAL);

        createComponents();

        createContentPane();
    }

    private void createComponents() {
        cancelButton = new JButton("Cancel");
        submitButton = new JButton("Submit");

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractEditDialog.this.dispose();
            }
        });
    }

    private void createContentPane() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(2, 2, 3, 3);

        for (TableColumn name : names) {
            JLabel label = new JLabel(name.toString().toLowerCase());
            JTextField textField = new JTextField(20);

            textFields.add(textField);

            c.gridx = 0;
            c.anchor = EAST;
            panel.add(label, c);

            c.gridx = 1;
            c.anchor = WEST;
            panel.add(textField, c);
        }

        c.gridx = 0;
        c.anchor = CENTER;
        panel.add(submitButton, c);

        c.gridx = 1;
        panel.add(cancelButton, c);
    }
}
