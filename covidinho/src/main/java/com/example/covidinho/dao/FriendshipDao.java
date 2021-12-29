package com.example.covidinho.dao;

import com.example.covidinho.beans.Friendship;
import com.example.covidinho.beans.Notification;
import com.example.covidinho.beans.User;
import utility.DBConnector;

import java.sql.*;
import java.util.ArrayList;

public class FriendshipDao {


    public String insertNewFriendship(int idUser1, int idUser2) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        con = DBConnector.createConnection();


        // insert new friendship relation x --> y
        String query1 = "insert into friendships(id_user1,id_user2) values (?,?)";
        preparedStatement1 = con.prepareStatement(query1);
        preparedStatement1.setInt(1, idUser1);
        preparedStatement1.setInt(2, idUser2);

        // insert new friendship relation y --> x
        String query2 = "insert into friendships(id_user1,id_user2) values (?,?)";
        preparedStatement2 = con.prepareStatement(query2);
        preparedStatement2.setInt(1, idUser2);
        preparedStatement2.setInt(2, idUser1);
        try{
            int i= preparedStatement1.executeUpdate();
            int j = preparedStatement2.executeUpdate();
            con.close();
            return "SUCCESS";
        }catch (SQLIntegrityConstraintViolationException e){
            e.printStackTrace();
            return "FAILURE";
        }

    }

    public String deleteOneFriendship(int idUser1, int idUser2) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        con = DBConnector.createConnection();


        // insert new friendship relation x --> y
        String query1 = "delete from friendships where id_user1=? and id_user2=?";
        preparedStatement1 = con.prepareStatement(query1);
        preparedStatement1.setInt(1, idUser1);
        preparedStatement1.setInt(2, idUser2);

        // insert new friendship relation y --> x
        String query2 = "delete from friendships where id_user1=? and id_user2=?";
        preparedStatement2 = con.prepareStatement(query2);
        preparedStatement2.setInt(1, idUser2);
        preparedStatement2.setInt(2, idUser1);
        try{
            int i= preparedStatement1.executeUpdate();
            int j = preparedStatement2.executeUpdate();
            con.close();
            return "SUCCESS";
        }catch (SQLIntegrityConstraintViolationException e){
            e.printStackTrace();
            return "FAILURE";
        }
    }

    public ArrayList<Friendship> getUserFriendships(int userId) throws SQLException {
        ArrayList <Friendship> friendships = new ArrayList<>();
        Connection connection = DBConnector.createConnection();
        String sql = "SELECT * FROM friendships WHERE id_user1=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);
        ResultSet result = statement.executeQuery();

        // On récupère l'user ami pour récupérer le nom de l'ami
        UserDao userDao = new UserDao();

        while(result.next()){
            Friendship friendship = new Friendship();
            friendship.setIdUser1(userId);
            friendship.setIdUser2(result.getInt("id_user2"));
            User user = userDao.getUserById(friendship.getIdUser2());
            friendship.setFriendUsername(user.getUsername());
            friendships.add(friendship);
        }
        return friendships;
    }
}
