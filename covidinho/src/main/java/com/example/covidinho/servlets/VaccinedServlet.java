package com.example.covidinho.servlets;

import com.example.covidinho.beans.Activity;
import com.example.covidinho.beans.Friendship;
import com.example.covidinho.beans.User;
import com.example.covidinho.dao.ActivityDao;
import com.example.covidinho.dao.FriendshipDao;
import com.example.covidinho.dao.NotificationDao;
import com.example.covidinho.dao.UserDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "VaccinedServlet", value = "/VaccinedServlet")
public class VaccinedServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FriendshipDao friendshipDao = new FriendshipDao();
        NotificationDao notificationDao = new NotificationDao();
        UserDao userDao = new UserDao();

        User user =(User) request.getSession().getAttribute("user");
        try {
            ArrayList<Friendship> friendships = friendshipDao.getUserFriendships(user.getId());

            for (Friendship friendship:friendships){
                notificationDao.friendVaccined(user, userDao.getUserById(friendship.getIdUser2()));
            }

            user.setIsVaccinated(1);
            userDao.modifyUser(user);
            request.getSession().setAttribute("user", user);

            request.getRequestDispatcher("/Home.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
