package agent;

import common.Move;
import common.SuecaState;
import java.util.LinkedList;
import model.Card;
import model.Round;


public class AgentState extends SuecaState {
    
     /**
     * List of the agent's pieces.
     */    
    protected LinkedList<Card> agentCards;
    
    public AgentState(){
        super();
    }
    public AgentState(LinkedList<Card> agentCards) {
        this.agentCards = agentCards;
    }  
    
    

}
