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
    //var modelEarth = new AR.Model("assets/Bronze.wt3", {
    var modelEarth = new AR.Model("assets/RegaloVerde2.wt3", {
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
                                //var architectSdkUrl = "architectsdk://Bronze/" + key + '/' + latitude + '/' longitude;
                                var architectSdkUrl = "architectsdk://Bronze/" + key;
                                document.location = architectSdkUrl;
                                }
                                });
},
   
//Funcion para crear modelo Silver
createModelSilverAtLocation: function createModelSilverAtLocationFn(latitude,longitude, key) {

    //var modelEarth = new AR.Model("assets/Silver.wt3", {
    var modelEarth = new AR.Model("assets/RegaloVerde2.wt3", {
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
                               //var architectSdkUrl = "architectsdk://Silver/" + key + '/' + latitude + '/' longitude;
                               var architectSdkUrl = "architectsdk://Silver/" + key;
                               document.location = architectSdkUrl;
                               }
                               });
},
    
//Funcion para crear modelo Gold
createModelGoldAtLocation: function createModelGoldAtLocationFn(latitude,longitude, key) {
    //alert("Oro");
    
    //var modelEarth = new AR.Model("assets/Gold.wt3", {
    var modelEarth = new AR.Model("assets/RegaloVerde2.wt3", {
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
                               //var architectSdkUrl = "architectsdk://Gold/" + key + '/' + latitude + '/' longitude;
                               var architectSdkUrl = "architectsdk://Gold/" + key;
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

    
    /*
     Casa Lic. Carlos
     */
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

AR.context.onLocationChanged = World.locationChanged;


