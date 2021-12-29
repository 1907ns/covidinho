package com.example.covidinho.servlets;

import com.example.covidinho.beans.User;
import com.example.covidinho.dao.UserDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.ParseException;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        // get parameters from form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String name = request.getParameter("name");
        String birthdate = request.getParameter("birthdate");

        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setName(name);
        user.setBirthdate(birthdate);
        UserDao userDao = new UserDao();

        String userRegistered = null;
        try {
            userRegistered = userDao.registerUser(user);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(userRegistered.equals("SUCCESS"))   //On success, you can display a message to user on Home page
        {
            request.setAttribute("succMessage", "Inscription réussie!");
            request.getRequestDispatcher("/Register.jsp").forward(request, response);
        }
        else   //On Failure, display a meaningful message to the User.
        {
            request.setAttribute("errMessage", "Ce nom d'utilisateur est déjà pris");
            request.getRequestDispatcher("/Register.jsp").forward(request, response);
        }
    }
}
