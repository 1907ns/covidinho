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

@WebServlet(name = "AddNewUserServlet", value = "/AddNewUserServlet")
public class AddNewUserServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get parameters from form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String name = request.getParameter("name");
        String birthdate = request.getParameter("birthdate");
        String admin = request.getParameter("admin");
        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setName(name);
        user.setBirthdate(birthdate);
        user.setAdmin(Integer.valueOf(admin));
        UserDao userDao = new UserDao();

        String userRegistered = null;
        try {
            userRegistered = userDao.registerUser(user);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(userRegistered.equals("SUCCESS"))   //On success, you can display a message to user on Home page
        {
            request.setAttribute("succMessage", "Utilisateur ajouté avec succès!");
            ArrayList<User> listeUser = null;
            try {
                listeUser = userDao.getAllUsers();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            request.getSession().setAttribute("allusers", listeUser);
            request.getRequestDispatcher("/Users.jsp").forward(request, response);
        }
        else   //On Failure, display a meaningful message to the User.
        {
            request.setAttribute("errMessage", "Cet utilisateur existe déjà.");
            request.getRequestDispatcher("/Users.jsp").forward(request, response);
        }
    }
}
