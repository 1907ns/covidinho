package com.example.covidinho.dao;

import com.example.covidinho.beans.Activity;
import com.example.covidinho.beans.Place;
import com.example.covidinho.beans.User;
import utility.DBConnector;

import java.sql.*;

public class PlaceDao {

    public String insertPlace(Place place) throws SQLException {

        Connection con = null;
        PreparedStatement preparedStatement = null;
        try
        {
            con = DBConnector.createConnection();
            Place place1 = getPlaceById(place.getId());
            if(place1==null){
                String query = "insert into places(id,address) values (?,?);"; //Insert notification details into the table 'Notifications'
                preparedStatement = con.prepareStatement(query); //Making use of prepared statements here to insert bunch of data
                preparedStatement.setString(1, place.getId());
                preparedStatement.setString(2, place.getAddress());

                try{
                    int i= preparedStatement.executeUpdate();
                }catch (SQLIntegrityConstraintViolationException e){
                    e.printStackTrace();
                    return "FAILURE";
                }
                return "SUCCESS";
            }
            return "SUCCESS";
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return "Oops.. Something went wrong there..!";  // On failure, send a message from here.

    }


    public Place getPlaceById (String id) throws SQLException {
        Connection connection = DBConnector.createConnection();
        String sql = "SELECT * FROM places WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);

        ResultSet result = statement.executeQuery();
        Place place = null;

        if (result.next()) {
            String adresse = result.getString("address");
            place = new Place(id, adresse);
        }
        connection.close();
        return place;

    }
}
