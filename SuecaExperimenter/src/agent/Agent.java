package agent;

import common.Move;
import java.util.LinkedList;
import java.util.Random;
import model.Card;
import model.GameState;
import model.Round;

public class Agent {

    private AlphaBeta alfabeta;
    private GameAlgorithm algorithm;
    private HandsSimulator handsSimulator;
    private RandomAlgorithm randomAlgorithm;
    protected AgentCurrentState currentState;
    private Random random;

    public Agent() {
        handsSimulator = new HandsSimulator();
        random = new Random();
        alfabeta = new AlphaBeta();
        randomAlgorithm = new RandomAlgorithm();
    }

    public void setCurrentState(GameState state, LinkedList<Card> cards) {
        currentState = new AgentCurrentState(state, cards);
    }

    public void setCurrentState(AgentCurrentState state) {
        currentState = state;
    }

    public Move play(Round round) {
        return algorithm.takeDecision(currentState, round);
    }

    public void removeCardFromHand(Card card) {
        this.currentState.getAgentCards().remove(card);

        /*LinkedList<Card> cards = (LinkedList) currentState.getAgentCards().clone();
        for(Card c : cards){
            if(c.getCard() == card.getCard() && c.getSuit() == card.getSuit()){
                currentState.getAgentCards().remove(c);
            }
        }*/
    }

    /* public void notifyAction(Move move) {
        currentState.placePiece(action.getLine(), action.getColumn());
    }*/
    public final void useAlfabeta() {
        //algorithm = alfabeta;
        algorithm = handsSimulator;
        algorithm.setRandom(random);
    }

    public final void useRandomAlgorithm() {
        algorithm = randomAlgorithm;
        algorithm.setRandom(random);
    }

    public AgentCurrentState getCurrentState() {
        return currentState;
    }
    
    /**
     * Sets the depth search when the agent uses the alpha-beta algorithm.
     * @param searchDepth The search depth.
     */    
    public void setSearchDepth(int searchDepth) {
        algorithm.setSearchDepth(searchDepth);
    }

    /**
     * Sets the number of hands to be simulated by the agent each time it must
     * take a decision using the alpha-beta algorithm.
     * @param handsLimit 
     */    
    public void setHandsLimit(int handsLimit) {
        algorithm.setHandsLimit(handsLimit);
    }
    
    /**
     * Sets the number of hands to be simulated by the agent each time it must
     * take a decision using the alpha-beta algorithm.
     * @param handsLimit 
     */    
    public void setSearchRoundLimit(int roundsLimit) {
        algorithm.setNumRounds(roundsLimit);
    }

}
