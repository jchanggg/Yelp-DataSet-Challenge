package ca.ece.ubc.cpen221.mp5;

/** 
 * Abstract data type for a User object 
 * A User  is represented entirely by its attributes as dictated by the Yelp dataset
 *
 */

public class User { 
	
	//ATTRIBUTES
	private String url;
	private Long votes_funny;
	private Long votes_useful;
	private Long votes_cool;
	private Long review_count;
	private String type;
	private String user_id;
	private String name;
	private double average_stars;
/**
 * 
 * @param url 
 * @param votes_funny
 * @param votes_useful
 * @param votes_cool
 * @param review_count
 * @param type
 * @param user_id
 * @param name
 * @param double1
 */
	public User(String url, Long votes_funny, Long votes_useful, Long votes_cool, Long review_count,
			String type, String user_id, String name, Double double1) {
			this.url = url;
	
			this.votes_funny = votes_funny;
			this.votes_useful = votes_useful;
			this.votes_cool = votes_cool;
			this.review_count = review_count;
			this.type = type;
			this.user_id = user_id;
			this.name = name;
			this.average_stars = double1;
	}
	
	//returns user id
	public String getuser_id(){
		return user_id;
	}
	//returns a user's name
	public String getName() {
		return name;
	}
}


