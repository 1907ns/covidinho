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
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Accueil</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" ></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/gh/xcash/bootstrap-autocomplete@v2.3.7/dist/latest/bootstrap-autocomplete.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
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
                    <a class="dropdown-item" href="#">Mes activités</a>
                </div>
            </li>

            <% if (user.getAdmin()==1){ %>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarAdmin" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Admin
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarAdmin">
                    <a class="dropdown-item" href="admin/AllUsersServlet">Utilisateurs</a>
                    <a class="dropdown-item" href="FriendshipsServlet">Activités</a>
                </div>
            </li>
            <%} %>

            <li>
                <p class="navbar-text">Bienvenue </p> <b><%= user.getUsername() %></b>
            </li>
        </ul>

        <form class="form-inline my-2 my-lg-0" id="form" action="SearchUserServlet" method="get">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" id="searcheduser" name="searcheduser" aria-label="Search">
            <button class="btn btn-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>
<div class="container">
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
                <% String errorPlace = (String)request.getAttribute("errPlace");
                    if(errorPlace!=null) {%>
                <div class='alert alert-danger' role='alert'>
                    <%= errorPlace %>
                </div>
                <% } %>
                <div id="placeFound">

                </div>
                <div class="form-group row">
                    <div class="col">
                        <label for="begining" class="form-label"><p class="text-white">Horaire de début d'activité</p></label>
                        <input type="datetime-local" id="begining" name="begining">
                    </div>
                    <div class="col">
                        <label for="end" class="form-label"><p class="text-white">Horaire de fin d'activité</p></label>
                        <input type="datetime-local" id="end" name="end" disabled="true">
                    </div>
                </div>
                <% String errorTimes = (String)request.getAttribute("errTime");
                    if(errorTimes!=null) {%>
                <div class='alert alert-danger' role='alert'>
                    <%= errorTimes %>
                </div>
                <% } %>
                <div class="form-group row ">
                    <div class="col">
                        <button type="submit" id="submit" class="btn btn-light btn-lg">Submit</button>
                    </div>
                </div>
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
        </div>
        <div class="col-4 justify-content-center align-items-center" >
            <div class="container">
                <div class="row">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="#28a745" class="bi bi-bookmark-check-fill" viewBox="0 0 16 16">
                        <path fill-rule="evenodd" d="M2 15.5V2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v13.5a.5.5 0 0 1-.74.439L8 13.069l-5.26 2.87A.5.5 0 0 1 2 15.5zm8.854-9.646a.5.5 0 0 0-.708-.708L7.5 7.793 6.354 6.646a.5.5 0 1 0-.708.708l1.5 1.5a.5.5 0 0 0 .708 0l3-3z"/>
                    </svg>
                </div>
                <div class="row">
                    button
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
<script>

    $("#form").on("submit", function (e) {
        e.preventDefault();
        var self = $(this);
        $("#adress").val(encodeURIComponent($("#adress").val()));
        console.log($("#adress").val())
        $("#form").off("submit");
        self.unbind('submit');
        self.submit();
    });

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
            $("#place").html("<div class=\"col\" ><label for=\"adress\" class=\"form-label\"><p class=\"text-white\">Adresse</p></label> <input class=\"form-control adress\" type=\"text\" id=\"adress\" name=\"adresse\" autocomplete=\"off\"></div>");
            $('#placeFound').html("")
        }
    })

    $("#option2").change(function () {
        if($("#option2").val()){
            $("#place").html("<div class=\"col\"><label for=\"lat\" class=\"form-label\"><p class=\"text-white\">Latitude</p></label> <input class=\"form-control adress\" type=\"number\" id=\"lat\" name=\"lat\" step=\"0.001\"></div><div class=\"col\" ><label for=\"lon\" class=\"form-label\"><p class=\"text-white\">Longitude</p></label> <input class=\"form-control adress\" type=\"number\" id=\"lon\" name=\"lon\" step=\"0.001\"></div><script>enableJqueryForLatAndLong()</\script>");
            $("#submit").prop('disabled', true);
        }
    })

    function fetchPositionFromCoords(lat, lon){
        fetch("https://api-adresse.data.gouv.fr/reverse/?lat="+lat+"&lon="+lon+"&limit=1").then(function(response) {
            if(response.ok) {
                response.json().then(function(json) {
                    console.log(json);
                    console.log(json.features.length)
                    if(json.features.length == 0){
                        $('#placeFound').html("<p class=\"text-white\">Aucune Correspondance</p>")
                        $("#submit").prop('disabled', true);
                    } else{
                        console.log("elliort")
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
                        data: { 'q': qry, 'type':"housenumber"}
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

</script>
<%
    }
%>