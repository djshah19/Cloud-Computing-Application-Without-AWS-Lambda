package com.webapi.dao;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BasicDAO {

    public int register(String user, String pass){
        java.sql.Connection connection = null;
        java.sql.PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment2", "root", "9769402842@Dj");
            String query = "INSERT INTO assignment2.users(username,password) VALUES (?,?);";


            stmt = connection.prepareStatement(query);
            stmt.setString(1, user);
            stmt.setString(2, pass);
            if(!checkIfUserExist(user)) {
                stmt.execute();
            }
            else
                return 1;

            //STEP 5: Extract data from result set

            stmt.close();
            connection.close();
            return 2;
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException" + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException" +ex);
        }
        return 0;
    }

    public boolean checkIfUserExist(String user){
        java.sql.Connection connection = null;
        java.sql.PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment2", "root", "9769402842@Dj");
            String query = "SELECT username from assignment2.users where username like ?;";


            stmt = connection.prepareStatement(query);
            stmt.setString(1, user);

            stmt.execute();
            ResultSet result = stmt.getResultSet();
            String username = "";
            while(result.next())
                username = result.getString("username");
            if(!username.isEmpty()){
                stmt.close();
                connection.close();
                return true;
            }
            //STEP 5: Extract data from result set

            stmt.close();
            connection.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException" + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException" +ex);
        }
        return false;
    }

    public boolean verifyUser(String user,String password){
        java.sql.Connection connection = null;
        java.sql.PreparedStatement stmt = null;
        try {

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment2", "root", "9769402842@Dj");
            String query = "SELECT username,password from assignment2.users where username like ?";


            stmt = connection.prepareStatement(query);
            stmt.setString(1, user);
            stmt.execute();
            ResultSet result = stmt.getResultSet();
            String username = "";
            String encrptPassword = "";
            while(result.next()) {
                username = result.getString("username");
                encrptPassword = result.getString("password");
                if(!username.isEmpty() && BCrypt.checkpw(password,encrptPassword)){
                    stmt.close();
                    connection.close();
                    return true;
                }
            }
            //STEP 5: Extract data from result set

            stmt.close();
            connection.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException" + ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("SQLException" +ex);
        }
        return false;
    }
}
