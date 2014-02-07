package ui.panels.info;

import db.utils.Tables;
import ui.panels.utils.CommonUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author Katarzyna Janocha, Michał Piekarz
 */
public class WydarzeniaInfoPanel extends JPanel {
    public WydarzeniaInfoPanel() {
        setLayout(new GridBagLayout());

        GridBagConstraints c = CommonUI.defaultGridBagConstraints();

        add(all(), c);
        add(specified(), c);
    }

    private Button all() {
        Button b = CommonUI.universalButton("WYSWIETL WSZYSTKIE WYDARZENIA");

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CommonUI.selectAllFrom(Tables.AKTYWNOSCI_KAPLANOW, false);
            }
        });

        return b;
    }

    private Choice specEvents() {
        final Choice c = new Choice();

        final String[] key = {"CHRZTY", "PIERWSZE_KOMUNIE", "BIERZMOWANIA", "SLUBY", "POGRZEBY", "WIZYTY_DUSZPASTERSKIE", "INTENCJE_MSZALNE"};
        final Tables[] val = {Tables.CHRZTY_SZCZEGOLY, Tables.PIERWSZE_KOMUNIE, Tables.BIERZMOWANIA_SZCZEGOLY, Tables.SLUBY, Tables.POGRZEBY, Tables.WIZYTY_DUSZPASTERSKIE, Tables.INTENCJE_MSZALNE};
        for (int i = 0; i < key.length; i++) {
            c.add(key[i]);
        }

        c.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent arg0) {
                String selected = c.getSelectedItem();
                for (int i = 0; i < key.length; i++) {
                    if (selected == key[i]) {
                        CommonUI.selectAllFrom(val[i], i != 0);
                    }
                }
            }
        });

        return c;
    }

    private Button specified() {
        Button b = CommonUI.universalButton("WYSWIETL WYDARZENIA KONKRETNEGO TYPU");

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Choice c = specEvents();
                c.setVisible(true);
            }
        });

        return b;
    }
}
