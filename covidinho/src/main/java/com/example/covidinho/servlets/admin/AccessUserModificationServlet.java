package com.example.covidinho.servlets.admin;

import com.example.covidinho.beans.User;
import com.example.covidinho.dao.UserDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AccessUserModificationServlet", value = "/AccessUserModificationServlet")
public class AccessUserModificationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.valueOf(request.getParameter("userid"));
        UserDao userDao = new UserDao();
        try {
            User userModif = userDao.getUserById(userId);
            request.getSession().setAttribute("userModif", userModif);
            request.getRequestDispatcher("../UserModification.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
