///---------------------------------------------------------------------------------------------------------------------------Management map

var placeFound = []
var myLayer



var map = L.map('carte1').setView([48.692054, 6.184417], 16);
L.tileLayer('http://b.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/deed.fr">OpenStreetMap France</a>',
    maxZoom: 19,
    id: 'mapbox/streets-v11',
    tileSize: 256,
    zoomOffset: 0,
}).addTo(map);

function setPlaceFromSelect(index){
    var placeSelected = placeFound[index]
    map.eachLayer(function(layer) {
        if (!!layer.toGeoJSON) {
            map.removeLayer(layer);
        }
    });


    $("#lat").val(placeSelected.lat)
    $("#lon").val(placeSelected.lon)

    myLayer = L.geoJSON().addTo(map);
    myLayer.addData(placeSelected.geojson);
    map.setView([placeSelected.lat, placeSelected.lon], 16);
}

function mapInit() {
    $("#SearchResult").change(function () {
        var select = $(this)
        setPlaceFromSelect(select.val())

    })


    $('#searchPlace').on('click', function(event) {
        var btn = $(this);

        placeSearched = $('#placeSearched').val()
        if(placeSearched!=""){



            parmasQuery = new URLSearchParams({
                q: placeSearched,
                format: "json",
                email: "elliot.faugier4@etu.univ-lorraine.fr",
                polygon: 1,
                polygon_geojson: 1,
                limit: 5,
                addressdetails:1,

            });

            fetch('http://nominatim.openstreetmap.org/search?' + parmasQuery).then(function(response) {
                if(response.ok) {
                    response.json().then(function (json) {
                        placeFound = json
                        var select =$("#SearchResult")

                        if(placeFound.length == 0){
                            select.empty()
                            select.prop('disabled', true);
                        } else{
                            select.empty()
                            for(singlePLaceFound in placeFound){

                                string = placeFound[singlePLaceFound]["display_name"]

                                if(placeFound[singlePLaceFound]["road"]!==undefined){

                                } else if(placeFound[singlePLaceFound]["boundary"]!==undefined){

                                }
                                select.append("<option value="+singlePLaceFound+">"+string+"</option>")
                            }
                            setPlaceFromSelect(0)
                            select.prop('disabled', false);
                            $("#submit").prop('disabled', false);
                        }

                    })
                }
            });

            btn.prop('disabled', true);
            setTimeout(function(){
                btn.prop('disabled', false);
            }, 1.5*1000);
        }
    });
}