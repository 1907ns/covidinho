package com.example.covidinho.servlets;

import com.example.covidinho.beans.User;
import com.example.covidinho.dao.UserDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "DeleteMyProfileServlet", value = "/DeleteMyProfileServlet")
public class DeleteMyProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        int userId = user.getId();

        UserDao userDao = new UserDao();
        String requestSent = null;
        try {
            requestSent = userDao.deleteUserById(userId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(requestSent.equals("SUCCESS"))   //On success, you can display a message to user on Home page
        {
            request.setAttribute("succMessage", "Votre profil a été supprimé avec succès");
            request.getRequestDispatcher("/LogoutServlet").forward(request, response);

        }
        else   //On Failure, display a meaningful message to the User.
        {
            request.setAttribute("errMessage", "Erreur lors de la suppression du profil");
            request.getRequestDispatcher("/MyProfile.jsp").forward(request, response);

        }
    }
}
