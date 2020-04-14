import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class Aerodromes {
	JsonElement[] features;
	transient static HashMap<String, Aerodrome> airports;
	public void initialize() {
		Gson gson = new Gson();
		airports = new HashMap<String,Aerodrome>();
		for(JsonElement element: this.features) {
			Aerodrome temp = gson.fromJson(element, Aerodrome.class);
			String designator = temp.getProperty("designator");
			airports.put(designator, temp);
		}
	}
}

class Aerodrome{
	private HashMap<String, String> properties;
	private AerodromeGeometry geometry;
	
	public String getProperty(String property) {
		return this.properties.get(property);
	}
	
	public AerodromeGeometry getGeometry() {
		return this.geometry;
	}
}

class AerodromeGeometry{
	private double[] coordinates;
	public double[] getPoint() {
		return this.coordinates;
	}
	public double getLatitude() {
		return this.coordinates[1];
	}
	
	public double getLongitude() {
		return this.coordinates[0];
	}
}
