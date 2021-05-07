
const getCoordinates = (obj) => {
    let address = obj.address;
    let addressFinal= address.street+" " + address.buildingNumber+" " + address.country+" " + address.commune+" " + address.postCode;
    let test = "Rue du duc 29 bruxelles"
    const Http = new XMLHttpRequest();
    const url=`http://api.positionstack.com/v1/forward?access_key=c9f2d2aaa769991d9e4d60e371687223&query=${test}`;
    Http.open("GET", url);
    Http.send();
  
    Http.onreadystatechange=function(){
      if(this.readyState==4 && this.status==200){
        let obj = JSON.parse(Http.response);
        createMap(obj.data[0].latitude,obj.data[0].longitude,addressFinal);
      }
    }
  };
  
  const createMap = (latitude, longitude,addressFinal) => {
    var attribution = new ol.control.Attribution({
      collapsible: false
    });
  
    var map = new ol.Map({
        controls: ol.control.defaults({attribution: false}).extend([attribution]),
        layers: [
            new ol.layer.Tile({
                source: new ol.source.OSM({
                    url: 'https://tile.openstreetmap.be/osmbe/{z}/{x}/{y}.png',
                    attributions: [ ol.source.OSM.ATTRIBUTION, 'Tiles courtesy of <a href="https://geo6.be/">GEO-6</a>' ],
                    maxZoom: 18
                })
            })
        ],
        target: 'map',
        view: new ol.View({
            center: ol.proj.fromLonLat([4.35247, 50.84673]),
            maxZoom: 18,
            zoom: 8
        })
    });
  
    var layer = new ol.layer.Vector({
      source: new ol.source.Vector({
          features: [
              new ol.Feature({
                  geometry: new ol.geom.Point(ol.proj.fromLonLat([longitude, latitude]))
              })
          ]
      })
  });
  map.addLayer(layer);

  var container = document.getElementById('popup');
 var content = document.getElementById('popup-content');
 var closer = document.getElementById('popup-closer');

 var overlay = new ol.Overlay({
     element: container,
     autoPan: true,
     autoPanAnimation: {
         duration: 250
     }
 });
 map.addOverlay(overlay);

 closer.onclick = function() {
     overlay.setPosition(undefined);
     closer.blur();
     return false;
 };

 map.on('singleclick', function (event) {
  if (map.hasFeatureAtPixel(event.pixel) === true) {
      var coordinate = event.coordinate;

      content.innerHTML = `${addressFinal}`;
      overlay.setPosition(coordinate);
  } else {
      overlay.setPosition(undefined);
      closer.blur();
  }
});
}

  export {getCoordinates};