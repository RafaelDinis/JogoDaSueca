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

    private HashMap<Suit, int[]> cardsToGive;
    private Card trumpCard;

    public GameHistory(int id) {
        cardsToGive = new HashMap<>();
        initializeCardsToGive(id);
    }

    public GameHistory() {

    }

    private void initializeCardsToGive(int id) {
        int[] players = new int[3];
        int j = 0;
        for (int i = 1; i < 5; i++) {
            if (i != id) {
                players[j] = i;
                j++;
            }
        }
        cardsToGive.put(Suit.CLUBS, players);

        players = new int[3];
        j = 0;
        for (int i = 1; i < 5; i++) {
            if (i != id) {
                players[j] = i;
                j++;
            }
        }
        cardsToGive.put(Suit.HEARTS, players);

        players = new int[3];
        j = 0;
        for (int i = 1; i < 5; i++) {
            if (i != id) {
                players[j] = i;
                j++;
            }
        }
        cardsToGive.put(Suit.SPADES, players);

        players = new int[3];
        j = 0;
        for (int i = 1; i < 5; i++) {
            if (i != id) {
                players[j] = i;
                j++;
            }
        }
        cardsToGive.put(Suit.DIAMONDS, players);
    }

    public HashMap<Suit, int[]> getCardsTogive() {
        return cardsToGive;
    }

    public void setCardsTogive(HashMap<Suit, int[]> cardsTogive) {
        this.cardsToGive = cardsTogive;
    }

    public void removePlayerFromCardsToGive(CardPlayed c, Suit s) {
        int[] playersIds = cardsToGive.get(s);
        int[] playersFinal = new int[3];
        int j = 0;
        for (int i = 0; i < playersIds.length; i++) {
            if (playersIds[i] != c.getPlayer().getId()) {
                playersFinal[j] = playersIds[i];
                j++;
            }
        }
        if (j < 4) {
            int[] playersFinal2 = new int[j];
            for (int i = 0; i < j; i++) {
                playersFinal2[i] = playersFinal[i];
            }
            cardsToGive.put(s, playersFinal2);
        } else {
            cardsToGive.put(s, playersFinal);
        }
    }

    public Card getTrumpCard() {
        return trumpCard;
    }

    public void setTrumpCard(Card trumpCard) {
        this.trumpCard = trumpCard;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append(Suit.CLUBS);
        for (int i = 0; i < cardsToGive.get(Suit.CLUBS).length; i++) {
            string.append(" " + cardsToGive.get(Suit.CLUBS)[i] + " ");
        }
        string.append("\n");

        string.append(Suit.DIAMONDS);
        for (int i = 0; i < cardsToGive.get(Suit.DIAMONDS).length; i++) {
            string.append(" " + cardsToGive.get(Suit.DIAMONDS)[i] + " ");
        }
        string.append("\n");

        string.append(Suit.HEARTS);
        for (int i = 0; i < cardsToGive.get(Suit.HEARTS).length; i++) {
            string.append(" " + cardsToGive.get(Suit.HEARTS)[i] + " ");
        }
        string.append("\n");

        string.append(Suit.SPADES);
        for (int i = 0; i < cardsToGive.get(Suit.SPADES).length; i++) {
            string.append(" " + cardsToGive.get(Suit.SPADES)[i] + " ");
        }
        string.append("\n");
        string.append(trumpCard.toString() + "\n");
        return string.toString();
    }

    private HashMap<Suit, int[]> copyHashMap() {
        HashMap<Suit, int[]> clone = new HashMap<>();
        int[] playersIds = null;
        int[] playersFinal = null;
        int j = 0;

        playersIds = cardsToGive.get(Suit.CLUBS);
        playersFinal = new int[playersIds.length];
        j = 0;
        for (int i = 0; i < playersIds.length; i++) {
            playersFinal[j] = playersIds[i];
            j++;
        }
        clone.put(Suit.CLUBS, playersFinal);
        
        playersIds = cardsToGive.get(Suit.DIAMONDS);
        playersFinal = new int[playersIds.length];
        j = 0;
        for (int i = 0; i < playersIds.length; i++) {
            playersFinal[j] = playersIds[i];
            j++;
        }
        clone.put(Suit.DIAMONDS, playersFinal);
        
        playersIds = cardsToGive.get(Suit.HEARTS);
        playersFinal = new int[playersIds.length];
        j = 0;
        for (int i = 0; i < playersIds.length; i++) {
            playersFinal[j] = playersIds[i];
            j++;
        }
        clone.put(Suit.HEARTS, playersFinal);
        
        playersIds = cardsToGive.get(Suit.SPADES);
        playersFinal = new int[playersIds.length];
        j = 0;
        for (int i = 0; i < playersIds.length; i++) {
            playersFinal[j] = playersIds[i];
            j++;
        }
        clone.put(Suit.SPADES, playersFinal);

        return clone;
    }

    @Override
    protected GameHistory clone(){
        GameHistory clone = new GameHistory();
        clone.setCardsTogive(copyHashMap());
        clone.setTrumpCard(trumpCard.clone());
        return clone;
    }

}
