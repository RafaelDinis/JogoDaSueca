/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import common.SuecaState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class GameHistory {

    private int playerId;
    private Card trumpCard;
    private LinkedList<CardProb> cardsToGive;

    public GameHistory(int id) {
        playerId = id;
        cardsToGive = new LinkedList();
    }

    public GameHistory() {

    }

    
    public void initializeCardsToGive(int id, LinkedList<Card> cards) {
        for(Card card : cards){
            CardProb c = new CardProb(card);
            c.initializeProbabilities(id);
            cardsToGive.add(c);
        }
    }

    public Card getTrumpCard() {
        return trumpCard;
    }

    public void setTrumpCard(Card trumpCard) {
        this.trumpCard = trumpCard;
    }

    public LinkedList<CardProb> getCardsToGive() {
        return cardsToGive;
    }

    public void setCardsToGive(LinkedList<CardProb> cardsToGive) {
        this.cardsToGive = cardsToGive;
    }
    
    public CardProb getCard(Card card){
        for(CardProb c : cardsToGive){
            if(c.getCard().getCard().equals(card.getCard()) && c.getCard().getSuit().equals(card.getSuit())){
                return c;
            }
        }
        return null;
    }
    
    @Override
    protected GameHistory clone(){
        GameHistory clone = new GameHistory();
        clone.playerId = this.playerId;
        clone.setCardsToGive((LinkedList<CardProb>) cardsToGive.clone());
        clone.setTrumpCard(trumpCard.clone());
        return clone;
    }

}
