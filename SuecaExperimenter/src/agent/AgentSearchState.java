package agent;

import common.Move;
import java.util.LinkedList;
import model.Card;
import model.GameState;
import model.Player;

public class AgentSearchState extends AgentState {

    private Move move;
    private Player currentPlayer;
    private GameState game;
    
    private LinkedList<Card> opponent1Cards;
    
    private LinkedList<Card> opponent2Cards;
    
    private LinkedList<Card> teammateCards;

    public AgentSearchState(GameState game, Player current) {
        super(current.getCards());
        this.game = game;
        this.currentPlayer = current;
    }

    public Move getMove() {
        return move;
    }

    public LinkedList<AgentSearchState> getSucessors() {
        LinkedList<AgentSearchState> sucessors = new LinkedList<>();
        LinkedList<Card> cards = (LinkedList<Card>) currentPlayer.getCards().clone();
        for (Card c : cards) {
            if (validateCard(c, game.getRounds().get(game.getCurrentRound()).getRoundSuit(), currentPlayer)) {
                GameState g = game.clone();
                g.playCardSimulation(c);
                AgentSearchState sucessor = new AgentSearchState(g, g.getActivePlayer());
                sucessor.move = new Move(c, g.getRounds().get(g.getCurrentRound()));
                sucessors.add(sucessor);
            }
        }
        return sucessors;
    }

    public double evaluate() {
        if (game.getTeam1().belongsToTeam(currentPlayer)) {

            if (currentPlayer.getTeam().getFinalScore() > 60) {
                return 1000;
            } else if (currentPlayer.getTeam().getFinalScore() > game.getTeam2().getFinalScore()) {
                return 100;
            } else if (game.getTeam2().getFinalScore() > 60) {
                return -1000;
            } else if (currentPlayer.getTeam().getFinalScore() < game.getTeam2().getFinalScore()) {
                return -100;
            }
        }
        if (game.getTeam2().belongsToTeam(currentPlayer)) {

            if (currentPlayer.getTeam().getFinalScore() > 60) {
                return 1000;
            } else if (currentPlayer.getTeam().getFinalScore() > game.getTeam1().getFinalScore()) {
                return 100;
            } else if (game.getTeam1().getFinalScore() > 60) {
                return -1000;
            } else if (currentPlayer.getTeam().getFinalScore() < game.getTeam1().getFinalScore()) {
                return -100;
            }
        }
        return 0;
    }
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameState getGame() {
        return game;
    }

    public void setGame(GameState game) {
        this.game = game;
    }
    
     public LinkedList<Card> getOpponent1Cards() {
        return opponent1Cards;
    }

    public void setOpponent1Cards(LinkedList<Card> opponent1Cards) {
        this.opponent1Cards = opponent1Cards;
    }

    public LinkedList<Card> getOpponent2Cards() {
        return opponent2Cards;
    }

    public void setOpponent2Cards(LinkedList<Card> opponent2Cards) {
        this.opponent2Cards = opponent2Cards;
    }

    public LinkedList<Card> getTeammateCards() {
        return teammateCards;
    }

    public void setTeammateCards(LinkedList<Card> teammateCards) {
        this.teammateCards = teammateCards;
    }
    

}
