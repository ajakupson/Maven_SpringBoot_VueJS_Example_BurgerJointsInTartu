package BurgerJointsInTartu.Models;

public class MapMarker {

	private Double lat;
	private Double lng;
	private String imgUrl;
	private String address;
	private String name;
	
	public MapMarker(Double lat, Double lng, String imgUrl, String address, String name) {
		this.lat = lat;
		this.lng = lng;
		this.imgUrl = imgUrl;
		this.address = address;
		this.name = name;
	}
	
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
