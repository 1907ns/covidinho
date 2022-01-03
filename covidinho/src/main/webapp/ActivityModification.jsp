<%@ page pageEncoding="UTF-8" %>
<%@page buffer="8192kb" autoFlush="true" %>
<%@ page import="com.example.covidinho.beans.User" %>
<%@ page import="com.example.covidinho.beans.Activity" %>
<%@ page import="com.example.covidinho.beans.Place" %>
<%@ page import="com.example.covidinho.dao.PlaceDao" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: enescobar
  Date: 03/01/2022
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User) request.getSession().getAttribute("user");
    if(user == null) {
        response.sendRedirect("Login.jsp");
    }else{
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin - Activités</title>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
<body onload="initAutoComplete()">
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand" href="Home.jsp">Covidinho</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="../LoginServlet">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item ">
                <a class="nav-link" href="../LogoutServlet">Se déconnecter</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Actions
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="../MyProfile.jsp">Mon profil</a>
                    <a class="dropdown-item" href="../FriendshipsServlet">Mes amis</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="../NotificationServlet">Mes notifications</a>
                    <a class="dropdown-item" href="../MyActivitiesServlet">Mes activités</a>
                </div>
            </li>
            <% if (user.getAdmin()==1){ %>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarAdmin" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Admin
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarAdmin">
                    <a class="dropdown-item" href="AllUsersServlet">Utilisateurs</a>
                    <a class="dropdown-item" href="AllActivitiesServlet">Activités</a>
                </div>
            </li>
            <%} %>
            <li>
                <p class="navbar-text">Bienvenue </p> <b><%= user.getUsername() %></b>
            </li>
        </ul>

        <form class="form-inline my-2 my-lg-0" action="../SearchUserServlet" method="get">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" id="searcheduser" name="searcheduser" aria-label="Search">
            <button class="btn btn-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>

<% Activity modifAct = (Activity) request.getSession().getAttribute("actModif");
    PlaceDao placeDao = new PlaceDao();
    Place place = placeDao.getPlaceById(modifAct.getPlace());
    String beginning = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm").format(modifAct.getBegining());
    String end = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm").format(modifAct.getEnd());
    request.getSession().setAttribute("modifactid", modifAct.getId());
%>

<div class="container align-content-center justify-content-center">
    <div>
        <div class="pt-5">
            <div class="form-group row ">
                <div class="col btn-group btn-group-toggle" data-toggle="buttons">
                    <label class="btn btn-light active">
                        <input type="radio" id="option11" autocomplete="off"> Adresse
                    </label>
                    <label class="btn btn-light">
                        <input type="radio" id="option22" autocomplete="off"> Coordonnées GPS
                    </label>
                    <label class="btn btn-light">
                        <input type="radio" id="option33" autocomplete="off"> Map
                    </label>
                </div>
            </div>
        </div>
        <form action="ModifyActivityServlet" id="activityForm" method="post">
            <div class="form-group row" id="place">
                <div class="col" >
                    <label for="adress" class="form-label"><p class="text-white">Adresse</p></label>
                    <input class="form-control adress" type="text" id="adress" name="adresse" autocomplete="off" value="<%=place.getAddress()%>"/>
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
                    <input type="datetime-local" id="begining" name="begining" required="" >
                </div>
                <div class="col">
                    <label for="end" class="form-label"><p class="text-white">Horaire de fin d'activité</p></label>
                    <input type="datetime-local" id="end" name="end" disabled="true" required="">
                </div>
                <div class="col align-self-end">
                    <button type="submit" id="submit" class="btn btn-warning btn-lg">Submit</button>
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
        <div id="carte1" class="h-50"></div>
    </div>
</div>

<script src="../scripts/script.js" ></script>
<script src="../scripts/mymap.js"></script>
</body>
</html>

<%
    }
%>