package agent;

import common.Move;
import java.util.LinkedList;
import model.Round;

/**
 * This class represents a meta search algorithm that simulates the fictitious
 * hands. It is a meta-algorithm because it uses another algorithm (alpha-beta)
 * to work.
 */
public class HandsSimulator extends GameAlgorithm<AgentCurrentState> {

    private AlphaBeta alphaBeta;

    public HandsSimulator() {
        alphaBeta = new AlphaBeta();
    }

    /**
     * This method has two modes of working: 1) If the agent doesn't know
     * exactly what are the opponent pieces: In this case, the method creates
     * *handsLimit* current fictitious states (in each of these states, the
     * pieces of the opponent are "guessed"). For each of these states its list
     * of successors is generated and the minimaxValues method of the alpha-beta
     * algorithm is called (which receives the successors list). We remember
     * that the minimaxValues method returns the minimax values of each
     * successor. The minimax values obtained by each successor/move in all
     * simulated hands are summed and, at the end, the method returns the move
     * corresponding to the successor that obtained a larger number of minimax
     * values. Notice that, for each current fictitious state, the agent pieces
     * are always the same, which means that the moves that may be played are
     * also the same. 2) If the agent already knows exactly what pieces its
     * opponent has: In this case, an AgentSearchState is built (where the
     * opponent pieces are not guessed), the takeDecision method of alpha-beta
     * is called and the move that resulted from the search process is returned.
     *
     * @param currentState
     * @return
     */
    @Override
    public Move takeDecision(AgentCurrentState currentState, Round round) {
        int numOfAgentPossibleMoves = currentState.getAgentCards().size();
        double[] sums = new double[numOfAgentPossibleMoves];
        double[] values = new double[numOfAgentPossibleMoves];
        LinkedList<AgentSearchState> sucessors = null;

        /*if (currentState.getNumberOfOpponentPossiblePieces() == currentState.getNumberOfOpponentPieces()) {
            //case 2
            AgentSearchState state = currentState.getAgentSearchState(currentState.getOpponentPossiblePieces());
            return alphaBeta.takeDecision(state);
        } else {*/
        //case 1
        LinkedList<AgentSearchState> states = currentState.buildGuessedCurrentStates(handsLimit);
        for (AgentSearchState state : states) {
            sucessors = state.getSucessors();
            values = alphaBeta.minimaxValues(state, sucessors);
            for (int i = 0; i < values.length; i++) {
                sums[i] += values[i];
            }
        }
        int maxIndex = getMaxIndex(sums, values.length);
        return sucessors.get(maxIndex).getMove();
    }

    @Override
    public void setSearchDepth(int depthLimit) {
        alphaBeta.setSearchDepth(depthLimit);
    }

    public int getMaxIndex(double[] sums, int possibleMoves) {
        double maxValue = Double.NEGATIVE_INFINITY;
        int maxIndex = 0;
        for (int i = 0; i < possibleMoves; i++) {
            if (maxValue <= sums[i]) {
                maxValue = sums[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
