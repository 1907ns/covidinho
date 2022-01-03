<%@ page import="com.example.covidinho.beans.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.covidinho.dao.UserDao" %>
<%@ page import="com.example.covidinho.beans.Activity" %>
<%@ page import="com.example.covidinho.dao.PlaceDao" %>
<%@ page import="com.example.covidinho.beans.Place" %><%--
  Created by IntelliJ IDEA.
  User: enescobar
  Date: 30/12/2021
  Time: 16:57
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
    <title>Admin - Utilisateurs</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script  type="application/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="application/javascript" src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" ></script>
    <script type="application/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script type="application/javascript" src="https://cdn.jsdelivr.net/gh/xcash/bootstrap-autocomplete@v2.3.7/dist/latest/bootstrap-autocomplete.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
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

<h1 class="text-center title">Activités</h1>

<div class="row justify-content-center">
    <div class="col-auto">
        <table class="table table-striped table-responsive-sm table-responsive-md table-responsive-lg table-responsive-xl table-hover">
            <thead>
            <tr>
                <th scope="col">Date de début</th>
                <th scope="col">Date de fin</th>
                <th scope="col">Utilisateur</th>
                <th scope="col">Adresse</th>
                <th scope="col">Actions</th>
            </tr>
            </thead>
            <tbody>
                <%
        ArrayList<Activity> listeAct = (ArrayList<Activity>) request.getSession().getAttribute("allactivities");
        PlaceDao placeDao = new PlaceDao();
        UserDao userDao = new UserDao();
        if(listeAct.size()!=0){
            for(Activity a : listeAct){
                Place p = placeDao.getPlaceById(a.getPlace());
                User actCreator = userDao.getUserById(a.getIdUser());

    %>
            <tr class="table-info"><td><%=a.getBegining()%></td><td><%=a.getEnd()%></td><td><%=actCreator.getUsername()%></td><td><%=p.getAddress()%></td><td><a class="btn btn-danger" href="DeleteActivityServlet?actid=<%=a.getId()%>" role="button">Supprimer</a>
                <a class="btn btn-info" href="AccessActivityModificationServlet?actid=<%=a.getId()%>" role="button">Modifier</a>
            </td>
            </tr>

                <% } }else { %>
            <div class='alert alert-info' role='alert'>
                <p> Il n'y a aucune activité sur la plateforme.</p>
            </div>
                <% } %>
    </div>



    <div class="d-flex justify-content-center">
        <% String error = (String)request.getAttribute("errMessage");
            String success = (String)request.getAttribute("succMessage");
            if(error!=null)
            {
        %>
        <div class='alert alert-danger' role='alert'>
            <%= error %>
        </div>
        <% } else if (success != null){ %>

        <div class='alert alert-success' role='alert'>
            <%= success %>
        </div>
        <% } %>
    </div>
</div>

</body>
</html>

<%
    }
%>
