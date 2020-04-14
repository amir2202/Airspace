import java.util.HashMap;

public class Flight {
//	private HashMap<String,String> properties;
	private String id;
	private FlightGeometry geometry;
	private HashMap<String,String> properties;
	
	public String getProperty(String property) {
		return properties.get(property);
	}
	
	public FlightGeometry getGeometry() {
		return this.geometry;
	}
	
}

class FlightGeometry{
	//longitude latitude
	double[][] coordinates;
	
	public double[][] getCords(){
		return this.coordinates;
	}
}