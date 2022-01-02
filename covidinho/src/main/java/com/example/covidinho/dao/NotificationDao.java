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
            notif.setSenderId(result.getInt("src_user"));
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

    public String insertFriendDeletedNotification(User sender, User receiver) throws SQLException {

        Connection con = null;
        PreparedStatement preparedStatement = null;
        try
        {
            con = DBConnector.createConnection();


            String query = "insert into notifications(id_user,src_user, type,content,is_read) values (?,?,?,?,?);"; //Insert notification details into the table 'Notifications'
            preparedStatement = con.prepareStatement(query); //Making use of prepared statements here to insert bunch of data
            preparedStatement.setInt(1, receiver.getId());
            preparedStatement.setInt(2, sender.getId());
            preparedStatement.setInt(3, 0);
            preparedStatement.setString(4, sender.getUsername() + " vous a supprimé de ses amis.");
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

    public String deleteNotificationById(int notifid) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        con = DBConnector.createConnection();
        String query = "delete from notifications where id = ?";  // Update notification state
        preparedStatement = con.prepareStatement(query);
        preparedStatement.setInt(1, notifid);
        try{
            int i= preparedStatement.executeUpdate();
            con.close();
            return "SUCCESS";
        }catch (SQLIntegrityConstraintViolationException e){
            e.printStackTrace();
            return "FAILURE";
        }
    }

    public String insertFriendResponseNotification(User sender, User receiver) throws SQLException {

        Connection con = null;
        PreparedStatement preparedStatement = null;
        try
        {
            con = DBConnector.createConnection();


            String query = "insert into notifications(id_user,src_user, type,content,is_read) values (?,?,?,?,?);"; //Insert notification details into the table 'Notifications'
            preparedStatement = con.prepareStatement(query); //Making use of prepared statements here to insert bunch of data
            preparedStatement.setInt(1, receiver.getId());
            preparedStatement.setInt(2, sender.getId());
            preparedStatement.setInt(3, 0);
            preparedStatement.setString(4, sender.getUsername() + " a accepté votre demande d'ami.");
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


    public Notification getFriendRequest(int senderid, int receiverid) throws SQLException {

        Connection connection = DBConnector.createConnection();
        String sql = "SELECT * FROM notifications WHERE id_user=? and src_user=? and type=?";

        // on vérifie la relation x ----> y
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, senderid);
        statement.setInt(2, receiverid);
        statement.setInt(3, 1);

        // on vérifie la relation y -----> x
        PreparedStatement statement2 = connection.prepareStatement(sql);
        statement2.setInt(1, receiverid);
        statement2.setInt(2, senderid);
        statement2.setInt(3, 1);

        ResultSet result = statement.executeQuery();
        ResultSet result2 = statement2.executeQuery();
        if(result.next()){
            Notification notif = new Notification();
            notif.setId(result.getInt("id"));
            return notif;
        }else if (result2.next()){
            Notification notif = new Notification();
            notif.setId(result2.getInt("id"));
            return notif;
        }
        return null;
    }

    public String friendCovided(User sender, User receiver) throws SQLException {

        Connection con = null;
        PreparedStatement preparedStatement = null;
        try
        {
            con = DBConnector.createConnection();


            String query = "insert into notifications(id_user,src_user, type,content,is_read) values (?,?,?,?,?);"; //Insert notification details into the table 'Notifications'
            preparedStatement = con.prepareStatement(query); //Making use of prepared statements here to insert bunch of data
            preparedStatement.setInt(1, receiver.getId());
            preparedStatement.setInt(2, sender.getId());
            preparedStatement.setInt(3, 0);
            preparedStatement.setString(4, sender.getUsername() + " est covided");
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

    public String someoneMetWasCovided(User sender, User receiver) throws SQLException {

        Connection con = null;
        PreparedStatement preparedStatement = null;
        try
        {
            con = DBConnector.createConnection();


            String query = "insert into notifications(id_user,src_user, type,content,is_read) values (?,?,?,?,?);"; //Insert notification details into the table 'Notifications'
            preparedStatement = con.prepareStatement(query); //Making use of prepared statements here to insert bunch of data
            preparedStatement.setInt(1, receiver.getId());
            preparedStatement.setInt(2, sender.getId());
            preparedStatement.setInt(3, 0);
            preparedStatement.setString(4, "Vous avez rencontré un covideux");
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


    public String friendVaccined(User sender, User receiver) throws SQLException {

        Connection con = null;
        PreparedStatement preparedStatement = null;
        try
        {
            con = DBConnector.createConnection();


            String query = "insert into notifications(id_user,src_user, type,content,is_read) values (?,?,?,?,?);"; //Insert notification details into the table 'Notifications'
            preparedStatement = con.prepareStatement(query); //Making use of prepared statements here to insert bunch of data
            preparedStatement.setInt(1, receiver.getId());
            preparedStatement.setInt(2, sender.getId());
            preparedStatement.setInt(3, 0);
            preparedStatement.setString(4, sender.getUsername() + " est complètement vacciné.");
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

}

