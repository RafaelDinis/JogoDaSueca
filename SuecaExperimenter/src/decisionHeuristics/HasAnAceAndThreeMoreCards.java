/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionHeuristics;

import agent.DecisionHeuristic;
import common.Move;
import java.util.LinkedList;
import model.Card;
import model.CardPlayed;
import model.GameState;
import model.PossibleCards;
import model.Round;

/**
 *
 * @author Yeezus
 */
public class HasAnAceAndThreeMoreCards extends DecisionHeuristic{

    @Override
    public Move analyze(LinkedList<Card> agentCards, Round round, GameState g) {
        int i = 0;
        for (Card c : agentCards) {
            if (round.getCards().isEmpty()) {
                if (c.getCard() == PossibleCards.ACE && c.getSuit() != g.getTrump()) {
                    i = getCardSuitCount(c, agentCards);
                    if (i < 5) {
                        return new Move(c, round);
                    }
                }
            } else if (c.getCard() == PossibleCards.ACE && c.getSuit() == round.getRoundSuit() && !checkTrumpWasPlayed(round, g) && round.getRoundNumber() < 5) {
                return new Move(c, round);
            }
        }
        return null;
    }

    private boolean checkTrumpWasPlayed(Round round, GameState g) {
        for (CardPlayed c : round.getCards()) {
            if (c.getCard().getSuit() == g.getTrump()) {
                return true;
            }
        }
        return false;
    }

    private int getCardSuitCount(Card c, LinkedList<Card> agentCards) {
        int i = 0;
        for (Card c1 : agentCards) {
            if (c1.getSuit() == c.getSuit()) {
                i++;
            }
        }
        return i;
    }
}
