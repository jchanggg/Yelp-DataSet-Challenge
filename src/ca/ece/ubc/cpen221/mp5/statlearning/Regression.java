package ca.ece.ubc.cpen221.mp5.statlearning;

import java.util.Set;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ca.ece.ubc.cpen221.mp5.*;

public class PriceFeatureFunction implements FeatureFunction {
	
	FeatureFunction price = (db, restaurant_id) -> db.getRestaurant(restaurant_id).getprice();
	
	public double getSxx (RestaurantDB db, String restaurant_id){
		Restaurant toplot = db.getRestaurant(restaurant_id);
		Integer pricing = toplot.getprice();
		ArrayList<Review> allReviews = db.getAllReviews();
		for(int index = 0; index < allReviews.size(); index++){
			
		}
	
	}
	

}

FeatureFunction latitude = (db, restaurant_id) -> db.getRestaurant(restaurant_id).getlatitude();
FeatureFunction longitude = (db, restaurant_id) -> db.getRestaurant(restaurant_id).getlongitude();
FeatureFunction stars = (db, restaurant_id) -> db.getRestaurant(restaurant_id).getstars();