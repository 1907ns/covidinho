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

@WebServlet(name = "DeleteNotificationServlet", value = "/DeleteNotificationServlet")
public class DeleteNotificationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int notifId = Integer.valueOf(request.getParameter("notifid"));

        NotificationDao notificationDao = new NotificationDao();
        String requestSent = null;
        try {
            requestSent = notificationDao.deleteNotificationById(notifId);
            User user  = (User) request.getSession().getAttribute("user");
            ArrayList<Notification> listeNotif = notificationDao.getUserNotifications(user.getId());
            request.getSession().setAttribute("notifications", listeNotif);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(requestSent.equals("SUCCESS"))   //On success, you can display a message to user on Home page
        {
            request.setAttribute("succMessage", "Notification supprimée avec succès");
            request.getRequestDispatcher("/Notifications.jsp").forward(request, response);
            response.sendRedirect("Notifications.jsp");
        }
        else   //On Failure, display a meaningful message to the User.
        {
            request.setAttribute("errMessage", "Erreur lors de la suppression de la notification");
            request.getRequestDispatcher("/Notifications.jsp").forward(request, response);
            response.sendRedirect("Notifications.jsp");
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
