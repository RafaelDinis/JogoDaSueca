/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import common.Move;
import java.util.LinkedList;
import java.util.Random;
import model.Card;
import model.CardPlayed;
import model.GameState;
import model.Player;
import model.Round;

/**
 *
 * @author Yeezus
 */
public class AgentCurrentState extends AgentState {

    private Player currentPlayer;
    private GameState game;

    public AgentCurrentState(GameState game, LinkedList<Card> cards) {
        super(cards);
        this.currentPlayer = game.getActivePlayer().clone();
        this.game = game.clone();
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
        GameState g = game.clone();
        AgentSearchState a = new AgentSearchState(g, p);
        return a;
    }

    public AgentSearchState getAgentSearchState(LinkedList<Card> opponent1Cards, LinkedList<Card> opponent2Cards, LinkedList<Card> teammateCards) {
        Player p = game.getActivePlayer().clone();
        GameState g = game.clone();
        AgentSearchState a = new AgentSearchState(g, p);
        a.setOpponent1Cards(opponent1Cards);
        a.setOpponent2Cards(opponent2Cards);
        a.setTeammateCards(teammateCards);
        return a;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public AgentCurrentState clone() {
        return new AgentCurrentState(game.clone(), (LinkedList<Card>) agentCards.clone());
    }

    /**
     * SIZE DAS LISTAS DE CARTAS ADIVINHADAS TÁ MAL
     *
     *
     * @return 
     */
    public LinkedList<AgentSearchState> buildGuessedCurrentStates() {
        LinkedList<AgentSearchState> guessedCurrentStates = new LinkedList<>();
        int numPossibleOpponentHands = 4;
        LinkedList<Card> possibleCards = GameState.getAllCards();
        Random random = new Random();

        if (!game.getPlayedCards().isEmpty()) {
            for (CardPlayed c : game.getPlayedCards()) {
                possibleCards.remove(c.getCard());
            }
        }

        for (Card c : agentCards) {
            possibleCards.remove(c);
        }

        LinkedList<Card> guessedOpponent1Cards = new LinkedList<>();
        LinkedList<Card> guessedOpponent2Cards = new LinkedList<>();
        LinkedList<Card> guessedTeammateCards = new LinkedList<>();

        for (int i = 0; i < numPossibleOpponentHands; i++) {
            for (int j = 0; j < game.getNumberOfCardsOfPlayer(game.getOpponentTeam(currentPlayer).getFirst()); j++) {
                guessedOpponent1Cards.add(possibleCards.get(random.nextInt(possibleCards.size())));
            }

            for (int j = 0; j < game.getNumberOfCardsOfPlayer(game.getOpponentTeam(currentPlayer).getLast()); j++) {
                Card c = possibleCards.get(random.nextInt(possibleCards.size()));
                while (guessedOpponent1Cards.contains(c)) {
                    c = possibleCards.get(random.nextInt(possibleCards.size()));
                }
                guessedOpponent2Cards.add(c);
            }
            for (int j = 0; j < game.getNumberOfCardsOfPlayer(game.getTeammate(currentPlayer)); j++) {
                Card c = possibleCards.get(random.nextInt(possibleCards.size()));
                while (guessedOpponent1Cards.contains(c) || guessedOpponent2Cards.contains(c)) {
                    c = possibleCards.get(random.nextInt(possibleCards.size()));
                }
                guessedTeammateCards.add(c);
            }
            guessedCurrentStates.add(getAgentSearchState(guessedOpponent1Cards, guessedOpponent2Cards, guessedTeammateCards));
        }

        return guessedCurrentStates;
    }

}
