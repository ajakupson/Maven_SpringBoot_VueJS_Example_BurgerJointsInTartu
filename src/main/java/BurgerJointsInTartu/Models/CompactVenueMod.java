package BurgerJointsInTartu.Models;

import fi.foyt.foursquare.api.entities.CompactVenue;

public class CompactVenueMod extends CompactVenue {

	private static final long serialVersionUID = -8465151702494654970L;
	private String address;
	
	public CompactVenueMod(CompactVenue cv) {
		super();
		this.id = cv.getId();
		this.location = cv.getLocation();
		this.name = cv.getName();
		this.address = cv.getLocation().getAddress();
	}
	
	public String getAddress() {
		return this.address;
	}
	public void setAddress(String newAddress) {
		this.address = newAddress;
	}
	
}

