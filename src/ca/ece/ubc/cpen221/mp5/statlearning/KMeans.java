package ca.ece.ubc.cpen221.mp5.statlearning; 

import java.util.ArrayList; 
import java.util.List; 

/** 
 * Solution adapted from algorithm on DataOnFocus (dataonfocus.com)
 */
public class KMeans {


	    private int NUM_CLUSTERS;    
	    private ArrayList <Point> points;
	    private ArrayList <Cluster> clusters;
	    
	    public KMeans (ArrayList< Point> givenPoints,int numClusters) {
	    	this.points = new ArrayList <Point> (givenPoints);
	    	this.clusters = new ArrayList<Cluster>();    
	    	this.NUM_CLUSTERS = numClusters; 
	    	
	    }
	    
	    
	    //Initializes the process
	    public void init() {
	    	
	    	//Create Clusters
	    	//Set Random Centroids
	    	for (int i = 0; i < NUM_CLUSTERS; i++) {
	    		Cluster cluster = new Cluster(i);
	    		Point centroid = Point.createRandomPoint(Integer.MIN_VALUE, Integer.MAX_VALUE);
	    		cluster.setCentroid(centroid);
	    		clusters.add(cluster);
	    	}
	    	
	
	    }
	 
	    
		//The process to calculate the K Means, with iterating method.
	    public ArrayList <Cluster> calculate() {
	        boolean finish = false;
	        
	        // Add in new data, one at a time, recalculating centroids with each new one. 
	        while(!finish) {
	        	//Clear cluster state
	        	clearClusters();
	        	
	        	ArrayList <Point> lastCentroids = getCentroids();
	        	
	        	//Assign points to the closer cluster
	        	assignCluster();
	            
	            //Calculate new centroids
	        	calculateCentroids();
	        	
	        	
	        	ArrayList <Point> currentCentroids = getCentroids();
	        	
	        	//Calculates total distance between new and old centroids
	        	double distance = 0;
	        	for(int i = 0; i < lastCentroids.size(); i++) {
	        		distance += Point.distance(lastCentroids.get(i),currentCentroids.get(i));
	        	}
	        	
	        	        	
	        	if(distance == 0) {
	        		finish = true;
	        	} 
	        } 
	        //Return list of calculated clusters 
	        if (finish == true) { 
	        		return new ArrayList <Cluster> (clusters);
	        }
	        return null;
	    }
	    
	    /** 
	     * Empties the cluster list for a new iteration
	     */
	    private void clearClusters() {
	    	for(Cluster cluster : clusters) {
	    		cluster.clear();
	    	}
	    }
	    /** 
	     * Calculates the new centroids of each cluster
	     * @return
	     */
	    private ArrayList <Point> getCentroids() {
	    	ArrayList <Point>centroids = new ArrayList <Point>(NUM_CLUSTERS);
	    	for(Cluster cluster : clusters) {
	    		Point aux = cluster.getCentroid();
	    		Point point = new Point(aux.getX(),aux.getY(), "x");
	    		centroids.add(point);
	    	}
	    	return centroids;
	    }
	    /** 
	     * Assigns each point to their new cluster
	     */
	   private void assignCluster() {
	        double max = Double.MAX_VALUE;
	        double min = max; 
	        int cluster = 0;                 
	        double distance = 0.0; 
	        
	        for(Point point : points) {
	        	
	            for(int i = 0; i < NUM_CLUSTERS; i++) {
	            	Cluster c = clusters.get(i);
	                distance = Point.distance(point, c.getCentroid());
	                if(distance < min){
	                    min = distance;
	                    cluster = i;
	                }
	            }
	            point.setCluster(cluster);
	            clusters.get(cluster).addPoint(point);
	        }
	    }
	    
	    private void calculateCentroids() {
	        for(Cluster cluster : clusters) {
	            double sumX = 0;
	            double sumY = 0;
	            ArrayList <Point> list = cluster.getPoints();
	            int n_points = list.size();
	            
	            // sum all x-coordinates and y-coordinates 
	            for(Point point : list) {
	            	sumX += point.getX();
	                sumY += point.getY();
	            }
	            
	            //change the centroid by calculating new "average" position 
	            Point centroid = cluster.getCentroid(); 
	            if(n_points > 0) {                   			// note: cluster must be non-empty
	            	double newX = sumX / n_points; 
	            	double newY = sumY / n_points;
	                centroid.setX(newX);
	                centroid.setY(newY);
	            }
	        }
	    } 
}

