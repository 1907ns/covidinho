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
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Inscription</title>
</head>
<body>
<h1 class="text-center title">Inscrivez-vous!</h1>
<div class="container">
    <form action="RegisterServlet" method="post" class="justify-content-center">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" class="form-control" id="username"  name="username" required="" placeholder="Enter username">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password" name="password" required="" placeholder="Password">
        </div>
        <div class="form-group">
            <label for="firstname">Firstname</label>
            <input type="text" class="form-control" pattern="[A-Z a-z]*" id="firstname"  name="firstname" required="" placeholder="Enter firstname">
        </div>
        <div class="form-group">
            <label for="name">Name</label>
            <input type="text" class="form-control" id="name" pattern="[A-Z a-z]*" name="name" placeholder="Enter name" required="">
        </div>
        <div class="form-group">
            <label for="birthdate">Birthdate</label>
            <input type="date" class="form-control" id="birthdate" pattern="d{1,2}-d{1,2}-d{4}" name="birthdate" required="">
        </div>
        <button type="submit" class="btn btn-primary">S'inscrire</button>
    </form>

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
        <div class="container">
            <p> Déjà un Compte? <a href="Login.jsp">Connectez-vous!</a></p>
        </div>
</body>
</html>

<%
    }
%>