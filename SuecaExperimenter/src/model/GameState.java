/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import agent.ObservationHeuristic;
import common.SuecaState;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omg.CORBA.TIMEOUT;

/**
 *
 * @author Yeezus
 */
public class GameState extends SuecaState {

    private Team team1;
    private Team team2;
    private Team winnerTeam;
    private Suit trump;
    private Player activePlayer;
    private int activePlayerNumber;
    private Card trumpCard;

    public GameState(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
        playedCards = new LinkedList<>();
        rounds = new LinkedList<>();
        shuffleDeck();
        giveCards();

        Random r = new Random();
        this.activePlayerNumber = r.nextInt(4 - 1) + 1;
        switch (activePlayerNumber) {
            case 1:
                activePlayer = team1.getPlayer1();
                break;
            case 2:
                activePlayer = team2.getPlayer1();
                break;
            case 3:
                activePlayer = team1.getPlayer2();
                break;
            case 4:
                activePlayer = team2.getPlayer2();
                break;
        }
        int num = (Math.random() <= 0.5) ? 1 : 2;
        if (num == 1) {
            trumpCard = activePlayer.getCards().getFirst();
        } else {
            trumpCard = activePlayer.getCards().getLast();
        }
        this.trump = trumpCard.getSuit();

        this.currentRound = 0;
        rounds.add(new Round(currentRound));
    }

    private GameState() {
        rounds = new LinkedList<>();
        playedCards = new LinkedList<>();
    }

    public Suit getTrump() {
        return trump;
    }

    public void setTrump(Suit trump) {
        this.trump = trump;
    }

    private void nextPlayer() {
        if (this.activePlayerNumber < 4) {
            this.activePlayerNumber++;
        } else {
            this.activePlayerNumber = 1;
        }
        switch (this.activePlayerNumber) {
            case 1:
                this.activePlayer = team1.getPlayer1();
                break;
            case 2:
                this.activePlayer = team2.getPlayer1();
                break;
            case 3:
                this.activePlayer = team1.getPlayer2();
                break;
            case 4:
                this.activePlayer = team2.getPlayer2();
                break;
        }

    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public int getActivePlayerNumber() {
        return activePlayerNumber;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public Team getWinnerTeam() {
        return winnerTeam;
    }

    public void setWinnerTeam(Team winnerTeam) {
        this.winnerTeam = winnerTeam;
    }

    private void shuffleDeck() {
        Collections.shuffle(allCards);
    }

    public Card getTrumpCard() {
        return trumpCard;
    }

    public void setTrumpCard(Card trumpCard) {
        this.trumpCard = trumpCard;
    }

    private void giveCards() {
        int i = 0;
        for (Card card : allCards) {
            if (i < 10) {
                team1.getPlayer1().receiveCard(card);
            } else if (i >= 10 && i < 20) {
                team1.getPlayer2().receiveCard(card);
            } else if (i >= 20 && i < 30) {
                team2.getPlayer1().receiveCard(card);
            } else if (i >= 30) {
                team2.getPlayer2().receiveCard(card);
            }
            i++;
        }
    }

    public Boolean playCard(Card card) {
        if (rounds.get(currentRound).getCards().isEmpty()) {
            rounds.get(currentRound).getCards().add(new CardPlayed(card, activePlayer));
            rounds.get(currentRound).setRoundSuit(card.getSuit());
            activePlayer.getCards().remove(card);
            CardPlayed c = new CardPlayed(card, activePlayer);
            playedCards.add(c);
            if (card.getSuit() == trump) {
                rounds.get(currentRound).setTrumpPlayed(true);
            }
            updateGameHistory(c, rounds.get(currentRound), activePlayerNumber);
            nextPlayer();
        } else if (validateCard(card, rounds.get(currentRound).getRoundSuit(), activePlayer)) {
            rounds.get(currentRound).getCards().add(new CardPlayed(card, activePlayer));
            activePlayer.getCards().remove(card);
            CardPlayed c = new CardPlayed(card, activePlayer);
            playedCards.add(c);
            if (card.getSuit() == trump) {
                rounds.get(currentRound).setTrumpPlayed(true);
            }
            updateGameHistory(c, rounds.get(currentRound), activePlayerNumber);
            nextPlayer();
        } else {
            return false;
        }

        if (rounds.get(currentRound).getCards().size() == 4) {
            endRound();
        }
        return true;
    }

    private void endRound() {
        CardPlayed winnerCard = rounds.get(currentRound).getWinnerCard(this.trump);
        int score = rounds.get(currentRound).getRoundScore();
        winnerCard.getPlayer().getTeam().addScore(score);

        if (currentRound == 9) {
            endGame();
        }

        nextRound();

    }

    private void nextRound() {
        this.activePlayer = rounds.get(currentRound).getWinnerPlayer();

        if (activePlayer.getTeam() == team1) {
            if (activePlayer.getTeam().getPlayer1() == activePlayer) {
                this.activePlayerNumber = 1;
            } else {
                this.activePlayerNumber = 3;
            }
        } else if (activePlayer.getTeam() == team2) {
            if (activePlayer.getTeam().getPlayer1() == activePlayer) {
                this.activePlayerNumber = 2;
            } else {
                this.activePlayerNumber = 4;
            }
        }
        currentRound++;
        rounds.add(new Round(currentRound));
    }

    private void endGame() {

    }

    @Override
    public GameState clone() {
        GameState g = new GameState();
        g.setTeam1(team1.clone());
        g.setTeam2(team2.clone());
        g.setActivePlayer(activePlayer.clone());
        g.activePlayerNumber = activePlayerNumber;
        g.setTrump(trump);
        for (Round r : rounds) {
            g.getRounds().add(r.clone());
        }
        g.setWinnerTeam(winnerTeam);
        g.currentRound = currentRound;
        for (CardPlayed c : playedCards) {
            g.getPlayedCards().add(c.clone());
        }
        return g;
    }

    public Boolean playCardSimulation(Card card) {
        if (rounds.get(currentRound).getCards().isEmpty()) {
            rounds.get(currentRound).getCards().add(new CardPlayed(card, activePlayer));
            rounds.get(currentRound).setRoundSuit(card.getSuit());
            activePlayer.getCards().remove(card);
            CardPlayed c = new CardPlayed(card, activePlayer);
            playedCards.add(c);
            if (card.getSuit() == trump) {
                rounds.get(currentRound).setTrumpPlayed(true);
            }
            updateGameHistory(c, rounds.get(currentRound), activePlayerNumber);
            nextPlayer();
        } else if (super.validateCard(card, rounds.get(currentRound).getRoundSuit(), activePlayer)) {
            rounds.get(currentRound).getCards().add(new CardPlayed(card, activePlayer));
            activePlayer.getCards().remove(card);
            CardPlayed c = new CardPlayed(card, activePlayer);
            playedCards.add(c);
            if (card.getSuit() == trump) {
                rounds.get(currentRound).setTrumpPlayed(true);
            }
            updateGameHistory(c, rounds.get(currentRound), activePlayerNumber);
            nextPlayer();
        } else {
            return false;
        }

        if (rounds.get(currentRound).getCards().size() == 4) {
            endRoundSimulation();
        }
        return true;
    }

    private void endRoundSimulation() {
        CardPlayed winnerCard = rounds.get(currentRound).getWinnerCard(this.trump);
        int score = rounds.get(currentRound).getRoundScore();
        if (currentRound == 9) {

        }
        nextRound();

    }

    public void updateGameState() {
        team1.getPlayer1().setCurrentState(this, team1.getPlayer1().getCards());
        team2.getPlayer1().setCurrentState(this, team2.getPlayer1().getCards());
        team1.getPlayer2().setCurrentState(this, team1.getPlayer2().getCards());
        team2.getPlayer2().setCurrentState(this, team2.getPlayer2().getCards());
    }

    public int getNumberOfCardsOfPlayer(Player player) {
        if (rounds.get(currentRound).getCards().size() > 0) {
            for (CardPlayed c : rounds.get(currentRound).getCards()) {
                if (c.getPlayer().getName().equalsIgnoreCase(player.getName())) {
                    return 10 - currentRound - 1;
                }
            }
            return 10 - currentRound;
        }
        return 0;
    }

    public LinkedList<Player> getOpponentTeam(Player player) {
        LinkedList<Player> players = new LinkedList<>();
        if (!team1.belongsToTeam(player)) {
            players.add(team1.getPlayer1().clone());
            players.add(team1.getPlayer2().clone());
        } else if (!team2.belongsToTeam(player)) {
            players.add(team2.getPlayer1().clone());
            players.add(team2.getPlayer2().clone());
        }
        return players;
    }

    public Player getTeammate(Player player) {
        if (team1.belongsToTeam(player)) {
            if (team1.getPlayer1().getName().equalsIgnoreCase(player.getName())) {
                return team1.getPlayer2().clone();
            } else {
                return team1.getPlayer1().clone();
            }
        } else if (team2.belongsToTeam(player)) {
            if (team2.getPlayer1().getName().equalsIgnoreCase(player.getName())) {
                return team2.getPlayer2().clone();
            } else {
                return team2.getPlayer1().clone();
            }
        }
        return null;
    }

    private void updateGameHistory(CardPlayed c, Round round, int activePlayer) {
        Player p1 = getTeam1().getPlayer1();
        if (p1.getId() != activePlayer) {
            p1.runObservationHeuristics(c, round, p1.getGameHistory().getCardsToGive(), (LinkedList<CardPlayed>) playedCards.clone());
        }

        Player p2 = getTeam1().getPlayer2();
        if (p2.getId() != activePlayer) {
            p2.runObservationHeuristics(c, round, p2.getGameHistory().getCardsToGive(), (LinkedList<CardPlayed>) playedCards.clone());
        }

        Player p3 = getTeam2().getPlayer1();
        if (p3.getId() != activePlayer) {
            p1.runObservationHeuristics(c, round, p3.getGameHistory().getCardsToGive(), (LinkedList<CardPlayed>) playedCards.clone());
        }

        Player p4 = getTeam2().getPlayer2();
        if (p4.getId() != activePlayer) {
            p4.runObservationHeuristics(c, round, p4.getGameHistory().getCardsToGive(), (LinkedList<CardPlayed>) playedCards.clone());
        }
    }

}
