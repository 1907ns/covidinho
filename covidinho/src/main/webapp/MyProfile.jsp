<%@ page import="com.example.covidinho.beans.User" %><%--
  Created by IntelliJ IDEA.
  User: enescobar
  Date: 28/12/2021
  Time: 11:26
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
    <title>Mon profil</title>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" ></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/gh/xcash/bootstrap-autocomplete@v2.3.7/dist/latest/bootstrap-autocomplete.min.js"></script>
    <script src="jquery-ui/jquery-ui.min.js"></script>
    <script src="scripts/datepicker.js"> </script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="jquery-ui/jquery-ui.min.css">


</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand" href="Home.jsp">Covidinho</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="Home.jsp">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item ">
                <a class="nav-link" href="LogoutServlet">Se d??connecter</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Actions
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="#">Mon profil</a>
                    <a class="dropdown-item" href="FriendshipsServlet">Mes amis</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="NotificationServlet">Mes notifications</a>
                    <a class="dropdown-item" href="MyActivitiesServlet">Mes activit??s</a>
                </div>
            </li>

            <% if (user.getAdmin()==1){ %>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarAdmin" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Admin
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarAdmin">
                    <a class="dropdown-item" href="admin/AllUsersServlet">Utilisateurs</a>
                    <a class="dropdown-item" href="admin/AllActivitiesServlet">Activit??s</a>
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
<h1 class="text-center title">Mon profil</h1>
<div class="container bg-light shadow" style="margin-bottom: 10px; padding: 10px">
    <form action="ProfileModificationServlet" method="post" class="justify-content-center">
        <div class="form-group hide">
            <input type="hidden" type="text" class="form-control" id="userid"  value="<%=user.getId()%>" name="userid">
        </div>
        <div class="form-group">
            <label for="username">Pseudo</label>
            <input type="text" class="form-control" id="username"  value="<%=user.getUsername()%>" name="username" required="" placeholder="Enter username">
        </div>
        <div class="form-group">
            <label for="password">Mote de passe</label>
            <input type="password" class="form-control" id="password" value="<%=user.getPassword()%>" name="password" required="" placeholder="Password">
        </div>
        <div class="form-group">
            <label for="firstname">Pr??nom</label>
            <input type="text" class="form-control" pattern="[A-Z a-z]*" id="firstname"  value="<%=user.getFirstname()%>" name="firstname" required="" placeholder="Enter firstname">
        </div>
        <div class="form-group">
            <label for="name">Nom</label>
            <input type="text" class="form-control" id="name" pattern="[A-Z a-z]*" value="<%=user.getName()%>" name="name" placeholder="Enter name" required="">
        </div>
        <div class="form-group">
            <label for="birthdate">Date de naissance</label>
            <input type="text" class="form-control" id="birthdate" name="birthdate" required="" value="<%=user.getBirthdate()%>">
        </div>
        <button type="submit" class="btn btn-primary">Modifier</button>
    </form>
    <br>
    <a href="DeleteMyProfileServlet" class="btn btn-danger">Supprimer votre compte</a>
    <br>
        <% String error = (String)request.getAttribute("errMessage");

            if(error!=null)
            {
            %>
    <div class='alert alert-danger' role='alert'>
            <%= error %>
        <div/>
            <% } %>
        <!----------------------------------------------------------------------------->
            <% String success = (String)request.getAttribute("succMessage");
            if(success!=null)
            {
            %>
        <div class='alert alert-success' role='alert'>
            <%= success %>
            <div/>
            <% } %>

        </div>
</body>
</html>

<%
    }
%>

