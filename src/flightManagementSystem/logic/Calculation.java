package flightManagementSystem.logic;

import flightManagementSystem.data.Route; 


import flightManagementSystem.data.*;
import static java.lang.Math.*;

/**
 * Calculates the Route
 * @author landev
 */
public class Calculation {


    private Route mRoute;
    private double mVelocity;

    /**
     * Calculates the route, the route is made of waypoints
     * @param pRoute The route to compute
     */
    public static void calculate(Route pRoute)
    {
        //System.out.println(pRoute.size());
        for(int i=0; i<pRoute.size()-1; i++)
        {
            pRoute.addLeg(calculateLeg(pRoute.getWaypoint(i),pRoute.getWaypoint(i+1),pRoute.getSpeed())); 
        }
    }

    /**
     * Calculates a single leg, so to say from one waypoint to another
     * @param source Source waypoint
     * @param destination Destination waypoint
     * @param pSpeed Speed of the airplain
     * @return Returns a Leg, containing the infromation form one Waypoint to another
     */
    public static Leg calculateLeg(Waypoint source, Waypoint destination, double pSpeed )
    {
        double beta = toRadians( 90-latitudeFromString(source.getLatitude()) );
        double alpha = toRadians( 90-latitudeFromString(destination.getLatitude()) );
        double gamma = toRadians( longitudeFromString(source.getLongitude()) - longitudeFromString(destination.getLongitude()));
        double c = acos(cos(alpha)*cos(beta)+sin(alpha)*sin(beta)*cos(gamma) );
        Double distance = toDegrees(c)*60;
        Double heading = toDegrees(2*PI -acos( (cos(alpha)- cos(beta)*cos(c))  / (sin(beta)*sin(c))));
        
        return new Leg(source.getName(), destination.getName(), distance, heading, distance/pSpeed);            //TODO: Division by zero abfangen
    }
    
    /**
     * Calculates the latitude string to Degree
     * the Latitude string is checked once more in the Waypoint class
     * @param pLatitude The Latitude to generate
     * @return returns zero if the latitude is wrong
     */
    public static double latitudeFromString(String pLatitude)
    {
        if (!Waypoint.testLatitude(pLatitude)) return 0;
        double Latitude = Double.parseDouble(pLatitude.substring(1,3));         //Grade abschneiden
        Latitude += Double.parseDouble(pLatitude.substring(3,6))/600;           //Minuten dezuzählen
        if(pLatitude.charAt(0)=='N') return Latitude;
        else if (pLatitude.charAt(0)=='S') return Latitude*(-1);
        return 0;
    }
    
    /**
     * Calculates the longitude string to Degrees
     * the Latitude string is checked once more in the Waypoint class
     * @param pLongitude The Longitude to generate
     * @return returns zero if the latitude is wrong
     */
    public static double longitudeFromString(String pLongitude)
    {
        if (!Waypoint.testLongitude(pLongitude)) return 0;
        double Longitude = Double.parseDouble(pLongitude.substring(1,4));       //Grade abschneiden
        Longitude += Double.parseDouble(pLongitude.substring(4,7))/600;         //Minuten dazuzählen
        if(pLongitude.charAt(0)=='E') return Longitude;
        else if (pLongitude.charAt(0)=='W') return Longitude*(-1);
        return 0;
        
    }
}

