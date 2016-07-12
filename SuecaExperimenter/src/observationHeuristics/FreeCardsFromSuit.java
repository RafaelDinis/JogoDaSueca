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
public class FreeCardsFromSuit extends ObservationHeuristic {

    private final double probToRemove = 0.05;

    @Override
    public LinkedList<CardProb> analyze(LinkedList<CardProb> cards, CardPlayed card, Round round, LinkedList<CardPlayed> playedCards) {
        double probAux = 0.0;
        double probToSplit = 0.0;
        if ((!round.getCards().isEmpty()) && (card.getCard().getSuit() != round.getRoundSuit())) {
            for (CardProb c : cards) {
                if (c.getCard().getSuit() == card.getCard().getSuit()) {
                    probAux = 0.0;
                    probToSplit = 0.0;
                    HashMap<Integer, Double> probabilities = (HashMap<Integer, Double>) c.getProbabilities().clone();

                    int[] ids = getIds(probabilities);
                    if (ids.length == 1 || !probabilities.containsKey(card.getPlayer().getId())) {
                        return cards;
                    }
                    for (int i = 0; i < ids.length; i++) {
                        if (ids[i] == card.getPlayer().getId()) {

                            probAux = probabilities.get(ids[i]);
                            probToSplit = probToRemove;
                            probAux = probAux - probToSplit;

                            if (probAux <= 0) {
                                probAux = 0;
                                probToSplit = probabilities.get(ids[i]);
                                probabilities.remove(ids[i]);

                                if (ids.length == 3) {
                                    for (Integer key : probabilities.keySet()) {
                                        if (key != ids[i]) {
                                            probAux = probabilities.get(key);
                                            probAux = probAux + probToSplit;
                                            probabilities.put(key, probAux);
                                        }
                                    }
                                } else {
                                    for (Integer key : probabilities.keySet()) {
                                        if (key != ids[i]) {
                                            probabilities.put(key, 1.0);
                                        }
                                    }
                                }
                            } else {
                                probabilities.put(ids[i], probAux);
                                if (ids.length == 3) {
                                    for (Integer key : probabilities.keySet()) {
                                        if (key != ids[i]) {
                                            probAux = probabilities.get(key);
                                            probAux = probAux + (probToSplit * 0.5);
                                            probabilities.put(key, probAux);
                                        }
                                    }
                                } else {
                                    for (Integer key : probabilities.keySet()) {
                                        if (key != ids[i]) {
                                            probAux = probabilities.get(key);
                                            probAux = probAux + probToSplit;
                                            probabilities.put(key, probAux);
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
        return cards;
    }

}
