/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package flightManagementSystem.logic;

import flightManagementSystem.data.*;


/**
 * Class to test the claculation so we could have written the complete project seperatly
 * @author landev
 */
public class CalculationTest {
    /**
     * The main method, with correct an wrong data
     * @param args Commandline arguments
     */
    public static void main(String[] args)
    {
        String myBuggyString[][] = {{ "Zürich-Kloten", "LSZH",  "n47275", "e008329" },       //längerer N0000235322
                                    { " ",             "VEBIT", "N47161", "E008004" },
                                    { " ",             "ROTOS", "N47114", "E007435" },
                                    { " ",             "ULMES", "N46573", "E-07176" },       // negative Zahlen
                                   { "Genf",          "LSGG",  "N46143", "E366066" }};     // winkel grösser gleich 360°
                
        String myString[][] = {{ "Zürich-Kloten", "LSZH",  "n47275", "e008329" },       //längerer N0000235322
                               { " ",             "VEBIT", "N47161", "E008004" },
                               { " ",             "ROTOS", "N47114", "E007435" },
                               { " ",             "ULMES", "N46573", "E007176" },
                               { "Genf",          "LSGG",  "N46143", "E006066" }};
        
        Route myRoute=null;
        
        try
        {
        myRoute = new Route(myString, 500);
        }
        catch (Exception e)
        {
            System.out.println("Error in Data");
        }
        
        for(int i = 0; i<myRoute.size(); i++)
        {
            System.out.print(Calculation.latitudeFromString(myRoute.getWaypoint(i).getLatitude()));
            System.out.print("   ");
            System.out.println(Calculation.longitudeFromString(myRoute.getWaypoint(i).getLongitude()));
        }
        
        Calculation.calculate(myRoute);
        for(int i=0; i<myRoute.size(); i++)
        {
                System.out.format("%15s", myRoute.getWaypoint(i).getName());
                System.out.format("%15s", myRoute.getWaypoint(i).getAdditionalInformation());
                System.out.format("%15s", myRoute.getWaypoint(i).getLatitude());
                System.out.format("%15s", myRoute.getWaypoint(i).getLongitude());
            System.out.println();
        }
        
        String[][] result  = myRoute.legsToRoundedArray();
        
        System.out.println("------------------------------------");
        for(int i=0; i<result.length; i++)
        {
            for(int j=0; j<result[0].length; j++)
                System.out.format("%15s", result[i][j]);
            System.out.println();
        }
        System.out.println("Total Dist: " + myRoute.getTotalDistance()+ " Total Time: " + myRoute.getTotalTime() + " at Speed: " + myRoute.getSpeed());
    }
}
