package com.example.covidinho.servlets;

import com.example.covidinho.beans.Activity;
import com.example.covidinho.beans.Friendship;
import com.example.covidinho.beans.User;
import com.example.covidinho.dao.ActivityDao;
import com.example.covidinho.dao.FriendshipDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "MyActivitiesServlet", value = "/MyActivitiesServlet")
public class MyActivitiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActivityDao activityDao = new ActivityDao();
        User user  = (User) request.getSession().getAttribute("user");
        try {
            ArrayList<Activity> listeAct = activityDao.getUserActivities(user.getId());
            request.getSession().setAttribute("activities", listeAct);
            request.getRequestDispatcher("/MyActivities.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
