package agent;

import common.Move;
import java.util.LinkedList;
import java.util.Random;
import model.Card;
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
        useRandomAlgorithm();
    }

    public void notifyNewGame(LinkedList<Card> agentCards) {
        currentState = new AgentCurrentState(agentCards);
    }

    public Move play(Round round) {
        return algorithm.takeDecision(currentState, round);
    }
    
    public void removeCardFromHand(Card card) {
        Boolean sim = this.currentState.getAgentCards().remove(card);
        if(sim){
            System.out.println("REMOVEU");
        } else{
            System.out.println("NAO ENCONTROU");
        }
    }

   /* public void notifyAction(Move move) {
        currentState.placePiece(action.getLine(), action.getColumn());
    }*/

     public final void useAlfabeta() {
        algorithm = handsSimulator;
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
