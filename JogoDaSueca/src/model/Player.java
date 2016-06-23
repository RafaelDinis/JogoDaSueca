/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import agent.Agent;
import agent.AgentCurrentState;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Yeezus
 */
public class Player extends Agent {

    private int id;
    private String name;
    private LinkedList<Card> cards;
    private Team team;
    private GameHistory gameHistory;

    public Player(int id, String name, Team team) {
        this.id = id;
        this.name = name;
        this.team = team;
        cards = new LinkedList<>();
        this.gameHistory = new GameHistory(id);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Card> getCards() {
        return cards;
    }

    public void setCards(LinkedList<Card> cards) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GameHistory getGameHistory() {
        return gameHistory;
    }

    public void setGameHistory(GameHistory gameHistory) {
        this.gameHistory = gameHistory;
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
    public String getCardToString() {
        StringBuilder string = new StringBuilder();
        int i = 1;
        for (Card c : cards) {
            string.append(" " + i + "-");
            string.append(c.toString());
            i++;
        }

        return string.toString();
    }

    @Override
    public Player clone(){
        Player p = new Player(id, name, team);
        p.setCards((LinkedList<Card>) cards.clone());
        p.setGameHistory(gameHistory.clone());
        //p.setCurrentState(currentState.clone());
        return p;
    }
    
    


}
