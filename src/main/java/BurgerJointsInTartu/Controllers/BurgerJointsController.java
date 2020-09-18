package BurgerJointsInTartu.Controllers;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import BurgerJointsInTartu.Models.MapMarker;
import BurgerJointsInTartu.Services.FoursquareService;
import BurgerJointsInTartu.Utils.xConstants;
import fi.foyt.foursquare.api.FoursquareApiException; 

@RestController
public class BurgerJointsController {

	@GetMapping("/api/burger-joints")
	public String GetBurgerJoints() {
		
		String response = "";
		FoursquareService foursquareService = new FoursquareService();
		
		try {
			List<MapMarker> mapMarkers = foursquareService.GetMapMarkers(xConstants.TARTU_LAT_LNG, 
					 xConstants.TARTU_BURGER_JOINTS_SEARCH_KEYWORDS);
			response = new Gson().toJson(mapMarkers);
		} catch (FoursquareApiException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	
}
