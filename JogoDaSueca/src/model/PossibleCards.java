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
public enum PossibleCards {
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, QUEEN, JACK, KING, ACE;
    
    public static PossibleCards generateCardFromInt(int i){
        switch(i){
            case 0:
                return PossibleCards.TWO;
            case 1:
                return PossibleCards.THREE;
            case 2:
                return PossibleCards.FOUR;
            case 3:
                return PossibleCards.FIVE;
            case 4:
                return PossibleCards.SIX;
            case 5:
                return PossibleCards.SEVEN;
            case 6:
                return PossibleCards.QUEEN;
            case 7:
                return PossibleCards.JACK;
            case 8:
                return PossibleCards.KING;
            case 9:
                return PossibleCards.ACE;
            default: 
                return null;
        }
    }
}
