/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionRules;

import agent.DecisionRule;
import common.Move;
import java.util.LinkedList;
import java.util.Random;
import model.Card;
import model.CardPlayed;
import model.GameState;
import model.Round;

/**
 *
 * @author Yeezus
 */
public class PlayTrumpWhenRoundHasTenPointsOrMore extends DecisionRule {

    @Override
    public Move analyze(LinkedList<Card> agentCards, Round round, GameState g) {
        LinkedList<Card> agentTrumpCards = getAgentTrumpCards(agentCards, g);
        if (round.getRoundScore() >= 10 && !playerHasRoundSuitCards(round, agentCards) && round.getRoundSuit() != g.getTrump()) {
            if (getTrumpCardsPlayedByOpponents(round, g).isEmpty() && !checkIfHigherCardIsntFromTeamMate(round, g)) {
                Random r = new Random();
                if (agentTrumpCards.size() > 0) {
                    return new Move(agentTrumpCards.get(r.nextInt(agentTrumpCards.size())), round);
                }
            } else {
                Card c = checkIfAgentHasHigherTrumpCard(round, g, getTrumpCardsPlayedByOpponents(round, g), agentTrumpCards);
                if (c != null) {
                    return new Move(c, round);
                }
            }

        }
        return null;
    }

    private boolean playerHasRoundSuitCards(Round r, LinkedList<Card> agentCards) {
        for (Card c : agentCards) {
            if (c.getSuit() == r.getRoundSuit()) {
                return true;
            }
        }
        return false;
    }

    private LinkedList<Card> getAgentTrumpCards(LinkedList<Card> agentCards, GameState g) {
        LinkedList<Card> cards = new LinkedList<>();
        for (Card c : agentCards) {
            if (c.getSuit() == g.getTrump()) {
                cards.add(c);
            }
        }
        return cards;
    }

    private LinkedList<CardPlayed> getTrumpCardsPlayedByOpponents(Round round, GameState g) {
        LinkedList<CardPlayed> l = new LinkedList<>();
        int i = 0;
        for (CardPlayed c : round.getCards()) {
            i++;
            if (c.getCard().getSuit() == g.getTrump() && (g.getActivePlayerNumber() == i + 1 || g.getActivePlayerNumber() == i + 3)) {
                l.add(c);
            }
        }
        return l;
    }

    private Card checkIfAgentHasHigherTrumpCard(Round round, GameState g, LinkedList<CardPlayed> trumpCardsPlayed, LinkedList<Card> agentTrumpCards) {
        for (Card c : agentTrumpCards) {
            for (CardPlayed p : trumpCardsPlayed) {
                if (c.getWeight() > p.getCard().getWeight()) {
                    return c;
                }
            }
        }
        return null;
    }

    private boolean checkIfHigherCardIsntFromTeamMate(Round round, GameState g) {
        Card maxCard = new Card();
        maxCard.setWeight(0);
        int i = 0;
        for (CardPlayed c : round.getCards()) {
            if (maxCard.getWeight() < c.getCard().getWeight()) {
                maxCard = c.getCard();
            }
        }
        return getMaxCardIndex(round.getCards(), maxCard) + 2 == g.getActivePlayerNumber();
    }

    private int getMaxCardIndex(LinkedList<CardPlayed> cards, Card maxCard) {
        int i = 0;
        for (CardPlayed c : cards) {
            if (maxCard.equals(c.getCard())) {
                return i;
            }
            i++;
        }
        return 0;
    }
}
