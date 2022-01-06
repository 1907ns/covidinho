package com.example.covidinho.servlets;

import com.example.covidinho.beans.User;
import com.example.covidinho.dao.UserDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.ParseException;

@WebServlet(name = "ProfileModificationServlet", value = "/ProfileModificationServlet")
public class ProfileModificationServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get parameters from form
        User user = (User) request.getSession().getAttribute("user");
        int id = Integer.valueOf(request.getParameter("userid"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String name = request.getParameter("name");
        String birthdate = request.getParameter("birthdate");

        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setName(name);
        user.setBirthdate(birthdate);

        UserDao userDao = new UserDao();

        User userModified = null;
        try {
            userModified = userDao.modifyUser(user);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(userModified != null)   //On success, you can display a message to user on Home page
        {
            request.setAttribute("succMessage", "Votre profil a été modifié avec succès");
            HttpSession session = request.getSession();
            session.setAttribute("user", userModified);
            request.getRequestDispatcher("/MyProfile.jsp").forward(request, response);
        }
        else   //On Failure, display a meaningful message to the User.
        {
            request.setAttribute("errMessage", "Ce nom d'utilisateur est déjà pris, veuillez en choisir un autre.");
            request.getRequestDispatcher("/MyProfile.jsp").forward(request, response);
        }
    }
}

