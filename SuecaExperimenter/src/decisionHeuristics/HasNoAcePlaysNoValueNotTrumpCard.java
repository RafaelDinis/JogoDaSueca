/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionHeuristics;

import agent.DecisionHeuristic;
import common.Move;
import java.util.LinkedList;
import java.util.Random;
import model.Card;
import model.GameState;
import model.PossibleCards;
import model.Round;

/**
 *
 * @author Yeezus
 */
public class HasNoAcePlaysNoValueNotTrumpCard extends DecisionHeuristic {

    @Override
    public Move analyze(LinkedList<Card> agentCards, Round round, GameState g) {
        LinkedList<Card> noValueCards = new LinkedList<>();
        if (round.getCards().isEmpty() && round.getRoundNumber() < 6) {
            for (Card c : agentCards) {
                if (c.getCard() == PossibleCards.ACE) {
                    return null;
                }
                if (c.getValue() == 0 && c.getSuit() != g.getTrump()) {
                    noValueCards.add(c);
                }
            }
            Random r = new Random();
            if (!noValueCards.isEmpty()) {
                return new Move(noValueCards.get(r.nextInt(noValueCards.size())), round);
            }
        }
        return null;
    }
}
