package ru.ramil.homeworkLesson2.serverside.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;

public class ClientHandler {
    private final MyServer myServer;
    private final Socket socket;
    private final DataInputStream dis;
    private final DataOutputStream dos;

    private String name;

    private static final int timeForAuthenticationInSecond = 120;
    private static final int timeForReadMessageFromClientInSeconds = 180;
    private volatile boolean isAuthorized = false;
    private volatile long timeLastReadedMessage;

    // команды от клиента
    private static final String AUTH = "/auth";              // аутентификация /auth login password
    private static final String END = "/end";                // отключить соединение
    private static final String SEND_PRIVATE_MESSAGE = "/w"; // отправить личное сообщение /w nick message
    private static final String LIST = "/list";              // получить список онлайн пользователей

    // результаты выполнения команд от клиента
    private static final String AUTH_OK = "/authok ";             // успешная авторизация

    public ClientHandler(MyServer myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            startAuthenticationTimer();
            startReadMessagesFromClient();
        } catch(IOException e) {
            closeConnection();
            throw new RuntimeException("Проблемы при создании ClientHandler");
        }
    }
    
    private void startAuthenticationTimer() {
        new Thread(() -> {
                try {
                    Thread.sleep(timeForAuthenticationInSecond * 1000);
                    if(!isAuthorized) {
                        closeConnection();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
    }
    
    private void startReadMessageFromClientTimer(long timeInMillis) {
        new Thread(() -> {
            try {
                while(true) {
                    Thread.sleep(1);
                    if(System.currentTimeMillis() - timeLastReadedMessage >= timeInMillis) {
                        closeConnection();
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void startReadMessagesFromClient() {
        new Thread(() -> {
            try {
                authentication();
                readMessage();
            } catch (IOException ignored) {

            } catch (SQLException ex) {
                System.out.println("Соединение с базой данных разорвано");
            } finally {
                closeConnection();
            }
        }).start();
    }

    public void authentication() throws IOException {
        while(true) {
            String str = dis.readUTF();
            if(str.startsWith(AUTH)) {
                String[] arr = str.split("\\s");
                String nick = myServer
                        .getAuthService()
                        .getNickByLoginAndPassword(arr[1], arr[2]);
                if(nick != null) {
                    if(!myServer.isNickBusy(nick)) {
                        isAuthorized = true;
                        sendMessage(AUTH_OK + nick);
                        name = nick;
                        myServer.broadcastMessage(name + " вошел в чат");
                        myServer.subscribe(this);
                        return;
                    } else {
                        sendMessage("Ник занят");
                    }
                } else {
                    sendMessage("Неправильный логин или пароль");
                }
            }
        }
    }

    public void readMessage() throws IOException, SQLException {
        long timeInMillis = timeForReadMessageFromClientInSeconds * 1000;
        timeLastReadedMessage = System.currentTimeMillis();
        startReadMessageFromClientTimer(timeInMillis);
        while(true) {
            String messageFromClient = dis.readUTF();
            timeLastReadedMessage = System.currentTimeMillis();
            System.out.println(name + " send message " + messageFromClient);
            if(isServiceMessage(messageFromClient)) {
                boolean isEndSession = handleServiceMessage(messageFromClient);
                if(isEndSession) {
                    return;
                }
                continue;
            }
            myServer.broadcastMessage("[" + name + "]: " + messageFromClient);
        }
    }

    private boolean isServiceMessage(String message) {
        return message.trim().startsWith("/");
    }

    private boolean handleServiceMessage(String message) throws SQLException {
        String trimedMessage = message.trim();
        if(trimedMessage.startsWith(END)) {
            return true;
        }
        if(trimedMessage.startsWith(SEND_PRIVATE_MESSAGE)) {
            String[] arr = message.split("\\s", 3);
            if(!this.name.equals(arr[1])) {
                myServer.sendPrivateMessage(this, arr[1], arr[2]);
            }
        }
        if(trimedMessage.startsWith(LIST)) {
            myServer.getOnlineUsersList(this);
        }
        return false;
    }

    public void sendMessage(String message) {
        try {
            dos.writeUTF(message);
        } catch (IOException ignored) {
        }
    }

    private void closeConnection() {
        myServer.unsubscribe(this);
        myServer.broadcastMessage(name + " покинул чат");
        try {
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }
}
