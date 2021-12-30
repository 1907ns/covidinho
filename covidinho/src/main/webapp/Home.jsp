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

        <form class="form-inline my-2 my-lg-0" action="SearchUserServlet" method="get">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" id="searcheduser" name="searcheduser" aria-label="Search">
            <button class="btn btn-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>
<div class="container">
    <div class="row">
        <div class="col-8">
            <form action="ActivityServlet" method="post" class="justify-content-center">
                <div class="mb-3">
                    <label for="adress" class="form-label">Adresse</label>
                    <input class="form-control adress" type="text" id="adress" name="adresse" autocomplete="off">
                </div>
                <div class="mb-3">
                    <label for="begining" class="form-label">Horraire de début d'activité</label>
                    <input type="datetime-local" id="begining" name="begining">
                </div>
                <div class="mb-3">
                    <label for="end" class="form-label">Horraire de fin d'activité</label>
                    <input type="datetime-local" id="end" name="end" disabled="true">
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
        <div class="col-4">
            Afficher si on est comtaminé ou cas contact
        </div>
    </div>
</div>

</body>
</html>
<script>

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