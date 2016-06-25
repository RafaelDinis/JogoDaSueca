package agent;

import common.Move;
import common.SuecaState;
import java.util.LinkedList;
import model.Card;
import model.Round;

public class AgentState extends SuecaState {

    /**
     * List of the agents cards.
     */
    protected LinkedList<Card> agentCards;

    protected LinkedList<Card> opponent1Cards;
    
    protected LinkedList<Card> opponent2Cards;
    
    protected LinkedList<Card> teammateCards;

    public AgentState() {
        super();
    }

    public AgentState(LinkedList<Card> agentCards) {
        this.agentCards = agentCards;
    }

    public LinkedList<Card> getOpponent1Cards() {
        return opponent1Cards;
    }

    public void setOpponent1Cards(LinkedList<Card> opponent1Cards) {
        this.opponent1Cards = opponent1Cards;
    }

    public LinkedList<Card> getOpponent2Cards() {
        return opponent2Cards;
    }

    public void setOpponent2Cards(LinkedList<Card> opponent2Cards) {
        this.opponent2Cards = opponent2Cards;
    }

    public LinkedList<Card> getTeammateCards() {
        return teammateCards;
    }

    public void setTeammateCards(LinkedList<Card> teammateCards) {
        this.teammateCards = teammateCards;
    }

    
    
}
