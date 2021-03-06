package agent;

import common.Move;
import decisionRules.HasAnAceAndTwoMoreCards;
import decisionRules.HasNoAcePlaysNoValueNotTrumpCard;
import decisionRules.PlayTrumpWhenRoundHasTenPointsOrMore;
import java.util.LinkedList;
import java.util.Random;
import model.Card;
import model.CardPlayed;
import model.CardProb;
import model.GameState;
import model.Round;
import observationHeuristics.DrySuit;
import observationHeuristics.FreeCardsFromSuit;
import observationHeuristics.GivePointsRoundLost;

public class Agent {

    private AlphaBeta alfabeta;
    private GameAlgorithm algorithm;
    private HandsSimulator handsSimulator;
    private RandomAlgorithm randomAlgorithm;
    protected AgentCurrentState currentState;
    private Random random;
    private LinkedList<ObservationHeuristic> observationHeuristics;
    private LinkedList<DecisionRule> decisionHeuristics;

    public Agent() {
        handsSimulator = new HandsSimulator();
        random = new Random();
        randomAlgorithm = new RandomAlgorithm();
        observationHeuristics = new LinkedList<>();
        decisionHeuristics = new LinkedList<>();
        DrySuit drySuit = new DrySuit();
        FreeCardsFromSuit freeCardsFromSuit = new FreeCardsFromSuit();
        GivePointsRoundLost givePointsRoundLost = new GivePointsRoundLost();
        observationHeuristics.add(freeCardsFromSuit);
        observationHeuristics.add(drySuit);        
        observationHeuristics.add(givePointsRoundLost);
        
        HasNoAcePlaysNoValueNotTrumpCard noAceHeuristic = new HasNoAcePlaysNoValueNotTrumpCard();
        HasAnAceAndTwoMoreCards aceHeuristic = new HasAnAceAndTwoMoreCards();
        PlayTrumpWhenRoundHasTenPointsOrMore trumpHeuristic = new PlayTrumpWhenRoundHasTenPointsOrMore();
        decisionHeuristics.add(noAceHeuristic);
        decisionHeuristics.add(aceHeuristic);
        decisionHeuristics.add(trumpHeuristic);

    }

    public void setCurrentState(GameState state, LinkedList<Card> cards) {
        currentState = new AgentCurrentState(state, cards);
    }

    public void setCurrentState(AgentCurrentState state) {
        currentState = state;
    }

    public Move play(Round round) {
        if (algorithm == handsSimulator) {
            for (DecisionRule d : decisionHeuristics) {
                Move m = d.analyze(currentState.getAgentCards(), round, currentState.getGame());
                if (m != null) {
                    return m;
                }
            }
        }
        return algorithm.takeDecision(currentState, round);
    }
    
    public void runObservationHeuristics(CardPlayed c, Round round, LinkedList<CardProb> cards, LinkedList<CardPlayed> playedCards){
        if (algorithm == handsSimulator) {
            for (ObservationHeuristic o : observationHeuristics) {
                o.analyze(cards, c, round, playedCards);
            }
        }
    }

    public void removeCardFromHand(Card card) {
        this.currentState.getAgentCards().remove(card);
    }

    public final void useAlfabeta() {
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
     *
     * @param searchDepth The search depth.
     */
    public void setSearchDepth(int searchDepth) {
        algorithm.setSearchDepth(searchDepth);
    }

    /**
     * Sets the number of hands to be simulated by the agent each time it must
     * take a decision using the alpha-beta algorithm.
     *
     * @param handsLimit
     */
    public void setHandsLimit(int handsLimit) {
        algorithm.setHandsLimit(handsLimit);
        //System.out.println("hands " + algorithm.handsLimit);
    }

    /**
     * Sets the number of rounds to be simulated by the agent each time it must
     * take a decision using the alpha-beta algorithm.
     *
     * @param roundsLimit
     */
    public void setSearchRoundLimit(int roundsLimit) {
        algorithm.setNumRounds(roundsLimit);
    }

    public GameAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(GameAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public LinkedList<ObservationHeuristic> getObservationHeuristics() {
        return observationHeuristics;
    }

    public void setObservationHeuristics(LinkedList<ObservationHeuristic> observationHeuristics) {
        this.observationHeuristics = observationHeuristics;
    }

}
