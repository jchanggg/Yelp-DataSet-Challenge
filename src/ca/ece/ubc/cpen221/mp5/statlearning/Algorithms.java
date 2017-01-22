package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import ca.ece.ubc.cpen221.mp5.*;

public class Algorithms {

	/**
	 * Use k-means clustering to compute k clusters for the restaurants in the
	 * database.
	 *
	 * @param db
	 * @return
	 */
	public static List<Set<Restaurant>> kMeansClustering(int k, RestaurantDB db) {
		// obtain a list of restaurants from the database
			ArrayList <Restaurant> restaurantList = db.getAllRestaurants(); 
			ArrayList <Point> coordinateList = new ArrayList <Point> (); 
			int numPoints  = restaurantList.size();  
				
				// create a list of points from the database
			for (int index = 0; index < numPoints; index++){ 
				Restaurant restaurant = restaurantList.get(index); 
				coordinateList.add(coordinatesToPoint(restaurant)); 
					
			}
				
			//perform the k means calculation 
			KMeans kmeans = new KMeans(coordinateList, k);
		    kmeans.init();
		    ArrayList <Cluster> sortedClusters = kmeans.calculate(); 
		    	
		    // Create a list of sets of restaurants:
		    	
		    List <Set<Restaurant>> outputList = new ArrayList <Set<Restaurant>> (); 
		    	
		    for (int index = 0; index < k; index++ ) {  
		    	ArrayList <Point> clusterPoints = sortedClusters.get(index).getPoints();   
		    	Set <Restaurant> currentSet = new HashSet <Restaurant> (); 
		    		
		    		for ( Point point: clusterPoints) { 
		    			String pointID = point.getID();   
		    			
		    			for (Restaurant restaurant: restaurantList){ 
		    				String restaurantID = restaurant.getbusiness_id();  
		    				
		    				if (pointID.equals(restaurantID)){ 
		    					currentSet.add(restaurant);
		    				}
		    			}	
		    		}
		    outputList.add(currentSet);
		    }	
			return outputList;
	}
	private static Point coordinatesToPoint(Restaurant restaurant) {
		Point p = new Point(restaurant.getX(),restaurant.getY(),restaurant.getbusiness_id());
		
		return p;
	}
	/** 
	 * Converts cluster list to JSON format 
	 * Includes x and y coordinates, cluster number (index in list) , and restaurant name
	 * @param clusters ( a list of sets of restaurants)
	 * @return a formatted string
	 */
	public static String convertClustersToJSON(List<Set<Restaurant>> clusters) {
		String finalString = "["; 
		
		for ( Set<Restaurant> cluster : clusters){ 
			int clusterNum = clusters.indexOf(cluster); 
			
			for (Restaurant restaurant :cluster){ 
				double X = restaurant.getX(); 
				double Y = restaurant.getY(); 
				String name = restaurant.getName(); 
				double weight = restaurant.getstars();
				
		    finalString = finalString + "{" + "\"x\": " +  X + ", \"y\": " + Y;
		    finalString = finalString + ", \"name\": " + "\""+ name + "\"" + ", \"cluster\": " + clusterNum; 
		    finalString = finalString + ", \"weight\": " + weight + "}, ";
			}
		} 
		int size = finalString.length();
		finalString = finalString.substring(0,size-3); //remove the trailing comma and space on final list item
		finalString = finalString + "]"; 
		
		return finalString;
	}
	/**
	 * Return a user rating prediction function
	 * 
	 * @param user_id
	 *            the id of the user we are interested in
	 * @param db
	 *            the database object that represents the yelp dataset
	 * @param featureFunction
	 *            that returns the feature we want to use in making predictions
	 * @return a LeastSquaresRegression function that returns the predicted
	 *         rating
	 * @throws Nosuchelement
	 *             exception if the user id has no reviews on the specified
	 *             restaurant or if the featurefunction is invalid
	 */
	public static LeastSquaresRegression getPredictor(String user_id, RestaurantDB db,
			FeatureFunction featureFunction) {
	
				// the
				// four
				// different
				// features
				// and
				// mapping
				// them
				// as
				// functions
				FeatureFunction getFeatprice = (dbl, rest_idl) -> getRestaurantprice(dbl, rest_idl);
				FeatureFunction getFeatlatitude = (dbl, rest_idl) -> getRestaurantlatitude(dbl, rest_idl);
				FeatureFunction getFeatlongitude = (dbl, rest_idl) -> getRestaurantlongitude(dbl, rest_idl);
				FeatureFunction getFeatstars = (dbl, rest_idl) -> getRestaurantstars(dbl, rest_idl);

				long priceSum = 0;
				long latitudeSum = 0;
				long longitudeSum = 0;
				long starsSum = 0;
				long ratingSumprice = 0;
				long ratingSumLatitude = 0;
				long ratingSumLongitude = 0;
				long ratingSumStars = 0;
				long ratingSum = 0;
				double priceMean = 0;
				double latitudeMean = 0;
				double longitudeMean = 0;
				double starsMean = 0;
				double ratingMean = 0;
				/*
				 * Creating ArrayLists of all relevant reviews and restaurants according
				 * to user id
				 */
				ArrayList<Review> allRev = new ArrayList<Review>();
				ArrayList<String> allRestaurant_ids = new ArrayList<String>();
				ArrayList<Review> allReviews = db.getAllReviews();
				ArrayList<Restaurant> allRestaurants = db.getAllRestaurants();
				ArrayList<Restaurant> allRest = new ArrayList<Restaurant>();
				for (int index = 0; index < allReviews.size(); index++) {
					if (allReviews.get(index).getuser_id().equals(user_id)) {
						allRestaurant_ids.add(allReviews.get(index).getrestaurant_id());
						allRest.add(db.getRestaurant(allReviews.get(index).getrestaurant_id()));
						allRev.add(db.getReview(allReviews.get(index).getreview_id()));
					}
				}

				// Calculating the mean of each feature
				for (int index = 0; index < allRestaurant_ids.size(); index++) {
					String restaurant_id = allRestaurant_ids.get(index);
					int count = -1;
					for (int index2 = 0; index2 < allRestaurants.size(); index2++) {
						if (allRestaurants.get(index2).getrestaurant_id().equals(restaurant_id)) {
							priceMean = allRestaurants.get(index2).getprice();
							latitudeMean = allRestaurants.get(index2).getX();
							longitudeMean = allRestaurants.get(index2).getY();
							starsMean = allRestaurants.get(index2).getstars();
							ratingMean = allRestaurants.get(index2).getstars();
							count = 0;
						}
						if (count == -1 && index2 == allRestaurants.size() - 1) {
							throw new NoSuchElementException();
						}

					}
					// Finding the sum of each feature
					priceSum += Math.pow((getFeatprice.getFeature(db, restaurant_id) - priceMean), 2);
					latitudeSum += Math.pow(getFeatlatitude.getFeature(db, restaurant_id) - latitudeMean, 2);
					longitudeSum += Math.pow(getFeatlongitude.getFeature(db, restaurant_id) - longitudeMean, 2);
					starsSum += Math.pow(getFeatstars.getFeature(db, restaurant_id) - starsMean, 2);
					ratingSumprice += (allRev.get(index).getstars() - ratingMean)
							* (getFeatprice.getFeature(db, restaurant_id) - priceMean);
					ratingSumLatitude += (allRev.get(index).getstars() - ratingMean)
							* (getFeatlatitude.getFeature(db, restaurant_id) - latitudeMean);
					ratingSumLongitude += (allRev.get(index).getstars() - ratingMean)
							* (getFeatlongitude.getFeature(db, restaurant_id) - longitudeMean);
					ratingSumStars += (allRev.get(index).getstars() - ratingMean)
							* (getFeatstars.getFeature(db, restaurant_id) - starsMean);
					ratingSum += Math.pow((allRev.get(index).getstars() - ratingMean), 2);
				}
				// Plotting Regression based on feature
				double Sxx = 0;
				double Sxy = 0;
				double Syy = ratingSum;
				//output based on input feature function
				if (featureFunction.equals(getFeatprice)) {
					Sxx = priceSum;
					Sxy = ratingSumprice;
					double b = Sxy / Sxx;
					double a = ratingMean - b * priceMean;
					//defining the lambda expression for regression
					LeastSquaresRegression reg = (dbl, restaurant_idl) -> (predictRatingprice(dbl, restaurant_idl) * b) + a;
					R2 = Math.pow(Sxy, 2) / (Sxx * Syy);
					return reg;

				}
				if (featureFunction.equals(getFeatlatitude)) {
					Sxx = latitudeSum;
					Sxy = ratingSumLatitude;
					double b = Sxy / Sxx;
					double a = ratingMean - b * latitudeMean;
					//defining the lambda expression for regression
					LeastSquaresRegression reg = (dbl, restaurant_idl) -> (predictRatinglatitude(dbl, restaurant_idl) * b) + a;
					R2 = Math.pow(Sxy, 2) / (Sxx * Syy);
					return reg;
				}
				if (featureFunction.equals(getFeatlongitude)) {
					Sxx = longitudeSum;
					Sxy = ratingSumLongitude;
					double b = Sxy / Sxx;
					double a = ratingMean - b * longitudeMean;
					//defining the lambda expression for regression
					LeastSquaresRegression reg = (dbl, restaurant_idl) -> (predictRatinglongitude(dbl, restaurant_idl) * b) + a;
					R2 = Math.pow(Sxy, 2) / (Sxx * Syy);
					return reg;
				}
				if (featureFunction.equals(getFeatstars)) {
					Sxx = starsSum;
					Sxy = ratingSumStars;
					double b = Sxy / Sxx;
					double a = ratingMean - b * starsMean;
					//defining the lambda expression for regression
					LeastSquaresRegression reg = (dbl, restaurant_idl) -> (predictRatingstars(dbl, restaurant_idl) * b) + a;
					R2 = Math.pow(Sxy, 2) / (Sxx * Syy);
					return reg;
				} else {

					throw new NoSuchElementException();
					// Throws an exception if feature is undefined
				}
	
		
	
	}  
	/**
	 * Only use after getpredictor is called or else it will return -1 Takes in
	 * no arguments
	 * 
	 * @return Rsquared value of getpredictor
	 */
	public static double getR2() {
		return R2;
	}

	/**
	 * This method finds the price of a restaurant in the database based on the
	 * restaurant id
	 * 
	 * @param db
	 *            parsed database of yelp
	 * @param restaurant_id
	 *            which we would like to search for its feature
	 * @return the price of the restaurant
	 * @throws nosuchelementexception
	 *             if there is no restaurant in the database that has the same
	 *             restaurant id as the parameter
	 */
	public static double predictRatingprice(RestaurantDB db, String restaurant_id) {
		//iterates through a list to find the price of restaurant corresponding to restaurant id 
		ArrayList<Restaurant> allRest = db.getAllRestaurants();
		Restaurant theOne = allRest.get(0);
		int count = -1;
		for (int index = 0; index < allRest.size(); index++) {
			if (allRest.get(index).getrestaurant_id().equals(restaurant_id)) {
				theOne = allRest.get(index);
				count = 0;
			}
			if (index == allRest.size() - 1 && count == -1) {
				throw new NoSuchElementException();
			}
		}
		return theOne.getprice();
	}

	/**
	 * This method finds the latitude of a restaurant in the database based on
	 * the restaurant id
	 * 
	 * @param db
	 *            parsed database of yelp
	 * @param restaurant_id
	 *            which we would like to search for its feature
	 * @return the latitude of the restaurant
	 * @throws nosuchelementexception
	 *             if there is no restaurant in the database that has the same
	 *             restaurant id as the parameter
	 */
	public static double predictRatinglatitude(RestaurantDB db, String restaurant_id) {
		//iterates through a list to find the latitude of restaurant corresponding to restaurant id 
		ArrayList<Restaurant> allRest = db.getAllRestaurants();
		Restaurant theOne = allRest.get(0);
		int count = -1;
		for (int index = 0; index < allRest.size(); index++) {
			if (allRest.get(index).getrestaurant_id().equals(restaurant_id)) {
				theOne = allRest.get(index);
				count = 0;
			}
			if (index == allRest.size() - 1 && count == -1) {
				throw new NoSuchElementException();
			}
		}
		return theOne.getlatitude();
	}

	/**
	 * This method finds the longitude of the restaurant based on the restaurant
	 * id in the database
	 * 
	 * @param db
	 *            parsed database of yelp
	 * @param restaurant_id
	 *            which we would like to search for its feature
	 * @return the longitude of the restaurant
	 * @throws nosuchelementexception
	 *             if there is no restaurant in the database that has the same
	 *             restaurant id as the parameter
	 */
	public static double predictRatinglongitude(RestaurantDB db, String restaurant_id) {
		//iterates through a list to find the longitude of restaurant corresponding to restaurant id 
		ArrayList<Restaurant> allRest = db.getAllRestaurants();
		Restaurant theOne = allRest.get(0);
		int count = -1;
		for (int index = 0; index < allRest.size(); index++) {
			if (allRest.get(index).getrestaurant_id().equals(restaurant_id)) {
				theOne = allRest.get(index);
				count = 0;
			}
			if (index == allRest.size() - 1 && count == -1) {
				throw new NoSuchElementException();
			}
		}
		return theOne.getY();
	}

	/**
	 * This method finds the stars of the restaurant in the given database
	 * 
	 * @param db
	 *            parsed database of yelp
	 * @param restaurant_id
	 *            which we would like to search for its feature
	 * @return the stars of the restaurant
	 * @throws nosuchelementexception
	 *             if there is no restaurant in the database that has the same
	 *             restaurant id as the parameter
	 */
	public static double predictRatingstars(RestaurantDB db, String restaurant_id) {
		//iterates through a list to find the stars of restaurant corresponding to restaurant id 
		ArrayList<Restaurant> allRest = db.getAllRestaurants();
		Restaurant theOne = allRest.get(0);
		int count = -1;
		for (int index = 0; index < allRest.size(); index++) {
			if (allRest.get(index).getrestaurant_id().equals(restaurant_id)) {
				theOne = allRest.get(index);
				count = 0;
			}
			if (index == allRest.size() - 1 && count == -1) {
				throw new NoSuchElementException();
			}
		}
		return theOne.getstars();
	}


	/**
	 * This method finds the price of a restaurant in the database based on the
	 * restaurant id
	 * 
	 * @param db
	 *            parsed database of yelp
	 * @param restaurant_id
	 *            which we would like to search for its feature
	 * @return the price of the restaurant
	 * @throws nosuchelementexception
	 *             if there is no restaurant in the database that has the same
	 *             restaurant id as the parameter
	 */
	public static double getRestaurantprice(RestaurantDB db, String restaurant_id) {
		//iterates through a list to find the price of restaurant corresponding to restaurant id 
		double toreturn = -1;
		ArrayList<Restaurant> allRest = db.getAllRestaurants();
		for (int index = 0; index < allRest.size(); index++) {
			if (allRest.get(index).getrestaurant_id().equals(restaurant_id)) {
				toreturn = allRest.get(index).getprice();
				return toreturn;
			}
		}
		if (toreturn == -1) {
			throw new NoSuchElementException();
		}
		return toreturn;
	}
	/**
	 * This method finds the longitude of the restaurant based on the restaurant
	 * id in the database
	 * 
	 * @param db
	 *            parsed database of yelp
	 * @param restaurant_id
	 *            which we would like to search for its feature
	 * @return the longitude of the restaurant
	 * @throws nosuchelementexception
	 *             if there is no restaurant in the database that has the same
	 *             restaurant id as the parameter
	 */
	public static double getRestaurantlongitude(RestaurantDB db, String restaurant_id) {
		//iterates through a list to find the longitude of restaurant corresponding to restaurant id 
		double toreturn = -1;
		ArrayList<Restaurant> allRest = db.getAllRestaurants();
		for (int index = 0; index < allRest.size(); index++) {
			if (allRest.get(index).getrestaurant_id().equals(restaurant_id)) {
				toreturn = allRest.get(index).getprice();
				return toreturn;
			}
		}
		if (toreturn == -1) {
			throw new NoSuchElementException();
		}
		return toreturn;
	}
	/**
	 * This method finds the latitude of a restaurant in the database based on
	 * the restaurant id
	 * 
	 * @param db
	 *            parsed database of yelp
	 * @param restaurant_id
	 *            which we would like to search for its feature
	 * @return the latitude of the restaurant
	 * @throws nosuchelementexception
	 *             if there is no restaurant in the database that has the same
	 *             restaurant id as the parameter
	 */
	public static double getRestaurantlatitude(RestaurantDB db, String restaurant_id) {
		//iterates through a list to find the latitude of restaurant corresponding to restaurant id 
		double toreturn = -1;
		ArrayList<Restaurant> allRest = db.getAllRestaurants();
		for (int index = 0; index < allRest.size(); index++) {
			if (allRest.get(index).getrestaurant_id().equals(restaurant_id)) {
				toreturn = allRest.get(index).getprice();
				return toreturn;
			}
		}
		if (toreturn == -1) {
			throw new NoSuchElementException();
		}
		return toreturn;
	}
	/**
	 * This method finds the stars of the restaurant in the given database
	 * 
	 * @param db
	 *            parsed database of yelp
	 * @param restaurant_id
	 *            which we would like to search for its feature
	 * @return the stars of the restaurant
	 * @throws nosuchelementexception
	 *             if there is no restaurant in the database that has the same
	 *             restaurant id as the parameter
	 */
	public static double getRestaurantstars(RestaurantDB db, String restaurant_id) {
		//iterates through a list to find the stars of restaurant corresponding to restaurant id 
		double toreturn = -1;
		ArrayList<Restaurant> allRest = db.getAllRestaurants();
		for (int index = 0; index < allRest.size(); index++) {
			if (allRest.get(index).getrestaurant_id().equals(restaurant_id)) {
				toreturn = allRest.get(index).getprice();
				return toreturn;
			}
		}
		if (toreturn == -1) {
			throw new NoSuchElementException();
		}
		return toreturn;
	}

	/**
	 * Returns the best predictor for a user's rating
	 * 
	 * @param user_id
	 *            the user id for the user we are interested in
	 * @param db
	 *            the database object that represents the yelp dataset
	 * @param featureFunctionList
	 *            is a list of feature functions from which the best is selected
	 * @return the best prediction function
	 * @throws indexoutofboundsexception
	 *             if there is an error with the functions nested inside this
	 *             method or if the ArrayList is empty
	 */
	public static LeastSquaresRegression getBestPredictor(String user_id, RestaurantDB db,
			List<FeatureFunction> featureFunctionList) {
		//get all rsquared values for predictors
		int theindex = -1;
		final ArrayList<Double> allR2 = new ArrayList<Double>();
		for (int index = 0; index < featureFunctionList.size(); index++) {
			getPredictor(user_id, db, featureFunctionList.get(index));
			allR2.add(getR2());
		}
		// finds predictor function with best rsquared value
		Double largest = new Double(-1.1);
		for (int index = 0; index < allR2.size(); index++) {
			if (largest <= allR2.get(index)) {
				largest = allR2.get(index);
				theindex = index;
			}
		}

		return getPredictor(user_id, db, featureFunctionList.get(theindex));
	}
}

	