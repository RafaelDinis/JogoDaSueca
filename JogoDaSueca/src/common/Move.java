/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import model.Card;
import model.CardPlayed;
import model.Round;

/**
 *
 * @author Yeezus
 */
public class Move {
    
    private Card card;
    private Round round;
    
    public Move(Card card, Round round) {
        this.card = card;
        this.round = round;
    } 

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }
    
    
}
