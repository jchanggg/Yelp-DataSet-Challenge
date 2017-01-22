package ca.ece.ubc.cpen221.mp5.statlearning;
import ca.ece.ubc.cpen221.mp5.*;

import ca.ece.ubc.cpen221.mp5.RestaurantDB;

public interface LeastSquaresRegression {

	/**
	 * Compute a predicted rating given restaurant information for a particular
	 * user. The user information is used a priori to construct the function.
	 *
	 * @param db
	 *            the database for the yelp dataset
	 * @param restaurant_id
	 *            the restaurant for which we would like a predicted rating
	 * @return the predi cted rating
	 */
	public double lsrf(RestaurantDB db, String restaurant_id);
	
	 
	
	
}

