/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Yeezus
 */
public class Round {

    private int roundNumber;
    private LinkedList<CardPlayed> cards;
    private int roundScore;
    private Team winnerTeam;
    private Player winnerPlayer;
    private Suit roundSuit;
    private boolean trumpPlayed;

    public Round(int roundNumber) {
        this.roundNumber = roundNumber;
        this.cards = new LinkedList<>();
        this.roundScore = 0;
        this.trumpPlayed = false;
    }

    public Player getWinnerPlayer() {
        return winnerPlayer;
    }

    public boolean isTrumpPlayed() {
        return trumpPlayed;
    }

    public void setTrumpPlayed(boolean trumpPlayed) {
        this.trumpPlayed = trumpPlayed;
    }

    public Suit getRoundSuit() {
        return roundSuit;
    }

    public void setRoundSuit(Suit roundSuit) {
        this.roundSuit = roundSuit;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public LinkedList<CardPlayed> getCards() {
        return cards;
    }

    public void setCards(LinkedList<CardPlayed> cards) {
        this.cards = cards;
    }

    public void setRoundScore(int roundScore) {
        this.roundScore = roundScore;
    }

    public CardPlayed getWinnerCard(Suit trump) {
        CardPlayed winnerCard = new CardPlayed(new Card(), null);
        winnerCard.getCard().setWeight(0);

        if (trumpPlayed) {
            winnerCard = getHighestTrump(trump);
            this.winnerTeam = winnerCard.getPlayer().getTeam();
            this.winnerPlayer = winnerCard.getPlayer();
            return winnerCard;
        }

        for (CardPlayed card : cards) {
            if (card.getCard().getSuit() == roundSuit && card.getCard().getWeight() >= winnerCard.getCard().getWeight()) {
                winnerCard = card;
            }
        }
        this.winnerTeam = winnerCard.getPlayer().getTeam();
        this.winnerPlayer = winnerCard.getPlayer();
        return winnerCard;
    }

    private CardPlayed getHighestTrump(Suit trump) {
        CardPlayed winnerCard = new CardPlayed(new Card(), null);
        winnerCard.getCard().setWeight(0);

        for (CardPlayed card : cards) {
            if (card.getCard().getSuit() == trump) {
                if (card.getCard().getWeight() >= winnerCard.getCard().getWeight()) {
                    winnerCard = card;
                }
            }
        }
        return winnerCard;
    }

    public int getRoundScore() {
        int score = 0;
        for (CardPlayed card : cards) {
            score = score + card.getCard().getValue();
        }
        this.roundScore = score;
        return score;
    }

    public String getCardsToString() {
        StringBuilder string = new StringBuilder();
        for (CardPlayed c : cards) {
            string.append(c.getCard().toString() + "      ");
        }
        return string.toString();
    }

    @Override
    protected Round clone() {
        Round r = new Round(roundNumber);
        r.setCards((LinkedList<CardPlayed>) cards.clone());
        r.setRoundSuit(roundSuit);
        r.setTrumpPlayed(trumpPlayed);
        r.setRoundScore(roundScore);
        r.setRoundNumber(roundNumber);
        return r;
    }

}
