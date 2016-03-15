package agent;

import com.sun.org.apache.bcel.internal.generic.L2D;
import common.Move;
import java.util.LinkedList;
import java.util.List;
import model.Player;

/**
 * Represents a fictitious state with the agent pieces and the "guessed"
 * opponent pieces (and other things). It is used by the alpha-beta algorithm
 * during the search process. Sugestions: - Define an attribute that indicates
 * which player plays in this state. - When the successors of a state are
 * generated (getSuccessors), you should determine for each successor what is
 * the player which plays in that state. This information is needed so that
 * alpha-beta algorithm can decide, for each succesor, if it calls maxValue or
 * minValue. - Override method isValidMove because, if not, all moves are
 * considered valid because the list of played pieces is not used in the search
 * process and is always empty (see the isValidMove method in class
 * DominoState). Notice that, during the search process you don't need to use
 * the already list of played pieces (defined in class DominoState), so, it is
 * always empty. You just need to know what are the left and right values in the
 * extremities of the already played pieces (also defined in class DominoState).
 * - You should not use the placePiece method as it is implemented in class
 * AgentCurrentState because it was conceived for the "real" state that the
 * agent knows (for example, it doesn't remove opponent pieces when it plays).
 * You should implement another method (maybe with the same name) that places a
 * piece and makes the necessary modifications, namely, it should remove a piece
 * from the playing player, create the Move describing the... move and determine
 * the next player to play. - You should, obviously, implement methods
 * getSuccessors, utility and evaluate and isEndOfGameState. - Other aspects you
 * may consider necessary.
 */
public class AgentSearchState extends AgentState {

    //Attributes...
    private Move move = null;
    private Player currentPlayer;
    private Player lastPlayer;

    public AgentSearchState() {
    }

    //Maybe you need at least one more constructor...
    /*public AgentSearchState() {
    }
*/
    public Move getMove() {
        return move;
    }

    /*public LinkedList<AgentSearchState> getSucessors() {
        LinkedList<AgentSearchState> sucessors = new LinkedList<>();
        LinkedList<Piece> pieces = (LinkedList<Piece>) (currentPlayer == Player.AGENT ? agentPieces.clone() : opponentPieces.clone());
        for (Piece piece : pieces) {
            if (isValidMove(Side.LEFT, piece)) {
                AgentSearchState sucessor = clone();
                sucessor.placePiece(currentPlayer, Side.LEFT, piece);
                sucessor.decidePlayer(currentPlayer);
                sucessor.move = new Move(Side.LEFT, piece);
                sucessors.add(sucessor);
            }
            if (isValidMove(Side.RIGHT, piece)) {
                AgentSearchState sucessor = clone();
                sucessor.placePiece(currentPlayer, Side.RIGHT, piece);
                sucessor.decidePlayer(currentPlayer);
                sucessor.move = new Move(Side.RIGHT, piece);
                sucessors.add(sucessor);
            }
        }

        return sucessors;
    }

    private void placePiece(Player player, Side side, Piece piece) {
        if (side == Side.LEFT) {
            leftValue = piece.getOppositeValue(leftValue);
        }
        if (side == Side.RIGHT) {
            rightValue = piece.getOppositeValue(rightValue);
        }
        if (player == Player.AGENT) {
            agentPieces.remove(piece);
        } else {
            opponentPieces.remove(piece);
        }
        lastPlayer = currentPlayer;
    }

    public boolean isEndOfGameState() {
        return !canPlay(agentPieces) && !canPlay(opponentPieces);
    }

    private void decidePlayer(Player player) {
        if (currentPlayer == Player.AGENT) {
            if (canPlay(opponentPieces)) {
                currentPlayer = Player.OPPONENT;
                lastPlayer = Player.AGENT;
            } else if (canPlay(agentPieces)) {
                currentPlayer = Player.AGENT;
                lastPlayer = Player.OPPONENT;
            }
        } else {
            if (canPlay(agentPieces)) {
                currentPlayer = Player.AGENT;
                lastPlayer = Player.OPPONENT;
            } else if (canPlay(opponentPieces)) {
                currentPlayer = Player.OPPONENT;
                lastPlayer = Player.AGENT;
            }
        }
    }

    private boolean canPlay(LinkedList<Piece> pieces) {
        for (Piece piece : pieces) {
            if (piece.hasValue(leftValue) || piece.hasValue(rightValue)) {
                return true;
            }
        }
        return false;
    }

    public double evaluate() {
        double points = 0;
        if (isEndOfGameState()) {
            return utility();
        }

        for (Piece piece : opponentPieces) {
            points += piece.getValuesSum();
        }

        for (Piece piece : agentPieces) {
            points -= piece.getValuesSum();
        }

        if (currentPlayer == lastPlayer) {
            if (currentPlayer == Player.AGENT) {
                points += 100;
            } else {
                points -= 100;
            }
        }
      
        //estas condições só foi adicionadas após a realização das experiências
        if(canPlay(opponentPieces)){
            points -= 100;
        }
        if(canPlay(agentPieces)){
            points += 100;
        }
        
        return points;
    }

    private double utility() {
        if (getWinner() == Player.AGENT) {
            return 10000;
        } else if (getWinner() == Player.OPPONENT) {
            return -10000;
        }
        return 0;
    }

    private Player getWinner() {
        double agentScore = 0;
        double opponentScore = 0;
        Player player = Player.NONE;
        for (Piece piece : agentPieces) {
            agentScore += piece.getValuesSum();
        }
        for (Piece piece : opponentPieces) {
            opponentScore += piece.getValuesSum();
        }

        if (agentScore > opponentScore) {
            return Player.OPPONENT;
        } else if (agentScore < opponentScore) {
            return Player.AGENT;
        }
        return player;
    }

    // Maybe you need to override the clone method...    
    @Override
    protected AgentSearchState clone() {
        return new AgentSearchState((LinkedList<Piece>) agentPieces.clone(), (LinkedList<Piece>) opponentPieces.clone(),
                leftValue, rightValue, currentPlayer, lastPlayer);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getLastPlayer() {
        return lastPlayer;
    }

    @Override
    public boolean isValidMove(Side side, Piece piece) {
        return piece.hasValue(side == Side.LEFT ? leftValue : rightValue);
    }
*/

}
