/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flightManagementSystem.gui.helpWindow;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * Creates a Dialog that can display simple html files without css ability
 * thanks to romino for discovering the ability of displaing html with JEditorPane
 * alternative there is an openSource browser project called lobo with the ability of css
 * @author landev
 */
public class Help extends JDialog implements HyperlinkListener {

    JEditorPane htmlPane = null;
    
    /**
     * Creates the Dialog 
     * @param pParent Parent for setting it modal
     */
    public Help(JFrame pParent) {
        super(pParent, "Flight Management System - Hilfe");
        setSize(new Dimension(640, 480));
        this.setResizable(false);
        
        java.net.URL site = getClass().getResource("index.html");               //muss so gemacht werden, damit die HTML dateien auch im jar file sind
        try {
            htmlPane = new JEditorPane(site);
        } catch (Exception e) {
            htmlPane = new JEditorPane();
            htmlPane.setText("ERROR, Page not found");
        }
        htmlPane.setEditable(false);
        htmlPane.addHyperlinkListener(this);
        JScrollPane ScrollPane = new JScrollPane(htmlPane);
        ScrollPane.setBounds(0, 0, 634, 448);
        this.setLayout(null);
        this.add(ScrollPane);
        
        // Fenster verschoben zum Hauptfenser platzieren
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x, y;
        x = (screenSize.width / 2) - (getSize().width / 2);
        y = (screenSize.height / 2) - (getSize().height / 2);
        setLocation(x - 100, y - 100);
              
        this.setVisible(true);

    }

  /**
   * For the ability to follow the Hyperlinks
   * @param e Hyperlink event sent by the system, or better by JEditorPane
   */    
  public void hyperlinkUpdate(HyperlinkEvent e) {
    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
      URL target = e.getURL();
      System.out.println(target.toExternalForm());
      try
      {
        htmlPane.setPage(target);
      }
      catch(Exception ex)
      {
        htmlPane.setText("ERROR, Page not found");
      }
    }
  }
  
  /**
   * For testing the Help Dialogs
   * @param args
   */
  public static void main(String[] args) {
         new Help(null);
    }
}
