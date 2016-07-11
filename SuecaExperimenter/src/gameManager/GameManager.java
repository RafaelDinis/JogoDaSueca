/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameManager;

import agent.Agent;
import agent.GameAlgorithm;
import common.Move;
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

    public GameManager(ExperimentsManagerGUI gui) {
        this.gui = gui;
        upperTeamWins = 0;
        downTeamWins = 0;
        draws = 0;

    }

    public void setUpperTeamConfiguration(int upperTeamRoundsAhead, int upperTeamHands, int upperTeamAlgorithm) {
        this.upperTeamRoundsAhead = upperTeamRoundsAhead;
        this.upperTeamHands = upperTeamHands;
        this.upperTeamAlgorithm = upperTeamAlgorithm;
    }

    public void setDownTeamConfiguration(int downTeamRoundsAhead, int downTeamHands, int downTeamAlgorithm) {
        this.downTeamRoundsAhead = downTeamRoundsAhead;
        this.downTeamHands = downTeamHands;
        this.downTeamAlgorithm = downTeamAlgorithm;
    }
    
    public void run(int numGames) {
        for (int i = 0; i < numGames; i++) {
            System.out.println("jogo " + i);
            gui.showResults("Jogo " + i);
            GameState game = prepareGame();
            int result = playGame(game, i);
            switch (result) {
                case 1:
                    upperTeamWins++;
                    break;
                case 2:
                    downTeamWins++;
                    break;
                case 0:
                    draws++;
                    break;
            }
        }
        gui.showResults("Upper Team wins: " + upperTeamWins
                + "\nDown Team wins: " + downTeamWins
                + "\ndraws: " + draws);
        upperTeamWins = 0;
        downTeamWins = 0;
        draws = 0;
    }

    public GameState prepareGame() {
        team1 = new Team();
        team2 = new Team();

        player1 = new Player(1, "jogador1", team1);
        player2 = new Player(3, "jogador2", team1);
        team1.setPlayer1(player1);
        team1.setPlayer2(player2);
        team1.setName("equipa1");
        configureAgent(player1, upperTeamRoundsAhead, upperTeamHands, upperTeamAlgorithm);
        configureAgent(player2, upperTeamRoundsAhead, upperTeamHands, upperTeamAlgorithm);

        player3 = new Player(2, "jogador3", team2);
        player4 = new Player(4, "jogador4", team2);
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

    public int playGame(GameState game, int gameNum) {
        /*double prbtisplit = 0.05;
        double resultado = (1.0/3.0)-0.05;
        double prob1 = (1.0/3.0)+ (prbtisplit*0.5);
        double prob2 = (1.0/3.0)+ (prbtisplit*0.5);
        
        System.out.println("resultado: " + resultado);
        System.out.println("prob1: " + prob1);
        System.out.println("prob2: " + prob2);*/
        
        Boolean validPlay = true;
        for (int i = 0; i < 10; i++) {
            System.out.println("ronda " + i);
            gui.showResults("Ronda " + i + "do jogo " + gameNum);
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
        if (game.getTeam1().getFinalScore() == game.getTeam2().getFinalScore()) {
            return 0;
        } else if (game.getTeam1().getFinalScore() > game.getTeam2().getFinalScore()) {
            return 1;
        } else {
            return 2;
        }

    }

    public void configureAgent(Agent agent, int roundsLimit, int handsLimit, int algorithm) {
        if (algorithm == RANDOM_ALGORITHM) {
            agent.useRandomAlgorithm();
        } else {
            agent.useAlfabeta();
            agent.setSearchRoundLimit(roundsLimit);
            agent.setHandsLimit(handsLimit);
        }
    }

}
