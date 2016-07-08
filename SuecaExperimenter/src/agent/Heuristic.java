/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import java.util.LinkedList;
import model.CardPlayed;
import model.CardProb;
import model.Round;

/**
 *
 * @author Rafael
 */
public abstract class Heuristic {
    
    public abstract LinkedList<CardProb> analyze(LinkedList<CardProb> cards, CardPlayed card, Round round);
    
}
