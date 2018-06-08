package flightManagementSystem.data;

import flightManagementSystem.logic.Utils;
import java.text.DecimalFormat;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * This is the class where the data is stored, decorated with XML Tags so we can
 * serialize the complete class to a xml file without borthering about the xml design...
 * ok acknowledge, i am lazy sometimes...
 * but the important thing is we found out that it is import to have setters and getters 
 * for the serialization to work
 *  @author landev
 */
@XmlRootElement(name="RootElement")
public class Route {

    
    @XmlElement private java.util.ArrayList<Waypoint> mWaypoints = new java.util.ArrayList<Waypoint>();
    @XmlElement private java.util.ArrayList<Leg> mLegs = new java.util.ArrayList<Leg>();
    
    private double mSpeed;
    
    private double mTotalDistance;
    private double mTotalTime;
    
    
    /**
     * Default Non-arc constructor for serialisation
     */
    public Route()
    {
        
    }
    
    /**
     * Constructor that creates a complete route with a given String array^2
     * @param data The array containig the Data
     * @param pSpeed the speed to fly with
     * @throws java.lang.Exception Is thrown when the data is wrong
     */
    public Route(String[][] data, double pSpeed) throws java.lang.Exception
    {
        mSpeed=pSpeed;
        for (int i=0; i<data.length; i++)
        {   
            mWaypoints.add(new Waypoint(data[i]));
        }
    }
    
    /**
     * Getter for waypoints 
     * @param pNumber Nuber of the waypoint
     * @return returns the selected waypoint
     */
    public Waypoint getWaypoint(int pNumber) {
        return mWaypoints.get(pNumber);
    }
    /**
     * Setter for waypoints
     * @param val The waypoint to attache to the list
     */
    public void setWaypoint(Waypoint val) {
        this.mWaypoints.add(val);
    }
    /**
     * For getteing a referece of the waypoints
     * @return the reference of the waypoints
     */
    public java.util.ArrayList<Waypoint> getWaypoints() {
        return mWaypoints;
    }
    
    /**
     * Getter for a single leg with a given number, ths
     * Getter is required for the serialisation
     * @param pNumber the number of the desired leg
     * @return returns a reference to the desired leg
     */
    public Leg getLeg(int pNumber)             
    {
        return mLegs.get(pNumber);
    }
    
    /**
     * Setter for leg, this method is required for the serialisation
     * @param pLeg the leg to add
     */
    public void setLeg(Leg pLeg)                                    
    {
        mLegs.add(pLeg);
    }
     
   /**
     * Methode to add a leg
     * @param pLeg Leg to add
     */
    public void addLeg(Leg pLeg)
    {
        mLegs.add(pLeg);
    }
    
    /**
     * Method to clear all Legs
     */
    public void clearLegs()
    {
        mLegs=new java.util.ArrayList<Leg>();
    }
    
    /**
     * Gives a rounded string array^2 for displaying on the screen
     * @return the rounded string array
     */
    public String[][] legsToRoundedArray()
    {
        String[][] result = new String[mLegs.size()][5];
        for(int i=0; i<mLegs.size(); i++)
        {
            result[i][0]=mLegs.get(i).getFrom();
            result[i][1]=mLegs.get(i).getTo();
            result[i][2]=Utils.round(mLegs.get(i).getHeading());
            result[i][3]=Utils.round(mLegs.get(i).getDistance());
            result[i][4]=Utils.round(mLegs.get(i).getTime());
        }
        return result;
    }
   
    /**
     * Getter for the speed
     * @return
     */
    public double getSpeed()
    {
        return mSpeed;
    }
    
    /**
     * Setter for the speed
     * @param pSpeed
     */
    public void setSpeed(double pSpeed)
    {
        mSpeed=pSpeed;
    }
    
    /**
     * Getter for the Total distance
     * @return The total distance
     */
    public double getTotalDistance()
    {
        for(int i=0; i<mLegs.size(); i++)
        {
            mTotalDistance = mTotalDistance + mLegs.get(i).getDistance();
        }        
        return mTotalDistance;
    }

    /**
     * Setter for the total distance
     * @param pTotalDistance the total distance
     */
    public void setTotalDistance(double pTotalDistance)                                              //Für serialisierung, wobei der setter ins nirvana geht, da wir das ja mit getTotalTime berechnen
    {
        mTotalDistance=pTotalDistance;
    }
    
    /**
     * Getter for the total time
     * @return the total time
     */
    public double getTotalTime()
    {
        double TotalTime=0;
        for(int i=0; i<mLegs.size(); i++)
        {
            TotalTime = TotalTime + mLegs.get(i).getTime();
        }        
        return TotalTime;
    }
    
    /**
     * Setter for the total time 
     * @param pTotalTime the total time
     */
    public void setTotalTime(double pTotalTime)                                      //Für serialisierung, wobei der setter ins nirvana geht, da wir das ja mit getTotalTime berechnen
    {
        
    }
    
    /**
     * Method for getting the size of the waypoint table
     * @return
     */
    public int size()
    {
        return mWaypoints.size();
    }
}

