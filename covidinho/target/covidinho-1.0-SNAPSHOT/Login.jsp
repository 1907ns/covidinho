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
<body>
<body>
<h1 class="text-center title">Connectez-vous!</h1>
<div class="container">
    <form action="LoginServlet" method="post" class="justify-content-center">
        <div class="form-group">
            <label for="login">Username</label>
            <input type="text" class="form-control" id="login"  name="login" required="" placeholder="Enter username">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password" name="password" required="" placeholder="Password">
        </div>
        <button type="submit" class="btn btn-dark">Se connecter</button>
    </form>

    <br>
    <% String error = (String)request.getAttribute("errMessage");

        if(error!=null)
        {
    %>
    <div class='alert alert-danger' role='alert'>
        <%= error %>
        <% } %>
    </div>
    <div class="container">
        <p> Pas de compte? <a href="Register.jsp">Inscrivez-vous!</a></p>
    </div>

</div>
</body>
</body>
</html>

<%
    }
%>
