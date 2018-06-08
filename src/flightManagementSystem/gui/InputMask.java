package flightManagementSystem.gui;

import flightManagementSystem.data.Route;
import flightManagementSystem.data.Waypoint;
import flightManagementSystem.gui.about.AboutMask;
import flightManagementSystem.gui.helpWindow.Help;
import flightManagementSystem.logic.Calculation;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

/**
 * This class is the input GUI for entering the Data
 * @author  Filu
 */
public class InputMask extends JFrame {

    
    
    boolean TAL = true;  //Semaphore to prevent interferences between ButtonActionListener and TableActionListener

    // Instanzvariablen MenuBar
    private JMenuBar mMenuBar;
    private JMenu mFileMenu;
    private JMenuItem mNewFileMenuItem,  mOpenFileMenuItem,  mSaveFileMenuItem,  mXmlExportMenuItem,  mXmlImportMenuItem,  mExitMenuItem;
    private JMenu mHelpMenu;
    private JMenuItem mHandlingMenuItem,  mAboutMenuItem;
    private JMenu mOptionMenu;
    private JMenuItem mGermanMenuItem,  mEnglishMenuItem;
    // Instanzvariablen Fluggeschwindigkeit
    private JSpinner mSpeedField;
    private JLabel mTextLabel,  mUnitLabel;
    // Instanzvariablen Tabelle
    private DefaultTableModel mTabModel;
    private JTable mWaypointTable;
    // Instanzvariablen Berechnung
    private JButton mCalcButton;
    // Instanzvariablen Waypoints
    private JButton mAddButton;
    private JButton mDelButton;
    // Instanzvariablen Waypoints
    private JButton mUpButton;
    private JButton mDownButton;
    private static Locale sLocale = Locale.GERMAN;

    /**
     * Constructor for creating the GUI
     */
    public InputMask() {
        super("Flight_Management_System");
        setSize(new Dimension(570, 400));
        this.setResizable(false);

        // ContentPane holen und konfigurieren
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        
        // Einzelne Frames hinzufügen
        contentPane.add(createMenuBar());
        contentPane.add(createSpeedPane());
        contentPane.add(createWaypointPane());
        contentPane.add(createAddDelPane());
        contentPane.add(createMovePane());
        contentPane.add(createCalcPane());

        // Fenster in der Mitte des Bildschirmes platzieren
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x, y;
        x = (screenSize.width / 2) - (getSize().width / 2);
        y = (screenSize.height / 2) - (getSize().height / 2);
        setLocation(x, y);

        setVisible(true);
        addWindowListener(new MyWindowListener());
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    /**
     * Retruns a reference of this class for the subclasses
     * @return Returns a reference of this class
     */
    public InputMask getParentClass() {
        return this;
    }

    /**
     * Creates the Table with some initial Waypoints
     * @return Returns a reference of the paned table
     */
    private JScrollPane createWaypointPane() {
        Object[][] data = {
            {"Zürich-Kloten", "LSZH", "N47275", "E008329"},
            {"", "VEBIT", "N47161", "E008004"},
            {"", "ROTOS", "N47114", "E007435"},
            {"", "ULMES", "N46573", "E004176"},
            {"Genf", "LSGG", "N46143", "E006066"}
        };
        String[] columnTitle = {java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("airport"),
            java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("waypoint"),
            java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("latitude"),
            java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("latitude") };
        mTabModel = new DefaultTableModel(data, columnTitle);
        mWaypointTable = new JTable(mTabModel);
        mWaypointTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mWaypointTable.getTableHeader().setReorderingAllowed(false);
        mWaypointTable.getModel().addTableModelListener(new TableActionListener());
        mWaypointTable.setDefaultRenderer(Object.class, new InputTableCellRenderer());
        JScrollPane scrollPane = new JScrollPane(mWaypointTable);
        scrollPane.setBounds(10, 60, 500, 270);
        return scrollPane;
    }

    
    /**
     * Creates the pane for the speed selection
     * @return Returns a reference of this pane
     */
    private JPanel createSpeedPane() {
        JPanel speedPane = new JPanel();
        speedPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        mTextLabel = new JLabel(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("flightspeed"));
        mUnitLabel = new JLabel(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("[knot]"));
        mSpeedField = new JSpinner(new SpinnerNumberModel(500, 1, 10000, 1));   // von 1 - 1000 in 1er Schritten, Startwert 1
        speedPane.add(mTextLabel);
        speedPane.add(mSpeedField);
        speedPane.add(mUnitLabel);
        speedPane.setBounds(10, 25, 505, 30);
        return speedPane;
    }

    /**
     * Creates a the calculation button
     * @return Returns a reference of this paned button
     */
    private JPanel createCalcPane() {
        JPanel calcPane = new JPanel();
        calcPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        mCalcButton = new JButton(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("calculate"), new javax.swing.ImageIcon(getClass().getResource("images/calculator.png")));
        mCalcButton.addActionListener(new ButtonActionListener());
        calcPane.add(mCalcButton);
        calcPane.setBounds(10, 330, 505, 35);
        return calcPane;
    }

    /**
     * Creates the add / remove buttons
     * @return Returns a reference of this paned buttons
     */
    private JPanel createAddDelPane() {
        JPanel addDelPane = new JPanel();
        addDelPane.setLayout(new GridLayout(2, 1));
        mAddButton = new JButton("", new javax.swing.ImageIcon(getClass().getResource("images/add.png")));
        mAddButton.addActionListener(new ButtonActionListener());
        mAddButton.setToolTipText(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("add_waypoint"));
        mDelButton = new JButton("", new javax.swing.ImageIcon(getClass().getResource("images/delete.png")));
        mDelButton.setToolTipText(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("delete_selected_waypoint"));
        mDelButton.addActionListener(new ButtonActionListener());
        addDelPane.add(mAddButton);
        addDelPane.add(mDelButton);
        addDelPane.setBounds(520, 60, 30, 60);
        return addDelPane;
    }

    /**
     * Creates the move buttons
     * @return Returns a reference of this paned buttons
     */
    private JPanel createMovePane() {
        JPanel addDelPane = new JPanel();
        addDelPane.setLayout(new GridLayout(2, 1));
        mUpButton = new JButton("", new javax.swing.ImageIcon(getClass().getResource("images/arrow_up.png")));
        mUpButton.setToolTipText(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("move_waypoint_upwards"));
        mUpButton.addActionListener(new ButtonActionListener());
        mDownButton = new JButton("", new javax.swing.ImageIcon(getClass().getResource("images/arrow_down.png")));
        mDownButton.setToolTipText(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("move_waypoint_downwards"));
        mDownButton.addActionListener(new ButtonActionListener());
        addDelPane.add(mUpButton);
        addDelPane.add(mDownButton);
        addDelPane.setBounds(520, 130, 30, 60);
        return addDelPane;
    }

    /**
     * Creates the menubar
     * @return Returns a reference of this menubar
     */
    private JMenuBar createMenuBar() {
        mMenuBar = new JMenuBar();
        //// Menu Datei
        mFileMenu = new JMenu(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("file"));
        // Neu
        mNewFileMenuItem = new JMenuItem(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("new..."), new javax.swing.ImageIcon(getClass().getResource("images/page_white.png")));
        mNewFileMenuItem.setToolTipText(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("create_an_empty_route"));
        mFileMenu.add(mNewFileMenuItem);
        mNewFileMenuItem.addActionListener(new MenuBarActionListener());
        // Seperator
        mFileMenu.addSeparator();
        // Öffnen
        mOpenFileMenuItem = new JMenuItem(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("open..."), new javax.swing.ImageIcon(getClass().getResource("images/folder.png")));
        mOpenFileMenuItem.setToolTipText(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("open_an_allready_existing_route"));
        mFileMenu.add(mOpenFileMenuItem);
        mOpenFileMenuItem.addActionListener(new MenuBarActionListener());
        // Speichern
        mSaveFileMenuItem = new JMenuItem(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("save"), new javax.swing.ImageIcon(getClass().getResource("images/disk.png")));
        mSaveFileMenuItem.setToolTipText(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("save_current_route"));
        mFileMenu.add(mSaveFileMenuItem);
        mSaveFileMenuItem.addActionListener(new MenuBarActionListener());
        // Seperator
        mFileMenu.addSeparator();
        // XML-Export
        mXmlExportMenuItem = new JMenuItem(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("XML-Export"), new javax.swing.ImageIcon(getClass().getResource("images/disk.png")));
        mXmlExportMenuItem.setToolTipText(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("save_current_route_to_xml"));
        mFileMenu.add(mXmlExportMenuItem);
        mXmlExportMenuItem.addActionListener(new MenuBarActionListener());
        // XML-Import
        mXmlImportMenuItem = new JMenuItem(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("XML-Import"), new javax.swing.ImageIcon(getClass().getResource("images/disk.png")));
        mXmlImportMenuItem.setToolTipText(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("import_route_from_xml"));
        mFileMenu.add(mXmlImportMenuItem);
        mXmlImportMenuItem.addActionListener(new MenuBarActionListener());
        // Seperator
        mFileMenu.addSeparator();
        // Beenden
        mExitMenuItem = new JMenuItem(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("exit"), new javax.swing.ImageIcon(getClass().getResource("images/door_out.png")));
        mExitMenuItem.setToolTipText(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("exit_program"));
        mFileMenu.add(mExitMenuItem);
        mExitMenuItem.addActionListener(new MenuBarActionListener());
        // Menu hinzufÃ¼gen
        mMenuBar.add(mFileMenu);

        // Menu Optionen
        mOptionMenu = new JMenu(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("options"));
        mGermanMenuItem = new JMenuItem(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("german"), new javax.swing.ImageIcon(getClass().getResource("images/door_out.png")));
        mGermanMenuItem.setToolTipText(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("select_german_language"));
        mGermanMenuItem.addActionListener(new MenuBarActionListener());
        mOptionMenu.add(mGermanMenuItem);
        mEnglishMenuItem = new JMenuItem(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("english"), new javax.swing.ImageIcon(getClass().getResource("images/door_out.png")));
        mEnglishMenuItem.setToolTipText(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("select_english_language"));
        mEnglishMenuItem.addActionListener(new MenuBarActionListener());
        mOptionMenu.add(mEnglishMenuItem);
        mMenuBar.add(mOptionMenu);

        //// Menu Hilfe
        mHelpMenu = new JMenu(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("help"));
        // Bedienung
        mHandlingMenuItem = new JMenuItem(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("handling"), new javax.swing.ImageIcon(getClass().getResource("images/help.png")));
        mHandlingMenuItem.setToolTipText(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("information_about_handling"));
        mHelpMenu.add(mHandlingMenuItem);
        mHandlingMenuItem.addActionListener(new MenuBarActionListener());
        // Über uns
        mAboutMenuItem = new JMenuItem(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("about_us"), new javax.swing.ImageIcon(getClass().getResource("images/information.png")));
        mAboutMenuItem.setToolTipText(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("information_about_the_developers"));
        mHelpMenu.add(mAboutMenuItem);
        mAboutMenuItem.addActionListener(new MenuBarActionListener());
        // Menu hinzufügen
        mMenuBar.add(mHelpMenu);

        mMenuBar.setBounds(0, 0, 570, 20);
        return mMenuBar;
    }

    /**
     * Exits the program
     * 
     */
    private void exit() {
        System.exit(0);
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
     * Shows a selection dialog box 
     * @param message Message to display
     * @return returns true if the yes butten was pressed else false is returned
     */
    private int showConfirm(String message) {
        return (JOptionPane.showConfirmDialog(this, message, null, JOptionPane.YES_NO_OPTION));
    } // end method        

    /**
     * Creates a new file, with an empty waypoint table
     */
    public void newFile() {
        if (showConfirm(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("all_data_not_saved_will_be_lost,_continue?")) == 0) {
            int i = mWaypointTable.getRowCount() - 1;
            while (i > -1) {
                mTabModel.removeRow(i);
                --i;
            }
            Object[] tmp = {"", "", "", ""};
            mTabModel.addRow(tmp);
            mTabModel.addRow(tmp);
            mSpeedField.setModel(new SpinnerNumberModel(500, 1, 10000, 1));
        }
    }

    /**
     * Opens a filedialog to load a waypoint file
     * the file should be in the format: Airport:Waypointname:latitude:longitude
     * examplefile can be found in the project tree
     */
    public void openFile() {
        JComponent.setDefaultLocale(sLocale);
        //in separate methode da sonst unübersichtlich
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(getParentClass());
        if (returnVal != 0) {
            return;
        //java.io.FileReader fr = null;
        }
        java.io.InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(new FileInputStream(chooser.getSelectedFile()), "UTF-8");
        //fr = new java.io.FileReader(chooser.getSelectedFile());
        } catch (Exception ex) {
            System.out.println("File not found");
        }

        java.io.BufferedReader buf = new java.io.BufferedReader(isr);
        java.util.ArrayList<String[]> list = new java.util.ArrayList();

        try {
            while (true) {
                list.add(buf.readLine().split(":"));
            }
        } catch (Exception buferException) {
            System.out.println("EOF");
        }

        String[][] data = new String[list.size()][4];

        int lenght = mTabModel.getRowCount();
        for (int i = lenght; i > 0; i--) {
            mTabModel.removeRow(i - 1);
        }
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i)[0];
            data[i][1] = list.get(i)[1];
            data[i][2] = list.get(i)[2];
            data[i][3] = list.get(i)[3];
            mTabModel.addRow(data[i]);
        }
    }

    /**
     * Opens a filedialog to save a waypoint file
     * @see FlightManagementSystem.GUI.InputMask.openFile
     */
    public void saveFile() {
        JComponent.setDefaultLocale(sLocale);
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showSaveDialog(getParentClass());
        if (returnVal != 0) {
            return;
        //FileWriter fw;
        }
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(chooser.getSelectedFile());
            osw = new OutputStreamWriter(fos, "UTF-8");                 // for having german "umlaute" correct interpreted
            //fw = new FileWriter(chooser.getSelectedFile());

            for (int i = 0; i < mTabModel.getRowCount(); i++) {
                osw.write(mTabModel.getValueAt(i, 0) + ":" + mTabModel.getValueAt(i, 1) + ":" + mTabModel.getValueAt(i, 2) + ":" + mTabModel.getValueAt(i, 3) + "\n");
            }
            osw.close();
            fos.close();

        } catch (Exception ee) {
            System.out.println("Write ERROR");
        }
    }

    /**
     * Opens a filedialog to save an XML file, it will contain the of the waypoints
     * but also the calculated route
     */
    public void xmlExport() {
        JComponent.setDefaultLocale(sLocale);
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showSaveDialog(getParentClass());
        if (returnVal != 0) {
            return;
        }
        String[][] data = new String[mTabModel.getRowCount()][4];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < 4; j++) {
                data[i][j] = mTabModel.getValueAt(i, j).toString();
            }
        }
        Route route = null;
        try {
            route = new Route(data, Integer.parseInt(mSpeedField.getValue().toString()));
            route.setSpeed(Integer.parseInt(mSpeedField.getValue().toString()));

        } catch (Exception ee) {
            System.out.println("XML-Export error");
            System.out.println(ee.getMessage());
            ee.printStackTrace();
        }
        try {
            new Calculation().calculate(route);
        } catch (Exception ee) {
            showMessage(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("error_in_data"));
        }
        try {
            JAXBContext context = JAXBContext.newInstance(route.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", true);
            FileWriter fw = new FileWriter(chooser.getSelectedFile());
            marshaller.marshal(route, fw);      //Close nicht vergessen
            fw.close();
        } catch (Exception ee) {
            showMessage(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("XML-Export_error_(Marshaller)"));
        }
    }

    /**
     * Opens a filedialog to load existing XML data
     */
    public void xmlImport() {
        Route routeObject = null;
        JComponent.setDefaultLocale(sLocale);
        JFileChooser chooser = new JFileChooser();

        int returnVal = chooser.showOpenDialog(getParentClass());
        if (returnVal != 0) {
            return;
        }
        try {
            JAXBContext context = JAXBContext.newInstance(Route.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            //unmarshaller.setSchema(null);   //No Schema

            XMLStreamReader xmlReader = XMLInputFactory.newInstance().createXMLStreamReader(new FileReader(chooser.getSelectedFile()));

            routeObject = unmarshaller.unmarshal(xmlReader, Route.class).getValue();

        } catch (javax.xml.bind.PropertyException propEx) {
            System.out.println("javax.xml.bind.PropertyException caught: " + propEx.getMessage());
            propEx.printStackTrace();
        } catch (javax.xml.bind.JAXBException jaxbEx) {
            System.out.println("javax.xml.bind.JAXBException caught: " + jaxbEx.getMessage());
            jaxbEx.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Exception caugh: " + ex.getMessage());
            ex.printStackTrace();
        }

        if (showConfirm(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("all_data_not_saved_will_be_lost,_continue?")) == 0) {
            int i = mWaypointTable.getRowCount() - 1;
            while (i > -1) {
                mTabModel.removeRow(i);
                --i;
            }
        }
        String[] row = new String[4];
        for (int j = 0; j < routeObject.size(); j++) {
            row[0] = routeObject.getWaypoint(j).getAdditionalInformation();
            row[1] = routeObject.getWaypoint(j).getName();
            row[2] = routeObject.getWaypoint(j).getLatitude();
            row[3] = routeObject.getWaypoint(j).getLongitude();
            mTabModel.addRow(row);
        }
    }

    /**
     * This class is the Listener for menu bar actions
     * @author  Filu
     */
    class MenuBarActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            TAL = false;                                                          //Semaphore because the table action listener
            JMenuItem menuItem = (JMenuItem) e.getSource();

            if (menuItem == mExitMenuItem) {
                if (showConfirm(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("all_data_not_saved_will_be_lost,_continue?")) == 0) {
                    exit();
                }
            } else if (menuItem == mNewFileMenuItem) {
                newFile();
            } else if (menuItem == mAboutMenuItem) {
                AboutMask about = new AboutMask(getParentClass());
            } else if (menuItem == mHandlingMenuItem) {
                Help help = new Help(getParentClass());

            } else if (menuItem == mOpenFileMenuItem) {
                openFile();
            } else if (menuItem == mSaveFileMenuItem) {
                saveFile();
            } else if (menuItem == mXmlExportMenuItem) {
                xmlExport();
            } else if (menuItem == mXmlImportMenuItem) {
                xmlImport();
            } else if (menuItem == mGermanMenuItem) {
                sLocale = Locale.GERMAN;
                Locale.setDefault(Locale.GERMAN);
                getParentClass().dispose();
                new InputMask();                                                //we create a complete new GUI bebause otherwise we must go through all labels, textboxes and so on to change the content, so we set the locale and build a new one
            } else if (menuItem == mEnglishMenuItem) {                          //it could possibly also done with a propertyChangeListener, that changes the values for each element but we decided to go the simple way
                sLocale = Locale.US;
                Locale.setDefault(Locale.US);
                getParentClass().dispose();
                new InputMask();
            }
            TAL = true;                                                         //we reactivate the tableActionListener again
        }
    }

    /**
     * This class is the Listener for the button actions
     * @author  Filu
     */
    class ButtonActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            JButton button = (JButton) e.getSource();
            TAL =
                    false;                                                      //Semaphor for deactivating the TableActionListener while we are changing things form the outside, such as load...
            if (button == mAddButton) {
                Object[] tmp = {"", "",
                    "", ""
                };
                if (mWaypointTable.getSelectedRow() !=
                        - 1) {  // falls ausgewählt, neue Zeile oberhalb Markierung einfügen
                    mTabModel.insertRow(mWaypointTable.getSelectedRow(), tmp);
                } else { // Zeile am Ende anfügen
                    mTabModel.addRow(tmp);
                }
            } else if (button == mDelButton) {
                if (mWaypointTable.getSelectedRow() != -1) {
                    mTabModel.removeRow(mWaypointTable.getSelectedRow());
                } else {
                    showMessage(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("mark_a_row_first!"));
                }
            } else if (button == mUpButton) {
                if (mWaypointTable.getSelectedRow() > 0) {
                    mTabModel.moveRow(mWaypointTable.getSelectedRow(), mWaypointTable.getSelectedRow(), mWaypointTable.getSelectedRow() - 1);
                    mWaypointTable.setRowSelectionInterval(mWaypointTable.getSelectedRow() - 1, mWaypointTable.getSelectedRow() - 1);  // Auswahl soll sich auch verschieben
                    mWaypointTable.repaint();
                } else if (mWaypointTable.getSelectedRow() == 0) {
                    showMessage(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("waypoint_is_at_first_position!"));
                } else {
                    showMessage(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("mark_a_row_first!"));
                }
            } else if (button == mDownButton) {
                if (mWaypointTable.getSelectedRow() > - 1 && mWaypointTable.getSelectedRow() != mWaypointTable.getRowCount() - 1) {
                    mTabModel.moveRow(mWaypointTable.getSelectedRow(), mWaypointTable.getSelectedRow(), mWaypointTable.getSelectedRow() + 1);
                    mWaypointTable.setRowSelectionInterval(mWaypointTable.getSelectedRow() + 1, mWaypointTable.getSelectedRow() + 1);  // Auswahl soll sich auch verschieben
                } else if (mWaypointTable.getSelectedRow() == mWaypointTable.getRowCount() - 1) {
                    showMessage(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("waypoint_is_at_last_position!"));
                } else {
                    showMessage(java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("mark_a_row_first!"));
                }
            } else if (button == mCalcButton) {
                String[][] data = new String[mTabModel.getRowCount()][4];
                try {
                    for (int i = 0;
                            i < data.length; i++) {
                        for (int j = 0; j <
                                4; j++) {
                            data[i][j] = mTabModel.getValueAt(i, j).toString();
                        }
                    }
                    OutputMask calc = new OutputMask(getParentClass(), data, mSpeedField.getValue().toString());
                } catch (Exception ee) {
                    System.out.println("could not create output mask");
                }
            }
            TAL =
                    true;                                                         //reactivate the TableActionListener
        }
    }

    /**
     * This class is the Listener for the table, it must be deactivated
     * during external manimpuation of the table, this is done by the boolean
     * variable TAL
     * @author  Filu
     */
    public class TableActionListener implements TableModelListener {

        public void tableChanged(TableModelEvent e) {

            if (!TAL) {                                                         //if the table is modifyed by the loading or Xml importing the tableActionListener shall not be affected
                return;
            }

            try {
                if (e.getColumn() == 1) {
                    // Waypoint-Name immer in GROSSBUCHSTABEN
                    TAL = false;
                    Object upperData = mWaypointTable.getValueAt(e.getFirstRow(), e.getColumn()).toString().toUpperCase();
                    mWaypointTable.setValueAt(upperData, e.getFirstRow(), e.getColumn());
                    TAL = true;
                }
            } catch (Exception ee) {
                System.out.println("error writing waypoint name");
            }

            try {
                if (e.getColumn() ==
                        2) {
                    // Breitengrad prüfen 
                    if (!Waypoint.testLatitude(mWaypointTable.getValueAt(e.getFirstRow(), e.getColumn()).toString())) {
                        showMessage(mWaypointTable.getValueAt(e.getFirstRow(), e.getColumn()).toString() + " " + java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("_is_no_correct_value,_Example_N47275"));
                    }
                }
            } catch (Exception ee) {
                System.out.println("error in latitude");
            }
            try {
                if (e.getColumn() ==
                        3) {
                    // Längengrad prüfen                     
                    if (!Waypoint.testLongitude(mWaypointTable.getValueAt(e.getFirstRow(), e.getColumn()).toString())) {
                        showMessage(mWaypointTable.getValueAt(e.getFirstRow(), e.getColumn()).toString() + " " + java.util.ResourceBundle.getBundle("flightManagementSystem/gui/Bundle").getString("_is_no_correct_value,_Example_E008329"));
                    }
                }
            } catch (Exception ee) {
                System.out.println("error in longitude");
            }
        }
    }

    class MyWindowListener extends WindowAdapter {

        public void windowClosing(WindowEvent e) {
            exit();
        }
    }

}


