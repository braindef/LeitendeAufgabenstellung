/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flightManagementSystem.gui.about;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import static java.lang.Math.*;

/**
 * 
 * @author landev
 */
public class AboutMask extends JDialog implements Runnable, WindowListener {

    private Thread thread;
    private BufferedImage bimg;
    MP3 mp3;

    /**
     * Constructor for inizializing the about box
     * @param pParent the Parent class for possibly setting it modal
     */
    public AboutMask(JFrame pParent) {
        //super(pParent, "About");
        //setModal(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x, y;
        x = (screenSize.width / 2);
        y = (screenSize.height / 2);
        setLocation(x, y);

        this.pack();
        this.setSize(new Dimension(450, 220));
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        this.setVisible(true);
        this.addWindowListener(this);

        this.init();

    }

    /**
     *  Inizializes several things
     *  -the sound thread
     *  -the thread for computing the 3D model
     */
    public void init() {
        setBackground(Color.white);
        start = System.currentTimeMillis();

        InputStream stream = this.getClass().getResourceAsStream("Chavez - Ninja bolognese.mp3");   //
        mp3 = new MP3(null);
        mp3.playStream(stream);
        try {
            fgImage = getImage(new URL(this.getClass().getResource("background.jpg").toURI().toString()));
        } catch (Exception ee) {
            System.out.println("Image load error");
        }


    }
    protected static double[][] dmy = Matrix.yTurn(0.03);
    protected static double[][] dmz = Matrix.zTurn(0.02);
    protected static double[][] dmx = Matrix.xTurn(0.025);
    protected static double[] center = Cube.P00;
    public static double scale = 0.7;
    public static double i = 1;
    public double start;
    public int time;
    String[] credits = {"-=[Flightmanagementsystem]=-",
        " ",
        "Diese Applikation wurde konzipiert",
        "und entwickelt von:",
        "",
        "Romino Florio",
        "Pascal Jenni",
        "Marc Landolt",
        "Wir danken herzlich den Leuten",
        "von javazoom für das",
        "Programmieren einer OpenSource",
        "Library für das Abspielen",
        "von MP3's Platform unabhaengig",
        "",
        "Ausserdem möchte ich mich beim",
        "ganzen Team bedanken für die",
        "gute Zusammenarbeit",
        "", "",
        "und natürlich auch bei der Schule",
        "und ihren Dozenten",
        "",
        "Auch möchte ich die Gelegenheit",
        "nutzen und unsere ganze Klasse",
        "48 / 4i grüssen",
        "blablabla", "Könnte hier noch jemand noch", "so viel Text schreiben", "bis der Song fertig ist?"
    };
    Font H1 = new Font("Helvetica", Font.PLAIN, 16);
    Font body = new Font("Helvetica", Font.PLAIN, 12);

    /**
     * Draws the figures to a Graphics2D object which is then displayed 
     * it's not part of this work to exactly explain, but it is sort of art
     * which can be found in a much more glourius manner on (http://www.pouet.net)
     * @param pWidth Width of the object
     * @param pHeight Height of the object
     * @param g2 the Graphics2D object to draw to
     */
    public void drawDemo(int pWidth, int pHeight, Graphics2D g2) {
        addAlphaImage(g2);
        
        scale = 1 + sin(PI / 2 + (double) (System.currentTimeMillis() - start) / 271 * 2) * 0.5;   //275
        g2.setColor(new Color(190, 190, 30));
        int nx = 40;
        int ny = 20;
        Dimension d = getSize();
        int rx = d.width / nx;
        int ry = d.height / ny;

        for (int x = 0; x < nx; x++) {
            for (int y = 0; y < ny; y++) {
                System.currentTimeMillis();
                g2.setColor(new Color(255, 255, (int) (127 * (cos((x + y)) + 1))));
                g2.drawOval(x * rx, 20 + y * ry,
                        5 + (int) (rx * sin((x - nx / 2) + (y - ny / 2) * System.currentTimeMillis() / 1000.0)),
                        5 + (int) (ry * sin((x - nx / 2) + (y - ny / 2) * System.currentTimeMillis() / 1000.0)));

            }
        }
        g2.translate(110, 120);
        Cube.points = Matrix.mul(Cube.points, dmy);
        Cube.points = Matrix.mul(Cube.points, dmz);
        Cube.points = Matrix.mul(Cube.points, dmz);
        Cube.points = Matrix.mul(Cube.points, dmx);
        double[][] punkte = Matrix.scalarMul(Cube.points, 1 * cos(i * 0.04));

        for (int l = 0; l < Cube.W.length; l++) {
            Cube.W[l] = Matrix.mul(Cube.W[l], dmy);
            Cube.W[l] = Matrix.mul(Cube.W[l], dmz);
            Cube.W[l] = Matrix.mul(Cube.W[l], dmx);
            g2.setColor(Color.cyan);
            g2.drawLine((int) ((float) (center[0] + Cube.W[l][0][0] + Cube.W[l][0][2] * 0.1)),
                    (int) ((float) (center[1] + Cube.W[l][0][1] + Cube.W[l][0][2] * 0.1)),
                    (int) ((float) (center[0] + Cube.W[l][1][0] + Cube.W[l][1][2] * 0.1)),
                    (int) ((float) (center[1] + Cube.W[l][1][1] + Cube.W[l][1][2] * 0.1)));
            g2.setColor(Color.blue);
            g2.drawLine((int) ((float) (center[0] + 0.98 * Cube.W[l][0][0] + Cube.W[l][0][2] * 0.1)),
                    (int) ((float) (center[1] + 0.98 * Cube.W[l][0][1] + Cube.W[l][0][2] * 0.1)),
                    (int) ((float) (center[0] + 0.98 * Cube.W[l][1][0] + Cube.W[l][1][2] * 0.1)),
                    (int) ((float) (center[1] + 0.98 * Cube.W[l][1][1] + Cube.W[l][1][2] * 0.1)));
            g2.setColor(Color.ORANGE);
            g2.drawLine((int) ((float) (center[0] + scale * Cube.W[l][0][0] + scale * Cube.W[l][0][2] * 0.1)),
                    (int) ((float) (center[1] + scale * Cube.W[l][0][1] + scale * Cube.W[l][0][2] * 0.1)),
                    (int) ((float) (center[0] + scale * Cube.W[l][1][0] + scale * Cube.W[l][1][2] * 0.1)),
                    (int) ((float) (center[1] + scale * Cube.W[l][1][1] + scale * Cube.W[l][1][2] * 0.1)));
            g2.setColor(Color.red);
            g2.drawLine((int) ((float) (center[0] + 0.97 * scale * Cube.W[l][0][0] + scale * Cube.W[l][0][2] * 0.1)),
                    (int) ((float) (center[1] + 0.97 * scale * Cube.W[l][0][1] + scale * Cube.W[l][0][2] * 0.1)),
                    (int) ((float) (center[0] + 0.97 * scale * Cube.W[l][1][0] + scale * Cube.W[l][1][2] * 0.1)),
                    (int) ((float) (center[1] + 0.97 * scale * Cube.W[l][1][1] + scale * Cube.W[l][1][2] * 0.1)));
        }



        time = (int) (System.currentTimeMillis() - start);
        g2.setColor(Color.BLACK);
        g2.setFont(body);
        for (int i = 0; i < credits.length; i++) {
            g2.drawString(credits[i], (float) (130 + 10 * sin(i / 3.0 + time / 300.0)), (10 + i * 16 - (time / 30)) % (550 / 29 * credits.length) + 100 + (float) (sin(time / 100.0) * 5));

        }
        
    }

    /**
     * Creates an antialized Graphics2D object to draw to
     * @param pWidth Width of this object
     * @param pHeight Height of this object
     * @return the Grapics2D object to draw to
     */
    public Graphics2D createGraphics2D(int pWidth, int pHeight) {
        Graphics2D g2 = null;
        if (bimg == null || bimg.getWidth() != pWidth || bimg.getHeight() != pHeight) {
            bimg = (BufferedImage) createImage(pWidth, pHeight);

        }
        g2 = bimg.createGraphics();
        g2.setBackground(getBackground());
        g2.clearRect(0, 0, pWidth, pHeight);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        return g2;
    }

    /**
     * Is started when repaint() is called
     * @param g The Graphics object given from the system to draw to
     */
    public void paint(Graphics g) {
        Dimension d = getSize();
        //step(d.width, d.height);
        Graphics2D g2 = createGraphics2D(d.width, d.height);
        drawDemo(d.width, d.height, g2);
        //g2.dispose();
        g.drawImage(bimg, 0, 0, this);
    }

    /**
     * Starts the thread for calculating the model
     */
    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    /**
     * kills the thread
     */
    public synchronized void stop() {
        thread = null;
    }

    /**
     * Thread that calls repaint()
     */
    public void run() {
        Thread me = Thread.currentThread();
        while (thread == me) {
            repaint();
            try {
                thread.sleep(10);
            } catch (InterruptedException e) {
                break;
            }
        }
        thread = null;
    }

    /**
     * For testing the About Box
     * @param argv commandline arguments...
     */
    public static void main(String argv[]) {
        final AboutMask demo = new AboutMask(null);

    }

    public void windowOpened(WindowEvent e) {
        this.start();
    }

    public void windowClosing(WindowEvent e) {
        this.stop();
        mp3.close();
        this.dispose();
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
        this.stop();
    }

    public void windowDeiconified(WindowEvent e) {
        this.start();
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }
    Image fgImage = null;

    public void addAlphaImage(Graphics2D g2d) {

        // if a foreground image exists, paint it
        if (fgImage != null) {
            int imageW = fgImage.getWidth(null);
            int imageH = fgImage.getHeight(null);

            Graphics g = this.getGraphics();
            // we need to cast to Graphics2D for this operation
            //Graphics2D g2d = (Graphics2D)g;

            // create the composite to use for the translucency
            AlphaComposite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);

            // save the old composite
            Composite oldComp = g2d.getComposite();

            // set the translucent composite
            g2d.setComposite(comp);

            // calculate the x and y positions to paint at
            //int xloc = getComponent().getWidth() - imageW - 5;
            //int yloc = getComponent().getHeight() - imageH - 5;

            // paint the image using the new composite
            g2d.drawImage(fgImage, 0, 0, null);
            g2d.drawImage(fgImage, fgImage.getWidth(null), 0, null);
            
            // restore the original composite
            g2d.setComposite(oldComp);

        }
    }

    protected static Image getImage(URL imageURL) {
        Image image = null;

        try {
            // use ImageIO to read in the image
            image = ImageIO.read(imageURL);
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }

        return image;
    }
}
