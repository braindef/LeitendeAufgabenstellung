/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package flightManagementSystem.data;

/**
 *
 * @author landev
 */
public class Leg {
    private String From;
    private String To;
    private double Distance;
    private double Heading;
    private double Time;

    
    /**
     * empty constructor for the serialisation
     */
    public Leg()                                                                
    {
       
    }
    
    /**
     * Constructor for the leg
     * @param pFrom Flying from
     * @param pTo Flying to
     * @param pDistance Distantce between from and to
     * @param pHeading Heading of the leg
     * @param pTime Flight time of this leg
     */
    public Leg(String pFrom, String pTo, double pDistance, double pHeading, double pTime)
    {
        this.From=pFrom;
        this.To=pTo;
        this.Distance=pDistance;
        this.Heading=pHeading;
        this.Time=pTime;
    }
    /**
     * Getter from
     * @return returns the from waypoint name
     */
    public String getFrom() {               
        return From;
    }

    /**
     * Setter from
     * @param from sets the from waypoint name
     */
    public void setFrom(String from) {
        this.From = from;
    }

    /**
     * Getter to
     * @return returns the to waypoint name
     */
    public String getTo() {
        return To;
    }

    /**
     * Setter for the to waypoint
     * @param to Name of the to waypoint
     */
    public void setTo(String to) {
        this.To = to;
    }

    /**
     * Getter for distance
     * @return returns the distance from the from-waypoint to the to-waypoint
     */
    public double getDistance() {
        return Distance;
    }

    /**
     * Setter for distance
     * @param distance the distance to set
     */
    public void setDistance(double distance) {
        this.Distance = distance;
    }

    /**
     * Getter for heading
     * @return returns the heading
     */
    public double getHeading() {
        return Heading;
    }

    /**
     * Setter for the heading
     * @param heading the heading to set
     */
    public void setHeading(double heading) {
        this.Heading = heading;
    }

    /**
     * Getter for the time
     * @return returns the time ATTENTION (*60) 
     */
    public double getTime() {
        return Time*60;
    }

    /**
     * Setter for the time
     * @param time
     */
    public void setTime(double time) {
        this.Time = time;
    }
}
