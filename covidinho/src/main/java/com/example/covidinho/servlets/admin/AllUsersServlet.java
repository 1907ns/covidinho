package com.example.covidinho.servlets.admin;

import com.example.covidinho.beans.Notification;
import com.example.covidinho.beans.User;
import com.example.covidinho.dao.NotificationDao;
import com.example.covidinho.dao.UserDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "AllUsersServlet", value = "/AllUsersServlet")
public class AllUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        try {
            response.setBufferSize(30000);
            response.setHeader("Content-Type", "text/html");
            ArrayList<User> listeUsers = userDao.getAllUsers();
            request.getSession().setAttribute("allusers", listeUsers);
            request.getRequestDispatcher("/Users.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
