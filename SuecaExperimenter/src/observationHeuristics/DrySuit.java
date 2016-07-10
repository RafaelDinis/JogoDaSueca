/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observationHeuristics;

import agent.ObservationHeuristic;
import java.util.HashMap;
import java.util.LinkedList;
import model.CardPlayed;
import model.CardProb;
import model.Round;

/**
 *
 * @author Rafael
 */
public class DrySuit extends ObservationHeuristic {

    @Override
    public LinkedList<CardProb> analyze(LinkedList<CardProb> cards, CardPlayed card, Round round) {
        double probToSplit = 0.0;
        if (!card.getCard().getSuit().equals(round.getRoundSuit())) {
            for (CardProb c : cards) {
                if (c.getCard().getSuit().equals(round.getRoundSuit())) {
                    HashMap<Integer, Double> probabilities = (HashMap<Integer, Double>) c.getProbabilities().clone();
                    int[] ids = getIds(probabilities);
                    if (checkEqualProbabilities(probabilities, ids)) {
                        for (int i = 0; i < ids.length; i++) {
                            if (ids[i] == card.getPlayer().getId()) {
                                probToSplit = probabilities.get(card.getPlayer().getId());
                                probabilities.remove(ids[i]);
                                //UPDATE DOS OUTROS
                                if (ids.length == 3) {
                                    for (Integer key : probabilities.keySet()) {
                                        probabilities.put(key, 0.5);
                                    }
                                } else if (ids.length == 3) {
                                    for (Integer key : probabilities.keySet()) {
                                        probabilities.put(key, 1.0);
                                    }
                                }
                            }
                        }
                    } else {
                        for (int i = 0; i < ids.length; i++) {
                            if (ids[i] == card.getPlayer().getId()) {
                                if (ids.length == 3) {
                                    probToSplit = probabilities.get(card.getPlayer().getId());
                                    probabilities.remove(ids[i]);
                                    double prob = probToSplit * 0.5;
                                    //UPDATE DOS OUTROS
                                    for (Integer key : probabilities.keySet()) {
                                        probabilities.put(key, probabilities.get(key) + prob);
                                    }
                                }
                                else if (ids.length == 2) {
                                    for (Integer key : probabilities.keySet()) {
                                        probabilities.put(key, 1.0);
                                    }
                                }
                            }
                        }
                    }
                    c.setProbabilities(probabilities);
                }
            }
            return cards;
        }
        return cards;

    }

    private boolean checkEqualProbabilities(HashMap<Integer, Double> probabilities, int[] ids) {
        if (ids.length == 3) {
            if ((probabilities.get(ids[0]).compareTo(probabilities.get(ids[1])) == 0) && (probabilities.get(ids[1]).compareTo(probabilities.get(ids[2])) == 0)) {
                return true;
            }
        } else if (ids.length == 2) {
            if (probabilities.get(ids[0]).compareTo(probabilities.get(ids[1])) == 0) {
                return true;
            }
        }
        return false;
    }

}
