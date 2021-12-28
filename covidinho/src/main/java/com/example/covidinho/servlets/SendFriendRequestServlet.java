package com.example.covidinho.servlets;

import com.example.covidinho.beans.User;
import com.example.covidinho.dao.NotificationDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "SendFriendRequestServlet", value = "/SendFriendRequestServlet")
public class SendFriendRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User sender =(User) request.getSession().getAttribute("user");
        User receiver = (User) request.getSession().getAttribute("exSearchedUser");

        NotificationDao notificationDao = new NotificationDao();
        String requestSent = null;
        try {
            requestSent = notificationDao.insertFriendRequestNotification(sender, receiver);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(requestSent.equals("SUCCESS"))   //On success, you can display a message to user on Home page
        {
            request.setAttribute("succMessage", "Votre demande d'ami a été envoyée avec succès");
            request.getRequestDispatcher("/SearchProfile.jsp").forward(request, response);
        }
        else   //On Failure, display a meaningful message to the User.
        {
            request.setAttribute("errMessage", "Erreur lors de l'envoi de la demande");
            request.getRequestDispatcher("/SearchProfile.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

