/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observationHeuristics;

import agent.ObservationHeuristic;
import java.util.LinkedList;
import model.CardPlayed;
import model.CardProb;
import model.Round;

/**
 *
 * @author Rafael
 */
public class GivePointsRoundLost extends ObservationHeuristic{

    @Override
    public LinkedList<CardProb> analyze(LinkedList<CardProb> cards, CardPlayed card, Round round) {
        return null;
    }
    
}
