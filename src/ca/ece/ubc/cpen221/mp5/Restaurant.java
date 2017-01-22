package ca.ece.ubc.cpen221.mp5;

import java.util.ArrayList;

/** 
 * Abstract data type for a Restaurant object 
 * A restaurant is represented entirely by its attributes as dictated by the Yelp dataset
 *
 */
public class Restaurant { 
	
	//ATTRIBUTES
	private Boolean isopen;
	private String url;
	private Double longitude;
	private ArrayList<String> neighbourhoods;
	private String business_id;
	private String name;
	private ArrayList<String> categories; 
	private String state;
	private String type;
	private Double stars;
	private String city;
	private String full_address;
	private Long review_count;
	private String photo_url;
	private ArrayList<String> schools;
	private Double latitude;
	private Long price;

	// constructor creates an instance of a Restaurant 
	public Restaurant(Boolean isopen, String url, Double longitude, ArrayList<String> neighbourhoods,
			String business_id, String name, ArrayList<String> categories, String state,
			String type, Double stars, String city, String full_address, Long review_count, String photo_url,
			ArrayList<String> schools, Double latitude, Long price) {
		this.isopen = isopen;
		this.url = url;
		this.longitude = longitude;
		this.neighbourhoods = neighbourhoods;
		this.business_id = business_id;
		this.name = name;
		this.categories = categories;

		this.state = state;
		this.type = type;
		this.stars = stars;
		this.city = city;
		this.full_address = full_address;
		this.review_count = review_count;
		this.photo_url = photo_url;
		this.schools = schools;
		this.latitude = latitude;
		this.price = price;
	}
	
	/**
	 * Takes in nothing as the arguement and gets the business id of the restaurant object
	 * @return business_id as a String
	 */
	public String getbusiness_id(){
		return business_id;
	}
	/**
	 * Takes in nothing as the arguement and gets the latitude of the restaurant object
	 * @return latitude as a double
	 */
	public Double getX(){
		return latitude;
	}
	/**
	 * Takes in nothing as the arguement and gets the longitude of the restaurant object
	 * @return longitude as a double
	 */
	public Double getY(){
		return longitude;
	}
	/**
	 * Takes in nothing as the arguement and gets the price of the restaurant object
	 * @return price as an Integer object
	 */
	public Long getprice(){
		return price;
	}
	/**
	 * Takes in nothing as the arguement and gets the stars of the restaurant object
	 * @return stars as a float object
	 */
	public Double getstars(){
		return stars;
	}
	/**
	 * Takes in nothing as the arguement and gets the business id of the restaurant object
	 * @return business id as a String
	 */
	public String getrestaurant_id() {
		
		return business_id;
	} 
	
	public String getName(){ 
		return name;
	}
	/** 
	 * Takes restaurant object and turns into JSON formatted string
	 * @return JSON string
	 */
	public String toJSONString(){  
		String string = ""; 
		
		string = string + "{\"open\": " + isopen +", \"url\": " + "\"" + url + "\"" + ", \"longitude\": " + longitude +", \"neighborhoods\": " + "[";
		
		
		for (String neighbourhood: neighbourhoods){ 
			 string = string + "\"" + neighbourhood + "\", "; 
		} 
		string = string.substring(0, string.length()-3); // trim off last 2 characters 
		string = string + "], \"business_id\": \"" + business_id +"\", \"name\": \"" + name + "\", \"categories\": ["; 
		
		for (String category: categories){ 
			string = "\"" + category + "\", "; 
		}  
		
		string = string.substring(0, string.length()-3); // trim off last 2 characters  
		string = string + "], \"state\": \"" + state + "\", \"type\": " + "\"" + type + "\"" + ", \"stars\": " +  stars  + ", \"city\": " + "\"" + city + "\""; 
		string = string + ", \"full_address\": " + "\"" + full_address + "\"" + ", \"review_count\": " + review_count + ", \"photo_url\": " + "\"" + photo_url + "\"" + ", \"schools\": [";
		
		for (String school: schools){ 
			string = "\"" + school + "\", "; 
		}   
		string = string.substring(0, string.length()-3); // trim off last 2 characters 
		string = string +  "], \"latitude\": " + latitude + ", \"price\": " + price +"}";
				
		return string;
	}
}
