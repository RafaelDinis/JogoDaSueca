package agent;

import common.Move;
import java.util.LinkedList;
import model.Card;
import model.GameState;
import model.Player;

public class AgentSearchState extends AgentState {

    private Move move = null;
    private Player currentPlayer;
    private LinkedList<Card> opponent1Cards;
    private LinkedList<Card> opponent2Cards;
    private LinkedList<Card> teammateCards;
    private GameState game;

    public AgentSearchState(GameState game, Player currentPlayer, LinkedList<Card> opponent1Cards, LinkedList<Card> opponent2Cards, LinkedList<Card> teammateCards, LinkedList<Card> agentCards) {
        super(agentCards);
        this.currentPlayer = currentPlayer;
        this.opponent1Cards = opponent1Cards;
        this.opponent2Cards = opponent2Cards;
        this.teammateCards = teammateCards;
        this.game = game;
    }

    public Move getMove() {
        return move;
    }

    public LinkedList<AgentSearchState> getSucessors() {
        LinkedList<AgentSearchState> sucessors = new LinkedList<>();
        LinkedList<Card> cards = (LinkedList<Card>) agentCards.clone();
        for (Card c : cards) {
            if (super.validateCard(c, game.getRounds().get(game.getCurrentRound()).getRoundSuit(), currentPlayer)) {
                AgentSearchState sucessor = (AgentSearchState) clone();
                currentPlayer.removeCardFromHand(c);
                sucessor.move = new Move(c, game.getRounds().get(game.getCurrentRound()));
                sucessors.add(sucessor);
            }
        }
        return sucessors;
    }

    public double evaluate() {
        if (getWinner()) {
            return 10000;
        } else {
            return -10000;
        }
    }

    private Boolean getWinner() {
        double agentTeamScore = 0;
        double opponentTeamScore = 0;
        for (Card card : opponent1Cards) {
            opponentTeamScore += card.getValue();
        }

        for (Card card : opponent2Cards) {
            opponentTeamScore += card.getValue();
        }

        for (Card card : agentCards) {
            agentTeamScore += card.getValue();
        }

        for (Card card : teammateCards) {
            agentTeamScore += card.getValue();
        }

        if (agentTeamScore > opponentTeamScore) {
            return true;
        } else if (agentTeamScore < opponentTeamScore) {
            return false;
        }
        return false;
    }

    @Override
    protected AgentSearchState clone() {
        return new AgentSearchState(game, currentPlayer, opponent1Cards, opponent2Cards, teammateCards, agentCards);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

}
