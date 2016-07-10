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
public class AlphaBeta extends GameAlgorithm<AgentSearchState> {

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
        int depth = (numRounds * 4) + 4 - (state.getGame().getRounds().get(state.getGame().getCurrentRound()).getCards().size());     
        setSearchDepth(depth);     
        
        for (AgentSearchState s : successors) {
            if (s.getCurrentPlayer().getTeam().getName().equals(state.getCurrentPlayer().getTeam().getName())) {
                minimaxValues[i++] = maxValue(s, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1);
            } else {
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
            if (s.getCurrentPlayer().getTeam().getName().equals(state.getCurrentPlayer().getTeam().getName())) {
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
            if (s.getCurrentPlayer().getTeam().getName().equals(state.getCurrentPlayer().getTeam().getName())) {
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
    public Move takeDecision(AgentSearchState state, Round round) {
        List<AgentSearchState> successors = (List<AgentSearchState>) state.getSucessors();
        double moveValue, max = Double.NEGATIVE_INFINITY;
        Move nextMove = null;
        for (AgentSearchState s : successors) {
            if (s.getCurrentPlayer().getTeam().getName().equals(state.getCurrentPlayer().getTeam().getName())) {
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
