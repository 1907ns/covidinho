package com.example.covidinho.servlets;

import com.example.covidinho.beans.Friendship;
import com.example.covidinho.beans.Notification;
import com.example.covidinho.beans.User;
import com.example.covidinho.dao.FriendshipDao;
import com.example.covidinho.dao.NotificationDao;
import com.example.covidinho.dao.UserDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "AcceptFriendServlet", value = "/AcceptFriendServlet")
public class AcceptFriendServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int notifId = Integer.valueOf(request.getParameter("notifid"));
        int idUser1 = Integer.valueOf(request.getParameter("idUser1"));
        int idUser2 = Integer.valueOf(request.getParameter("idUser2"));


        FriendshipDao friendshipDao = new FriendshipDao();
        NotificationDao notificationDao = new NotificationDao();
        UserDao userDao = new UserDao();


        try {

            User sender =(User) request.getSession().getAttribute("user");
            User receiver = userDao.getUserById(idUser2);


            String friendshipResponse = friendshipDao.insertNewFriendship(idUser1, idUser2); // on crée la nouvelle relation d'ami
            String deletedNotif = notificationDao.deleteNotificationById(notifId); // on supprime la notification associée
            notificationDao.insertFriendResponseNotification(sender, receiver); // on notifie l'user qui a envyé la demande d'ami

            ArrayList<Notification> listeNotif = notificationDao.getUserNotifications(sender.getId());
            request.getSession().setAttribute("notifications", listeNotif);
            if(friendshipResponse.equals("SUCCESS") && deletedNotif.equals("SUCCESS"))
            {
                request.setAttribute("succMessage", "Requête acceptée avec succès");
                request.getRequestDispatcher("/Notifications.jsp").forward(request, response);
                response.sendRedirect("Notifications.jsp");
            }
            else   //On Failure, display a meaningful message to the User.
            {
                System.out.println("erreur");
                request.setAttribute("errMessage", "Erreur lors du processus.");
                request.getRequestDispatcher("/Notifications.jsp").forward(request, response);
                response.sendRedirect("Notifications.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errMessage", "Erreur lors du processus.");
            request.getRequestDispatcher("/Notifications.jsp").forward(request, response);
            response.sendRedirect("Notifications.jsp");
        }




    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
