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
			hereMapsPlatform: null
		}
	},
	created: function() {},
	mounted: function() {
		console.log("burger joints mounted");
		
		axios
		    .get('/api/burger-joints')
		    .then(response => {
		        console.log("response.data", response.data);
		    })
		    .catch(error => console.log(error));
		
		/*var that = this;
		
		var fourSquareUrl = 'https://api.foursquare.com/v2/venues/explore?' +
							 'client_id=ULTN0IESIICWPHWBN1RHEVGCK3HXNN1TD2MQ1PT2M0AZVNQL&' +
							 'client_secret=RCGHJXDJ1J5KDCO3BT3NB5P0BAT3I0DXMM4YJXWMVX3AWNV2&' +
							 'v=20180323&' +
							 'limit=1000&' +
							 'll=58.378025, 26.728493&' +
							 'query=mcdonalds&' + 
							 'radius=5000';
		
		axios
        	 .get(fourSquareUrl)
             .then(response => {
                console.log("response.data", response.data);

				var items = response.data.response.groups[0].items;
				console.log("response.data.response.groups", response.data.response.groups);

				setTimeout(function() {
					this.hereMapsPlatform = new H.service.Platform({
			  			'apikey': 'ReRwqKNLBA7zeTBC27_hlEPk5w6cHv-43WNAoORo_ho'
					});
					var defaultLayers = this.hereMapsPlatform.createDefaultLayers();
					var map = new H.Map(
			    		document.getElementById('map-container'),
			    		defaultLayers.vector.normal.map,
			    		{
			      			zoom: 10,
			      			center: { lat: 58.378025, lng: 26.728493 }
			    		});
		
					var ui = H.ui.UI.createDefault(map, defaultLayers);
					var behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));
					
					console.log("items", items);
					if(items) {
						for(var i = 0; i < items.length; i++) {
							addMarkersToMap(map, items[i].venue.location.lat, items[i].venue.location.lng);
							//document.body.innerHTML += '<img src="' + items[i].venue.categories[0].icon.prefix + items[i].venue.categories[0].id + items[i].venue.categories[0].icon.suffix + '">';
						
						var fourSquareUrl2 = 'https://api.foursquare.com/v2/venues/' + items[i].venue.id +'/photos?' +
							 'client_id=ULTN0IESIICWPHWBN1RHEVGCK3HXNN1TD2MQ1PT2M0AZVNQL&' +
							 'client_secret=RCGHJXDJ1J5KDCO3BT3NB5P0BAT3I0DXMM4YJXWMVX3AWNV2&' +
							 'limit=10&' + 
							 'v=20150916';
						
								axios
        	 						.get(fourSquareUrl2)
             						.then(response => {
                						console.log("photos", response.data);
									})
									.catch(error => console.log(error));
						}	
					}
				}, 1000);
				
				
				function addMarkersToMap(map, lat, lng) {
					console.log(lat, lng);
				    var marker = new H.map.Marker({lat: lat, lng: lng});
				    map.addObject(marker);
				}
				
             })
             .catch(error => console.log(error));*/

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