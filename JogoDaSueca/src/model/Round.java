/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Yeezus
 */
public class Round {

    private int roundNumber;
    private ArrayList<CardPlayed> cards;
    private int roundScore;
    private Team winnerTeam;
    private Player winnerPlayer;
    private Suit roundSuit;
    private boolean trumpPlayed;

    public Round(int roundNumber) {
        this.roundNumber = roundNumber;
        this.cards = new ArrayList<>();
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

    public ArrayList<CardPlayed> getCards() {
        return cards;
    }

    public void setCards(ArrayList<CardPlayed> cards) {
        this.cards = cards;
    }

    public CardPlayed getWinnerCard(Suit trump) {
        CardPlayed winnerCard = new CardPlayed(new Card(), null);
        winnerCard.getCard().setWeight(0);

        if (trumpPlayed) {
            winnerCard = getHighestTrump(trump);
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
                if (card.getCard().getWeight()>= winnerCard.getCard().getWeight()) {
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

}
