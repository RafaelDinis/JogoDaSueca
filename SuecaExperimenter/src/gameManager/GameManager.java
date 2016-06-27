/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameManager;

import agent.Agent;
import agent.GameAlgorithm;
import common.Move;
import static java.lang.Double.max;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import model.Card;
import model.GameState;
import model.Player;
import model.Team;

/**
 *
 * @author Yeezus
 */
public class GameManager {

    public static final int RANDOM_ALGORITHM = 1;
    public static final int ALPHA_BETA = 2;
    private ExperimentsManagerGUI gui;
    
    private int upperTeamRoundsAhead;
    private int upperTeamHands;
    private int upperTeamAlgorithm;
    
    private int downTeamRoundsAhead;
    private int downTeamHands;
    private int downTeamAlgorithm;
    
    
    private int upperTeamWins;
    private int downTeamWins;
    private int draws;
    
    
    private Team team1;
    private Player player1;
    private Player player2;
    
    private Team team2;    
    private Player player3;
    private Player player4;
    
    private LinkedList<Player> players;
    
    public GameManager(ExperimentsManagerGUI gui){
        this.gui = gui;
        upperTeamWins = 0;
        downTeamWins = 0;
        draws = 0;
                
    }
    
    public void setUpperTeamConfiguration(int upperTeamRoundsAhead, int upperTeamHands, int upperTeamAlgorithm){
        this.upperTeamRoundsAhead = upperTeamRoundsAhead;
        this.upperTeamHands = upperTeamHands;
        this.upperTeamAlgorithm = upperTeamAlgorithm;
    }
    
    public void setDownTeamConfiguration(int downTeamRoundsAhead, int downTeamHands, int downTeamAlgorithm){
        this.downTeamRoundsAhead = downTeamRoundsAhead;
        this.downTeamHands = downTeamHands;
        this.downTeamAlgorithm = downTeamAlgorithm;
    }
    
    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
        Team team1 = new Team();
        Team team2 = new Team();

        Player player1 = new Player(1, "gajo1", team1);
        Player player2 = new Player(3, "gajo2", team1);
        player1.useAlfabeta();
        player2.useAlfabeta();
        team1.setPlayer1(player1);
        team1.setPlayer2(player2);
        team1.setName("equipa1");

        Player player3 = new Player(2, "gajo3", team2);
        Player player4 = new Player(4, "gajo4", team2);
        player3.useRandomAlgorithm();
        player4.useRandomAlgorithm();
        team2.setPlayer1(player3);
        team2.setPlayer2(player4);
        team2.setName("equipa2");
        

        LinkedList<Player> players = new LinkedList<>();
        players.add(player1);
        players.add(player3);
        players.add(player2);
        players.add(player4);

        GameState game = new GameState(team1, team2);
        System.out.println("agent1 CARDS --> " + player1.getCardToString());
        System.out.println("agent3 CARDS --> " + player3.getCardToString());
        System.out.println("agent2 CARDS --> " + player2.getCardToString());
        System.out.println("agent4 CARDS --> " + player4.getCardToString());
        
        int num = (Math.random() <= 0.5) ? 1 : 2;
        Card trumpCard = null;
        if(num == 1){
            trumpCard = player1.getCards().getFirst();
        } else{
            trumpCard = player1.getCards().getLast();
        }
        player1.getGameHistory().setTrumpCard(trumpCard);        
        player2.getGameHistory().setTrumpCard(trumpCard);        
        player3.getGameHistory().setTrumpCard(trumpCard);        
        player4.getGameHistory().setTrumpCard(trumpCard);
        
        
        /*System.out.println(player1.getGameHistory().toString());
        System.out.println(player2.getGameHistory().toString());
        System.out.println(player3.getGameHistory().toString());
        System.out.println(player4.getGameHistory().toString());*/
        
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
        }

        System.out.println("\nTRUMP is " + game.getTrump().toString());
        Boolean validPlay = true;
        for (int i = 0; i < 10; i++) {
            game.updateGameState();
            System.out.println("\n                      CURRENT ROUND : " + (game.getCurrentRound() + 1));
            for (int j = 0; j < 4; j++) {
                Player player = players.get(game.getActivePlayerNumber() - 1);
                do {
                    System.out.println(game.getRounds().get(game.getCurrentRound()).getCardsToString());
                    System.out.println(player.getName() + " PLAYING ----> " + player.getCardToString() + "\n");
                    Move move = player.play(game.getRounds().get(game.getCurrentRound()));
                    validPlay = game.playCard(move.getCard());
                    game.updateGameState();

                } while (!validPlay);

            }
            /*System.out.println(player1.getGameHistory().toString());
            System.out.println(player2.getGameHistory().toString());
            System.out.println(player3.getGameHistory().toString());
            System.out.println(player4.getGameHistory().toString());
        }

    }*/
    
    public void run(int numGames){
        for (int i = 0; i < numGames; i++) {
            GameState game = prepareGame();
            int result = playGame(game);
            switch(result){
                case 1: upperTeamWins++; break;
                case 2: downTeamWins++;  break;
                case 0: draws++; break;
            }
        }
        gui.showResults("Upper Team wins: " + upperTeamWins + 
                "\nDown Team wins: " + downTeamWins + 
                "\ndraws: " + draws);
        upperTeamWins = 0;
        downTeamWins = 0;
        draws = 0;
    }
    
    public GameState prepareGame(){
        team1 = new Team();
        team2 = new Team();

        player1 = new Player(1, "gajo1", team1);
        player2 = new Player(3, "gajo2", team1);
        player1.useAlfabeta();
        player2.useAlfabeta();
        team1.setPlayer1(player1);
        team1.setPlayer2(player2);
        team1.setName("equipa1");
        configureAgent(player1, upperTeamRoundsAhead, upperTeamHands, upperTeamAlgorithm);
        configureAgent(player2, upperTeamRoundsAhead, upperTeamHands, upperTeamAlgorithm);

        player3 = new Player(2, "gajo3", team2);
        player4 = new Player(4, "gajo4", team2);
        player3.useRandomAlgorithm();
        player4.useRandomAlgorithm();
        team2.setPlayer1(player3);
        team2.setPlayer2(player4);
        team2.setName("equipa2");
        configureAgent(player3, downTeamRoundsAhead, downTeamHands, downTeamAlgorithm);
        configureAgent(player4, downTeamRoundsAhead, downTeamHands, downTeamAlgorithm);
        
        

        players = new LinkedList<>();
        players.add(player1);
        players.add(player3);
        players.add(player2);
        players.add(player4);

        GameState game = new GameState(team1, team2);

        player1.getGameHistory().setTrumpCard(game.getTrumpCard());        
        player2.getGameHistory().setTrumpCard(game.getTrumpCard());        
        player3.getGameHistory().setTrumpCard(game.getTrumpCard());        
        player4.getGameHistory().setTrumpCard(game.getTrumpCard());
        
        return game;
        
    }
    
    public int playGame(GameState game){
        Boolean validPlay = true;
        for (int i = 0; i < 10; i++) {
            game.updateGameState();
            for (int j = 0; j < 4; j++) {
                Player player = players.get(game.getActivePlayerNumber() - 1);
                do {
                    Move move = player.play(game.getRounds().get(game.getCurrentRound()));
                    validPlay = game.playCard(move.getCard());
                    game.updateGameState();

                } while (!validPlay);

            }
        }
        if(game.getTeam1().getFinalScore() == game.getTeam2().getFinalScore()){
            return 0;
        } else if(game.getTeam1().getFinalScore() > game.getTeam2().getFinalScore()){
            return 1;
        } else {
            return 2;
        }
    }
    
    public void configureAgent(Agent agent, int roundsLimit, int handsLimit, int algorithm){
        if (algorithm == RANDOM_ALGORITHM) {
            agent.useRandomAlgorithm();
        } else { 
            agent.useAlfabeta();
            agent.setSearchRoundLimit(roundsLimit);
            agent.setHandsLimit(handsLimit);
        }
    }    

}
