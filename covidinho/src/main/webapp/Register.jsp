<%@ page import="com.example.covidinho.beans.User" %><%--
  Created by IntelliJ IDEA.
  User: enescobar
  Date: 28/12/2021
  Time: 11:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%
    User user = (User) request.getSession().getAttribute("user");
    if(user != null) {
        response.sendRedirect("Home.jsp");
    }else{
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="h-100">
<head>
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

    <title>Inscription</title>
</head>
<body class="h-100 bg-white">
<div class="container h-100" style="margin-bottom: 10px; padding: 10px">
    <div class="row h-100 justify-content-center align-items-center bg-light shadow">
        <div class="col-10 col-md-8 col-lg-6">
            <h1 class="text-center title">Inscrivez-vous!</h1>
            <form action="RegisterServlet" method="post" class="justify-content-center ">
                <div class="form-group">
                    <label for="username">Pseudo</label>
                    <input type="text" class="form-control" id="username"  name="username" required="" placeholder="Enter username">
                </div>
                <div class="form-group">
                    <label for="password">Mot de passe</label>
                    <input type="password" class="form-control" id="password" name="password" required="" placeholder="Password">
                </div>
                <div class="form-group">
                    <label for="firstname">Prénom</label>
                    <input type="text" class="form-control" pattern="[A-Z a-z]*" id="firstname"  name="firstname" required="" placeholder="Enter firstname">
                </div>
                <div class="form-group">
                    <label for="name">Nom</label>
                    <input type="text" class="form-control" id="name" pattern="[A-Z a-z]*" name="name" placeholder="Enter name" required="">
                </div>
                <div class="form-group">
                    <label for="birthdate">Date de naissance</label>
                    <input type="text" class="form-control" id="birthdate" name="birthdate" required="">
                </div>
                <button type="submit" class="btn btn-warning">S'inscrire</button>
            </form>

            <p> Déjà un Compte? <a href="Login.jsp">Connectez-vous!</a> | <a href="index.jsp">Accueil</a></p>

            <% String error = (String)request.getAttribute("errMessage");

                if(error!=null)
                {
            %>
            <div class='alert alert-danger' role='alert'>
                <%= error %>
            </div>
            <% } %>
            <!----------------------------------------------------------------------------->
            <% String success = (String)request.getAttribute("succMessage");
                if(success!=null)
                {
            %>
            <div class='alert alert-success' role='alert'>
                <%= success %>
            </div>
            <% } %>
        </div>


    </div>
    <br>

    </div>

</body>
</html>

<%
    }
%>