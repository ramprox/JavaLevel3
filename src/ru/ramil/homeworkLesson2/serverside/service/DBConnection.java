/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ramil.homeworkLesson2.serverside.service;

import java.sql.*;

public class DBConnection {
    private static final String DB = "jdbc:mysql://localhost:3306/chat";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static Connection dbConnection;
    
    public static Connection getConnection() throws SQLException {
        /*try {
            Class.forName("com.mysql.cj.jdbc.Driver");   // в версии mysql connector 8.0.23 писать это необязательно
        } catch (ClassNotFoundException e) {             // т.к. этот класс загружается автоматически
            e.printStackTrace();
        }*/
        if(dbConnection == null) {
            dbConnection = DriverManager.getConnection(DB, USER, PASSWORD);
        }
        return dbConnection;
    }
    
    public static void closeConnection() {
        try {
            if(dbConnection != null) {
                dbConnection.close();
            }
        } catch(SQLException ex) {
            System.out.println("Ошибка при закрытии соединения с базой данных.");
            ex.printStackTrace();
        } finally {
            dbConnection = null;
        }
    }
}
