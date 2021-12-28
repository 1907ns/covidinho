package com.example.covidinho.servlets;

import com.example.covidinho.beans.User;
import com.example.covidinho.dao.UserDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "SearchUserServlet", value = "/SearchUserServlet")
public class SearchUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchedUsername = request.getParameter("searcheduser");
        UserDao userDao = new UserDao();

        try {
            User searchedUser = userDao.getUserByUsername(searchedUsername);
            String destPage = "SearchProfile.jsp";
            if (searchedUser != null) {
                HttpSession session = request.getSession();
                session.setAttribute("searcheduser", searchedUser);
            } else {
                String message = "Il n'existe pas un utilisateur avec ce pseudo";
                request.setAttribute("errMessage", message);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
            dispatcher.forward(request, response);

        } catch (SQLException ex) {
            throw new ServletException(ex);
        }

    }


}