package flightManagementSystem.gui;

import flightManagementSystem.data.*;
import flightManagementSystem.logic.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.table.*;

/**
 * This is the GUI for displaying the calculated Data
 * @author landev
 */
public class OutputMask extends JDialog {

    // Instanzvariablen Tabelle
    private DefaultTableModel mTabModel;
    private JTable mWaypointTable;
    // Instanzvariablen Resultat
    private JLabel mSpeedLabel,  mSpeedUnitLabel;
    private JTextField mSpeedField;
    private JLabel mTimeLabel,  mTimeUnitLabel;
    private JTextField mTimeField;
    private JLabel mDistanceLabel,  mDistanceUnitLabel;
    private JTextField mDistanceField;

    private Route myRoute = null;    
    
    /**
     * Constructor, creates the output mask with passed data
     * @param pParent Parent frame for setting the dialog modal
     * @param pData String Array^2 of the data to be calculated
     * @param pSpeed Speed for the calculation
     */
    public OutputMask(JFrame pParent, String[][] pData, String pSpeed) {
        super(pParent, java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("fms_calculatoin"));
        setModal(true);
        setSize(new Dimension(570, 450));
        this.setResizable(false);

        try {
            myRoute = new Route(pData, Double.parseDouble(pSpeed));     
        } catch (Exception e) {
            showMessage(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("error_in_data"));
        }                                //Fenster mit meldung und allfälliger überprüfung und Farbig markieren


        // ContentPane holen und konfigurieren
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        // Einzelne Frames hinzufügen
        contentPane.add(createResultPane(pData, pSpeed));
        contentPane.add(createSpeedPane(pSpeed));
        contentPane.add(createDistancePane(Utils.round(myRoute.getTotalDistance())));
        contentPane.add(createTimePane(Utils.round(myRoute.getTotalTime())));

        // Fenster verschoben zum Hauptfenser platzieren
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x, y;
        x = (screenSize.width / 2) - (getSize().width / 2);
        y = (screenSize.height / 2) - (getSize().height / 2);
        setLocation(x - 100, y - 100);

        setVisible(true);
        addWindowListener(new MyWindowListener());
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }
    /**
     * Shows a simple dialog box 
     * @param message Message to display
     * 
     */
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    } // end method
   
    /**
     * Creates the Table with the resulting route
     * @return Returns a reference of this paned table
     */
    private JScrollPane createResultPane(String[][] pData, String pSpeed) {
        Object[][] data = pData;
        String[] columnTitle = {java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("origin_waypoint"),
                                java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("destination_waypoint"), 
                                java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("heading_[°]"), 
                                java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("distance_[NM]"), 
                                java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("flighttime_[Min]")};
        Calculation.calculate(myRoute);
        mTabModel = new DefaultTableModel(myRoute.legsToRoundedArray(), columnTitle);
        mWaypointTable = new JTable(mTabModel) {
            public boolean isCellEditable(int x, int y) {
                return false;
            }
        };
        mWaypointTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mWaypointTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(mWaypointTable);
        scrollPane.setBounds(10, 10, 545, 300);
        return scrollPane;
    }
    /**
     * Creates a pane with the lable and the textbox for the speed
     * @param pSpeed the speed to display
     * @return Returns a reference of the paned elements
     */
    private JPanel createSpeedPane(String pSpeed) {
        JPanel speedPane = new JPanel();
        speedPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        mSpeedLabel = new JLabel(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("flightspeed"));
        mSpeedUnitLabel = new JLabel(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("[knot]"));
        mSpeedField = new JTextField(pSpeed, 10);
        mSpeedField.setHorizontalAlignment(JTextField.RIGHT);
        mSpeedField.setEditable(false);
        mSpeedField.setBackground(new Color(255, 255, 255));
        speedPane.add(mSpeedLabel);
        speedPane.add(mSpeedField);
        speedPane.add(mSpeedUnitLabel);
        speedPane.setBounds(200, 320, 350, 30);
        return speedPane;
    }
    /**
     * Creates a pane with the lable and the textbox for the distance
     * @param pDistance the distance to display
     * @return Returns a reference of the paned elements
     */
    private JPanel createDistancePane(String pDistance) {
        JPanel distancePane = new JPanel();
        distancePane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        mDistanceLabel = new JLabel(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("journey_distance"));
        mDistanceUnitLabel = new JLabel(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("[NM]"));
        mDistanceField = new JTextField(pDistance, 10);
        mDistanceField.setHorizontalAlignment(JTextField.RIGHT);
        mDistanceField.setEditable(false);
        mDistanceField.setBackground(new Color(255, 255, 255));
        distancePane.add(mDistanceLabel);
        distancePane.add(mDistanceField);
        distancePane.add(mDistanceUnitLabel);
        distancePane.setBounds(278, 350, 250, 30);
        return distancePane;
    }
    /**
     * Creates a pane with the lable and the textbox for the time
     * @param pTime the time to display
     * @return Returns a reference of the paned elements
     */
    private JPanel createTimePane(String pTime) {
        JPanel timePane = new JPanel();
        timePane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        mTimeLabel = new JLabel(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("journey_time"));
        mTimeUnitLabel = new JLabel(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("[Min]"));
        mTimeField = new JTextField(pTime, 10);
        mTimeField.setHorizontalAlignment(JTextField.RIGHT);
        mTimeField.setEditable(false);
        mTimeField.setBackground(new Color(255, 255, 255));
        timePane.add(mTimeLabel);
        timePane.add(mTimeField);
        timePane.add(mTimeUnitLabel);
        timePane.setBounds(280, 380, 250, 30);
        return timePane;
    }
/**
     * Closes the window
     * 
     */
    private void exit() {
        this.dispose();
    }

    class MyWindowListener extends WindowAdapter {

        public void windowClosing(WindowEvent e) {
            exit();
        }
    }
}