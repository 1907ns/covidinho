package com.example.covidinho.dao;

import com.example.covidinho.beans.User;
import utility.DBConnector;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao {
    public String registerUser(User registerBean) throws ParseException {
        String username = registerBean.getUsername();
        String password = getHashSHA1(registerBean.getPassword());
        String firstname = registerBean.getFirstname();
        String name = registerBean.getName();
        String birthdate = registerBean.getBirthdate();

        //on formate la date
        DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date date = formatter.parse(birthdate);
        java.sql.Date formattedBDate = new java.sql.Date(date.getTime());

        Connection con = null;
        PreparedStatement preparedStatement = null;
        try
        {
            con = DBConnector.createConnection();
            System.out.println(con);
            String query = "insert into users(login,password,firstname,name, birthdate,admin,is_positive) values (?,?,?,?,?,?,?)"; //Insert user details into the table 'USERS'
            preparedStatement = con.prepareStatement(query); //Making use of prepared statements here to insert bunch of data
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstname);
            preparedStatement.setString(4, name);
            preparedStatement.setString(5, birthdate);
            preparedStatement.setInt(6, 0);
            preparedStatement.setInt(7, 0);

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

    public User checkLogin(String login, String password) throws SQLException,
            ClassNotFoundException {
        Connection connection = DBConnector.createConnection();
        String sql = "SELECT * FROM users WHERE login = ? and password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, login);
        statement.setString(2, getHashSHA1(password));

        ResultSet result = statement.executeQuery();
        User user = null;

        if (result.next()) {
            user = new User();
            user.setId(result.getInt("id"));
            user.setName(result.getString("name"));
            user.setFirstname(result.getString("firstname"));
            user.setUsername(login);
            user.setPassword(password);
            user.setAdmin(result.getInt("admin"));
            user.setIsPositive(result.getInt("is_positive"));
        }

        connection.close();

        return user;
    }

    public User modifyUser(User modifierBean){
        int id = modifierBean.getId();
        String username = modifierBean.getUsername();
        String password = getHashSHA1(modifierBean.getPassword());
        String firstname = modifierBean.getFirstname();
        String name = modifierBean.getName();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try
        {
            con = DBConnector.createConnection();
            String query = "update users set login = ?, password = ?, firstname =? , name = ? where id = ?"; //Insert user details into the table 'USERS'
            preparedStatement = con.prepareStatement(query); //Making use of prepared statements here to insert bunch of data
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstname);
            preparedStatement.setString(4, name);
            preparedStatement.setInt(5, id);

            User user = null;
            try{
                int i= preparedStatement.executeUpdate();
                String sql = "SELECT * FROM users WHERE id = ?";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setInt(1, id);
                ResultSet result = statement.executeQuery();
                if (result.next()) {
                    System.out.println(password);
                    user = new User();
                    user.setId(result.getInt("id"));
                    user.setName(result.getString("name"));
                    user.setFirstname(result.getString("firstname"));
                    user.setUsername(username);
                    user.setPassword(modifierBean.getPassword());
                    user.setAdmin(result.getInt("admin"));
                    user.setIsPositive(result.getInt("is_positive"));
                }
                con.close();
            }catch (SQLIntegrityConstraintViolationException e){
                return null;
            }

            return user;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserByUsername (String username) throws SQLException {
        Connection connection = DBConnector.createConnection();
        String sql = "SELECT * FROM users WHERE login = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);

        ResultSet result = statement.executeQuery();
        User user = null;

        if (result.next()) {
            user = new User();
            user.setId(result.getInt("id"));
            user.setName(result.getString("name"));
            user.setFirstname(result.getString("firstname"));
            user.setUsername(username);
            user.setBirthdate(String.valueOf(result.getDate("birthdate")));
        }

        connection.close();

        return user;

    }
    public String getHashSHA1(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i =0; i < byteData.length; i++){
                sb.append(Integer.toString((byteData[i]+0xff)+0x100, 16).substring(1));
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException e){
            Logger.getLogger("SHA-1").log(Level.SEVERE, null, e);
            return null;
        }
    }
}

