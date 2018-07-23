var exchanging = false;
//var actualARObject;

var World = {


loaded: false,
rotating: false,
init: function initFn() {

 this.createOverlays();

},


alertPrueba: function alertPruebaFn(word) {
    alert(word);
},

deleteObjectGeo: function deleteObjectGeoFn() {
    AR.context.destroyAll();
    document.body.innerHTML = "<div id=\"container\" class=\"container\"><div class=\"content\"><div id=\"loadingMessage\" class=\"loading\">No hay objetos cerca, revisa tu mapa...</div></div></div>";
     //this.createOverlays();
},

destroyObjectGeo: function destroyObjectGeoFn() {
    //actualARObject.destroy();
},


//Funcion para crear modelo Bronze
createModelBronzeAtLocation: function createModelBronzeAtLocationFn(latitude,longitude, key,age) {

    exchanging=false;
    var modelBronze = new AR.Model("assets/"+age+"/BronzeChest.wt3", {
    //var modelBronze = new AR.Model("assets/"+age+"/Wildcard.wt3", {
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
                                          },3500);
                                }
                                }
                                });
    //actualARObject = obj;
},

//Funcion para crear modelo Silver
//createModelSilverAtLocation: function createModelSilverAtLocationFn(latitude,longitude, key) {
createModelSilverAtLocation: function createModelSilverAtLocationFn(latitude,longitude, key,age) {
    exchanging=false;
    var modelSilver = new AR.Model("assets/"+age+"/SilverChest.wt3", {
    //var modelSilver = new AR.Model("http://admin.yovendorecarga.com/Uploads/recargo/model/plata.wt3", {

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
                                          },2400);
                               }
                               }
                               });
    //actualARObject = obj;
    },

//Funcion para crear modelo Gold
//createModelGoldAtLocation: function createModelGoldAtLocationFn(latitude,longitude, key) {
createModelGoldAtLocation: function createModelGoldAtLocationFn(latitude,longitude, key,age) {
    exchanging=false;
    //var modelGold = new AR.Model("http://admin.yovendorecarga.com/Uploads/recargo/model/oro.wt3", {
    var modelGold = new AR.Model("assets/"+age+"/GoldChest.wt3", {
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
                                          },2400);
                               }
                               }
                               });
    //actualARObject = obj;
},

//Funcion para crear modelo Comodin
//createModelWildcardAtLocation: function createModelWildcardAtLocationFn(latitude,longitude, key) {
createModelWildcardAtLocation: function createModelWildcardAtLocationFn(latitude,longitude, key,age) {

    exchanging=false;
    var modelWildcard = new AR.Model("http://www.clarorocket.com/v1/rg/ar/assets/assets/"+age+"/Wildcard.wt3", {
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
    //actualARObject = obj;
},


createModelSponsorAtLocation: function createModelSponsorAtLocationFn(latitude,longitude,visible,sponsor,key,brandid) {

    exchanging=false;
    var modelSponsor = new AR.Model("assets/Sponsor/"+sponsor+".wt3", {
                                     onLoaded: this.worldLoaded,
                                     scale: {
                                     x: 1,
                                     y: 1,
                                     z: 1
                                     }
                                     });

    var a = new AR.PropertyAnimation(modelSponsor, 'rotate.heading', 90.0, -270, 3000);


    a.start(-1);


    var SponsorAnim = new AR.ModelAnimation(modelSponsor, "Scene");
    var indicatorImage = new AR.ImageResource("assets/GoldArrow.png");
    var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
                                                 verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
                                                 });
    var location = new AR.GeoLocation(latitude,longitude , AR.CONST.UNKNOWN_ALTITUDE);

    var obj = new AR.GeoObject(location, {
                               drawables: {
                               cam: [modelSponsor],
                               indicator: [indicatorDrawable]
                               },
                               onClick : function() {
                               if(!exchanging)
                               {
                               exchanging = true;
                               SponsorAnim.start(1);
                               setTimeout(function() {
                                          var architectSdkUrl = "architectsdk://Sponsor//"+brandid+"//"+visible+"//"+key;
                                          document.location = architectSdkUrl;
                                          exchanging = false;
                                          },3000);
                               }
                               }
                               });
},

/*

*******************************************************************************************************

        IMAGE SCAN

*******************************************************************************************************

*/

createOverlays: function createOverlaysFn() {
    /*
     First an AR.ImageTracker needs to be created in order to start the recognition engine. It is initialized with a AR.TargetCollectionResource specific to the target collection that should be used. Optional parameters are passed as object in the last argument. In this case a callback function for the onTargetsLoaded trigger is set. Once the tracker loaded all its target images, the function worldLoaded() is called.
     Important: If you replace the tracker file with your own, make sure to change the target name accordingly.
     Use a specific target name to respond only to a certain target or use a wildcard to respond to any or a certain group of targets.
     */
    //alert("InterLoad");
    var targetCollectionResource = new AR.TargetCollectionResource("assets/RecarGOPlusTracker.wtc", {
                                                                    });

    var tracker = new AR.ImageTracker(targetCollectionResource, {

//                                       onTargetsLoaded: this.worldLoaded,
                                       onError: function(errorMessage) {
                                       alert(errorMessage);
                                       }
                                       });

    /*
     The next step is to create the augmentation. In this example an image resource is created and passed to the AR.ImageDrawable. A drawable is a visual component that can be connected to an IR target (AR.ImageTrackable) or a geolocated object (AR.GeoObject). The AR.ImageDrawable is initialized by the image and its size. Optional parameters allow for position it relative to the recognized target.
     */


    var modelBrand = new AR.Model("assets/Sponsor/Metro.wt3", {
                                 onLoaded: this.worldLoaded,
                                 scale: {
                                 x: 0.5,
                                 y: 0.5,
                                 z: 0.5
                                 },
                                 rotate: {
                                 x: 270.0
                                 },
                                 onClick : function() {
                                 if(!exchanging)
                                 {
                                 exchanging = true;
                                 brandAnim.start(1);
                                 setTimeout(function() {
                                            var architectSdkUrl = "architectsdk://Sponsor//1//3//3";
                                            document.location = architectSdkUrl;
                                            exchanging = false;
                                            },2000);
                                 }
                                 }
                                 });
    var brandAnim = new AR.ModelAnimation(modelBrand, "Scene");



//    var modelBrand1 = new AR.Model("assets/Vikingos/Oro.wt3", {
//                                  onLoaded: this.worldLoaded,
//                                  scale: {
//                                  x: 0.5,
//                                  y: 0.5,
//                                  z: 0.5
//                                  },
//                                  rotate: {
//                                  x: 270.0
//                                  },
//                                  onClick : function() {
//                                  if(!exchanging)
//                                  {
//                                  exchanging = true;
//                                  brandAnim1.start(1);
//                                  setTimeout(function() {
//                                             var architectSdkUrl = "architectsdk://Brand?Puma";
//                                             document.location = architectSdkUrl;
//                                             exchanging = false;
//                                             },4000);
//                                  }
//                                  }
//                                  });
//    var brandAnim1 = new AR.ModelAnimation(modelBrand1, "Scene");


    var modelBrand2 = new AR.Model("assets/Futbolistica/Wildcard.wt3", {
    //var modelBrand2 = new AR.Model("assets/Acuatica/Wildcard.wt3", {
                                   onLoaded: this.worldLoaded,
                                   scale: {
                                   x: 0.5,
                                   y: 0.5,
                                   z: 0.5
                                   },
                                   rotate: {
                                   x: 270.0
                                   },
                                   onClick : function() {
                                   if(!exchanging)
                                   {
                                   exchanging = true;
                                   brandAnim2.start(1);
                                   setTimeout(function() {
                                              var architectSdkUrl = "architectsdk://Sponsor//2//3//3";
                                              document.location = architectSdkUrl;
                                              exchanging = false;
                                              },3000);
                                   }
                                   }
                                   });
    var brandAnim2 = new AR.ModelAnimation(modelBrand2, "Scene");

    /*
     The last line combines everything by creating an AR.ImageTrackable with the previously created tracker, the name of the image target and the drawable that should augment the recognized image.
     Please note that in this case the target name is a wildcard. Wildcards can be used to respond to any target defined in the target collection. If you want to respond to a certain target only for a particular AR.ImageTrackable simply provide the target name as specified in the target collection.
     */
    var pageOne = new AR.ImageTrackable(tracker, "Metrocentro", {
                                        drawables: {
                                        cam: modelBrand
                                        },
//                                        onImageRecognized: this.removeLoadingBar,
                                        onError: function(errorMessage) {
                                        alert(errorMessage);
                                        }
                                        });

    var pageTwo = new AR.ImageTrackable(tracker, "Wendys", {
                                        drawables: {
                                        cam: modelBrand2
                                        },
                                        //                                        onImageRecognized: this.removeLoadingBar,
                                        onError: function(errorMessage) {
                                        alert(errorMessage);
                                        }
                                        });

    var pageThree = new AR.ImageTrackable(tracker, "Metro", {
                                          drawables: {
                                          cam: modelBrand
                                          },
                                        //                                        onImageRecognized: this.removeLoadingBar,
                                        onError: function(errorMessage) {
                                        alert(errorMessage);
                                        }
                                        });


    // Create overlay for page one
//    var imgOne = new AR.ImageResource("assets/Siman.png");
//    var overlayOne = new AR.ImageDrawable(imgOne, 1, {
//                                          translate: {
//                                          x: 0.0,
//                                          }
//                                          });

    var pageFour = new AR.ImageTrackable(tracker, "Siman", {
                                          drawables: {
                                          cam: modelBrand2
                                          },
                                          //onImageRecognized: alert("Siman Escaneado"),
                                          onError: function(errorMessage) {
                                          alert(errorMessage);
                                          }
                                          });

    var pageFive = new AR.ImageTrackable(tracker, "Multiplaza", {
                                         drawables: {
                                         cam: modelBrand2
                                         },
                                         //onImageRecognized: alert("Siman Escaneado"),
                                         onError: function(errorMessage) {
                                         alert(errorMessage);
                                         }
                                         });

    var pageSix = new AR.ImageTrackable(tracker, "ClaroLogo", {
                                         drawables: {
                                         cam: modelBrand2
                                         },
                                         //onImageRecognized: alert("Siman Escaneado"),
                                         onError: function(errorMessage) {
                                         alert(errorMessage);
                                         }
                                         });


},


worldLoaded: function worldLoadedFn() {
    World.loaded = true;
    var e = document.getElementById('container');
    e.parentElement.removeChild(e);
}
};

World.init();
AR.context.onLocationChanged = World.locationChanged;
