package ca.ece.ubc.cpen221.mp5;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Wrapper for a RestaurantDB instance 
 * Server can be started from command line using   
 * "java ca.ece.ubc.cpen221.mp5.RestaurantDBServer 4949" where 4949 is the port number 
 * It is a multi-threaded server
 * 
 *
 */  

public class RestaurantDBServer { 
	
	private static final String RESTAURANTS_PATH = "data/restaurants.json";
	private static final String REVIEWS_PATH = "data/reviews.json";
	private static final String USERS_PATH = "data/users.json";
	private static final int DEFAULT_PORT = 4949; 
	private RestaurantDB database;
	int listenPort;


	/**
	 * Creates a new RestaurantDBServer on the given port.  Default port is 4949. 
	 * 
	 * @param a port number
	 * 		 Must be between 1 and 65535. 
	 * 		 If invalid port is given, will use default (4949).
	 */
	public RestaurantDBServer(Integer port) {

		if (port > 65535 || port < 0) {
			listenPort = DEFAULT_PORT;
		} else {
			listenPort = port;
		}

		if (database == null) { 
			// create a new instance of RestaurantDB
			database = new RestaurantDB(RESTAURANTS_PATH, REVIEWS_PATH, USERS_PATH);
		}
	} 
	
	/* Methods for some Simple Requests*/
	
	/**
	 * Gets a random review from the database. Restaurants must be unique.  
	 * Error messages will be returned otherwise. 
	 * 
	 * @param name
	 * 		restaurant's name.
	 * @return
	 * 		A random review for the restaurant, in JSON format.
	 */ 
	
	public String getRandomReview(String name) { 
		
		// get a list of all the restaurants in the database
		List<Restaurant> restaurants = database.getAllRestaurants();  
		List<Review> allReviews = database.getAllReviews();
	    List<Restaurant> foundRestaurants = new ArrayList <Restaurant> ();
		
	    // find all restaurants with the name 
		for (Restaurant restaurant: restaurants) { 
			if (restaurant.getName().equals(name)) { 
				foundRestaurants.add(restaurant);
			}
		} 
		
		// there must be 1 and only 1 
		if  (foundRestaurants.size() == 0)
			return "ERR: NO_RESTAURANT_FOUND";
		else if (foundRestaurants.size() != 1)
			return "ERR: MULTIPLE_RESTAURANTS";
		
		//find all the reviews for the restaurant
		List<Review> desiredReviews = new ArrayList <Review> (); 
		Restaurant theRestaurant = foundRestaurants.get(0); 
		
		for (Review review: allReviews){ 
			if (review.getbusiness_id().equals(theRestaurant.getbusiness_id())){ 
				desiredReviews.add(review);
			}
		}
		//		
		if (desiredReviews.size() == 0)
			return "ERR: NO_REVIEWS_FOUND"; 
		
		//return a random one
		return desiredReviews.get(new Random().nextInt(desiredReviews.size())).toJSONString();
	}
	
	/**
	 * Finds the restaurant with the given id in the database
	 * 
	 * @param id
	 * 		the restaurant ID.
	 * @return
	 * 		The restaurant's information as a JSON formatted string
	 */
	public String getRestaurant(String id) { 
		
		// get all restaurants in database
		List<Restaurant> restaurants = database.getAllRestaurants();  
		Restaurant target = null; 
		
		//search for one with matching id
		for (Restaurant restaurant: restaurants) { 
			if (restaurant.getbusiness_id().equals(id)) { 
			target = restaurant;
			}
		}   
		
		//return error message if not found
		if (target == null){ 
			return "ERR: NO_RESTAURANT_FOUND";
		} 
		
		//otherwise return data as JSONString
		return target.toJSONString();
	}
	
	/**
	 * Tries to add a new user to the database.
	 * 
	 * @param info
	 * 		the information for the new user; should have a name
	 * @return
	 * 		The new user's information as a JSON formatted string or an error message
	 */
	public String addUser(String info) {
		try {
			database.addUser(info);
			return info;
		} catch (IllegalArgumentException e) {
			return "ERR: INVALID_USER_STRING";
		}
	}
	
	/**
	 * Tries to add a new restaurant to the database.
	 * 
	 * @param info
	 * 		the restaurant's information as a JSON-formatted string.
	 * @return
	 * 		The newly created restaurant's information as a JSON-formatted string,
	 * 		or an error message 
	 */
	public String addRestaurant(String info) {
		
		RestaurantDB.getYelpRestaurant (info);
		
		
		try{
		database.addRestaurant(info);
		return info;
		} catch (IllegalArgumentException e) {
			return "ERR: INVALID_RESTAURANT_STRING";} 
		
		
		
	}
	
	/**
	 * Tries to add a new review to the database.
	 * 
	 * @param info
	 * 		the review's information as a JSON-formatted string.
	 * @return
	 * 		The newly created review's information as a JSON-formatted string,
	 * 		or an error message if add was unsuccessful.
	 */
	public String addReview(String info) {
		try {
			Review review = database.addReview(info);
			return review.toJSONString();
		} catch (IllegalArgumentException e) {
			return "ERR: INVALID_REVIEW_STRING";
		}
	}  
	
	/* Running the Server */ 
	/** 
	 * Helper method creates spinoff socket
	 * @param socket
	 * @return
	 */
	private ClientThread spinOff(Socket socket) {
		return new ClientThread(socket);
	}
	/**
	 * Main program that runs the primary server socket to listen for client connections
	 * 
	 * @param argv[0]
	 * 		argv[0] is port to run on, which must be a number [1,65535].
	 * 		Use 0 for a random port. If no port is provided, default 4949 will be
	 * 		used.
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static void main(String[] argv) {
		
		int port;		
		if (argv.length == 0) {
			port = DEFAULT_PORT;
		} else {
			port = new Integer(argv[0]);
		}
		
		RestaurantDBServer server;
		server = new RestaurantDBServer(port); 
		
		

		try {
			ServerSocket serverSocket = new ServerSocket();
			
	
			serverSocket.setReuseAddress(true);
			serverSocket.bind(new InetSocketAddress(server.listenPort));
			
			if (server.listenPort == 0) {
				server.listenPort = serverSocket.getLocalPort();
			}
			//handle multi-threading...
			System.out.println(String.format("Listening on port %d", server.listenPort));
			while (true) {
				// listen for new connections
				Socket socket = serverSocket.accept();
				System.out.println("Connected");
				// spin off a thread to handle communication with that client
				RestaurantDBServer.ClientThread spinOff = server.spinOff(socket);
				new Thread(spinOff).start();
			}
		} catch (IOException e) {
			System.out.println("Error: Server unable to start");
			e.printStackTrace();
		}
	} 
	
	/**
	 *
	 * Executes client commands on DB and returns results
	 */
	private class ClientThread implements Runnable { 
		
		private Socket socket;
		public ClientThread(Socket socket) {
					this.socket = socket;
				} 
		
		public void run() {
			
			try {
				
				PrintStream pStream = new PrintStream(socket.getOutputStream());
				BufferedReader clientInput = new BufferedReader
						(new InputStreamReader(socket.getInputStream()));
				
				String query = null;
				
			
				//begin parsing requests
				while ((query = clientInput.readLine()) != null) {
				
					// treat a request and parses it...these are in all caps and no spaces
					String tosplit = "[ ]+";
					String[] request = query.split(tosplit, 2);

					// requests all depend on string at request [0]
					switch (request[0]) {
						case "RANDOMREVIEW":
							pStream.println(getRandomReview(request[1]));
							break;
						case "GETRESTAURANT":
							pStream.println(getRestaurant(request[1]));
							break;
						case "ADDUSER":
							pStream.println(addUser(request[1]));
							break;
						case "ADDRESTAURANT":
							pStream.println(addRestaurant(request[1]));
							break;
						case "ADDREVIEW":
							pStream.println(addReview(request[1]));
							break;
						default:
							pStream.print("Unknown Request !!!");
						}	
					}
				
				// close the socket
				pStream.close();
				socket.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
