/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import common.Move;
import java.util.LinkedList;
import model.Card;
import model.CardPlayed;
import model.CardProb;
import model.GameState;
import model.Round;

/**
 *
 * @author Rafael
 */
public abstract class DecisionRule {
    
    public abstract Move analyze(LinkedList<Card> agentCards, Round round, GameState g);
    
}
