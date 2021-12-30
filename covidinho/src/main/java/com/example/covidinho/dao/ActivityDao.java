package com.example.covidinho.dao;

import com.example.covidinho.beans.Activity;
import com.example.covidinho.beans.User;
import utility.DBConnector;

import java.net.http.HttpClient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class ActivityDao {

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
}
