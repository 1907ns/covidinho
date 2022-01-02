<%@ page pageEncoding="UTF-8" %>
<%@ page import="com.example.covidinho.beans.User" %><%--
  Created by IntelliJ IDEA.
  User: enescobar
  Date: 28/12/2021
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%
    User user = (User) request.getSession().getAttribute("user");
    if(user == null) {
        response.sendRedirect("Login.jsp");
    }else{
        user.isCured();
        session.setAttribute("user", user);

%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Accueil</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" ></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/gh/xcash/bootstrap-autocomplete@v2.3.7/dist/latest/bootstrap-autocomplete.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css"
          integrity="sha512-xodZBNTC5n17Xt2atTPuE1HxjVMSvLVW9ocqUKLsCC5CXdbqCmblAshOMAS6/keqq/sMZMZ19scR4PsZChSR7A=="
          crossorigin=""/>
    <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"
            integrity="sha512-XQoYMqMTK8LvdxXYG3nZ448hOEQiglfqkJs1NOQV44cWnUrBc8PkAOcXy20w0vlaXaVUearIOBhiXZ5V3ynxwA=="
            crossorigin=""></script>
</head>
<body>

<!--
<div class="container justify-content-center align-items-center">
    <div class="row h-100 justify-content-center align-items-center">
        <div class="col-8 bg-primary rounded-lg border-0 border-dark p-3 justify-content-center align-items-center">
            <div class="form-group row">
                <div class="col btn-group btn-group-toggle" data-toggle="buttons">
                    <label class="btn btn-light active">
                        <input type="radio" id="option1" autocomplete="off"> Adresse
                    </label>
                    <label class="btn btn-light">
                        <input type="radio" id="option2" autocomplete="off"> Coordonnées GPS
                    </label>
                </div>
            </div>
            <form action="ActivityServlet" method="post">
                <div class="form-group row" id="place">
                    <div class="col" >
                        <label for="adress" class="form-label"><p class="text-white">Adresse</p></label>
                        <input class="form-control adress" type="text" id="adress" name="adresse" autocomplete="off"/>
                    </div>
                </div>
                <%// String errorPlace = (String)request.getAttribute("errPlace");
                    //if(errorPlace!=null) {%>
                <div class='alert alert-danger' role='alert'>
                    <%//= errorPlace %>
                </div>
                <% //} %>
                <div id="placeFound">
-->

<div class="container-fluid bg-light p-0 m-0 d-flex min-vh-100 flex-column">

    <nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-lg" style="z-index: 100;">
        <a class="navbar-brand" href="#">Covidinho</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
    
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item ">
                    <a class="nav-link" href="LogoutServlet">Se déconnecter</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Actions
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="MyProfile.jsp">Mon profil</a>
                        <a class="dropdown-item" href="FriendshipsServlet">Mes amis</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="NotificationServlet">Mes notifications</a>
                        <a class="dropdown-item" href="MyActivitiesServlet">Mes activités</a>
                    </div>
                </li>
    
                <% if (user.getAdmin()==1){ %>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarAdmin" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Admin
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarAdmin">
                        <a class="dropdown-item" href="admin/AllUsersServlet">Utilisateurs</a>
                        <a class="dropdown-item" href="admin/AllActivitiesServlet">Activités</a>
                    </div>
                </li>
                <%} %>
    
                <li>
                    <p class="navbar-text">Bienvenue </p> <b><%= user.getUsername() %></b>
                </li>
                <% if(user.getIsPositive()==0) {%>
                <li>
                    <form class="justify-content-center align-items-center" action="CovidedServlet" method="post">
                        <input type="hidden" value="covided">
                        <div class="col justify-content-center align-items-center">
                            <button type="submit" id="covided" class="btn btn-danger btn-lg">Je suis Covided</button>
                        </div>
                    </form>
                </li>
                <% } %>
            </ul>
    
            <form class="form-inline my-2 my-lg-0" id="form" action="SearchUserServlet" method="get">
                <input class="form-control mr-sm-2" type="search" placeholder="Search" id="searcheduser" name="searcheduser" aria-label="Search">
                <button class="btn btn-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>
    </nav>
    <div class="container-fluid p-0 bg-light flex-fill fill d-flex justify-content-center shadow-lg">
        <div class="col-8 bg-primary border-0 border-dark d-flex ">
            <div class="container align-content-center justify-content-center">
                <div>
                    <div class="pt-5">
                        <div class="form-group row ">
                            <div class="col btn-group btn-group-toggle" data-toggle="buttons">
                                <label class="btn btn-light active">
                                    <input type="radio" id="option1" autocomplete="off"> Adresse
                                </label>
                                <label class="btn btn-light">
                                    <input type="radio" id="option2" autocomplete="off"> Coordonnées GPS
                                </label>
                                <label class="btn btn-light">
                                    <input type="radio" id="option3" autocomplete="off"> Map
                                </label>
                            </div>
                        </div>
                    </div>
                    <form action="ActivityServlet" id="activityForm" method="post">
                        <div class="form-group row" id="place">
                            <div class="col" >
                                <label for="adress" class="form-label"><p class="text-white">Adresse</p></label>
                                <input class="form-control adress" type="text" id="adress" name="adresse" autocomplete="off"/>
                            </div>
                        </div>
                        <% String errorPlace = (String)request.getAttribute("errPlace");
                            if(errorPlace!=null) {%>
                        <div class='alert alert-danger' role='alert'>
                            <%= errorPlace %>
                        </div>
                        <% } %>
                        <div class="form-group row " id="placeFound">
                            <p class="text-white invisible mr-3 ml-3">Aucune Correspondance</p>
                        </div>
                        <div class="form-group row pb-3">
                            <div class="col">
                                <label for="begining" class="form-label"><p class="text-white">Horaire de début d'activité</p></label>
                                <input type="datetime-local" id="begining" name="begining">
                            </div>
                            <div class="col">
                                <label for="end" class="form-label"><p class="text-white">Horaire de fin d'activité</p></label>
                                <input type="datetime-local" id="end" name="end" disabled="true">
                            </div>
                            <div class="col align-self-end">
                                <button type="submit" id="submit" class="btn btn-light btn-lg">Submit</button>
                            </div>
                        </div>
                        <% String errorTimes = (String)request.getAttribute("errTime");
                            if(errorTimes!=null) {%>
                        <div class='alert alert-danger' role='alert'>
                            <%= errorTimes %>
                        </div>
                        <% } %>
                        <% String succActivity = (String)request.getAttribute("succActivity");
                            if(succActivity!=null) {%>
                        <div class='alert alert-success' role='alert'>
                            <%= succActivity %>
                        </div>
                        <% } %>
                        <% String errActivity = (String)request.getAttribute("errActivity");
                            if(errActivity!=null) {%>
                        <div class='alert alert-danger' role='alert'>
                            <%= errActivity %>
                        </div>
                        <% } %>
                    </form>
                    <div id="carte" class="h-50"></div>
                </div>
            </div>

        </div>
        <div class="col-4 bg-light border-0 border-dark d-flex flex-wrap align-items-center carousel slide" data-ride="carousel" id="carousel">
            <ol class="carousel-indicators">
                <li data-target="#carouselExampleIndicators" style="color: #000000" data-slide-to="0" class="active"></li>
                <li data-target="#carouselExampleIndicators" style="color: #000000" data-slide-to="1"></li>
            </ol>
            <!--
            <div class="row justify-content-center align-items-center">
                <svg xmlns="http://www.w3.org/2000/svg" fill="#28a745" class="bi bi-bookmark-check-fill" viewBox="0 0 16 16">
                    <path fill-rule="evenodd" d="M2 15.5V2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.74.439L8 13.069l-5.26 2.87A.5.5 0 0 1 2 15.5zm8.854-9.646a.5.5 0 0 0-.708-.708L7.5 7.793 6.354 6.646a.5.5 0 1 0-.708.708l1.5 1.5a.5.5 0 0 0 .708 0l3-3z"/>
                </svg>
            </div> -->
            <div class="carousel-inner h-100 col d-flex align-items-center ">
                <div class="carousel-item active h-100 ">
                    <div class="h-50 container d-flex ">
                        <div class="row align-items-center h-100 w-100 justify-content-center">
                            <canvas id="hospitalisations"></canvas>
                        </div>
                    </div>
                    <div class="h-50 d-flex ">
                        <div class="row align-items-center h-100 w-100 justify-content-center">
                            <canvas id="doseVaccin"></canvas>
                        </div>
                    </div>
                </div>
                <div class="carousel-item h-100 ">
                    <div class="h-50 container d-flex ">
                        <div class="row align-items-center h-100 w-100 justify-content-center">
                            <canvas id="incidence"></canvas>
                        </div>
                    </div>
                    <!--
                    <div class="h-50 d-flex ">
                        <div class="row align-items-center h-100 w-100 justify-content-center">
                            <canvas id="doseVaccin"></canvas>
                        </div>
                    </div>
                    -->
                </div>
            </div>

            <!--
            <div class="row justify-content-center align-items-center">
                <div></div>
            </div>

            <div class="row justify-content-center align-items-center">
            </div>
            <div class="row justify-content-center align-items-center">
            </div>
            -->

            <a class="carousel-control-prev" style="color: #000000" href="#carousel" role="button" data-slide="prev">
                <span class="bi bi-arrow-left-short" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" style="color: #000000" href="#carousel" role="button" data-slide="next">
                <span class="bi bi-arrow-right-short" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
    </div>
</div>

</body>
</html>
<script>
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

    $("#option1").change(function () {
        if($("#option1").val()){
            $("#place").html("<div class=\"col\" ><label for=\"adress\" class=\"form-label\"><p class=\"text-white\">Adresse</p></label> <input class=\"form-control adress\" type=\"text\" id=\"adress\" name=\"adresse\" autocomplete=\"off\"></div><script>initAutoComplete()</\script>");
            $('#placeFound').html("<p class=\"text-white invisible pr-3 pl-3\">Aucune Correspondance</p>")
            $("#submit").prop('disabled', false);
        }
    })

    $("#option2").change(function () {
        if($("#option2").val()){
            $("#place").html("<div class=\"col\"><label for=\"lat\" class=\"form-label\"><p class=\"text-white\">Latitude</p></label> <input class=\"form-control adress\" type=\"number\" id=\"lat\" name=\"lat\" step=\"0.001\"></div><div class=\"col\" ><label for=\"lon\" class=\"form-label\"><p class=\"text-white\">Longitude</p></label> <input class=\"form-control adress\" type=\"number\" id=\"lon\" name=\"lon\" step=\"0.001\"></div><script>enableJqueryForLatAndLong()</\script>");
            $('#placeFound').html("<p class=\"text-white  pr-3 pl-3\">Aucune Correspondance</p>")
            $("#submit").prop('disabled', true);
        }
    })

    $("#option3").change(function () {
        if($("#option3").val()){
            $("#place").html("<div class=\"col flex-grow-1\" ><label for=\"adress\" class=\"form-label\"><p class=\"text-white\">Search</p></label> <input class=\"form-control \" type=\"text\" id=\"placeSearched\"></div><div class=\"col align-self-end\" ><button type=\"button\" class=\"btn btn-success\" id=\"searchPlace\">Search</button></div>" +
                "<div><input class=\"form-control adress \" type=\"hidden\" id=\"lat\" name=\"lat\" ><input class=\"form-control adress\" type=\"hidden\" id=\"lon\" name=\"lon\"></div>");
            $('#placeFound').html("<div class=\"col\" ><label for=\"adress\" class=\"form-label\"><p class=\"text-white\">Lieu(x) trouvé(s)</p></label><select class=\"form-select form-control \" disabled id=\"SearchResult\" aria-label=_\"Elliot\"></select> </div>")
            $("#submit").prop('disabled', true);
            mapInit()
        }
    })

    function fetchPositionFromCoords(lat, lon){
        fetch("https://api-adresse.data.gouv.fr/reverse/?lat="+lat+"&lon="+lon+"&limit=1").then(function(response) {
            if(response.ok) {
                response.json().then(function(json) {
                    if(json.features.length == 0){
                        $('#placeFound').html("<p class=\"text-white\">Aucune Correspondance</p>")
                        $("#submit").prop('disabled', true);
                    } else{
                        $('#placeFound').html("<p class=\"text-white\">"+json.features[0].properties.label+"</p>")
                        $("#submit").prop('disabled', false);
                    }
                });
            } else{
                $('#placeFound').html("<p class=\"text-white\">Erreur</p>")
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
                console.log(item);
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
                    console.log(list)
                    return list;
                }
            }
        });
    }
    initAutoComplete()

    //------------------------------------------------------------------------------Data (Chart.js) management

    colors = []
    colors.push({"backgroundColor":'rgba(40,167,69,0.5)', "borderColor":'rgb(40,167,69)'})
    colors.push({"backgroundColor":'rgba(214,51,132,0.5)', "borderColor":'rgb(214,51,132)'})
    colors.push({"backgroundColor":'rgba(253,126,20,0.5)', "borderColor":'rgb(253,126,20)'})

    function fetchIcidence(url, idDiv, title){
        return fetch(url).then(function(response) {
            if(response.ok) {
                response.json().then(function(json) {
                    let dataFetched = new Object();
                    dataFetched.name = json.nom;
                    dataFetched.unite = json.unite;
                    dataFetched.lastValues = new Object();
                    dataFetched.lastValues.last_value = json.france[0].last_value;
                    dataFetched.lastValues.last_date = json.france[0].last_date;
                    dataFetched.lastValues.evol = json.france[0].evol;
                    dataFetched.lastValues.evol_percentage = json.france[0].evol_percentage;
                    dataFetched.values = []
                    json.france[0].values.forEach(value => dataFetched.values.push({x:value.date, y:parseInt(value.value)}));
                    return dataFetched;
                }).then(function (dataFetched) {

                    if(idDiv in chartList){
                        dataSet = {
                            label: dataFetched.unite,
                            backgroundColor: 'rgba(40,167,69,0.5)',
                            borderColor: 'rgb(40,167,69)',
                            fill: 'origin',
                            data: dataFetched.values,
                            showLine: false
                        }
                        chartList[idDiv].data.datasets.push(dataSet)
                        chartList[idDiv].update();
                    } else {
                        data = {
                            type: 'line',
                            datasets: [{
                                label: dataFetched.unite,
                                backgroundColor: 'rgba(0,123,255,0.5)',
                                borderColor: 'rgb(0,123,255)',
                                fill: 'origin',
                                data: dataFetched.values,
                                showLine: false
                            }]
                        };

                        config = {
                            type: 'line',
                            data: data,
                            options: {
                                responsive: true,
                                elements :{
                                    point:{
                                        radius:0,
                                    }
                                },
                                line: {
                                    tension:0.4,
                                },
                                plugins: {
                                    title: {
                                        display: true,
                                        text: title,/*
                                        padding: {
                                            top: 40,
                                            bottom: 10
                                        }*/
                                    }
                                },
                                scales: {
                                    x: {
                                        ticks:{
                                            autoSkip: true,
                                            maxTicksLimit: 7
                                        }
                                    },
                                },
                                interaction: {
                                    intersect: false,
                                    mode: 'index',
                                },
                            }
                        };

                        var myChart = new Chart(
                            document.getElementById(idDiv),
                            config
                        );

                        chartList[idDiv]=myChart
                    }

                });
            }
        });
    }

    chartList = {}

    fetchIcidence("https://data.widgets.dashboard.covid19.data.gouv.fr/taux_incidence.json", "incidence", "Nombre moyen de nouvelles hospitalisations quotidiennes");
    console.log(chartList)
    fetchIcidence("https://data.widgets.dashboard.covid19.data.gouv.fr/hospitalisations_moyenne_quotidienne.json", "hospitalisations", "Taux d'incidence");
    console.log(chartList)
    fetchIcidence("https://data.widgets.dashboard.covid19.data.gouv.fr/vaccins_premiere_dose.json", "doseVaccin", "Nombre de personnes vaccinées");
    console.log(chartList)
    fetchIcidence("https://data.widgets.dashboard.covid19.data.gouv.fr/vaccins_vaccines.json", "doseVaccin", "Nombre de personnes vaccinées");
    console.log(chartList)

    ///---------------------------------------------------------------------------------------------------------------------------Management map

    var placeFound = []
    var myLayer



    var map = L.map('carte').setView([48.692054, 6.184417], 16);
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

        console.log(placeSelected)

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
                console.log(placeSearched)



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
                                    console.log(placeFound[singlePLaceFound])

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


</script>
<%
    }
%>