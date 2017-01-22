
package ca.ece.ubc.cpen221.mp5.statlearning; 

import java.util.Random; 

/** 
 * Abstract data type for a point that has x and y coordinates and a String ID
 */
public class Point {
	private double x = 0;
    private double y = 0; 
    private String ID;
    private int cluster_number = 0;
  
    //constructor
    public Point(double x, double y, String ID)
    {
        this.setX(x);
        this.setY(y); 
        this.ID = ID;
    }
    // set x coordinate
    public void setX(double x) {
        this.x = x;
    }
    // get x coordinate
    public double getX()  {
        return this.x;
    }
    // set y coordinate
    public void setY(double y) {
        this.y = y;
    }
    // get y coordinate
    public double getY() {
        return this.y;
    }
    // associates point with a cluster (establishes link)
    public void setCluster(int n) {
        this.cluster_number = n;
    }
    // returns cluster number
    public int getCluster() {
        return this.cluster_number;
    } 
    //returns ID ( unique identifier)
    public String getID () { 
    	return ID;
    }
    
    //Calculates the Manhattan distance between two points.
    protected static double distance(Point p, Point centroid) {
        return Math.sqrt(Math.pow((centroid.getY() - p.getY()), 2) + Math.pow((centroid.getX() - p.getX()), 2));
    }
    
    //Creates random point with ID "x" - used for centroids
    protected static Point createRandomPoint(int min, int max) {
    	Random r = new Random();
    	double x = min + (max - min) * r.nextDouble();
    	double y = min + (max - min) * r.nextDouble(); 
    	
    	return new Point(x,y, "x");
    }
    
   
}

