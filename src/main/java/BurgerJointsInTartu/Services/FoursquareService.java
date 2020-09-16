package BurgerJointsInTartu.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import BurgerJointsInTartu.Models.CompactVenueMod;
import BurgerJointsInTartu.Models.MapMarker;
import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.PhotoGroup;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

public class FoursquareService {

	private String clientId = "ULTN0IESIICWPHWBN1RHEVGCK3HXNN1TD2MQ1PT2M0AZVNQL";
	private String clientSecret = "RCGHJXDJ1J5KDCO3BT3NB5P0BAT3I0DXMM4YJXWMVX3AWNV2";
	private FoursquareApi foursquareApi;
	
	public FoursquareService() {
		this.foursquareApi = new FoursquareApi(clientId, clientSecret, null);
	}
	
	public Result<VenuesSearchResult> VenuesSearch(String latLng, String searchKeyWord) throws FoursquareApiException {
		Result<VenuesSearchResult> result = this.foursquareApi.venuesSearch(latLng, searchKeyWord, null, null, null, null, null, null);
		
		return result;
	}
	
	public List<CompactVenueMod> SearchForJoints(String latLng, String[] keyWords) throws FoursquareApiException {
		List<CompactVenue> compactVenues = new ArrayList<CompactVenue>();
		List<CompactVenueMod> result = new ArrayList<CompactVenueMod>();
		for(String keyWord: keyWords) {
			Result<VenuesSearchResult> venuesSearchResult = this.VenuesSearch(latLng, keyWord);
			
			if(venuesSearchResult.getMeta().getCode() == 200) {
				compactVenues.addAll(Arrays.asList(venuesSearchResult.getResult().getVenues()));	
			}
			
			List<CompactVenueMod> venuesFixedMissingAddresses = this.FixMissingAddresses(venuesSearchResult, compactVenues);
			result.addAll(venuesFixedMissingAddresses);
		}
		
		return result;
	}
	
	public List<Result<PhotoGroup>> SearchForVenuePhotos(List<CompactVenue> venues, Integer limit) throws FoursquareApiException {
		List<Result<PhotoGroup>> result = new ArrayList<Result<PhotoGroup>>();
		
		for(CompactVenue compactVenue: venues) {
			Result<PhotoGroup> photosSearchResult = this.foursquareApi.venuesPhotos(compactVenue.getId(), null, limit, null);
			if(photosSearchResult.getMeta().getCode() == 200) {
				result.add(photosSearchResult);
			}
		}
		
		return result;
	}
	
	public List<MapMarker> GetMapMarkers(String latLng, String[] keyWords) throws FoursquareApiException {
		List<CompactVenueMod> venues = this.SearchForJoints(latLng, keyWords);
		//List<Result<PhotoGroup>> venuePhotos = this.SearchForVenuePhotos(venues, 10);
		
		List<MapMarker> mapMarkers = new ArrayList<MapMarker>();
		for(CompactVenueMod venue: venues) {
			MapMarker mapMarker = new MapMarker(venue.getLocation().getLat(), venue.getLocation().getLng(), "",
					venue.getAddress(), venue.getName());
			mapMarkers.add(mapMarker);
		}
		
		return mapMarkers;
	}
	
	// not used
	private List<CompactVenueMod> FixMissingAddresses(Result<VenuesSearchResult> venuesSearchResult, List<CompactVenue> venues) {
		List<CompactVenueMod> compactVenuesMod = new ArrayList<CompactVenueMod>();
		
		for(CompactVenue compactVenue: venuesSearchResult.getResult().getVenues()) {
			for(CompactVenue venue: venues) {
				if(compactVenue.getId().equals(venue.getId())) {
					CompactVenueMod compactVenueMod = new CompactVenueMod(venue);
					compactVenueMod.setAddress(compactVenue.getLocation().getAddress());
					compactVenuesMod.add(compactVenueMod);
				}
			}
		}
		
		return compactVenuesMod;
	}
	
}
