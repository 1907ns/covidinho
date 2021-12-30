package com.example.covidinho.servlets.admin;

import com.example.covidinho.beans.Notification;
import com.example.covidinho.beans.User;
import com.example.covidinho.dao.NotificationDao;
import com.example.covidinho.dao.UserDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "DeleteUserServlet", value = "/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.valueOf(request.getParameter("userid"));

        UserDao userDao = new UserDao();
        String requestSent = null;
        try {
            requestSent = userDao.deleteUserById(userId);
            User user  = (User) request.getSession().getAttribute("user");
            ArrayList<User> listeUser = userDao.getAllUsers();
            request.getSession().setAttribute("allusers", listeUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(requestSent.equals("SUCCESS") && !response.isCommitted())   //On success, you can display a message to user on Home page
        {
            request.setAttribute("succMessage", "Utilisateur supprimé avec succès");
            request.getRequestDispatcher("/admin/AllUsersServlet").forward(request, response);

        }
        else   //On Failure, display a meaningful message to the User.
        {
            request.setAttribute("errMessage", "Erreur lors de la suppression de l'utilisateur");
            request.getRequestDispatcher("/admin/AllUsersServlet").forward(request, response);

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
