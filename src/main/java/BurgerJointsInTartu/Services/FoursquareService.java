package BurgerJointsInTartu.Services;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.amazonaws.http.HttpMethodName;
import com.google.gson.Gson;

import BurgerJointsInTartu.Models.MapMarker;
import BurgerJointsInTartu.Models.PhotoGroupWrapper;
import BurgerJointsInTartu.Services.aws.ApiGatewayResponse;
import BurgerJointsInTartu.Services.aws.JsonApiGatewayCaller;
import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.Photo;
import fi.foyt.foursquare.api.entities.PhotoGroup;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

public class FoursquareService {

	private final String FOURSQUARE_CLIENT_ID = "ULTN0IESIICWPHWBN1RHEVGCK3HXNN1TD2MQ1PT2M0AZVNQL";
	private final String FOURSQUARE_CLIENT_SECRET = "JFFGBVR5VJGWPKYYGOHYRJ04ITKYAXFYIIW5CJCBALAMOWH5";
	private FoursquareApi foursquareApi;
	
	private final String AWS_IAM_ACCESS_KEY = "{KEY}";
	private final String AWS_IAM_SECRET_ACCESS_KEY = "{SECRET}";
	private final String AWS_REGION = "eu-west-1";
	private final String AWS_API_GATEWAY_ENPOINT = "https://pplkdijj76.execute-api.eu-west-1.amazonaws.com/prod";
	
	public FoursquareService() {
		this.foursquareApi = new FoursquareApi(FOURSQUARE_CLIENT_ID, FOURSQUARE_CLIENT_SECRET, null);
	}
	
	public Result<VenuesSearchResult> VenuesSearch(String latLng, String searchKeyWord) throws FoursquareApiException {
		Result<VenuesSearchResult> result = this.foursquareApi.venuesSearch(latLng, searchKeyWord, null, null, null, null, null, null);
		
		return result;
	}
	
	public List<CompactVenue> SearchForJoints(String latLng, String[] keyWords) throws FoursquareApiException {
		List<CompactVenue> result = new ArrayList<CompactVenue>();
		for(String keyWord: keyWords) {
			Result<VenuesSearchResult> venuesSearchResult = this.VenuesSearch(latLng, keyWord);
			
			if(venuesSearchResult.getMeta().getCode() == 200) {
				result.addAll(Arrays.asList(venuesSearchResult.getResult().getVenues()));	
			}
		}
		
		return result;
	}
	
	public List<PhotoGroupWrapper> SearchForVenuePhotos(List<CompactVenue> venues, Integer limit) throws FoursquareApiException {
		List<PhotoGroupWrapper> result = new ArrayList<PhotoGroupWrapper>();
		
		for(CompactVenue compactVenue: venues) {
			Result<PhotoGroup> photosSearchResult = this.foursquareApi.venuesPhotos(compactVenue.getId(), null, limit, null);
			if(photosSearchResult.getMeta().getCode() == 200) {
				result.add(new PhotoGroupWrapper(photosSearchResult, compactVenue.getId()));
			}
		}
		
		return result;
	}
	
	public List<MapMarker> GetMapMarkers(String latLng, String[] keyWords) throws FoursquareApiException, URISyntaxException {
		List<CompactVenue> venues = this.SearchForJoints(latLng, keyWords);
		List<PhotoGroupWrapper> venuePhotos = this.SearchForVenuePhotos(venues, 10);
		
		//final String exampleJsonRequest = "{ \"urls\": [\"https://igx.4sqi.net/img/general/500x500/4495867__HsoPFjbsWlCOjVut_11I_LwSNCnvwqm8R_D7bXiYus.jpg\"] }";
		
        JsonApiGatewayCaller caller = new JsonApiGatewayCaller(
                AWS_IAM_ACCESS_KEY,
                AWS_IAM_SECRET_ACCESS_KEY,
                null,
                AWS_REGION,
                new URI(AWS_API_GATEWAY_ENPOINT)
        );
		
        List<String> allVenuePhotos = new ArrayList<String>();
		Map<String, String> foundPhotos = new HashMap<String, String>();
		for(PhotoGroupWrapper venuePhotoWrapper: venuePhotos) {
			Photo[] photos = venuePhotoWrapper.getPhotoGroup().getResult().getItems();
			for(Photo photo: photos) {
				String photoUrl = photo.getPrefix() + photo.getWidth() + "x" + photo.getHeight() + photo.getSuffix();
				//System.out.println(photoUrl);
				allVenuePhotos.add(photoUrl);
			}
			
			String jsonRequest = "{\"urls\"\n: [\n";
			for(String url : allVenuePhotos) {
				jsonRequest += "\"" + url + "\",\n";
	        }
			jsonRequest += "\n]\n}" ;
			System.out.println(jsonRequest);
			//ApiGatewayResponse response = caller.execute(HttpMethodName.POST, "/recognize", new ByteArrayInputStream(jsonRequest.getBytes()));
	        //System.out.println(response.getBody());
	        
			//foundPhotos.put(venuePhotoWrapper.getVenueId(), photoUrl);
		}

		
		
		List<MapMarker> mapMarkers = new ArrayList<MapMarker>();
		for(CompactVenue venue: venues) {
			MapMarker mapMarker = new MapMarker(venue.getLocation().getLat(), venue.getLocation().getLng(), "",
					venue.getLocation().getAddress(), venue.getName());
			mapMarkers.add(mapMarker);
		}
		
		return mapMarkers;
	}
	
}
