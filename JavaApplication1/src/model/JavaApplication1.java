/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Yeezus
 */
public class JavaApplication1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Team team1 = new Team();
        Team team2 = new Team();
        Player player1 = new Player("gajo1", team1);
        Player player2 = new Player("gajo2", team1);
        team1.setPlayer1(player1);
        team1.setPlayer2(player2);
        Player player3 = new Player("gajo3", team2);
        Player player4 = new Player("gajo4", team2);
        team2.setPlayer1(player3);
        team2.setPlayer2(player4);
        Game game = new Game(team1, team2);
        
        System.out.println("CARDS" + player1.getCards().toString() + " " + player1.getCards().size());
        System.out.println("CARDS" + player3.getCards().toString() + " " + player3.getCards().size());
        System.out.println("CARDS" + player2.getCards().toString() + " " + player2.getCards().size());
        System.out.println("CARDS" + player4.getCards().toString() + " " + player4.getCards().size());
        
        for (int i = 0; i < 10; i++) {
            System.out.println("TRUMP is " + game.getTrump().toString());
            System.out.println("CURRENT ROUND : " + (game.getCurrentRound()+1));
            for (int j = 0; j < 4; j++) {
                Boolean validPlay = true;
                System.out.println("\n" + game.getActivePlayer().getName() + " playing");
                System.out.println("CARDS" + game.getActivePlayer().getCards().toString());

                do{
                    Scanner in = new Scanner(System.in);
                    int num = in.nextInt() - 1;
                    System.out.println("CARD PLAYED -> " + game.getActivePlayer().getCards().get(num).toString());                
                    validPlay = game.playCard(game.getActivePlayer().getCards().get(num));
                } while(!validPlay);
            }
            System.out.println("ROUND SCORE : " + game.getRounds().get(game.getCurrentRound()).getRoundScore());
        }

    }

}
