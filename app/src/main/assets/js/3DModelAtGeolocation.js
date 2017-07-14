var exchanging = false;

var World = {


    loaded: false,
    rotating: false,
    init: function initFn()
    {

    },


    alertPrueba: function alertPruebaFn(word)
    {
        alert(word);
    },

    deleteObjectGeo: function deleteObjectGeoFn()
    {
        AR.context.destroyAll();
    },


    //Funcion para crear modelo Bronze
    createModelBronzeAtLocation: function createModelBronzeAtLocationFn(latitude, longitude, key)
    {

        exchanging = false;
        var modelBronze = new AR.Model("assets/BronzeChest.wt3",
        {
            onLoaded: this.worldLoaded,
            scale:
            {
                x: 1,
                y: 1,
                z: 1
            }
        });

        var a = new AR.PropertyAnimation(modelBronze, 'rotate.heading', 90.0, -270.0, 5000);
        a.start(-1);

        var bronzeChestAnim = new AR.ModelAnimation(modelBronze, "Scene");

        var indicatorImage = new AR.ImageResource("assets/indi.png");
        var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1,
        {
            verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
        });
        var location = new AR.GeoLocation(latitude, longitude, AR.CONST.UNKNOWN_ALTITUDE);
        var obj = new AR.GeoObject(location,
        {
            drawables:
            {
                cam: [modelBronze],
                indicator: [indicatorDrawable]
            },
            onClick: function()
            {
                if (!exchanging)
                {
                    exchanging = true;
                    bronzeChestAnim.start(1);
                    setTimeout(function()
                    {
                        var architectSdkUrl = "architectsdk://Bronze//" + key + "//" + latitude + "//" + longitude;
                        document.location = architectSdkUrl;
                        exchanging = false;
                    }, 2450);
                }
            }
        });
    },

    //Funcion para crear modelo Silver
    createModelSilverAtLocation: function createModelSilverAtLocationFn(latitude, longitude, key)
    {

        exchanging = false;
        var modelSilver = new AR.Model("assets/SilverChest.wt3",
        {
            onLoaded: this.worldLoaded,
            scale:
            {
                x: 1,
                y: 1,
                z: 1
            }
        });

        var a = new AR.PropertyAnimation(modelSilver, 'rotate.heading', 90.0, -270.0, 5000);
        a.start(-1);

        var silverChestAnim = new AR.ModelAnimation(modelSilver, "Scene");

        var indicatorImage = new AR.ImageResource("assets/indi.png");
        var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1,
        {
            verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
        });
        var location = new AR.GeoLocation(latitude, longitude, AR.CONST.UNKNOWN_ALTITUDE);
        var obj = new AR.GeoObject(location,
        {
            drawables:
            {
                cam: [modelSilver],
                indicator: [indicatorDrawable]
            },
            onClick: function()
            {
                if (!exchanging)
                {
                    exchanging = true;
                    silverChestAnim.start(1);
                    setTimeout(function()
                    {
                        var architectSdkUrl = "architectsdk://Silver//" + key + "//" + latitude + "//" + longitude;
                        document.location = architectSdkUrl;
                        exchanging = false;
                    }, 2450);
                }
            }
        });
    },

    //Funcion para crear modelo Gold
    createModelGoldAtLocation: function createModelGoldAtLocationFn(latitude, longitude, key)
    {

        exchanging = false;
        var modelGold = new AR.Model("assets/GoldChest.wt3",
        {
            onLoaded: this.worldLoaded,
            scale:
            {
                x: 1,
                y: 1,
                z: 1
            }
        });

        var a = new AR.PropertyAnimation(modelGold, 'rotate.heading', 90.0, -270.0, 5000);

        a.start(-1);


        var goldChestAnim = new AR.ModelAnimation(modelGold, "Scene");


        var indicatorImage = new AR.ImageResource("assets/indi.png");
        var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1,
        {
            verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
        });
        var location = new AR.GeoLocation(latitude, longitude, AR.CONST.UNKNOWN_ALTITUDE);
        var obj = new AR.GeoObject(location,
        {
            drawables:
            {
                cam: [modelGold],
                indicator: [indicatorDrawable]
            },
            onClick: function()
            {
                if (!exchanging)
                {
                    exchanging = true;
                    goldChestAnim.start(1);
                    setTimeout(function()
                    {
                        var architectSdkUrl = "architectsdk://Gold//" + key + "//" + latitude + "//" + longitude;
                        document.location = architectSdkUrl;
                        exchanging = false;
                    }, 2450);
                }
            }
        });
    },


    worldLoaded: function worldLoadedFn()
    {
        World.loaded = true;
        var e = document.getElementById('loadingMessage');
        e.parentElement.removeChild(e);
    }
};

World.init();
AR.context.onLocationChanged = World.locationChanged;