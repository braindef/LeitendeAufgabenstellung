// KOMMENTAR ZU DIESER DATEI: WIR LASSEN DIE EXTRA HIER DRINN UM DIE ALTERNATIVE ZUM HYPERLINKLISTENER ZU SEHEN
// zur verwendung dieser datei empfielt es sich die Library Lobo.jar zu laden
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

// Alternativer Browser an Stelle der JEditorPane mit Hyperlink Listener
/*
package FlightManagementSystem.GUI.HelpWindow;

import org.lobobrowser.gui.*;
import org.lobobrowser.main.*;
import javax.swing.*;

public class LoboTestFrame extends JFrame {
	public static void main(String[] args) throws Exception {
		// This optional step initializes logging so only warnings
		// are printed out.
		PlatformInit.getInstance().initLogging(false);

		// This step is necessary for extensions to work:
		PlatformInit.getInstance().init(false, false);

		// Create frame with a specific size.
		JFrame frame = new LoboTestFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setVisible(true);
	}

	public LoboTestFrame() throws Exception {
		FramePanel framePanel = new FramePanel();
		this.getContentPane().add(framePanel);
		framePanel.navigate("./Help/Help.html");
	}
}
*/