package ui;

import ui.panels.SettingsPanel;
import ui.panels.edit.ParafianieModificationPanel;
import ui.panels.edit.WydarzeniaModificationPanel;
import ui.panels.info.FinanseInfoPanel;
import ui.panels.info.KsiezaInfoPanel;
import ui.panels.info.ParafianieInfoPanel;
import ui.panels.info.WydarzeniaInfoPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class MainWindow extends JFrame {

    public MainWindow(String title) {
        super(title);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setExtendedState(MAXIMIZED_BOTH);

        setContentPane(mainWindowTabs());
    }

    public static void main(String[] args) {

        final MainWindow mainWindow = new MainWindow("e-parafia welcome to!");

        LoginDialog loginDialog = new LoginDialog(mainWindow);
        loginDialog.addCancelButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.dispose();
            }
        });

        loginDialog.setVisible(true);
    }

    private JTabbedPane informationPanel() {
        JTabbedPane tabs = new JTabbedPane();

        tabs.add("PARAFIANIE", new ParafianieInfoPanel());
        tabs.add("WYDARZENIA", new WydarzeniaInfoPanel());
        tabs.add("KSIEZA", new KsiezaInfoPanel());
        tabs.add("FINANSE", new FinanseInfoPanel());

        return tabs;
    }

    private JTabbedPane modificationPanel() {
        JTabbedPane tabs = new JTabbedPane();

        tabs.add("PARAFIANIE", new ParafianieModificationPanel());
        tabs.add("WYDARZENIA", new WydarzeniaModificationPanel());
        tabs.add("KSIEZA", new KsiezaInfoPanel());
        tabs.add("FINANSE", new FinanseInfoPanel());

        return tabs;
    }

    private JTabbedPane mainWindowTabs() {
        final JTabbedPane tabs = new JTabbedPane();

        tabs.add("INFORMACJE", informationPanel());
        tabs.add("MODYFIKACJE", modificationPanel());
        tabs.add("USTAWIENIA", new SettingsPanel());

        tabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tabs.getSelectedIndex() == 2) {
                    ((SettingsPanel) tabs.getSelectedComponent()).init();
                }
            }
        });

        return tabs;
    }
}
