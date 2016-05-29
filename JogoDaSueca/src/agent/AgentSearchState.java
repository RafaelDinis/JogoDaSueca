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
                //System.out.println("sucessor for round " + game.getCurrentRound() + " card played -> " + c.toString());
                //if(!(game.getCurrentRound() == 0 && c.getSuit().equals(game.getTrump()))){
                    GameState g = game.clone();
                    //System.out.println(g.getRounds().equals(game.getRounds()));
                    //System.out.println(g.getRounds().toString());
                    //System.out.println(game.getRounds().toString());
                    g.playCardSimulation(c);
                    AgentSearchState sucessor = new AgentSearchState(g, g.getActivePlayer());
                    // currentPlayer.getCards().remove(c);
                    sucessor.move = new Move(c, g.getRounds().get(g.getCurrentRound()));
                    sucessors.add(sucessor);
                //}
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
        //double agentTeamScore = 0;
        //double opponentTeamScore = 0;                
        if(game.getTeam1().belongsToTeam(currentPlayer)){
            return currentPlayer.getTeam().getFinalScore() > game.getTeam2().getFinalScore();
        }
        
        if(game.getTeam2().belongsToTeam(currentPlayer)){
            return currentPlayer.getTeam().getFinalScore() > game.getTeam1().getFinalScore();
        }
        return true;
    }

    /*
    @Override
    protected AgentSearchState clone() {
        return new AgentSearchState(game, currentPlayer, opponent1Cards, opponent2Cards, teammateCards, agentCards);
    }
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameState getGame() {
        return game;
    }

    public void setGame(GameState game) {
        this.game = game;
    }

}
