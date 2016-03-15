package agent;

import common.Move;
import model.Round;


public class AlphaBeta extends GameAlgorithm {

    @Override
    public Move takeDecision(AgentState currentState, Round round) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //ALFABETA-DECISION(state) returns an action
    //    max = -∞
    //    For each a from ACTIONS(state) do
    //        v = MINVALUE(RESULT(state, a), -∞, +∞, 1)
    //        If (v > max) max = v; action = a
    //    returns action
    
    //MAXVALUE(state, alpha, beta, depth) returns the minimax value of state
    //    If TERMINAL-TEST(state, depth) returns EVALUATION_FUNCTION(state)
    //    For each a from ACTIONS(state) do
    //        alpha = max(alpha, MINVALUE(RESULT(state, a), alpha, beta, depth + 1))
    //        If alpha >= beta then returns alpha
    //    returns alpha
    
    //VALOR-MIN(state, alpha, beta, depth) returns the minimax value of state
    //    If TERMINAL-TEST(state, depth) returns EVALUATION_FUNCTION(state)
    //    For each a from ACTIONS(state) do
    //        beta = min(beta, MAXVALUE(RESULT(state, a), alpha, beta, depth + 1))
    //        If beta <= alpha then returns beta
    //    returns beta
    
    /*
    public Action takeDecision(AgentState currentState) {
        double actionValue, max = Double.NEGATIVE_INFINITY;
        Action nextAction = null;
        for (Action a : currentState.getActions()) {
            actionValue = minValue(currentState.result(a), max, Double.POSITIVE_INFINITY, 1);
            if (nextAction == null || actionValue > max) {
                max = actionValue;
                nextAction = a;
            }            
        }        
        return nextAction;        
    }

    private double maxValue(AgentState state, double alpha, double beta, int depth) {
        if (depth == depthLimit || state.isEndOfGameState()) {
            return state.evaluate();
        }
        
        for (Action a : state.getActions()) {
            alpha = Math.max(alpha, minValue(state.result(a), alpha, beta, depth + 1));
            
            if (alpha >= beta) {
                return alpha;
            }
        }
        return alpha;
    }

    private double minValue(AgentState state, double alpha, double beta, int depth) {
        if (depth == depthLimit || state.isEndOfGameState()) {
            return state.evaluate();
        }
        
        for (Action a : state.getActions()) {
            beta = Math.min(beta, maxValue(state.result(a), alpha, beta, depth + 1));

            if (beta <= alpha) {
                return beta;
            }
        }
        return beta;
    }  */
}
