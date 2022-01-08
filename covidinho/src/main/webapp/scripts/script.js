//------------------------------------------------------------------------------Activity Form Management

$("#begining").change(function (){
    $( "#end" ).prop( "disabled", false );
    $("#end").attr("min", $("#begining").val())
    date = Date.parse($( "#end" ).val())
    if(date != NaN){
        if(date < Date.parse($( "#begining" ).val())){
            $("#end").val("")
        }
    }
})

$("#option11").change(function () {
    if($("#option11").val()){
        $("#place").html("<div class=\"col\" ><label for=\"adress\" class=\"form-label\"><p class=\"text-body\">Adresse</p></label> <input class=\"form-control adress\" type=\"text\" id=\"adress\" name=\"adresse\" autocomplete=\"off\"></div><script>initAutoComplete()</\script>");
        $('#placeFound').html("<p class=\"text-body pr-3 pl-3\">Aucune Correspondance</p>")
        $("#submit").prop('disabled', false);
    }
})

$("#option22").change(function () {
    if($("#option22").val()){
        $("#place").html("<div class=\"col\"><label for=\"lat\" class=\"form-label\"><p class=\"text-body\">Latitude</p></label> <input class=\"form-control adress\" type=\"number\" id=\"lat\" name=\"lat\" step=\"0.001\"></div><div class=\"col\" ><label for=\"lon\" class=\"form-label\"><p class=\"text-body\">Longitude</p></label> <input class=\"form-control adress\" type=\"number\" id=\"lon\" name=\"lon\" step=\"0.001\"></div><script>enableJqueryForLatAndLong()</\script>");
        $('#placeFound').html("<p class=\"text-body  pr-3 pl-3\">Aucune Correspondance</p>")
        $("#submit").prop('disabled', true);
    }
})

$("#option33").change(function () {
    if($("#option33").val()){
        $("#place").html("<div class=\"col flex-grow-1\" ><label for=\"adress\" class=\"form-label\"><p class=\"text-body\">Search</p></label> <input class=\"form-control \" type=\"text\" id=\"placeSearched\"></div><div class=\"col align-self-end\" ><button type=\"button\" class=\"btn btn-success\" id=\"searchPlace\">Search</button></div>" +
            "<div><input class=\"form-control adress \" type=\"hidden\" id=\"lat\" name=\"lat\" ><input class=\"form-control adress\" type=\"hidden\" id=\"lon\" name=\"lon\"></div>");
        $('#placeFound').html("<div class=\"col\" ><label for=\"adress\" class=\"form-label\"><p class=\"text-body\">Lieu(x) trouvé(s)</p></label><select class=\"form-select form-control \" disabled id=\"SearchResult\" aria-label=_\"Elliot\"></select> </div>")
        $("#submit").prop('disabled', true);
        mapInit()
    }
})

function fetchPositionFromCoords(lat, lon){
    fetch("https://api-adresse.data.gouv.fr/reverse/?lat="+lat+"&lon="+lon+"&limit=1").then(function(response) {
        if(response.ok) {
            response.json().then(function(json) {
                if(json.features.length == 0){
                    $('#placeFound').html("<p class=\"text-body\">Aucune Correspondance</p>")
                    $("#submit").prop('disabled', true);
                } else{
                    $('#placeFound').html("<p class=\"text-body\">"+json.features[0].properties.label+"</p>")
                    $("#submit").prop('disabled', false);
                }
            });
        } else{
            $('#placeFound').html("<p class=\"text-body\">Erreur</p>")
        }
    });
}

function enableJqueryForLatAndLong(){
    $('#lat').on('input', function() {
        if($("#lon").val()!=""){
            fetchPositionFromCoords($('#lat').val(), $('#lon').val())
        }
    });
    $('#lon').on('input', function() {
        if($("#lat").val()!=""){
            fetchPositionFromCoords($('#lat').val(), $('#lon').val())
        }
    });
}

function initAutoComplete() {
    $('.adress').autoComplete({
        formatResult: function (item) {
            return {
                value: item.id,
                text: item.label,
                html: [
                    item.label
                ]
            };
        },
        resolver: 'custom',
        events: {
            search: function (qry, callback) {
                // let's do a custom ajax call
                $.ajax(
                    'https://api-adresse.data.gouv.fr/search/',
                    {
                        data: {'q': qry, 'type': "housenumber"}
                    }
                ).done(function (res) {
                    callback(res.features)
                });
            },
            searchPost: function (resultFromServer) {
                list = []
                resultFromServer.forEach(element => list.push(element.properties));
                return list;
            }
        }
    });
}


