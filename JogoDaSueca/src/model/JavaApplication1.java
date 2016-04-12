/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import agent.Agent;
import common.Move;
import java.util.LinkedList;
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
        player1.useAlfabeta();
        player2.useAlfabeta();
        team1.setPlayer1(player1);
        team1.setPlayer2(player2);

        Player player3 = new Player("gajo3", team2);
        Player player4 = new Player("gajo4", team2);
        player3.useRandomAlgorithm();
        player4.useRandomAlgorithm();
        team2.setPlayer1(player3);
        team2.setPlayer2(player4);

        LinkedList<Player> players = new LinkedList<>();
        players.add(player1);
        players.add(player3);
        players.add(player2);
        players.add(player4);

        GameState game = new GameState(team1, team2);

        /*System.out.println("agent1 CARDS --> " + player1.getCurrentState().getAgentCards().toString());
        System.out.println("agent3 CARDS --> " + player3.getCurrentState().getAgentCards().toString());
        System.out.println("agent2 CARDS --> " + player2.getCurrentState().getAgentCards().toString());
        System.out.println("agent4 CARDS --> " + player4.getCurrentState().getAgentCards().toString());*/
 /*for (int i = 0; i < 10; i++) {
            System.out.println("\nTRUMP is " + game.getTrump().toString());
            System.out.println("CURRENT ROUND : " + (game.getCurrentRound() + 1));
            for (int j = 0; j < 4; j++) {
                Boolean validPlay = true;
                Boolean validNumber = false;
                System.out.println("\n" + game.getActivePlayer().getName() + " playing");
                System.out.println("CARDS --> " + game.getActivePlayer().getCardToString());

                do {
                    Scanner in = new Scanner(System.in);
                    int num = in.nextInt();
                    validNumber = checkIndex(num, game);
                    if (validNumber) {
                        num = num - 1;
                        System.out.println("CARD PLAYED -> " + game.getActivePlayer().getCards().get(num).toString() + "\n");
                        validPlay = game.playCard(game.getActivePlayer().getCards().get(num));
                    }
                } while (!validPlay || !validNumber);
            }
        }*/

        System.out.println("\nTRUMP is " + game.getTrump().toString());
        Boolean validPlay = true;
        for (int i = 0; i < 10; i++) {
            player1.setCurrentState(game, player1.getCards());
            player2.setCurrentState(game, player2.getCards());
            player3.setCurrentState(game, player3.getCards());
            player4.setCurrentState(game, player4.getCards());
            System.out.println("\n                      CURRENT ROUND : " + (game.getCurrentRound() + 1));
            for (int j = 0; j < 4; j++) {
                Player player = players.get(game.getActivePlayerNumber() - 1);
                do {
                    System.out.println(game.getRounds().get(game.getCurrentRound()).getCardsToString());
                    System.out.println(player.getName() + " PLAYING ----> " + player.getCardToString() + "\n");
                    Move move = player.play(game.getRounds().get(game.getCurrentRound()));
                    validPlay = game.playCard(move.getCard());

                } while (!validPlay);

            }
            // System.out.println(agent1.getCurrentState().getAgentCards().size() + agent2.getCurrentState().getAgentCards().size() + agent3.getCurrentState().getAgentCards().size()+ agent4.getCurrentState().getAgentCards().size());
            //System.out.println(game.getTeam1().getPlayer1().getCards().size() + game.getTeam1().getPlayer2().getCards().size() + game.getTeam2().getPlayer1().getCards().size() + game.getTeam2().getPlayer2().getCards().size());

            //System.out.println(player1.getCurrentState().getAgentCards().size());
            //System.out.println(game.getTeam1().getPlayer1().getCards().size());
        }

    }

    public static boolean checkIndex(int num, GameState game) {
        if (num >= 1 && num <= game.getActivePlayer().getCards().size()) {
            return true;
        } else {
            System.out.println("INVALID INDEX!");
            return false;
        }
    }

}
