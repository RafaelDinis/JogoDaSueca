/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import common.Move;
import java.util.LinkedList;
import java.util.Random;
import model.Round;

/**
 *
 * @author Yeezus
 */
public class RandomAlgorithm extends GameAlgorithm<AgentCurrentState> {

    /**
     * Chooses randomly from the moves that can be played in the current state.
     * @param currentState
     * @param round
     * @return 
     */    
    @Override
    public Move takeDecision(AgentCurrentState currentState, Round round) {
        LinkedList<Move> possibleMoves = currentState.getAgentPossibleMoves(round);
        Random r = new Random();        
        return possibleMoves.get(r.nextInt(possibleMoves.size()));
    }
    
    
    
}
