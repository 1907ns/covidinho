<%@ page import="com.example.covidinho.beans.User" %><%--
  Created by IntelliJ IDEA.
  User: enescobar
  Date: 28/12/2021
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>

<%
    User user = (User) request.getSession().getAttribute("user");
    if(user != null) {
        response.sendRedirect("LoginServlet");
    }else{
%>
<html>
<head>
    <title>Connexion</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body class="h-100 bg-white">
<div class="container h-100" style="margin-bottom: 10px; padding: 10px">
    <div class="row h-100 justify-content-center align-items-center bg-light shadow">
        <div class="col-10 col-md-8 col-lg-6">
            <h1 class="text-center title">Connectez-vous!</h1>
            <form action="LoginServlet" method="post" class="justify-content-center">
                <div class="form-group">
                    <label for="login">Pseudo</label>
                    <input type="text" class="form-control" id="login"  name="login" required="" placeholder="Enter username">
                </div>
                <div class="form-group">
                    <label for="password">Mot de passe</label>
                    <input type="password" class="form-control" id="password" name="password" required="" placeholder="Password">
                </div>
                <button type="submit" class="btn btn-warning">Se connecter</button>
            </form>

            <p> Pas de compte? <a href="Register.jsp">Inscrivez-vous!</a> | <a href="index.jsp">Accueil</a></p>

            <br>
            <% String error = (String)request.getAttribute("errMessage");

                if(error!=null)
                {
            %>
            <div class='alert alert-danger' role='alert'>
                <%= error %>
                <% } %>
            </div>
        </div>
    </div>
</div>
</body>
</html>

<%
    }
%>
