import java.util.HashMap;

import com.google.gson.JsonElement;

//start point
//array of cordinates 

//end point
public class Flights {
	private String type;
	//each entry in this represents a flight
	private JsonElement[] features;
	private HashMap<String, String> properties;
	
	public JsonElement[] getFlights() {
		return this.features;
	}
	
}

