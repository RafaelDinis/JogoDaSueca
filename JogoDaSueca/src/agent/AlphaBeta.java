package agent;

import common.Move;
import java.util.LinkedList;
import java.util.List;
import model.Round;

/**
 * All methods in this class must take into consideration that, in the Domino
 * game, a player may play two or more times in a row. This happens if the other
 * player still has pieces but is not able to play any of them and is forced to
 * pass its turn.
 */
public class AlphaBeta extends GameAlgorithm<AgentCurrentState> {

    /**
     * Receives the successors of the current fictitious state (where the
     * opponent pieces are "guessed") and returns an array with the minimax
     * values. It is called when the agent doesn't know exactly what pieces its
     * opponent has.
     *
     * @param state
     * @param successors
     * @return
     */
    
    public double[] minimaxValues(AgentSearchState state, LinkedList<AgentSearchState> successors) {
        double[] minimaxValues = new double[successors.size()];
        int i = 0;
        for (AgentSearchState s : successors) {
            //FICA ENCRAVADO AQUI 
            if (s.getCurrentPlayer().getTeam() == state.getCurrentPlayer().getTeam()) {
                minimaxValues[i++] = maxValue(s, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1);
            } else{
                minimaxValues[i++] = minValue(s, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1);
            }
        }
        return minimaxValues;
    }

    private double maxValue(AgentSearchState state, double alpha, double beta, int depth) {
        if (depth == depthLimit) {
            return state.evaluate();
        }
        for (AgentSearchState s : state.getSucessors()) {
            //System.out.println(s.getCurrentPlayer().getTeam() != state.getCurrentPlayer().getTeam());
            if (s.getCurrentPlayer().getTeam() == state.getCurrentPlayer().getTeam()) {
                alpha = Math.max(alpha, maxValue(s, alpha, beta, depth + 1));
            } else {
                alpha = Math.max(alpha, minValue(s, alpha, beta, depth + 1));
            }

            if (alpha >= beta) {
                return alpha;
            }
        }
        return alpha;
    }

    private double minValue(AgentSearchState state, double alpha, double beta, int depth) {
        if (depth == depthLimit) {
            return state.evaluate();
        }
        for (AgentSearchState s : state.getSucessors()) {
            //System.out.println(s.getCurrentPlayer().getTeam() != state.getCurrentPlayer().getTeam());
            if (s.getCurrentPlayer().getTeam() == state.getCurrentPlayer().getTeam()) {
                beta = Math.min(beta, minValue(s, alpha, beta, depth + 1));
            } else {
                beta = Math.min(beta, maxValue(s, alpha, beta, depth + 1));
            }

            if (alpha >= beta) {
                return beta;
            }
        }
        return beta;
    }

    @Override
    public Move takeDecision(AgentCurrentState currentState, Round round) {
        //System.out.println("PLAYER CARDS --> " + currentState.getCurrentPlayer().getCards().size());
        //currentState.getAgentSearchState();

        int depth = (1 * 4) + 4 - (currentState.getAgentSearchState().getGame().getRounds().get(currentState.getAgentSearchState().getGame().getCurrentRound()).getCards().size());
        //System.out.println("DEPHT --> " + depth);
        setSearchDepth(depth);

        List<AgentSearchState> successors = (List<AgentSearchState>) currentState.getAgentSearchState().getSucessors();
        double moveValue, max = Double.NEGATIVE_INFINITY;
        Move nextMove = null;
        for (AgentSearchState s : successors) {
            if (s.getCurrentPlayer().getTeam() == currentState.getCurrentPlayer().getTeam()) {
                moveValue = maxValue(s, max, Double.POSITIVE_INFINITY, 1);
            } else {
                moveValue = minValue(s, max, Double.POSITIVE_INFINITY, 1);
            }
            if (nextMove == null || moveValue > max) {
                max = moveValue;
                nextMove = s.getMove();
            }
        }
        System.out.println("FINAL MOVE ->" + nextMove.getCard().toString());
        return nextMove;
    }

}
