package org.junit;

import java.util.List;
import java.util.Random;
import java.util.Set;

import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.RestaurantDB;
import ca.ece.ubc.cpen221.mp5.statlearning.Algorithms; 

/** 
 * Simple unit tests for the algorithms class
 *
 */
public class AlgorithimsTest {
	private static final String RESTAURANT_PATH = "data/restaurants.json";
	private static final String REVIEWS_PATH = "data/reviews.json";
	private static final String USERS_PATH = "data/users.json";
	
	private static RestaurantDB db;
	
	// construct the db

	public static void setUp() {
		db = new RestaurantDB(RESTAURANT_PATH, REVIEWS_PATH,
				USERS_PATH);
	}
	
	@Test
	public void testKMeans() {
		
		int numClusters = 6;
		
		List<Set<Restaurant>> clusters = Algorithms.kMeansClustering(numClusters, db);
	
		
		// print the cluster string for a visual test 
		System.out.println(Algorithms.convertClustersToJSON(clusters));
		
	}
	
	@Test
	public void testLeastSquaresRegression(){
		Random randomGenerator = new Random();
		int randomIndex = randomGenerator.nextInt(db.getAllUsers().size());
	
		System.out.println(db.getAllUsers().get(randomIndex).getID());
		LeastSquaresRegression predictor = Algorithms.getPredictor(db.getAllUsers().get(randomIndex).getID(),
				db, new PriceScaleFunction());
		
		randomIndex = randomGenerator.nextInt(db.getAllRestaurants().size());
		double prediction = predictor.lsrf(db, db.getAllRestaurants().get(randomIndex).getID());
		
		System.out.println(prediction);
		assertTrue(0.0 <= prediction && prediction <= 5.0);
		
	}
}
