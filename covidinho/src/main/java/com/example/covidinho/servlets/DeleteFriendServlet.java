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

@WebServlet(name = "DeleteFriendServlet", value = "/DeleteFriendServlet")
public class DeleteFriendServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idUser1 = Integer.valueOf(request.getParameter("iduser1"));
        int idUser2= Integer.valueOf(request.getParameter("iduser2"));

        FriendshipDao friendshipDao = new FriendshipDao();
        NotificationDao notificationDao = new NotificationDao();
        UserDao userDao = new UserDao();
        String deletedFriend = null;
        String notif = null;
        try {
            deletedFriend = friendshipDao.deleteOneFriendship(idUser1, idUser2);
            User sender = userDao.getUserById(idUser1);
            User receiver = userDao.getUserById(idUser2);
            notif = notificationDao.insertFriendDeletedNotification(sender, receiver);
            ArrayList<Friendship> listeAmis = friendshipDao.getUserFriendships(idUser1);
            request.getSession().setAttribute("friends", listeAmis);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(deletedFriend.equals("SUCCESS") && notif.equals("SUCCESS"))   //On success, you can display a message to user
        {
            request.setAttribute("succMessage", "Ami supprimé avec succès.");
            request.getRequestDispatcher("/Friendships.jsp").forward(request, response);
            response.sendRedirect("Friendships.jsp");
        }
        else   //On Failure, display a meaningful message to the User.
        {
            request.setAttribute("errMessage", "Erreur lors de la suppression de l'ami.");
            request.getRequestDispatcher("/Friendships.jsp").forward(request, response);
            response.sendRedirect("Friendships.jsp");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
