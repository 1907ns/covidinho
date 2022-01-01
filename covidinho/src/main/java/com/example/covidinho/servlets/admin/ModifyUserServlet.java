package com.example.covidinho.servlets.admin;

import com.example.covidinho.beans.User;
import com.example.covidinho.dao.UserDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

@WebServlet(name = "ModifyUserServlet", value = "/ModifyUserServlet")
public class ModifyUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.valueOf(request.getParameter("userid"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String name = request.getParameter("name");
        String birthdate = request.getParameter("birthdate");
        int admin = Integer.valueOf(request.getParameter("admin"));
        UserDao userDao = new UserDao();
        String requestSent = null;
        try {
            requestSent = userDao.adminModifyUser(id, username, password, name, firstname, birthdate, admin);
            ArrayList<User> listeUser = userDao.getAllUsers();
            request.getSession().setAttribute("allusers", listeUser);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(requestSent.equals("SUCCESS") && !response.isCommitted())   //On success, you can display a message to user on Home page
        {
            request.setAttribute("succMessage", "Utilisateur modifié avec succès");
            request.getRequestDispatcher("/admin/AllUsersServlet").forward(request, response);

        }
        else   //On Failure, display a meaningful message to the User.
        {
            request.setAttribute("errMessage", "Erreur lors de la modification de l'utilisateur");
            request.getRequestDispatcher("/admin/AllUsersServlet").forward(request, response);

        }
    }
}
