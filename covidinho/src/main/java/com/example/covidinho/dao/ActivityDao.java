package com.example.covidinho.dao;

import com.example.covidinho.beans.*;
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
        String sql = "SELECT tab1.* FROM activities AS tab1 JOIN activities AS tab2 ON tab1.id_place = tab2.id_place AND (tab1.begining >= tab2.begining OR tab1.begining <= tab2.end) AND tab2.id_user = ? AND tab1.id_user!= ? WHERE tab1.begining>=TIMESTAMPADD(DAY,-5,NOW())";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.setInt(2, id);
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

    public String updateActivity(Activity activity, Activity newActivity) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        con = DBConnector.createConnection();
        String query = "update activities set id_place = ?, begining = ?, end =? where id = ?";
        preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, newActivity.getPlace());
        preparedStatement.setTimestamp(2, newActivity.getBegining());
        preparedStatement.setTimestamp(3, newActivity.getEnd());
        preparedStatement.setInt(4, activity.getId());
        try{
            int i= preparedStatement.executeUpdate();
            con.close();
            return "SUCCESS";
        }catch (SQLIntegrityConstraintViolationException e){
            e.printStackTrace();
            return "FAILURE";
        }
    }

    public ArrayList<Activity> getUserActivities(int userId) throws SQLException {
        ArrayList <Activity> activities = new ArrayList<>();
        Connection connection = DBConnector.createConnection();
        String sql = "SELECT * FROM activities WHERE id_user=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);
        ResultSet result = statement.executeQuery();

        while(result.next()){
            Timestamp begining = result.getTimestamp("begining");
            Timestamp end = result.getTimestamp("end");
            String place = result.getString("id_place");
            int idUser = result.getInt("id_user");
            Activity activity = new Activity(begining, end, place, idUser);
            activity.setId(result.getInt("id"));
            activities.add(activity);
        }
        return activities;
    }


    public Activity getActivityById (int id) throws SQLException {
        Connection connection = DBConnector.createConnection();
        String sql = "SELECT * FROM activities WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet result = statement.executeQuery();
        Activity activity = null;

        if (result.next()) {
            Timestamp begining = result.getTimestamp("begining");
            Timestamp end = result.getTimestamp("end");
            String place = result.getString("id_place");
            int idUser = result.getInt("id_user");
            activity = new Activity(begining, end, place, idUser);
            activity.setId(result.getInt("id"));;
        }
        connection.close();
        return activity;

    }
}
