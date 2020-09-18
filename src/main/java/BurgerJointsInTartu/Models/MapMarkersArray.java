package BurgerJointsInTartu.Models;

public class MapMarkersArray {
	private MapMarker[] mapMarkers;

	public MapMarkersArray(MapMarker[] mapMarkers) {
		super();
		this.mapMarkers = mapMarkers;
	}
	
	public MapMarker[] getMapMarkers() {
		return mapMarkers;
	}
	public void setMapMarkers(MapMarker[] mapMarkers) {
		this.mapMarkers = mapMarkers;
	}

	@Override
	public String toString() {
		return "MapMarkersArray [mapMarkers=" + mapMarkers.toString() + "]";
	}
	
	
}
