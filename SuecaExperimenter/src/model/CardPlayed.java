/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Yeezus
 */
public class CardPlayed {
    
    private Card card;
    private Player player;

    public CardPlayed(Card card, Player player) {
        this.card = card;
        this.player = player;
    }
    
    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    protected CardPlayed clone() {
        CardPlayed c = new CardPlayed(card.clone(), player.clone());
        return c; 
    }
    
    
    
    
    
}
