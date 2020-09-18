package BurgerJointsInTartu.Services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.http.HttpMethodName;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import BurgerJointsInTartu.Models.MapMarker;
import BurgerJointsInTartu.Models.MapMarkersArray;
import BurgerJointsInTartu.Models.PhotoGroupWrapper;
import BurgerJointsInTartu.Services.aws.ApiGatewayException;
import BurgerJointsInTartu.Services.aws.ApiGatewayResponse;
import BurgerJointsInTartu.Services.aws.JsonApiGatewayCaller;
import BurgerJointsInTartu.Utils.xConstants;
import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.Photo;
import fi.foyt.foursquare.api.entities.PhotoGroup;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

public class FoursquareService {

	private FoursquareApi foursquareApi;
	
	public FoursquareService() {
		this.foursquareApi = new FoursquareApi(xConstants.FOURSQUARE_CLIENT_ID, xConstants.FOURSQUARE_CLIENT_SECRET, null);
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
	
	public List<MapMarker> GetMapMarkers(String latLng, String[] keyWords) throws FoursquareApiException, URISyntaxException, IOException {
		//List<PhotoGroupWrapper> venuePhotos = new ArrayList<PhotoGroupWrapper>();
		//final String exampleJsonRequest = "{ \"urls\": [\"https://igx.4sqi.net/img/general/500x500/4495867__HsoPFjbsWlCOjVut_11I_LwSNCnvwqm8R_D7bXiYus.jpg\"] }";
		String filePathReadWrire = xConstants.FILE_WITH_JOINTS;
		
		/*
		List<CompactVenue> venues = this.SearchForJoints(latLng, keyWords);
		List<PhotoGroupWrapper> venuePhotos = this.SearchForVenuePhotos(venues, 100);
		
        JsonApiGatewayCaller caller = new JsonApiGatewayCaller(
                xConstants.AWS_IAM_ACCESS_KEY,
                xConstants.AWS_IAM_SECRET_ACCESS_KEY,
                null,
                xConstants.AWS_REGION,
                new URI(xConstants.AWS_API_GATEWAY_ENPOINT)
        );
		
        List<String> allVenuePhotos = new ArrayList<String>();
		Map<String, String> foundPhotos = new HashMap<String, String>();
		Gson gson = new Gson();
		Url url;
		
		for(PhotoGroupWrapper venuePhotoWrapper: venuePhotos) {
			allVenuePhotos = new ArrayList<String>();
			Photo[] photos = venuePhotoWrapper.getPhotoGroup().getResult().getItems();
			for(Photo photo: photos) {
				String photoUrl = photo.getPrefix() + photo.getWidth() + "x" + photo.getHeight() + photo.getSuffix();
				//System.out.println(photoUrl);
				allVenuePhotos.add(photoUrl);
			}
			System.out.println(venuePhotoWrapper.getVenueId());
			String jsonRequest = "{\"urls\"\n: [\n";
			for(int i = 0; i < allVenuePhotos.size(); i++) {
				jsonRequest += "\"" + allVenuePhotos.get(i) + "\"";
				if(i != allVenuePhotos.size() - 1) {
					jsonRequest += ",\n";
				}
	        }
			jsonRequest += "\n]\n}" ;
			System.out.println(jsonRequest);
			
			try {
				ApiGatewayResponse response = caller.execute(HttpMethodName.POST, "/recognize", new ByteArrayInputStream(jsonRequest.getBytes()));
		        System.out.println(response.getBody());
		        url = gson.fromJson(response.getBody(), Url.class);
		        if(url.urlWithBurger != null && !url.urlWithBurger.isEmpty()) {
		        	foundPhotos.put(venuePhotoWrapper.getVenueId(), url.urlWithBurger);
		        }
			} catch(ApiGatewayException apiGateEx) {
				System.out.println("error during fetching data from aws service: " + apiGateEx.getErrorMessage());
			}
		}
		*/

		List<MapMarker> mapMarkers = new ArrayList<MapMarker>();
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePathReadWrire));

        Gson gson = new Gson();
        Object mapMarkersObject = gson.fromJson(bufferedReader, MapMarkersArray.class);
        final JsonObject jsonObject = gson.toJsonTree(mapMarkersObject).getAsJsonObject();
        JsonArray mapMarkersArray = jsonObject.getAsJsonArray("mapMarkers");
        for (JsonElement mm : mapMarkersArray) {
            JsonObject mapMakerObject = mm.getAsJsonObject();
            
			MapMarker mapMarker = new MapMarker(
					mapMakerObject.get("lat").getAsDouble(),
					mapMakerObject.get("lng").getAsDouble(),
					mapMakerObject.get("imgUrl").getAsString(),
					mapMakerObject.get("address").getAsString(),
					mapMakerObject.get("name").getAsString()
			);
			mapMarkers.add(mapMarker);
        }
		
		
		//String strToWrite = "{ [";
		//int counter = 0;

		/*
		for(CompactVenue venue: venues) {
			MapMarker mapMarker = new MapMarker(venue.getLocation().getLat(), venue.getLocation().getLng(), xConstants.DEFAULT_BURGER_URL,
					venue.getLocation().getAddress(), venue.getName());
	
			for(Map.Entry<String, String> set : foundPhotos.entrySet()) {
			    if(set.getKey().equals(venue.getId())) {
			    	mapMarker.setImgUrl(set.getValue());
			    }
			}
			
			mapMarkers.add(mapMarker);
			
			
			//strToWrite += "{\"address\": " + "\"" + mapMarker.getAddress() + "\", ";
			//strToWrite += "\"imgUrl\": " + "\"" + mapMarker.getImgUrl() + "\", ";
			//strToWrite += "\"lat\": " + mapMarker.getLat() + ", ";
			//strToWrite += "\"lng\": " + mapMarker.getLng() + ", ";
			//strToWrite += "\"name\": " + "\"" + mapMarker.getName() + "\"";

			//if(counter != venues.size() - 1) strToWrite += "}, ";
			//else strToWrite += "}";
			//counter++;
			
		}
		*/
		
		/*
		strToWrite += " ] }";
		
	    BufferedWriter writer = new BufferedWriter(new FileWriter(filePathReadWrire, true));
	    writer.write(strToWrite);
	    writer.close();
	    */
		
		return mapMarkers;
	}
}

final class Url {
	
	public String urlWithBurger;

	public Url(String urlWithBurger) {
		this.urlWithBurger = urlWithBurger;
	}
}
