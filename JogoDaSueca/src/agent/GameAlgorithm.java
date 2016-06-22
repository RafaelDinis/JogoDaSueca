package agent;

import common.Move;
import java.util.Random;
import model.Round;


public abstract class GameAlgorithm <S extends AgentState>{

    /**
     * The maximum depth search level from the current state (current state has level 0).
     */     
    protected int depthLimit;
    /**
     * The number of hands to be simulated by the agent each time it must take a decision.
     */    
    protected int handsLimit = 10;
    /**
     * The random number generator.
     */    
    protected Random random; 
    /**
     * The number of rounds to simulate.
     */ 
    protected int numRounds = 1;
    
    
    public abstract Move takeDecision(S currentState, Round round);
    
    public void setSearchDepth(int depthLimit){
        this.depthLimit = depthLimit;
    }
    
    public void setHandsLimit(int handsLimit){
        this.handsLimit = handsLimit;
    }
    
    public void setRandom(Random random) {
        this.random = random;
    }  
}
