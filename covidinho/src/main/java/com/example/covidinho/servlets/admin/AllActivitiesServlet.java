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

@WebServlet(name = "AllActivitiesServlet", value = "/AllActivitiesServlet")
public class AllActivitiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActivityDao activityDao = new ActivityDao();
        try {
            ArrayList<Activity> listeAct = activityDao.getAllActivities();
            request.getSession().setAttribute("allactivities", listeAct);
            request.getRequestDispatcher("/Activities.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
