/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import common.Move;
import java.util.LinkedList;
import java.util.List;
import model.Card;
import model.CardPlayed;
import model.Round;

/**
 *
 * @author Yeezus
 */
public class AgentCurrentState extends AgentState {

    public AgentCurrentState(LinkedList<Card> agentCards) {
        super(agentCards);
    }

    public LinkedList<Move> getAgentPossibleMoves(Round round) {
        LinkedList<Move> possibleMoves = new LinkedList<>();
        for (Card c : agentCards) {           
                if (round.getCards().isEmpty()) {
                    return playerCardsToMoves(round);
                }
                if (c.getSuit() == round.getRoundSuit()) {
                    possibleMoves.add(new Move(c, round));
                }
        }
        if(possibleMoves.isEmpty()){
            return playerCardsToMoves(round);
        }
        return possibleMoves;
    }

    public LinkedList<Card> getAgentCards() {
        return agentCards;
    }

    public void setAgentCards(LinkedList<Card> agentCards) {
        this.agentCards = agentCards;
    }
    

}
