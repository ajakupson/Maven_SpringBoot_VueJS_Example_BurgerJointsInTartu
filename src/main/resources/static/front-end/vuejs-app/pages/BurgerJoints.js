import MainLayout from '../layouts/Main'
import VLink from '../components/VLink'
import axios from 'axios'

export default {
	template: `<main-layout>
				<h1>Burger Joints In Tartu</h1>
				<p><v-link href="/">Home</v-link></p>
				<div id="map-container"></div>
			   </main-layout>`,
	components: {
	'v-link': VLink,
	'main-layout': MainLayout
    },
	data: function() {
		return {
			hereMapsPlatform: null,
			mapMarkersData: [],
		}
	},
	created: function() {},
	mounted: function() {
		console.log("burger joints mounted");
		
		this.hereMapsPlatform = new H.service.Platform({
			'apikey': 'ReRwqKNLBA7zeTBC27_hlEPk5w6cHv-43WNAoORo_ho'
		});
		var defaultLayers = this.hereMapsPlatform.createDefaultLayers();
		var map = new H.Map(
    		document.getElementById('map-container'),
    		defaultLayers.vector.normal.map,
    		{
      			zoom: 10,
      			center: { lat: 58.378025, lng: 26.728493 } // default Tartu
    		});

		var ui = H.ui.UI.createDefault(map, defaultLayers);
		var behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));
		
		axios
		    .get('/api/burger-joints')
		    .then(response => {
		        console.log("response.data", response.data);

				if(response.data) {
					this.mapMarkersData = response.data;
					this.mapMarkersData.forEach(mapMarkerData => {
						
						// to fix missing address
						opencage
  							.geocode({ q: mapMarkerData.lat + ', ' + mapMarkerData.lng, key: 'b3f0b519255244b389a33cf8b23b726f' })
  							.then(data => {
    							
								  if (data.status.code == 200) {
								  	if (data.results.length > 0) {
								      var place = data.results[0];
		
								      //console.log(place.formatted);
								      //console.log(place.components.road);
									  //console.log(place.components.house_number);
							
									  mapMarkerData.address = place.formatted;
									}
								  }
						
								  var domMapMarker = `<div class="dom-map-marker">
													  	<p class="name">${mapMarkerData.name}</p><br/>
														<p class="address">Address: ${mapMarkerData.address}</p>
														<div class="burger-img-container">
															<img src="../front-end/assets/img/hamburger-icon.png"/>
														</div>
													  </div>`;
											
								 var mapMarker = new H.map.DomIcon(domMapMarker),
	    						 coords = {lat: mapMarkerData.lat, lng: mapMarkerData.lng },
	    						 marker = new H.map.DomMarker(coords, {icon: mapMarker});
								 map.addObject(marker);
  							})
  							.catch(error => {
    							console.log('error', error.message);
  							});
					});	
				}
		    })
		    .catch(error => console.log(error));

	},
	methods: {
		setInteractive: function(map) {
		  // get the vector provider from the base layer
		  var provider = map.getBaseLayer().getProvider();
		
		  // get the style object for the base layer
		  var style = provider.getStyle();
		
		  var changeListener = (evt) => {
		    if (style.getState() === H.map.Style.State.READY) {
		      style.removeEventListener('change', changeListener);
		
		      // enable interactions for the desired map features
		      style.setInteractive(['places', 'places.populated-places'], true);
		    }
		  };
		  style.addEventListener('change', changeListener);
		}
	}
}