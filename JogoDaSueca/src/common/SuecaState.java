/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.LinkedList;
import model.Card;
import model.Player;
import model.PossibleCards;
import model.Round;
import model.Suit;

/**
 *
 * @author Yeezus
 */
public class SuecaState {
    
    /**
     * Number of total cards of a deck
     */
    public static final int TOTAL_CARDS = 40;    
    /**
     * Number of cards per player
     */
    public static final int CARDS_PER_PLAYER = 12;  
    /**
     * Number of rounds per game
     */
    public static final int ROUNDS = 10; 
    /**
     * Number of cards per round
     */
    public static final int CARDS_PER_ROUND = 4;   
    /**
     * List of all cards
     */  
    protected static LinkedList<Card> allCards;
    
    /**
     * Inicialization of the allCards list.
     */  
    static {
        allCards = new LinkedList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                Card c = new Card();
                if (i == 0) {
                    c.setSuit(Suit.CLUBS);
                    c.setCard(PossibleCards.generateCardFromInt(j));
                    c.setWeight(j+1);
                    c.giveValue(c.getCard());
                }
                if (i == 1) {
                    c.setSuit(Suit.DIAMONDS);
                    c.setCard(PossibleCards.generateCardFromInt(j));
                    c.setWeight(j+1);
                    c.giveValue(c.getCard());
                }
                if (i == 2) {
                    c.setSuit(Suit.HEARTS);
                    c.setCard(PossibleCards.generateCardFromInt(j));
                    c.setWeight(j+1);
                    c.giveValue(c.getCard());
                }
                if (i == 3) {
                    c.setSuit(Suit.SPADES);
                    c.setCard(PossibleCards.generateCardFromInt(j));
                    c.setWeight(j+1);
                    c.giveValue(c.getCard());
                }
                allCards.add(c);
            }
        }
    }
    /**
     * List of already played cards.
     */     
    protected LinkedList<Card> playedCards;
    /**
     * List of the rounds of a game
     */     
    protected LinkedList<Round> rounds;
    
    /**
     * Number of the current round
     */
    protected int currentRound;
    
    public SuecaState(){
       
        //initialize();
    }
    
    private void initialize() {
        playedCards.clear();
        rounds.clear();
    }

    public static LinkedList<Card> getAllCards() {
        return (LinkedList<Card>) allCards.clone();
    }

    public static void setAllCards(LinkedList<Card> allCards) {
        SuecaState.allCards = allCards;
    }

    public LinkedList<Card> getPlayedCards() {
        return playedCards;
    }

    public void setPlayedCards(LinkedList<Card> playedCards) {
        this.playedCards = playedCards;
    }

    public LinkedList<Round> getRounds() {
        return rounds;
    }

    public void setRounds(LinkedList<Round> rounds) {
        this.rounds = rounds;
    }
    
    protected boolean validateCard(Card card, Suit roundSuit, Player activePlayer) {
        if (roundSuit == null || roundSuit == card.getSuit()) {
            return true;
        } else {
            return !activePlayer.hasCardsFromSuit(roundSuit);
        }
    }   
    
    
}
