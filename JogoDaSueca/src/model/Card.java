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
    private PossibleCards card;
    private int value;
    private int weight;

    public Card() {

    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
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

    @Override
    protected Card clone() {
        Card c = new Card();
        c.setCard(card);
        c.setSuit(suit);
        c.setValue(value);
        c.setWeight(weight);
        return c; 
    }
    
    

}
