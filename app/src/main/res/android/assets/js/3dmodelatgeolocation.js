/*
var World = {
    
  
loaded: false,
rotating: false,
    
init: function initFn() {
    
    //this.createModelBronzeAtLocation(13.685976, -89.242948,"Bronce");
    //this.createModelGoldAtLocation(13.685525, -89.244185,"Oro");
    //this.createModelSilverAtLocation(13.684915, -89.241423,"Plata");
    //this.createModelAtLocation();

},
   
    
alertPrueba: function alertPruebaFn(word) {
    alert(word);
},
    
    
//Funcion para crear modelo Bronze
createModelBronzeAtLocation: function createModelBronzeAtLocationFn(latitude,longitude, key) {
    var modelEarth = new AR.Model("assets/Bronze.wt3", {
                                  onLoaded: this.worldLoaded,
                                  scale: {
                                  x: 1,
                                  y: 1,
                                  z: 1
                                  }
                                  });
    
    var a = new AR.PropertyAnimation(modelEarth, 'rotate.heading', 90.0, -270.0, 5000);
    var indicatorImage = new AR.ImageResource("assets/indi.png");
    var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
                                                 verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
                                                 });
    var location = new AR.GeoLocation(latitude, longitude + 0.0005, AR.CONST.UNKNOWN_ALTITUDE);
    var obj = new AR.GeoObject(location, {
                                drawables: {
                                cam: [modelEarth],
                                indicator: [indicatorDrawable]
                                },
                                onClick : function() {
                                var architectSdkUrl = "architectsdk://Bronze";
                                document.location = architectSdkUrl;
                                }
                                });
},
   
//Funcion para crear modelo Silver
createModelSilverAtLocation: function createModelSilverAtLocationFn(latitude,longitude, key) {

    var modelEarth = new AR.Model("assets/Silver.wt3", {
                                  onLoaded: this.worldLoaded,
                                  scale: {
                                  x: 1,
                                  y: 1,
                                  z: 1
                                  }
                                  });
    
    var a = new AR.PropertyAnimation(modelEarth, 'rotate.heading', 90.0, -270.0, 5000);
    var indicatorImage = new AR.ImageResource("assets/indi.png");
    var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
                                                 verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
                                                 });
    var location = new AR.GeoLocation(latitude, longitude  + 0.0005, AR.CONST.UNKNOWN_ALTITUDE);
    var obj = new AR.GeoObject(location, {
                               drawables: {
                               cam: [modelEarth],
                               indicator: [indicatorDrawable]
                               },
                               onClick : function() {
                               var architectSdkUrl = "architectsdk://Silver";
                               document.location = architectSdkUrl;
                               }
                               });
},
    
//Funcion para crear modelo Gold
createModelGoldAtLocation: function createModelGoldAtLocationFn(latitude,longitude, key) {
    //alert("Oro");
    
    var modelEarth = new AR.Model("assets/Gold.wt3", {
                                  onLoaded: this.worldLoaded,
                                  scale: {
                                  x: 1,
                                  y: 1,
                                  z: 1
                                  }
                                  });
    
    var a = new AR.PropertyAnimation(modelEarth, 'rotate.heading', 90.0, -270.0, 5000);
    var indicatorImage = new AR.ImageResource("assets/indi.png");
    var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
                                                 verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
                                                 });
    var location = new AR.GeoLocation(latitude,longitude  + 0.0005, AR.CONST.UNKNOWN_ALTITUDE);
    var obj = new AR.GeoObject(location, {
                               drawables: {
                               cam: [modelEarth],
                               indicator: [indicatorDrawable]
                               },
                               onClick : function() {
                               var architectSdkUrl = "architectsdk://Gold";
                               document.location = architectSdkUrl;
                               }
                               });
},
    
    

createModelAtLocation: function createModelAtLocationFn(location, key) {

    var modelEarth = new AR.Model("assets/RegaloVerde2.wt3", {
    onLoaded: this.worldLoaded,
    scale: {
				x: 1,
				y: 1,
				z: 1
    }
        
    });
    
    var a = new AR.PropertyAnimation(modelEarth, 'rotate.heading', 90.0, -270.0, 5000);
    // a.start(-1);
    var indicatorImage = new AR.ImageResource("assets/indi.png");
    var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
    verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
    });

    
    */
/*
     Casa Lic. Carlos
     *//*

    var location1 = new AR.GeoLocation(13.688760, -89.226224  + 0.0005, AR.CONST.UNKNOWN_ALTITUDE);
    var obj1 = new AR.GeoObject(location1, {
                                drawables: {
                                cam: [modelEarth],
                                indicator: [indicatorDrawable]
                                },
                                onClick : function() {
                                a.start(1);
                                var architectSdkUrl = "architectsdk://salir";
                                document.location = architectSdkUrl;
                                }
                                });
    

    
    var b = new AR.PropertyAnimation(obj2, 'rotate.heading', 99.0, -270.0, 5000);
    
    
    

    
},
    
worldLoaded: function worldLoadedFn() {
    World.loaded = true;
    var e = document.getElementById('loadingMessage');
    e.parentElement.removeChild(e);
}
};

World.init();

*/


// implementation of AR-Experience (aka "World")
var World = {
	// true once data was fetched
	initiallyLoadedData: false,

	// POI-Marker asset
	markerDrawable_idle: null,

	// called to inject new POI data
	loadPoisFromJsonData: function loadPoisFromJsonDataFn(poiData) {

		/*
			The example Image Recognition already explained how images are loaded and displayed in the augmented reality view. This sample loads an AR.ImageResource when the World variable was defined. It will be reused for each marker that we will create afterwards.
		*/
		World.markerDrawable_idle = new AR.ImageResource("assets/marker_idle.png");

		/*
			For creating the marker a new object AR.GeoObject will be created at the specified geolocation. An AR.GeoObject connects one or more AR.GeoLocations with multiple AR.Drawables. The AR.Drawables can be defined for multiple targets. A target can be the camera, the radar or a direction indicator. Both the radar and direction indicators will be covered in more detail in later examples.
		*/
		var markerLocation = new AR.GeoLocation(poiData.latitude, poiData.longitude, poiData.altitude);
		var markerImageDrawable_idle = new AR.ImageDrawable(World.markerDrawable_idle, 2.5, {
			zOrder: 0,
			opacity: 1.0
		});

		// create GeoObject
		var markerObject = new AR.GeoObject(markerLocation, {
			drawables: {
				cam: [markerImageDrawable_idle]
			}
		});

		// Updates status message as a user feedback that everything was loaded properly.
		World.updateStatusMessage('1 place loaded');
	},

	// updates status message shon in small "i"-button aligned bottom center
	updateStatusMessage: function updateStatusMessageFn(message, isWarning) {

		var themeToUse = isWarning ? "e" : "c";
		var iconToUse = isWarning ? "alert" : "info";

		$("#status-message").html(message);
		$("#popupInfoButton").buttonMarkup({
			theme: themeToUse
		});
		$("#popupInfoButton").buttonMarkup({
			icon: iconToUse
		});
	},

	// location updates, fired every time you call architectView.setLocation() in native environment
	locationChanged: function locationChangedFn(lat, lon, alt, acc) {

		/*
			The custom function World.onLocationChanged checks with the flag World.initiallyLoadedData if the function was already called. With the first call of World.onLocationChanged an object that contains geo information will be created which will be later used to create a marker using the World.loadPoisFromJsonData function.
		*/
		if (!World.initiallyLoadedData) {
			// creates a poi object with a random location near the user's location
			var poiData = {
				"id": 1,
				"longitude": (lon + (Math.random() / 5 - 0.1)),
				"latitude": (lat + (Math.random() / 5 - 0.1)),
				"altitude": 100.0
			};

			World.loadPoisFromJsonData(poiData);
			World.initiallyLoadedData = true;
		}
	},
};

/*
	Set a custom function where location changes are forwarded to. There is also a possibility to set AR.context.onLocationChanged to null. In this case the function will not be called anymore and no further location updates will be received.
*/
AR.context.onLocationChanged = World.locationChanged;