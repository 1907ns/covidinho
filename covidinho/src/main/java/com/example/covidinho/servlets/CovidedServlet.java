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

@WebServlet(name = "CovidedServlet", value = "/CovidedServlet")
public class CovidedServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActivityDao activityDao = new ActivityDao();
        FriendshipDao friendshipDao = new FriendshipDao();
        NotificationDao notificationDao = new NotificationDao();
        UserDao userDao = new UserDao();

        User user =(User) request.getSession().getAttribute("user");
        try {
            List<Activity> activities = activityDao.getUsersMet(user.getId());
            ArrayList<Friendship> friendships = friendshipDao.getUserFriendships(user.getId());

            for (Activity activity:activities) {
                notificationDao.someoneMetWasCovided(user, userDao.getUserById(activity.getIdUser()));
            }

            for (Friendship friendship:friendships){
                notificationDao.friendCovided(user, userDao.getUserById(friendship.getIdUser2()));
            }

            user.setIsPositive(1);

            SimpleDateFormat parser = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
            Date date = new Date();
            Timestamp now = new Timestamp(date.getTime());
            user.setPositiveDate(parser.format(now));
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
