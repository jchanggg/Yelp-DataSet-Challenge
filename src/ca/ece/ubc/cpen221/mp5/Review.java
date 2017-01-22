package ca.ece.ubc.cpen221.mp5;
/** 
 * Abstract data type for a Review object 
 * A review is represented entirely by its attributes as dictated by the Yelp dataset. 
 * NOTE: the business id links a review to a restaurant while the review id is its unique identifier
 */
public class Review { 
	
	// ATTRIBUTES
	private String type;
	private String business_id;
	private Long votes_cool;
	private Long votes_useful;
	private Long votes_funny;
	private String review_id;
	private String text;
	private Long stars;
	private String user_id;
	private String date;


	/**
	 * Takes in all attributes of a yelp review and stores it into this data type. All attributes are passed through the constructor
	 * and is immutable
	 * @param type
	 * @param business_id
	 * @param votes_cool
	 * @param votes_useful
	 * @param votes_funny
	 * @param review_id
	 * @param text
	 * @param 1
	 * @param user_id
	 * @param date
	 */
	public Review(String type, String business_id, Long votes_cool, Long votes_useful, Long votes_funny,
			String review_id, String text, Long stars, String user_id, String date) {
		this.business_id = business_id;
		this.votes_funny = votes_funny;
		this.votes_useful = votes_useful;
		this.votes_cool = votes_cool;
		this.review_id = review_id;
		this.type = type;
		this.user_id = user_id;
		this.text = text;
		this.stars = stars;
		this.date = date;
	}
	/**Takes in nothing as the constructor and returns the review id as an string obj
	 * @return review id as a string
	 */
	public String getreview_id(){
		return review_id;
	}
	
	/**Takes in nothing as the constructor and returns the business id as an string obj
	 * @return business id as a string
	 */
	public String getrestaurant_id(){
		return business_id;
	}
	
	/**Takes in nothing as the constructor and returns the user id as an string obj
	 * @return user id as a string
	 */
	public String getuser_id(){
		return user_id;
	}
	
	/**Takes in nothing as the constructor and returns the number of stars given as an integer obj
	 * @return stars as an integer obj
	 */
	public Long getstars(){
		return stars;
	}
	/** 
	 *Takes review and creates JSON string
	 * @return JSON formatted string
	 */
	public String toJSONString(){ 
		String string = "";  
		string = string + "[ \"type\": " + "\"" + type +  "\"" + ", \"business_id\": " + "\"" + business_id + "\"" + ", \"votes\": {"; 
		string = string + "\"cool\": " + votes_cool + ", \"useful\": " + votes_useful + ", \"funny\": " + votes_funny + "}"; 
		string = string + ", \"review_id\": " + "\"" + review_id + "\"" + ", \"text\": " + "\"" + text + "\"" + ", \"stars\"" + stars; 
		string = string + ", \"user_id\": " + "\"" + user_id + "\"" + ", \"date\": "+ "\"" + date + "\"}";
		return string;
	}

}
