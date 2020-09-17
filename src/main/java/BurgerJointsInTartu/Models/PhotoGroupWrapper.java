package BurgerJointsInTartu.Models;

import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.PhotoGroup;

public class PhotoGroupWrapper {
 
	private Result<PhotoGroup> photoGroup;
	private String venueId;
	
	public PhotoGroupWrapper(Result<PhotoGroup> photoGroup) {
		this.photoGroup = photoGroup;
		
	}
	public PhotoGroupWrapper(Result<PhotoGroup> photoGroup, String venueId) {
		this.photoGroup = photoGroup;
		this.venueId = venueId;
	}
	
	// Getters / setters
	public Result<PhotoGroup> getPhotoGroup() {
		return photoGroup;
	}
	public void setPhotoGroup(Result<PhotoGroup> photoGroup) {
		this.photoGroup = photoGroup;
	}
	
	public String getVenueId() {
		return venueId;
	}
	public void setVenueId(String venueId) {
		this.venueId = venueId;
	}
	
	
	
	
	
}
