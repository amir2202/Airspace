import java.awt.geom.Path2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
//in airspace
//out airspace
//given a cordinate
public class Airspace {
	private HashMap<String,String> properties;
	private Geometry geometry;
	public Geometry getGeometry() {
		//update later, initialze once
		this.geometry.createShape();
		return this.geometry;
	}
	
	private boolean inAirspace(double longitude, double latitude) {
		return this.getGeometry().contains(longitude, latitude);
	}
	
	public boolean inAirspace(Flight flight) {
		FlightGeometry temp = flight.getGeometry();
		double[][] cords = temp.getCords();
		for(int pairs = 0; pairs < temp.getCords().length;pairs++) {
			double longitude = cords[pairs][0];
			double latitude = cords[pairs][1];
			if(this.inAirspace(longitude, latitude)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean inAirspace(Aerodrome aerodome) {
		return this.inAirspace(aerodome.getGeometry().getLongitude(), aerodome.getGeometry().getLatitude());
	}
	
	public boolean domestic(Flight flight) {
		return false;
	}
}


class Geometry{
	String type;
	double[][][] coordinates;
	private transient Path2D airregion;
	
	public void createShape() {
		double[][][] data = this.coordinates;
		airregion = new Path2D.Double();
		double initialx = 0;
		double initialy = 0;
		for(int cordinatesets = 0; cordinatesets < data.length;cordinatesets++) {
			for(int latitude = 0; latitude < data[0].length; latitude++) {
				double long1 = data[cordinatesets][latitude][0];
				for(int longitude = 0; longitude < data[0][1].length; longitude++) {
					if(longitude == 0 && latitude == 0) {
						initialy = data[cordinatesets][latitude][1];
						initialx = long1;
						airregion.moveTo(initialx,initialy);
					}
					double lati = data[cordinatesets][latitude][1];
					airregion.lineTo(long1, lati);
				}
			}
		}
	}
	
	public boolean contains(double longitude, double latitude) {
		return airregion.contains(longitude,latitude);
	}
	
	
	public double[][][] getCords(){
		return this.coordinates;
	}
	
}

