package com.example.covidinho.dao;

import com.example.covidinho.beans.Activity;
import com.example.covidinho.beans.Notification;
import com.example.covidinho.beans.User;
import utility.DBConnector;

import java.net.http.HttpClient;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ActivityDao {


    public List<Activity> getUsersMet(int id) throws SQLException, ParseException {
        ArrayList<Activity> activities = new ArrayList<Activity>();
        Connection connection = DBConnector.createConnection();
        String sql ="SELECT tab1.* " +
                    "FROM activities AS tab1 JOIN activities AS tab2 ON tab1.id_place = tab2.id_place AND tab1.begining >= tab2.begining AND tab1.begining <= tab2.end AND tab2.id_user = "+id+" AND tab1.id_user!="+id +
                    " WHERE tab1.begining>=TIMESTAMPADD(DAY,-5,NOW())";
        System.out.println(sql);
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();
        while(result.next()){
            Timestamp begining = result.getTimestamp("begining");
            Timestamp end = result.getTimestamp("end");
            String place = result.getString("id_place");
            int idUser = result.getInt("id_user");
            Activity activity = new Activity(begining, end, place, idUser);

            activities.add(activity);
        }
        return activities;
    }

    public String insertActivity(Activity activity, User user) throws SQLException {

        Connection con = null;
        PreparedStatement preparedStatement = null;
        try
        {
            con = DBConnector.createConnection();

            String query = "insert into activities(id_place,id_user, begining,end) values (?,?,?,?);"; //Insert notification details into the table 'Notifications'
            preparedStatement = con.prepareStatement(query); //Making use of prepared statements here to insert bunch of data
            //id place
            preparedStatement.setString(1, activity.getPlace());
            //id user
            preparedStatement.setInt(2, user.getId());
            //datetime begining
            preparedStatement.setTimestamp(3, activity.getBegining());
            //datetime end
            preparedStatement.setTimestamp(4, activity.getEnd());
            try{
                int i= preparedStatement.executeUpdate();
            }catch (SQLIntegrityConstraintViolationException e){
                System.out.println("FAILURE");
                return "FAILURE";
            }
            System.out.println("SUCCESS");
            return "SUCCESS";
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return "Oops.. Something went wrong there..!";  // On failure, send a message from here.

    }


    public ArrayList<Activity> getAllActivities() throws SQLException {
        Connection connection = DBConnector.createConnection();
        String sql = "SELECT * FROM activities";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();
        ArrayList <Activity> activities = new ArrayList<>();
        while (result.next()) {
            Timestamp begining = result.getTimestamp("begining");
            Timestamp end = result.getTimestamp("end");
            String place = result.getString("id_place");
            int idUser = result.getInt("id_user");
            Activity activity = new Activity(begining, end, place, idUser);
            activity.setId(result.getInt("id"));
            activities.add(activity);
        }
        connection.close();
        return activities;
    }



    public String deleteActivityById(int actId) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        con = DBConnector.createConnection();
        String query = "delete from activities where id = ?";  // Update notification state
        preparedStatement = con.prepareStatement(query);
        preparedStatement.setInt(1, actId);
        try{
            int i= preparedStatement.executeUpdate();
            con.close();
            return "SUCCESS";
        }catch (SQLIntegrityConstraintViolationException e){
            e.printStackTrace();
            return "FAILURE";
        }
    }
}
