package com.andy.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

interface Iplayer {
    public String sendMessage(String message);
}

public class Player implements Iplayer {
    //the Player role
    private String role = "";
    //the name of Player
    private String name = "";
    //listening port
    private int port = 8050;
    //if the server stop
    private boolean serverStop = false;

    public Player(String name) {
        this.name = name;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void startListening() {
        init();
    }

    ;

    /**
     * function send message to listener
     *
     * @param message message send from sender
     * @return receiver' message
     */
    @Override
    public String sendMessage(String message) {
        Socket socket = null;
        String mess = "";
        try {
            socket = new Socket("localhost", port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter
                    out = new PrintWriter(socket.getOutputStream());
            out.println(message);
            out.flush();

            mess = in.readLine();

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return mess;
    }

    /**
     * start listening
     */
    public void init() {
        if (!this.role.equals("listener")) {
            return;
        }
        Socket socket = null;
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("server started:" + port);
            while (!serverStop) {

                socket = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                while (true) {
                    String msg = in.readLine();

                    if (msg == null || msg.equals("")) {
                        break;
                    }
                    System.out.println("[receiver]message from sender:" + msg);
                    out.println("[receiver]send to sender:" + msg);
                    System.out.println("[receiver]send to sender:" + msg);
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        if (args != null && args.length > 0 && args[0].equals("listener")) {
            Player listener = new Player("listener");
            listener.setRole("listener");
            listener.setPort(8089);
            listener.startListening();
        } else {
            System.out.println(args[0]);
            Player initiator = new Player("initiator");
            initiator.setPort(8089);
            for (int i = 0; i < 10; i++) {
                String revMessage = initiator.sendMessage("Hello Listener" + i);
                System.out.println("result:" + revMessage);
            }
        }

    }
}
