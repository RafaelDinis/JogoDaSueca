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
        Agent agent1 = new Agent();
        Agent agent2 = new Agent();
        team1.setAgent1(agent1);
        team1.setAgent1(agent2);
        Player player1 = new Player("gajo1", team1);
        Player player2 = new Player("gajo2", team1);
        team1.setPlayer1(player1);
        team1.setPlayer2(player2);
        Agent agent3 = new Agent();
        Agent agent4 = new Agent();
        team2.setAgent1(agent3);
        team2.setAgent1(agent4);
        LinkedList<Agent> players = new LinkedList<>();
        players.add(agent1);
        players.add(agent3);
        players.add(agent2);
        players.add(agent4);
        Player player3 = new Player("gajo3", team2);
        Player player4 = new Player("gajo4", team2);
        team2.setPlayer1(player3);
        team2.setPlayer2(player4);
        GameState game = new GameState(team1, team2);
        agent1.notifyNewGame((LinkedList<Card>) player1.getCards());
        agent2.notifyNewGame((LinkedList<Card>) player2.getCards());
        agent3.notifyNewGame((LinkedList<Card>) player3.getCards());
        agent4.notifyNewGame((LinkedList<Card>) player4.getCards());
        
        System.out.println("agent1 CARDS --> " + agent1.getCurrentState().getAgentCards().toString());
        System.out.println("agent3 CARDS --> " + agent3.getCurrentState().getAgentCards().toString());
        System.out.println("agent2 CARDS --> " + agent2.getCurrentState().getAgentCards().toString());
        System.out.println("agent4 CARDS --> " + agent4.getCurrentState().getAgentCards().toString());
        
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
        for (int i = 0; i < 10; i++) {
            System.out.println("CURRENT ROUND : " + (game.getCurrentRound() + 1));
            for(Agent agent: players){                
                Move move = agent.play(game.getRounds().get(game.getCurrentRound()));
                game.playCard(move.getCard());
            }
            System.out.println(agent1.getCurrentState().getAgentCards().size() + agent2.getCurrentState().getAgentCards().size() + agent3.getCurrentState().getAgentCards().size()+ agent4.getCurrentState().getAgentCards().size());
            
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
