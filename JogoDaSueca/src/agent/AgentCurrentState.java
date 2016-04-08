/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import common.Move;
import java.util.LinkedList;
import model.Card;
import model.GameState;
import model.Player;
import model.Round;

/**
 *
 * @author Yeezus
 */
public class AgentCurrentState extends AgentState {

    private LinkedList<Card> teammateCards;
    private LinkedList<Card> opponent1Cards;
    private LinkedList<Card> opponent2Cards;
    private Player currentPlayer;
    private GameState game;

    public AgentCurrentState(GameState game, LinkedList<Card> cards) {
        super(cards);
        this.currentPlayer = game.getActivePlayer();
        this.game = game;
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
        if (possibleMoves.isEmpty()) {
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

    public LinkedList<Move> playerCardsToMoves(Round round) {
        LinkedList<Move> possibleMoves = new LinkedList<>();
        for (Card c : agentCards) {
            possibleMoves.add(new Move(c, round));
        }
        return possibleMoves;
    }

    public Move getHigherCard(LinkedList<Move> possibleMoves) {
        Move move = possibleMoves.get(0);
        for (Move possibleMove : possibleMoves) {
            if (possibleMove.getCard().getWeight() > move.getCard().getWeight()) {
                move = possibleMove;
            }
        }
        return move;
    }

    public AgentSearchState getAgentSearchState() {
        Player p = game.getActivePlayer().clone();
        AgentSearchState a = new AgentSearchState(game.clone(), p);
        return a;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
