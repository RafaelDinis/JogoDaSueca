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
     * @param successors
     * @return
     */
    /*public double[] minimaxValues(LinkedList<AgentSearchState> successors) {
        double[] minimaxValues = new double[successors.size()];
        int i = 0;
        for (AgentSearchState s : successors) {
            if (s.getCurrentPlayer() == ) {
                minimaxValues[i++] = maxValue(s, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1);
            } else if (s.getCurrentPlayer() == Player.OPPONENT) {
                minimaxValues[i++] = minValue(s, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1);
            }
        }
        return minimaxValues;
    }*/

    private double maxValue(AgentSearchState state, double alpha, double beta, int depth) {
        if (depth == depthLimit) {
            return state.evaluate();
        }
        for (AgentSearchState s : state.getSucessors()) {
            alpha = Math.max(alpha, maxValue(s, alpha, beta, depth + 1));

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
            beta = Math.min(beta, minValue(s, alpha, beta, depth + 1));

            if (beta <= alpha) {
                return beta;
            }
        }
        return beta;
    }

    @Override
    public Move takeDecision(AgentCurrentState currentState, Round round) {
        currentState.getAgentSearchState();
        List<AgentSearchState> successors = (List<AgentSearchState>) currentState.getAgentSearchState().getSucessors().clone();
        double moveValue, max = Double.NEGATIVE_INFINITY;
        Move nextMove = null;
        for (AgentSearchState s : successors) {
            if (s.getCurrentPlayer() == currentState.getCurrentPlayer()) {
                moveValue = maxValue(s, max, Double.POSITIVE_INFINITY, 1);
            } else {
                moveValue = minValue(s, max, Double.POSITIVE_INFINITY, 1);
            }
            if (nextMove == null || moveValue > max) {
                max = moveValue;
                nextMove = s.getMove();
            }
        }
        return nextMove;
    }

}
