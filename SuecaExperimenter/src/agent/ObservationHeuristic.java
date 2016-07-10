/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agent;

import java.util.HashMap;
import java.util.LinkedList;
import model.CardPlayed;
import model.CardProb;
import model.Round;

/**
 *
 * @author Rafael
 */
public abstract class ObservationHeuristic {
    
    public abstract LinkedList<CardProb> analyze(LinkedList<CardProb> cards, CardPlayed card, Round round);
    
    public int[] getIds(HashMap<Integer, Double> probabilities) {
        int[] ids = new int[probabilities.size()];
        int i = 0;
        for (Integer key : probabilities.keySet()) {
            ids[i] = key;
            i++;
        }
        return ids;
    }
}
