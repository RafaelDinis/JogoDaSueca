/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import agent.Agent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Yeezus
 */
public class Player extends Agent{

    private String name;
    private List<Card> cards;
    private Team team;

    public Player(String name, Team team) {
        this.name = name;
        this.team = team;
        cards = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void receiveCard(Card card) {
        this.cards.add(card);
    }

    public boolean hasCardsFromSuit(Suit suit) {
        for (Card card : cards) {
            if (card.getSuit() == suit) {
                return true;
            }
        }
        return false;
    }

    /*public void removeCardFromHand(Card card) {
        this.cards.remove(card);
    }*/
    
    public String getCardToString(){
        StringBuilder string = new StringBuilder();
        int i = 1;
        for(Card c:cards){
            string.append(" " + i + "-");
            string.append(c.toString());
            i++;
        }
        
        return string.toString();
    }
}
