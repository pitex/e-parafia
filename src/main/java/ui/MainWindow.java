package ui;

import javax.swing.*;

import db.utils.TableValues.Kaplani;

import panels.FinanseInfoPanel;
import panels.KsiezaInfoPanel;
import panels.ParafianieInfoPanel;
import panels.ParafianieModificationPanel;
import panels.WydarzeniaInfoPanel;
import panels.WydarzeniaModificationPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class MainWindow extends JFrame {

    public MainWindow(String title) {
        super(title);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

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
        JTabbedPane tabs = new JTabbedPane();

        tabs.add("INFORMACJE", informationPanel());

        tabs.add("MODYFIKACJE", modificationPanel());

        return tabs;
    }
}
