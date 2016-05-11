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
        /*algorithm = alfabeta;
        algorithm.setRandom(random);*/
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

}
