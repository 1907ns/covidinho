package com.example.covidinho.dao;

import com.example.covidinho.beans.Notification;
import com.example.covidinho.beans.User;
import utility.DBConnector;

import java.sql.*;
import java.util.ArrayList;

public class NotificationDao {


    public ArrayList<Notification> getUserNotifications(int userId) throws SQLException {
        ArrayList <Notification> notifications = new ArrayList<>();
        Connection connection = DBConnector.createConnection();
        String sql = "SELECT * FROM notifications WHERE id_user=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);
        ResultSet result = statement.executeQuery();
        while(result.next()){
            Notification notif = new Notification();
            notif.setId(result.getInt("id"));
            notif.setUserId(userId);
            notif.setRead(result.getInt("is_read"));
            notif.setContent(result.getString("content"));
            notif.setType(result.getInt("type"));
            notifications.add(notif);
        }
        return notifications;
    }

    public String insertFriendRequestNotification(User sender, User receiver) throws SQLException {

        Connection con = null;
        PreparedStatement preparedStatement = null;
        try
        {
            con = DBConnector.createConnection();


            String query = "insert into notifications(id_user,src_user, type,content,is_read) values (?,?,?,?,?);"; //Insert notification details into the table 'Notifications'
            preparedStatement = con.prepareStatement(query); //Making use of prepared statements here to insert bunch of data
            preparedStatement.setInt(1, receiver.getId());
            preparedStatement.setInt(2, sender.getId());
            preparedStatement.setInt(3, 1);
            preparedStatement.setString(4, sender.getUsername() + " veut vous ajouter en ami");
            preparedStatement.setInt(5, 0);
            try{
                int i= preparedStatement.executeUpdate();
            }catch (SQLIntegrityConstraintViolationException e){
                return "FAILURE";
            }

            return "SUCCESS";
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return "Oops.. Something went wrong there..!";  // On failure, send a message from here.

    }

    public void setNotificationRead(int notifId){
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try
        {
            con = DBConnector.createConnection();
            String query = "update notifications set is_read = ? where id = ?";  // Update notification state
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, notifId);

            try{
                int i= preparedStatement.executeUpdate();
                con.close();
            }catch (SQLIntegrityConstraintViolationException e){
                e.printStackTrace();
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }


    }


}

