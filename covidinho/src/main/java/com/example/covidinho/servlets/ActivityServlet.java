package com.example.covidinho.servlets;

import com.example.covidinho.beans.Activity;
import com.example.covidinho.beans.Place;
import com.example.covidinho.beans.User;
import com.example.covidinho.dao.ActivityDao;
import com.example.covidinho.dao.PlaceDao;
import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@WebServlet(name = "ActivityServlet", value = "/ActivityServlet")
public class ActivityServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        Date begining;
        Date end;
        try {
            HttpResponse<String> httpResponse;
            HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
            HttpRequest httpRequest;
            if(request.getParameterMap().containsKey("adresse")) {

                String adresse = request.getParameter("adresse");

                httpRequest = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create("https://api-adresse.data.gouv.fr/search/?q=" + URLEncoder.encode(adresse, StandardCharsets.UTF_8) + "&autocomplete=0&limit=1"))
                        .setHeader("User-Agent", "Java 11 HttpClient Bot")
                        .build();
            } else{
                String lat = request.getParameter("lat");
                String lon = request.getParameter("lon");
                httpRequest = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create("https://api-adresse.data.gouv.fr/reverse/?lat="+lat+"&lon="+lon+"&limit=1"))
                        .setHeader("User-Agent", "Java 11 HttpClient Bot")
                        .build();
            }
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject= new JSONObject(httpResponse.body());


            if(jsonObject.toMap().containsKey("title")){
                if(jsonObject.getString("title").equals("Missing query")){
                    request.setAttribute("errPlace", "Champs vide");
                    request.getRequestDispatcher("/Home.jsp").forward(request, response);
                    return;
                }
            }

            if(jsonObject.getJSONArray("features").length() == 0){
                request.setAttribute("errPlace", "Emplacement inconnu");
                request.getRequestDispatcher("/Home.jsp").forward(request, response);
                return;
            }

            jsonObject = jsonObject.getJSONArray("features").getJSONObject(0).getJSONObject("properties");

            if(request.getParameterMap().containsKey("adresse")) {
                if (!Objects.equals(jsonObject.getString("label"), request.getParameter("adresse"))) {
                    request.setAttribute("errPlace", "L'adresse '"+request.getParameter("adresse").toString()+"' est mal formatée");
                    request.getRequestDispatcher("/Home.jsp").forward(request, response);
                    return;
                }
            }

            String idAdresse = jsonObject.getString("id");
            String adresse = jsonObject.getString("label");
            begining = (Date) formatter.parse(request.getParameter("begining"));
            end = (Date) formatter.parse(request.getParameter("end"));

            Timestamp sqlBegining = new Timestamp(begining.getTime());
            Timestamp sqlEnd = new Timestamp(end.getTime());
            if(sqlEnd.before(sqlBegining)){
                request.setAttribute("errTime", "Le "+sqlEnd.toString()+" ne peut pas avoir eu lieu avant le "+sqlBegining.toString());
                request.getRequestDispatcher("/Home.jsp").forward(request, response);
                return;
            }
            User user  = (User) request.getSession().getAttribute("user");
            Place place = new Place(idAdresse, adresse);
            Activity activity = new Activity(sqlBegining, sqlEnd, idAdresse, user.getId());


            ActivityDao activityDao = new ActivityDao();
            PlaceDao placeDao = new PlaceDao();
            String addPlace = placeDao.insertPlace(place);
            String addActivity = activityDao.insertActivity(activity, user);

            if(addActivity.equals("SUCCESS"))   //On success, you can display a message to user on Home page
            {
                request.setAttribute("succActivity", "Activité ajoutée");
                request.getRequestDispatcher("/Home.jsp").forward(request, response);
                return;
            }
            else   //On Failure, display a meaningful message to the User.
            {
                request.setAttribute("errActivity", "Erreur");
                request.getRequestDispatcher("/Home.jsp").forward(request, response);
                return;
            }


        } catch (ParseException e) {
            request.setAttribute("errTime", "Date(s) manquante(s)");
            request.getRequestDispatcher("/Home.jsp").forward(request, response);
            return;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
