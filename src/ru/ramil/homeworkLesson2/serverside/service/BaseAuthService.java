package ru.ramil.homeworkLesson2.serverside.service;

import ru.ramil.homeworkLesson2.serverside.interfaces.AuthService;
import java.sql.*;

public class BaseAuthService implements AuthService {

    public BaseAuthService() throws SQLException {
        Connection connection = DBConnection.getConnection();
        connection.setAutoCommit(false);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "login VARCHAR(50) NOT NULL UNIQUE," +
                    "password VARCHAR(50) NOT NULL," +
                    "nick VARCHAR(50) NOT NULL UNIQUE" +
                    ");");
            ResultSet result = statement.executeQuery("SELECT * FROM users;");
            if(!result.next()) {
                statement.executeUpdate("INSERT INTO users (login, password, nick) values ('David', 'qazwsx', 'Давид')");
                statement.executeUpdate("INSERT INTO users (login, password, nick) values ('Viktor', 'qwerty', 'Виктор')");
                statement.executeUpdate("INSERT INTO users (login, password, nick) values ('Vladimir', '123456', 'Владимир')");
            }
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public void stop() {
        System.out.println("Сервис аутентификации остановлен");
    }

    @Override
    public String getNickByLoginAndPassword(String login, String password) throws SQLException {
        String queryNick = "SELECT * FROM users WHERE login=? AND password=?";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(queryNick)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            String nick = null;
            if (result.next()) {
                nick = result.getString("nick");
            }
            return nick;
        }
    }
}
