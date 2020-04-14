import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/**
 * 
 * @author amirghafghazi
 *
 */
public class FlightAreaControl {
	private Airspace airspace;
	private ArrayList<Flight> allflights;
	public FlightAreaControl(Airspace airspace,Flights flights) {
		allflights = new ArrayList<Flight>();
		this.airspace = airspace;
		Gson gson = new Gson();
		airspace.getGeometry().createShape();
		JsonElement[] flightlist = flights.getFlights();
		for(JsonElement element: flightlist) {
			Flight flight = gson.fromJson(element, Flight.class);
			allflights.add(flight);
		}
		
	}

	
	public static void main(String[] args) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Gson gson = new Gson();
		String dir = System.getProperty("user.dir");;
		Airspace airspace = gson.fromJson(new FileReader(args[0]), Airspace.class);
		Flights flights = gson.fromJson(new FileReader(args[1]), Flights.class);
		FlightAreaControl control = new FlightAreaControl(airspace, flights);
		Aerodromes aerodromes = gson.fromJson(new FileReader(args[2]), Aerodromes.class);
		aerodromes.initialize();
		control.showCharges();
	}
	
	public void showCharges() {
		
		for(Flight flight: this.allflights) {
			int rate = 0;
			if(this.airspace.inAirspace(flight) == true) {
				rate = 1;
				String departure = flight.getProperty("departure_airport");
				String arrival = flight.getProperty("arrival_airport");
				Aerodrome dep = Aerodromes.airports.get(departure);
				Aerodrome arr = Aerodromes.airports.get(arrival);
				if(this.airspace.inAirspace(dep) && this.airspace.inAirspace(arr)) {
					rate = 0;
				}
				if(!this.airspace.inAirspace(dep) && !this.airspace.inAirspace(arr)) {
					rate = 2;
				}
			}
			System.out.println(flight.getProperty("flight_number") +" " + flight.getProperty("airline")+" " + rate);
		}
	}
}
