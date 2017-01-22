package ca.ece.ubc.cpen221.mp5;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



// TODO: This class represents the Restaurant Database.
// Define the internal representation and
// state the rep invariant and the abstraction function.

/**
 * The database consists of 3 hashmaps of all users, reviews, and restaurants
 * respectively It also contains two complete arraylists of restaurants and
 * reviews The abstraction function is the complete JSON dataset of yelp given
 * to us and it is mapped to Hashmap and ArrayLists objects (given above) of
 * their corresponding type (Restaurant, User, or Review) The Hashmaps and
 * Arraylists themselves are mutable for adding new reviews, users, and
 * restaurants The Hashmaps have either user id, restaurant id or review id as a
 * key to their corresponding user, restaurant, or review Database inspired by
 * lab 11
 */
public class RestaurantDB {

	private HashMap<String, User> mUsers = new HashMap<String, User>();
	private HashMap<String, Review> mReviews = new HashMap<String, Review>();
	private HashMap<String, Restaurant> mRestaurants = new HashMap<String, Restaurant>();
	private ArrayList<Restaurant> lRestaurants = new ArrayList<Restaurant>();
	private Collection<Review> lReviews = new ArrayList<Review>();
	private Collection<User> lUsers = new ArrayList<User>();
	

	/**
	 * Create a database from the Yelp dataset given the names of three files:
	 * <ul>
	 * <li>One that contains data about the restaurants;</li>
	 * <li>One that contains reviews of the restaurants;</li>
	 * <li>One that contains information about the users that submitted reviews.
	 * </li>
	 * </ul>
	 * The files contain data in JSON format.
	 *
	 * @param restaurantJSONfilename
	 *            the filename for the restaurant data
	 * @param reviewsJSONfilename
	 *            the filename for the reviews
	 * @param usersJSONfilename
	 *            the filename for the users
	 */

	public RestaurantDB(String restaurantJSONfilename, String reviewsJSONfilename, String usersJSONfilename) {

		ArrayList<String> allStrings = new ArrayList<String>();
		ArrayList<String> allStrings2 = new ArrayList<String>();
		// read file into stream, try-with-resources

		try (Stream<String> streamgetusers = Files.lines(Paths.get(usersJSONfilename))) {
			
			streamgetusers
			    .forEach(lines -> mUsers.put(getYelpUser(lines).getuser_id(), getYelpUser(lines)));	
			// .forEach(lines -> allStrings.add(lines));// users
																												// into
																												// maps
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		//for(int index = 0; index < allStrings.size(); index++){
	//		mUsers.put(getYelpUser(allStrings.get(index)).getuser_id(), getYelpUser(allStrings.get(index)));
	//	}

		try (Stream<String> streamgetreviews = Files.lines(Paths.get(reviewsJSONfilename))) {
			
			streamgetreviews
			.forEach(lines -> mReviews.put(getYelpReview(lines).getreview_id(), getYelpReview(lines)))
			;// put
																														// reviews
																														// into
																														// arraylist
																														// and
																														// hashmap
			lReviews = mReviews.values();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		try (Stream<String> streamgetrestaurants = Files.lines(Paths.get(restaurantJSONfilename))) {
			streamgetrestaurants.forEach(lines -> mRestaurants.put(getYelpRestaurant(lines).getbusiness_id(), getYelpRestaurant(lines)));// put
																													// restaurants
				Set<String> allkeys = mRestaurants.keySet();
				Iterator<String> shift = allkeys.iterator();// into
					while(shift.hasNext()){
						String id = shift.next();
						lRestaurants.add(mRestaurants.get(id));
					}
																													// and
																													// hashmap
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method returns the user mapped to the specific user id
	 * 
	 * @param user_id
	 * @return user object mapped to the user id
	 */
	public User getUser(String user_id) {
		return mUsers.get(user_id);
	}

	/**
	 * This method returns the restaurant mapped to the specific restaurant id
	 * 
	 * @param restaurant_id
	 * @return restaurant object mapped to the user id
	 */
	public Restaurant getRestaurant(String restaurant_id) {
		return mRestaurants.get(restaurant_id);
	}

	/**
	 * This method returns the review mapped to the specific review id
	 * 
	 * @param restaurant_id
	 * @return review object mapped to the review id
	 */
	public Review getReview(String review_id) {
		return mReviews.get(review_id);
	}

	/**
	 * Takes in no arguement and returns an arraylist of type restaurant in the
	 * database
	 * 
	 * @return arraylist of all restaurnts in the database
	 */
	public ArrayList<Restaurant> getAllRestaurants() {
		return lRestaurants;
	}
	/**
	 * Takes in no arguement and returns an arraylist of type users in the
	 * database
	 * 
	 * @return arryalist of all users in the database
	 */
	public Collection<User> getUsers() {
		return (ArrayList<User>) lUsers;
	}

	/**
	 * Takes in no arguement and returns an arraylist of type review in the
	 * database
	 * 
	 * @return arryalist of all reviews in the database
	 */
	public ArrayList<Review> getAllReviews() {
		return (ArrayList<Review>) lReviews;
	}

	/**
	 * This method parses one line of json file into a string and then
	 * constructs a user object
	 * 
	 * @param jText
	 *            as a string, 1 line of json file
	 * @return user object
	 */
	private static User getYelpUser(String jText) {
		// parses object with a parser
		// then creates object with constructor
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(jText);
			JSONObject jsonObject = (JSONObject) obj;

			User y = new User((String) jsonObject.get("url"),
					(Long) ((JSONObject) jsonObject.get("votes")).get("funny"),
					(Long) ((JSONObject) jsonObject.get("votes")).get("useful"),
					(Long) ((JSONObject) jsonObject.get("votes")).get("cool"), (Long) jsonObject.get("review_count"),
					(String) jsonObject.get("type"), (String) jsonObject.get("user_id"),
					(String) jsonObject.get("name"), (Double) jsonObject.get("average_stars"));

			return y;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * This method parses one line of json file into a string and then
	 * constructs a review object
	 * 
	 * @param jText
	 *            as a string, 1 line of json file
	 * @return review object
	 */
	private static Review getYelpReview(String jText) {
		// parses object with a parser
		// then creates object with constructor
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(jText);
			JSONObject jsonObject = (JSONObject) obj;

			Review y = new Review((String) jsonObject.get("type"), (String) jsonObject.get("business_id"),
					(Long) ((JSONObject) jsonObject.get("votes")).get("cool"),
					(Long) ((JSONObject) jsonObject.get("votes")).get("useful"),
					(Long) ((JSONObject) jsonObject.get("votes")).get("funny"), (String) jsonObject.get("review_id"),
					(String) jsonObject.get("text"), (Long) jsonObject.get("stars"),
					(String) jsonObject.get("user_id"), (String) jsonObject.get("date"));

			return y;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * This method parses one line of json file into a string and then
	 * constructs a restaurant object
	 * 
	 * @param jText
	 *            as a string, 1 line of json file
	 * @return restaurant object
	 */
	private static Restaurant getYelpRestaurant(String jText) {
		// parses object with a parser
		// then creates object with constructor
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(jText);
			JSONObject jsonObject = (JSONObject) obj;

			ArrayList<String> sNeighbourhoods = new ArrayList<String>();

			JSONArray neighbourhoods = (JSONArray) jsonObject.get("neighborhoods");
			Iterator<String> iterator_neigh = neighbourhoods.iterator();
			while (iterator_neigh.hasNext()) {
				sNeighbourhoods.add(iterator_neigh.next());
			}

			ArrayList<String> sSchools = new ArrayList<String>();

			JSONArray schools = (JSONArray) jsonObject.get("schools");
			Iterator<String> iterator_schools = schools.iterator();
			while (iterator_schools.hasNext()) {
				sSchools.add(iterator_schools.next());
			}

			ArrayList<String> sCategories = new ArrayList<String>();

			JSONArray categories = (JSONArray) jsonObject.get("categories");
			Iterator<String> iterator_categories = categories.iterator();
			while (iterator_categories.hasNext()) {
				sSchools.add(iterator_categories.next());
			}

			Restaurant y = new Restaurant((Boolean) jsonObject.get("isopen"), (String) jsonObject.get("url"),
					(Double) jsonObject.get("longitude"), sNeighbourhoods, (String) jsonObject.get("business_id"),
					(String) jsonObject.get("name"), sCategories, (String) jsonObject.get("state"),
					(String) jsonObject.get("type"), (Double) jsonObject.get("stars"), (String) jsonObject.get("city"),
					(String) jsonObject.get("full_address"), (Long) jsonObject.get("review_count"),
					(String) jsonObject.get("photo_url"), sSchools, (Double) jsonObject.get("latitude"),
					(Long) jsonObject.get("price"));

			return y;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

}
