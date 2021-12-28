package com.example.covidinho.servlets;

import com.example.covidinho.beans.User;
import com.example.covidinho.dao.UserDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ProfileModificationServlet", value = "/ProfileModificationServlet")
public class ProfileModificationServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get parameters from form
        int id = Integer.valueOf(request.getParameter("userid"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String name = request.getParameter("name");

        User user = new User();

        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setName(name);

        UserDao userDao = new UserDao();

        User userModified = userDao.modifyUser(user);
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

