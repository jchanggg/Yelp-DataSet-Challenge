package org.junit;

import static org.junit.Assert.*;

import ca.ece.ubc.cpen221.mp5.RestaurantDB; 

/** 
 * Simple unit tests for restaurant database
 *
 */

public class DBTest {
	private static final String RESTAURANT_PATH = "data/restaurants.json";
	private static final String REVIEWS_PATH = "data/reviews.json";
	private static final String USERS_PATH = "data/users.json";
	
	private static RestaurantDB db;
	

	@Test
	public void setUpDB() {
		db = new RestaurantDB(RESTAURANT_PATH, REVIEWS_PATH,
				USERS_PATH); 
	} 
	@Test
	public void TestGetRestaurant() {
		assertEquals("Cafe 3",
				db.getRestaurant("4D7IdtyRjH8qxcsHaz1-GA"));
		assertNull(db.getRestaurant("this is not a restaurant id"));
	} 
	
	
	@Test
	public void TestGetUser() {
		assertEquals("Chris M.", db.getUser("_NH7Cpq3qZkByP5xR4gXog").getName());
		assertNull(db.getUser("this is not a user id"));
	}

}
