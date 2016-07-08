/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashMap;

/**
 *
 * @author Rafael
 */
public class CardProb {
    
    private Card card;
    private int player;
    private HashMap<Integer, Double> probabilities;
    
    public CardProb(Card card){
        this.card = card.clone();
        probabilities = new HashMap<>();
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public HashMap<Integer, Double> getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(HashMap<Integer, Double> probabilities) {
        this.probabilities = probabilities;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void initializeProbabilities(int id) {
        player = id;
        int playerId = id;
        
        playerId = nextPlayer(playerId);
        probabilities.put(playerId, 1.0/3.0);
        playerId = nextPlayer(playerId);
        probabilities.put(playerId, 1.0/3.0);
        playerId = nextPlayer(playerId);
        probabilities.put(playerId, 1.0/3.0); 
    }
    
    private int nextPlayer(int id) {
        if (id < 4) {
            id++;
        } else {
            id = 1;
        }
        return id;
    }
    
}
