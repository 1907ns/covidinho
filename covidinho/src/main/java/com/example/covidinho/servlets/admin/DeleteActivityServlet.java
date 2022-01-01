package com.example.covidinho.servlets.admin;

import com.example.covidinho.beans.Activity;
import com.example.covidinho.beans.User;
import com.example.covidinho.dao.ActivityDao;
import com.example.covidinho.dao.UserDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "DeleteActivityServlet", value = "/DeleteActivityServlet")
public class DeleteActivityServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int actId = Integer.valueOf(request.getParameter("actid"));

        ActivityDao activityDao = new ActivityDao();
        String requestSent = null;
        try {
            requestSent = activityDao.deleteActivityById(actId);

            ArrayList<Activity> listeUser = activityDao.getAllActivities();
            request.getSession().setAttribute("allactivities", listeUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(requestSent.equals("SUCCESS") && !response.isCommitted())   //On success, you can display a message to user on Home page
        {
            request.setAttribute("succMessage", "Activité supprimée avec succès");
            request.getRequestDispatcher("/admin/AllActivitiesServlet").forward(request, response);

        }
        else   //On Failure, display a meaningful message to the User.
        {
            request.setAttribute("errMessage", "Erreur lors de la suppression de l'activité");
            request.getRequestDispatcher("/admin/AllActivitiesServlet").forward(request, response);

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
