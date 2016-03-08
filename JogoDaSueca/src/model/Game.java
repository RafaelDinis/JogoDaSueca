/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Yeezus
 */
public class Game {

    private Team team1;
    private Team team2;
    private ArrayList<Round> rounds;
    private Team winnerTeam;
    private Suit trump;
    private ArrayList<Card> deck;
    private Player activePlayer;
    private int activePlayerNumber;
    private int currentRound;

    Game(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
        this.deck = generateCards();
        shuffleDeck();
        giveCards();
        this.trump = team1.getPlayer1().getCards().get(0).getSuit();
        this.activePlayer = team1.getPlayer1();
        this.activePlayerNumber = 1;
        this.currentRound = 0;
        this.rounds = new ArrayList<>();
        rounds.add(new Round(currentRound));
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

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    public int getActivePlayerNumber() {
        return activePlayerNumber;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setRounds(ArrayList<Round> rounds) {
        this.rounds = rounds;
    }

    public Team getWinnerTeam() {
        return winnerTeam;
    }

    public void setWinnerTeam(Team winnerTeam) {
        this.winnerTeam = winnerTeam;
    }

    public static ArrayList<Card> generateCards() {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                Card c = new Card();
                if (i == 0) {
                    c.setSuit(Suit.CLUBS);
                    c.setCard(PossibleCards.generateCardFromInt(j));
                    c.giveValue(c.getCard());
                }
                if (i == 1) {
                    c.setSuit(Suit.DIAMONDS);
                    c.setCard(PossibleCards.generateCardFromInt(j));
                    c.giveValue(c.getCard());
                }
                if (i == 2) {
                    c.setSuit(Suit.HEARTS);
                    c.setCard(PossibleCards.generateCardFromInt(j));
                    c.giveValue(c.getCard());
                }
                if (i == 3) {
                    c.setSuit(Suit.SPADES);
                    c.setCard(PossibleCards.generateCardFromInt(j));
                    c.giveValue(c.getCard());
                }
                cards.add(c);
            }
        }
        System.out.println(cards.size());
        return cards;
    }

    private void shuffleDeck() {
        Collections.shuffle(deck);
    }

    private void giveCards() {
        int i = 0;
        for (Card card : deck) {
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
            activePlayer.removeCardFromHand(card);
            if (card.getSuit() == trump) {
                rounds.get(currentRound).setTrumpPlayed(true);
            }
            nextPlayer();
        } else if (validateCard(card)) {
            rounds.get(currentRound).getCards().add(new CardPlayed(card, activePlayer));
            activePlayer.removeCardFromHand(card);
            if (card.getSuit() == trump) {
                rounds.get(currentRound).setTrumpPlayed(true);
            }
            nextPlayer();
        } else {
            System.out.println("INVALID CARD \n");
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

    private boolean validateCard(Card card) {
        if (rounds.get(currentRound).getRoundSuit() == card.getSuit()) {
            return true;
        } else {
            System.out.println(!activePlayer.hasCardsFromSuit(card.getSuit()));
            return !activePlayer.hasCardsFromSuit(card.getSuit());
        }
    }

    private void nextRound() {
        this.activePlayer = rounds.get(currentRound).getWinnerPlayer();

        if (activePlayer.getTeam() == team1) {
            if (activePlayer.getTeam().getPlayer1() == activePlayer) {
                this.activePlayerNumber = 1;
            } else {
                this.activePlayerNumber = 3;
            }
        } else if (activePlayer.getTeam().getPlayer1() == activePlayer) {
            this.activePlayerNumber = 2;
        } else {
            this.activePlayerNumber = 4;
        }
        currentRound++;
        rounds.add(new Round(currentRound));
    }

    private void endGame() {
        //TO DO
    }
    

}
