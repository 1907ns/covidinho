<%@ page import="com.example.covidinho.beans.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Covidinho</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">

</head>
<body class="bg-white">
<h1 class="text-center title">Bienvenue sur Covidinho!</h1>

<div class="container">
    <div class="jumbotron bg-light shadow">
        <h1 class="display-4">Qu'est-ce que Covidinho?</h1>
        <p class="lead">Covidinho est une application permettant de notifier vos amis et les personnes croisées et de vous notifier en cas de positivité au Covid-19.</p>
        <hr class="my-4">
        <p>Ajoutez vos amis, déclarez vos activités, et si c'est malheureusement le cas, déclarez votre positivité au Covid-19.</p>
        <p class="lead">
            <a class="btn btn-warning btn-lg" href="Register.jsp" role="button">S'inscrire</a>
            <a class="btn btn-warning btn-lg" href="Login.jsp" role="button">Se connecter</a>
        </p>
    </div>

    <div class="jumbotron bg-light shadow">
        <h1 class="display-4">Mais plus encore...</h1>
        <p class="lead">De plus, Covidinho vous permet de consultez les stats du Covid-19 grâce à des données fournies par le gouvernement français.</p>
        <hr class="my-4">
        <p>La déclaration d'activité est disponible par 3 moyens, l'adresse, les coordonnées de géolocalisation, et la carte!</p>
    </div>
</div>
<div class="container">
    <div class="card-deck" style="margin: 10px;">
        <div class="card border-dark">
            <div class="card-body bg-primary">
                <h5 class="card-title">Informez vos proches directement.</h5>
                <p class="card-text">Avec un clique vos proches seront notifiés si vous êtes vaccinés et/ou si vous êtes positifs au coronavirus.</p>
            </div>
        </div>
        <div class="card border-dark">
            <div class="card-body bg-primary">
                <h5 class="card-title">Déclarez vos activités.</h5>
                <p class="card-text">Pour notifier et être notifiés par les personnes croisées, déclarez vos activites en 1 minute!</p>
            </div>
        </div>
        <div class="card border-dark">
            <div class="card-body bg-primary">
                <h5 class="card-title">Soyez informés des chiffres du Covid-19 </h5>
                <p class="card-text">Notre équipe vous met à disposition les chiffres relatifs à l'évolution du Covid-19 et de la campagne de vaccination.</p>
            </div>
        </div>
    </div>
</div>

</body>
</html>