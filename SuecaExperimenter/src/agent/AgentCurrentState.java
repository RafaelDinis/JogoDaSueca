/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import common.Move;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;
import model.Card;
import model.CardPlayed;
import model.CardProb;
import model.GameState;
import model.Player;
import model.Round;
import model.Suit;

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
     *
     *
     *
     * @return
     */
    public LinkedList<AgentSearchState> buildGuessedCurrentStates(int handsLimit) {
        LinkedList<AgentSearchState> guessedCurrentStates = new LinkedList<>();
        LinkedList<Card> possibleCards = GameState.getAllCards();
        Random random = new Random();

        if (!game.getPlayedCards().isEmpty()) {
            LinkedList<Card> list = (LinkedList<Card>) possibleCards.clone();
            for (CardPlayed c : game.getPlayedCards()) {
                for (Card p : list) {
                    if (c.getCard().getCard().equals(p.getCard()) && c.getCard().getSuit().equals(p.getSuit())) {
                        possibleCards.remove(p);
                    }
                }
            }
        }
        LinkedList<Card> list = (LinkedList<Card>) possibleCards.clone();
        for (Card c : agentCards) {
            for (Card p : list) {
                if (c.getCard().equals(p.getCard()) && c.getSuit().equals(p.getSuit())) {
                    possibleCards.remove(p);
                }
            }
        }

        LinkedList<Card> guessedOpponent1Cards = new LinkedList<>();
        LinkedList<Card> guessedOpponent2Cards = new LinkedList<>();
        LinkedList<Card> guessedTeammateCards = new LinkedList<>();

        for (int i = 0; i < handsLimit; i++) {
            guessedOpponent1Cards.clear();
            guessedTeammateCards.clear();
            guessedOpponent2Cards.clear();

            for (Card card : possibleCards) {
                if (card.getSuit().equals(currentPlayer.getGameHistory().getTrumpCard().getSuit()) && card.getCard().equals(currentPlayer.getGameHistory().getTrumpCard().getCard())) {
                    if (currentPlayer.getId() == 3) {
                        guessedTeammateCards.add(card);
                    } else if (nextNumber(currentPlayer.getId()) == 3) {
                        guessedOpponent2Cards.add(card);
                    } else {
                        guessedOpponent1Cards.add(card);
                    }
                } else {

                    CardProb c = currentPlayer.getGameHistory().getCard(card);
                    HashMap<Integer, Double> cardToPlayerProb = c.getProbabilities();
                    //System.out.println("probs " + cardToPlayerProb.toString());

                    Random generator = new Random();
                    double number = generator.nextDouble() * 1.0;
                    double sum = 0;
                    //int x = nextNumber(currentPlayer.getGameHistory().getCard(card).getPlayer());
                    int x = 0;
                    //System.out.println("cenas" + cardToPlayerProb);
                    int[] playersIds = getIds(cardToPlayerProb);
                    int idPlayerFinal = 0;
                    //System.out.println("cenas" + cardToPlayerProb);
                    try {
                        while (number > sum || x < playersIds.length) {
                            int id = playersIds[x];
                            sum = sum + cardToPlayerProb.get(id);
                            x++;
                        }
                    } catch (Exception ArrayIndexOutOfBoundsException) {
                        System.out.println("x " + x);
                        System.out.println("playerIds " + playersIds.length);
                        System.out.println("sum " + sum);
                        System.out.println("number " + number);
                    }

                    if ((currentPlayer.getId() % 2) == (idPlayerFinal % 2)) {
                        guessedTeammateCards.add(card);
                    } else if (nextNumber(currentPlayer.getId()) == idPlayerFinal) {
                        guessedOpponent1Cards.add(card);
                    } else {
                        guessedOpponent2Cards.add(card);
                    }
                }
            }
            guessedCurrentStates.add(getAgentSearchState(guessedOpponent1Cards, guessedOpponent2Cards, guessedTeammateCards));
        }
        return guessedCurrentStates;
    }

    private int nextNumber(int current) {
        if (current < 4) {
            current++;
        } else {
            current = 1;
        }
        return current;
    }

    private int[] getIds(HashMap<Integer, Double> probabilities) {
        int[] ids = new int[probabilities.size()];
        int i = 0;
        for (int key : probabilities.keySet()) {
            ids[i] = key;
            i++;
        }
        return ids;
    }

    public GameState getGame() {
        return game;
    }

}
