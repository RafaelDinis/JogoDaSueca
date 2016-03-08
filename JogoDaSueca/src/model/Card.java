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
public class Card {

    private Suit suit;
    private Player player;
    private PossibleCards card;
    private int value;

    public Card() {

    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PossibleCards getCard() {
        return card;
    }

    public void setCard(PossibleCards card) {
        this.card = card;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void giveValue(PossibleCards card) {
        switch (card) {
            case ACE:
                this.value = 11;
                break;
            case SEVEN:
                this.value = 10;
                break;
            case KING:
                this.value = 4;
                break;
            case JACK:
                this.value = 3;
                break;
            case QUEEN:
                this.value = 2;
                break;
            default:
                this.value = 0;
                break;
        }
    }

    @Override
    public String toString() {
        return suit.toString() + " " + card.toString();
    }

}
