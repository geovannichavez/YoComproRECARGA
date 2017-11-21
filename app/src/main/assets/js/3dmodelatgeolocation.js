var exchanging = false;
var actualARObject;

var World = {


loaded: false,
rotating: false,
init: function initFn() {

},


alertPrueba: function alertPruebaFn(word) {
    alert(word);
},

deleteObjectGeo: function deleteObjectGeoFn() {
    AR.context.destroyAll();
    document.body.innerHTML = "<div id=\"container\" class=\"container\"><div class=\"content\"><div id=\"loadingMessage\" class=\"loading\">No hay cofres cerca, revisa tu mapa...</div></div></div>";
},

destroyObjectGeo: function destroyObjectGeoFn() {
    actualARObject.destroy();
},


//Funcion para crear modelo Bronze
createModelBronzeAtLocation: function createModelBronzeAtLocationFn(latitude,longitude, key) {

    exchanging=false;
    var modelBronze = new AR.Model("assets/BronzeChest.wt3", {
                                  onLoaded: this.worldLoaded,
                                  scale: {
                                  x: 1,
                                  y: 1,
                                  z: 1
                                  }
                                  });

    var a = new AR.PropertyAnimation(modelBronze, 'rotate.heading', 90.0, -270.0, 5000);
    a.start(-1);

    var bronzeChestAnim = new AR.ModelAnimation(modelBronze, "Scene");

    var indicatorImage = new AR.ImageResource("assets/BronzeArrow.png");
    var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
                                                 verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
                                                 });
    var location = new AR.GeoLocation(latitude, longitude , AR.CONST.UNKNOWN_ALTITUDE);
    var obj = new AR.GeoObject(location, {
                                drawables: {
                                cam: [modelBronze],
                                indicator: [indicatorDrawable]
                                },
                                onClick : function() {
                               if(!exchanging)
                               {
                               exchanging = true;
                               bronzeChestAnim.start(1);
                               setTimeout(function() {
                                          var architectSdkUrl = "architectsdk://Bronze//" + key + "//" + latitude + "//" + longitude;
                                          document.location = architectSdkUrl;
                                          exchanging = false;
                                          },2300);
                                }
                                }
                                });
    actualARObject = obj;
},

//Funcion para crear modelo Silver
createModelSilverAtLocation: function createModelSilverAtLocationFn(latitude,longitude, key) {

    exchanging=false;
    var modelSilver = new AR.Model("assets/SilverChest.wt3", {
                                  onLoaded: this.worldLoaded,
                                  scale: {
                                  x: 1,
                                  y: 1,
                                  z: 1
                                  }
                                  });

    var a = new AR.PropertyAnimation(modelSilver, 'rotate.heading', 90.0, -270.0, 5000);
    a.start(-1);

    var silverChestAnim = new AR.ModelAnimation(modelSilver, "Scene");

    var indicatorImage = new AR.ImageResource("assets/SilverArrow.png");
    var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
                                                 verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
                                                 });
    var location = new AR.GeoLocation(latitude, longitude  , AR.CONST.UNKNOWN_ALTITUDE);
    var obj = new AR.GeoObject(location, {
                               drawables: {
                               cam: [modelSilver],
                               indicator: [indicatorDrawable]
                               },
                               onClick : function() {
                               if(!exchanging)
                               {
                               exchanging = true;
                               silverChestAnim.start(1);
                               setTimeout(function() {
                                          var architectSdkUrl = "architectsdk://Silver//" + key + "//" + latitude + "//" + longitude;
                                          document.location = architectSdkUrl;
                                          exchanging = false;
                                          },2300);
                               }
                               }
                               });
    actualARObject = obj;
    },

//Funcion para crear modelo Gold
createModelGoldAtLocation: function createModelGoldAtLocationFn(latitude,longitude, key) {

    exchanging=false;
    var modelGold = new AR.Model("assets/GoldChest.wt3", {
                                  onLoaded: this.worldLoaded,
                                  scale: {
                                  x: 1,
                                  y: 1,
                                  z: 1
                                  }
                                  });

    var a = new AR.PropertyAnimation(modelGold, 'rotate.heading', 90.0, -270.0, 5000);

    a.start(-1);


    var goldChestAnim = new AR.ModelAnimation(modelGold, "Scene");
    var indicatorImage = new AR.ImageResource("assets/GoldArrow.png");
    var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
                                                 verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
                                                 });
    var location = new AR.GeoLocation(latitude,longitude , AR.CONST.UNKNOWN_ALTITUDE);
    var obj = new AR.GeoObject(location, {
                               drawables: {
                               cam: [modelGold],
                               indicator: [indicatorDrawable]
                               },
                               onClick : function() {
                               if(!exchanging)
                               {
                               exchanging = true;
                               goldChestAnim.start(1);
                               setTimeout(function() {
                                          var architectSdkUrl = "architectsdk://Gold//" + key + "//" + latitude + "//" + longitude;
                                          document.location = architectSdkUrl;
                                          exchanging = false;
                                          },2300);
                               }
                               }
                               });
    actualARObject = obj;
},

//Funcion para crear modelo Comodin
createModelWildcardAtLocation: function createModelWildcardAtLocationFn(latitude,longitude, key) {

    exchanging=false;
    var modelWildcard = new AR.Model("assets/Wildcard.wt3", {
                                 onLoaded: this.worldLoaded,
                                 scale: {
                                 x: 1,
                                 y: 1,
                                 z: 1
                                 }
                                 });

    var a = new AR.PropertyAnimation(modelWildcard, 'rotate.heading', 90.0, -270, 3000);

    //var a = new AR.PropertyAnimation(modelWildcard, 'rotate.heading', 0,0 , 10);

    a.start(-1);
    //a.start(1);


    var WildcardChestAnim = new AR.ModelAnimation(modelWildcard, "Scene");
    var indicatorImage = new AR.ImageResource("assets/GoldArrow.png");
    var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
                                                 verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
                                                 });
    var location = new AR.GeoLocation(latitude,longitude , AR.CONST.UNKNOWN_ALTITUDE);
    var obj = new AR.GeoObject(location, {
                               drawables: {
                               cam: [modelWildcard],
                               indicator: [indicatorDrawable]
                               },
                               onClick : function() {
                               if(!exchanging)
                               {
                               exchanging = true;
                               WildcardChestAnim.start(1);
                               setTimeout(function() {
                                          var architectSdkUrl = "architectsdk://Wildcard//"+key+"//"+latitude+"//"+longitude;
                                          document.location = architectSdkUrl;
                                          exchanging = false;
                                          },3000);
                               }
                               }
                               });
    actualARObject = obj;
},

worldLoaded: function worldLoadedFn() {
    World.loaded = true;
    var e = document.getElementById('container');
    e.parentElement.removeChild(e);
}
};

World.init();
AR.context.onLocationChanged = World.locationChanged;

