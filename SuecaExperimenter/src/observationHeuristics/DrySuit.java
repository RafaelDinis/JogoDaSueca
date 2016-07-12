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
    public LinkedList<CardProb> analyze(LinkedList<CardProb> cards, CardPlayed card, Round round, LinkedList<CardPlayed> playedCards) {
        double probToSplit = 0.0;
        if (!round.getCards().isEmpty()) {
            if (card.getCard().getSuit() != round.getRoundSuit()) {
                for (CardProb c : cards) {
                    if (c.getCard().getSuit() == round.getRoundSuit()) {
                        HashMap<Integer, Double> probabilities = (HashMap<Integer, Double>) c.getProbabilities().clone();
                        int[] ids = getIds(probabilities);
                        if(ids.length==1 || !probabilities.containsKey(card.getPlayer().getId())){
                            return cards;
                        }
                        if (checkEqualProbabilities(probabilities, ids)) {
                            for (int i = 0; i < ids.length; i++) {
                                if (ids[i] == card.getPlayer().getId()) {
                                    probToSplit = probabilities.get(ids[i]);
                                    probabilities.remove(ids[i]);
                                    if (ids.length == 3) {
                                        for (Integer key : probabilities.keySet()) {
                                            if (key != ids[i]) {
                                                probabilities.put(key, 0.5);
                                            }
                                        }
                                    } else {
                                        for (Integer key : probabilities.keySet()) {
                                            if (key != ids[i]) {
                                                probabilities.put(key, 1.0);
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            for (int i = 0; i < ids.length; i++) {
                                if (ids[i] == card.getPlayer().getId()) {
                                    if (ids.length == 3) {
                                        probToSplit = probabilities.get(ids[i]);
                                        probabilities.remove(ids[i]);
                                        double prob = probToSplit * 0.5;
                                        for (Integer key : probabilities.keySet()) {
                                            if (key != ids[i]) {
                                                double probAux = probabilities.get(key);
                                                probabilities.put(key, probAux + prob);
                                            }
                                        }
                                    } else if (ids.length == 2) {
                                        for (Integer key : probabilities.keySet()) {
                                            if (key != ids[i]) {
                                                probabilities.put(key, 1.0);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        c.setProbabilities(probabilities);
                    }
                }
            }
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
