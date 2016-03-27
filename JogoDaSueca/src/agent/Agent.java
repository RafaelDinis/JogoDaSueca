package agent;

import common.Move;
import java.util.LinkedList;
import java.util.Random;
import model.Card;
import model.GameState;
import model.Player;
import model.Round;


public class Agent{

    private AlphaBeta alfabeta;
    private GameAlgorithm algorithm;   
    private HandsSimulator handsSimulator;
    private RandomAlgorithm randomAlgorithm;
    private AgentCurrentState currentState;   
    private Random random;

    public Agent() {      
        handsSimulator = new HandsSimulator();
        random = new Random();
        alfabeta = new AlphaBeta();
        randomAlgorithm = new RandomAlgorithm();
        useAlfabeta();
    }

    public void notifyNewGame(GameState state, LinkedList<Card> agentCards, LinkedList<Card> teammateCards, LinkedList<Card> opponent1Cards, LinkedList<Card> opponent2Cards) {
        currentState = new AgentCurrentState(state, agentCards, teammateCards, opponent1Cards, opponent2Cards);
    }

    public Move play(Round round) {
        return algorithm.takeDecision(currentState, round);
    }
    
    public void removeCardFromHand(Card card) {
        this.currentState.getAgentCards().remove(card);
    }

   /* public void notifyAction(Move move) {
        currentState.placePiece(action.getLine(), action.getColumn());
    }*/

     public final void useAlfabeta() {
        algorithm = alfabeta;
        algorithm.setRandom(random);
    }

    public final void useRandomAlgorithm() {
        algorithm = randomAlgorithm;
        algorithm.setRandom(random);
    }

    
    public AgentCurrentState getCurrentState(){
        return currentState;
    }
    
    
}
