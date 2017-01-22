package ca.ece.ubc.cpen221.mp5.statlearning; 

import java.util.ArrayList;
 
/** 
 * Implementation of the cluster concept
 * Clusters have a set of points, one centroid and a name or ID
 */
public class Cluster {
	public ArrayList <Point> points;
	public Point centroid;
	public int id;
	
	//Creates a new Cluster
	public Cluster(int id) {
		this.id = id;
		this.points = new ArrayList <Point> ();
		this.centroid = null;
	}
	// get all points in cluster
	public ArrayList <Point> getPoints() {
		return points;
	}
	//add point to cluster
	public void addPoint(Point point) {
		points.add(point);
	}
     //initialize cluster
	public void setPoints(ArrayList <Point> points) {
		this.points = points;
	}
	//return current centroid
	public Point getCentroid() {
		return centroid;
	}
	//set point as a centroid
	public void setCentroid(Point centroid) {
		this.centroid = centroid;
	}
	//get cluster id
	public int getId() {
		return id;
	}
	// remove all points
	public void clear() {
		points.clear();
	}
	
	
 
}
