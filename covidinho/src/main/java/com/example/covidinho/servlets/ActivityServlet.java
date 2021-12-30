package com.example.covidinho.servlets;

import com.example.covidinho.beans.Activity;
import com.example.covidinho.beans.User;
import com.example.covidinho.dao.ActivityDao;
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

@WebServlet(name = "ActivityServlet", value = "/ActivityServlet")
public class ActivityServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        Date begining;
        Date end;
        try {
            System.out.println(request.getParameter("begining"));
            begining = (Date) formatter.parse(request.getParameter("begining"));
            end = (Date) formatter.parse(request.getParameter("end"));
            String adresse = request.getParameter("adresse");

            HttpClient httpClient =  HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://api-adresse.data.gouv.fr/search/?q="+ URLEncoder.encode(adresse, StandardCharsets.UTF_8)+"&autocomplete=0&limit=1"))
                    .setHeader("User-Agent", "Java 11 HttpClient Bot")
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject= new JSONObject(httpResponse.body());

            System.out.println(jsonObject.toString());
            System.out.println(jsonObject.toMap().keySet().toString());

            if(jsonObject.getJSONArray("features").length() == 0){
                //TODO prendre en charge quand ya pas de resultat
                System.out.println("PAS de resultat");
            }

            jsonObject = jsonObject.getJSONArray("features").getJSONObject(0).getJSONObject("properties");

            if(jsonObject.getString("label")!=adresse){
                //TODO prendre en charge quand les endroits sont différents == lieux mal entré
                System.out.println("WRONG adresse");
            }

            String idAdresse = jsonObject.getString("id");

            System.out.println(idAdresse);

            Timestamp sqlBegining = new Timestamp(begining.getTime());
            Timestamp sqlEnd = new Timestamp(end.getTime());
            if(sqlEnd.before(sqlBegining)){
                //TODO prendre en compte quand la date de fin est plus tôt que celle de début
            }
            Activity activity = new Activity(sqlBegining, sqlEnd, idAdresse);
            User user  = (User) request.getSession().getAttribute("user");

            ActivityDao activityDao = new ActivityDao();

            String addActivity = activityDao.insertActivity(activity, user);

            if(addActivity.equals("SUCCESS"))   //On success, you can display a message to user on Home page
            {
                request.setAttribute("succMessage", "Activité ajoutée");
                request.getRequestDispatcher("/Home.jsp").forward(request, response);
            }
            else   //On Failure, display a meaningful message to the User.
            {
                request.setAttribute("errMessage", "Erreur");
                request.getRequestDispatcher("/Home.jsp").forward(request, response);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
