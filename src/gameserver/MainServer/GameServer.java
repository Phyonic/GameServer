/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver.MainServer;

import gameserver.TicTacToe.Tictactoe;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class GameServer {

    private int nextFreePort = 5000;
    private boolean running = true;
    private HashMap<Integer, Thread> gameThreads = new HashMap<>();
    private HashMap<Integer, Gamebase> games = new HashMap<>();

    public void clear() throws IOException {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public boolean isRunning() {
        return running;
    }

    public void createGame() throws IOException {
        byte b = 0;
        int i = 0;

        clear();
        System.out.println("Which would you like to start?");
        System.out.println("1) TicTacToe");
        System.out.println("2) 4 Wins");
        System.out.println("3) Black Jack");
        System.out.println("4) Texas Hold'em");
        System.out.println("5) sixty-six");
        System.out.print("Your choice: ");
        Scanner scan = new Scanner(System.in);
        while (!scan.hasNextByte()) {
        }
        b = scan.nextByte();
        do {
            if (i != 0) {
                nextFreePort++;
            }
            i++;
        } while (gameThreads.containsKey(nextFreePort));

        switch (b) {
            case 1:
                Tictactoe ttt = new Tictactoe(nextFreePort);
                Thread t = new Thread(ttt);
                t.start();
                gameThreads.put(nextFreePort, t);
                games.put(nextFreePort, ttt);
                System.out.println("TicTacToe started at: " + nextFreePort);
                break;
            default:

        }
        nextFreePort++;

    }

    public void manageGame() {

        if(!games.isEmpty())
        {
            for (Gamebase value : games.values()) {
                System.out.println(""+value.getServerName());
            }
        }
        else
        {
            System.out.println("There are currently no Games running!");
        }
        
    }

    public void stopServer_S() {
        System.out.println("SS");
    }

    public void killAll() {
        running = false;
    }

    public void exit() {
        for (Thread t : gameThreads.values()) {
            t.interrupt();
        }
        running = false;
    }

    public byte printMainMenu() {
        System.out.println("What would you wish to do?");
        System.out.println("1) Create new game");
        System.out.println("2) Manage Running game");
        System.out.println("3) Stop server(s)");
        System.out.println("4) Kill'em all");
        System.out.println("5) Safe exit");
        System.out.print("Your choice: ");
        Scanner scan = new Scanner(System.in);
        while (!scan.hasNextByte()) {
        }
        return scan.nextByte();

    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
        do {
            try {

                server.clear();
                byte b = server.printMainMenu();
                switch (b) {
                    case 1:
                        server.createGame();
                        break;
                    case 2:
                        server.manageGame();
                        break;
                    case 3:
                        server.stopServer_S();
                        break;
                    case 4:
                        server.killAll();
                        break;
                    case 5:
                        server.exit();
                        break;

                }
            } catch (IOException ex) {
                System.out.println("Clear didn't wort exiting now");
            }

        } while (server.isRunning());
    }

}
