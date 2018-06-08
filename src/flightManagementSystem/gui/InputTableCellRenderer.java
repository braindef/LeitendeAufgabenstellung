/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flightManagementSystem.gui;

import flightManagementSystem.data.Waypoint;
import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

/**
 * TableCellRenderer for having nice colors in the table if there is wrong data
 * @author landev
 */
public class InputTableCellRenderer implements TableCellRenderer {

    public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {
        JLabel label = new JLabel((String) value);
        label.setOpaque(true);
        Border b = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        label.setBorder(b);
        label.setFont(table.getFont());
        label.setForeground(Color.black);
        label.setBackground(Color.white);
        if (hasFocus) {
            try {
                label.setBackground(new Color(128, 168, 208)); // hellblau-markierung
                label.setForeground(Color.white);
                if (column == 3 && !Waypoint.testLongitude(value.toString())) {
                    label.setBackground(new Color(255, 151, 151)); // rot-markierung
                    label.setForeground(Color.white);
                }
                if (column == 2 && !Waypoint.testLatitude(value.toString())) {
                    label.setBackground(new Color(255, 151, 151)); // rot-markierung
                    label.setForeground(Color.white);
                }
                if (column == 1 && value.toString().length() > 5) {
                label.setBackground(new Color(255, 151, 151)); // rot-markierung
                label.setForeground(Color.white);
                }   
            } catch (Exception e) {
                System.out.println("error while trying to color the cell with funny colors");
            }
        } else if (isSelected) {
            try {
                label.setBackground(new Color(184, 207, 229)); // hellblau
                label.setForeground(Color.black);
                if (column == 3 && !Waypoint.testLongitude(value.toString())) {
                    label.setBackground(new Color(255, 179, 179)); // rot
                    label.setForeground(Color.black);
                }
                if (column == 2 && !Waypoint.testLatitude(value.toString())) {
                    label.setBackground(new Color(255, 179, 179)); // rot
                    label.setForeground(Color.black);
                }
            } catch (Exception e) {
                System.out.println("error while trying to color the selected cell with funny colors");
            }
        } else if (column == 3 || column == 2) {
            try {
                if (column == 3 && !Waypoint.testLongitude(value.toString())) {
                    label.setBackground(new Color(255, 179, 179));
                }
                if (column == 2 && !Waypoint.testLatitude(value.toString())) {
                    label.setBackground(new Color(255, 179, 179));
                }
            } catch (Exception e) {
                System.out.println("error while trying to color the generel cell with funny colors");
            }
        } else if (column == 1) {
            if (value.toString().length() > 5) {
                label.setBackground(new Color(255, 179, 179));
            }
        }
        return label;
    }
}
