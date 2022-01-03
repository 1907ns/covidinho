package com.example.covidinho.servlets.admin;

import com.example.covidinho.beans.Activity;
import com.example.covidinho.beans.User;
import com.example.covidinho.dao.ActivityDao;
import com.example.covidinho.dao.UserDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AccessActivityModificationServlet", value = "/AccessActivityModificationServlet")
public class AccessActivityModificationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int actId = Integer.valueOf(request.getParameter("actid"));
        request.setCharacterEncoding("UTF-8");
        ActivityDao activityDao = new ActivityDao();
        try {
            Activity actModif = activityDao.getActivityById(actId);
            request.getSession().setAttribute("actModif", actModif);
            RequestDispatcher dispatcher = request.getRequestDispatcher("../ActivityModification.jsp");
            dispatcher.forward(request, response);
            response.sendRedirect("../ActivityModification.jsp");
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
