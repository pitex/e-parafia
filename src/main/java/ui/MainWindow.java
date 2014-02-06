package ui;

import javax.swing.*;
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

        tabs.add("PARAFIANIE", InformationPanels.parafianie());
        tabs.add("WYDARZENIA", InformationPanels.wydarzenia());
        tabs.add("KSIEZA", InformationPanels.ksieza());
        tabs.add("FINANSE", InformationPanels.finanse());

        return tabs;
    }

    private JTabbedPane modificationPanel() {
        JTabbedPane tabs = new JTabbedPane();

        tabs.add("PARAFIANIE", ModificationPanels.parafianie());
        tabs.add("WYDARZENIA", ModificationPanels.wydarzenia());
        tabs.add("KSIEZA", ModificationPanels.ksieza());
        tabs.add("FINANSE", ModificationPanels.finanse());

        return tabs;
    }

    private JTabbedPane mainWindowTabs() {
        JTabbedPane tabs = new JTabbedPane();

        tabs.add("INFORMACJE", informationPanel());

        tabs.add("MODYFIKACJE", modificationPanel());

        return tabs;
    }
}
