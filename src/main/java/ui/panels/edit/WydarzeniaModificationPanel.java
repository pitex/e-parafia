package ui.panels.edit;

import javax.swing.*;

public class WydarzeniaModificationPanel extends JTabbedPane {
    public WydarzeniaModificationPanel() {
        add("DODAJ", new WydarzeniaAddPanel());
    }
}
