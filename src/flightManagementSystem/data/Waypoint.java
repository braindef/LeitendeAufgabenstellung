package flightManagementSystem.data;

/**
 *  @author landev
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.B681BC36-30DF-8047-FBAB-0825EDE238B5]
// </editor-fold> 
public class Waypoint {

    private String mAdditionalInformation;
    private String mName;
    private String mLatitude;        // Breitengrad;
    private String mLongitude;       // Laengengrad;

    /**
     * non-arg constructor for serialisation
     * @throws java.lang.Exception
     */
    public Waypoint() throws java.lang.Exception
    {
        this(new String[]{"Aarau", "keiner", "N47235", "E008025"});
    }

    /**
     * Constructor for creating waypoints with a given string array^1
     * @param data the string array containing the waypoint data
     * @throws java.lang.Exception
     */
    public Waypoint(String[] data) throws java.lang.Exception {

        this.mAdditionalInformation = data[0];
        this.mName = data[1];
        if (!setLatitude(data[2])) {
            throw new java.lang.Exception();
        }
        if (!setLongitude(data[3])) {
            throw new java.lang.Exception();
        }
        //System.out.println(this.mName + " " + this.mAdditionalInformation + " " + this.mLatitude + " " + this.mLongitude);

    }

    /**
     * Getter for Latitude
     * @return a string of the latitude
     */
    public String getLatitude() {
        return mLatitude;
    }

    /**
     * Tests the format of the Latitude for several purposes, we designed it
     * that way because need it several times
     * @param pLatitude The latitude to test
     * @return returns true if the latitude is correct
     */
    public static boolean testLatitude(String pLatitude) {
        if (pLatitude.length() != 6) {
            return false;
        }
        if (pLatitude.charAt(1) == '-') {
            return false;
        }
        if (pLatitude.toUpperCase().charAt(0) != 'N' && pLatitude.toUpperCase().charAt(0) != 'S') {
            System.out.println("First String False: " + pLatitude);
            return false;
        }
        try {
            if (((Double.parseDouble(pLatitude.substring(1, 3)) //Grade
                    + Double.parseDouble(pLatitude.substring(3, 6)) / 600) //Minuten IB Grad
                    > 90.0)) {
                return false;
            }           //Kleiner 90°

        } catch (NumberFormatException ne) //Negativer grad
        {
            System.out.println("NumberFormatException");
            return false;
        } catch (IndexOutOfBoundsException ioe) {
            System.out.println("outOfBoundsException");
            return false;
        }
        return true;
    }

    /**
     * Setter for the Latitude
     * @param pLatitude The latitude to set
     * @return returns true if the latitude could have been set
     */
    public boolean setLatitude(String pLatitude) {

        if (testLatitude(pLatitude)) {
            this.mLatitude = pLatitude.toUpperCase();                                 // Wir arbeiten mit Grossbuchstaben
            return true;
        }
        return false;

    }

    /**
     * Getter for the longitude
     * @return returns a string of the longitude
     */
    public String getLongitude() {
        return mLongitude;
    }


    /**
     * Tests the format of the Longitude for several purposes, we designed it
     * that way because need it several times
     * @param pLongitude The longitude to test
     * @return returns true if the longitude is correct
     */
    public static boolean testLongitude(String pLongitude) {
        if (pLongitude.length() != 7) {
            return false;
        }
        if (pLongitude.charAt(1) == '-') {
            return false;
        }
        if (pLongitude.toUpperCase().charAt(0) != 'E' && pLongitude.toUpperCase().charAt(0) != 'W') {
            return false;
        }
        try {
            if (((Double.parseDouble(pLongitude.substring(1, 4)) //Grade
                    + Double.parseDouble(pLongitude.substring(4, 7)) / 600) //Minuten in GraD
                    > 180)) {
                return false;
            }  //nicht mehr als 
        } catch (NumberFormatException ne) //Negativer grad
        {
            System.out.println("NumberFormatException");
            return false;
        } catch (IndexOutOfBoundsException ioe) {
            System.out.println("outOfBoundsException");
            return false;
        }
        return true;
    }

    /**
     * Setter for the Longitude
     * @param pLongitude The longitude to set
     * @return Returns true if the longitude could have been set
     */
    public boolean setLongitude(String pLongitude) {

        if (testLongitude(pLongitude)) {
            this.mLongitude = pLongitude.toUpperCase();                               // Wir arbeiten mit Grossbuchstaben
            return true;
        }
        return false;

    }

    /**
     * Getter for the name of the Waypoint
     * @return Returns the name of the waypoint
     */
    public String getName() {
        return mName;
    }

    /**
     * Setter for the Name of the waypoint
     * @param Name The waypoint name
     */
    public void setName(String Name) {
        this.mName = Name;
    }

    /**
     * Getter for the additional information, such as airport
     * @return Returns the additional information
     */
    public String getAdditionalInformation() {
        return mAdditionalInformation;
    }

    /**
     * Getter for the additional information
     * @param AdditionalInformation The test of the information to set, such as airport
     */ 
    public void setAdditionalInformation(String AdditionalInformation) {
        this.mAdditionalInformation = AdditionalInformation;
    }
}

