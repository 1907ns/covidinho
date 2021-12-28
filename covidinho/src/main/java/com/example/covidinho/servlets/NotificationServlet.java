package com.example.covidinho.servlets;

import com.example.covidinho.beans.Notification;
import com.example.covidinho.beans.User;
import com.example.covidinho.dao.NotificationDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


@WebServlet(name = "NotificationServlet", value = "/NotificationServlet")
public class NotificationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NotificationDao notificationDao = new NotificationDao();
        User user  = (User) request.getSession().getAttribute("user");
        try {
            ArrayList<Notification> listeNotif = notificationDao.getUserNotifications(user.getId());
            request.getSession().setAttribute("notifications", listeNotif);
            request.getRequestDispatcher("/Notifications.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
