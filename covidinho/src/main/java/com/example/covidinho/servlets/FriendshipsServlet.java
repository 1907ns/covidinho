package com.example.covidinho.servlets;

import com.example.covidinho.beans.Friendship;
import com.example.covidinho.beans.Notification;
import com.example.covidinho.beans.User;
import com.example.covidinho.dao.FriendshipDao;
import com.example.covidinho.dao.NotificationDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "FriendshipsServlet", value = "/FriendshipsServlet")
public class FriendshipsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FriendshipDao friendshipDao = new FriendshipDao();
        User user  = (User) request.getSession().getAttribute("user");
        try {
            ArrayList<Friendship> listeAmis = friendshipDao.getUserFriendships(user.getId());
            request.getSession().setAttribute("friends", listeAmis);
            request.getRequestDispatcher("/Friendships.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
