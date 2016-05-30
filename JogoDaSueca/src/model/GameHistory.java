/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class GameHistory {
    
    private HashMap<Suit, int[]> cardsTogive;
    

    public GameHistory(int id) {
        cardsTogive = new HashMap<>();
        initializeCardsToGive(id);
    }
    
    private void initializeCardsToGive(int id){
        int[] players = new int[3];
        int j = 0;
        for (int i = 1; i < 5; i++) {
            if(i != id){
                players[j] = i;
                j++;
            }
        }
        cardsTogive.put(Suit.CLUBS, players);
        
        players = new int[3];
        j = 0;
        for (int i = 1; i < 5; i++) {
            if(i != id){
                players[j] = i;
                j++;
            }
        }
        cardsTogive.put(Suit.HEARTS, players);
        
        players = new int[3];
        j = 0;
        for (int i = 1; i < 5; i++) {
            if(i != id){
                players[j] = i;
                j++;
            }
        }
        cardsTogive.put(Suit.SPADES, players);
        
        players = new int[3];
        j = 0;
        for (int i = 1; i < 5; i++) {
            if(i != id){
                players[j] = i;
                j++;
            }
        }
        cardsTogive.put(Suit.DIAMONDS, players);
    }

    public HashMap<Suit, int[]> getCardsTogive() {
        return cardsTogive;
    }

    public void setCardsTogive(HashMap<Suit, int[]> cardsTogive) {
        this.cardsTogive = cardsTogive;
    }
    
    public void removePlayerFromCardsToGive(CardPlayed c){
        int[] playersIds = cardsTogive.get(c.getCard().getSuit());
        int[] playersFinal = new int[3];
        int j = 0;
        for(int i = 0; i < playersIds.length; i++){
            if(playersIds[i] != c.getPlayer().getId()){
                playersFinal[j]= playersIds[i];
                j++;
            }
        }
        cardsTogive.put(c.getCard().getSuit(), playersFinal);
    }
    
    
    
    
}
