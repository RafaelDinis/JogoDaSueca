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
public class GivePointsRoundLost extends ObservationHeuristic {

    private final double probToRemove = 0.1;

    @Override
    public LinkedList<CardProb> analyze(LinkedList<CardProb> cards, CardPlayed card, Round round, LinkedList<CardPlayed> playedCards) {
        double probAux = 0.0;
        double probToSplit = 0.0;
        if (card.getCard().hasValue() && card.getCard().getValue() < 11) {
            if (checkIfHigherCardNotYetPlayed(card, playedCards)) {
                for (CardProb c : cards) {
                    probAux = 0.0;
                    probToSplit = 0.0;
                    HashMap<Integer, Double> probabilities = (HashMap<Integer, Double>) c.getProbabilities().clone();
                    int[] ids = getIds(probabilities);
                    if (ids.length == 1 || !probabilities.containsKey(card.getPlayer().getId())) {
                        //System.out.println("sai");
                        return cards;
                    }
                    for (int i = 0; i < ids.length; i++) {
                        if (ids[i] == card.getPlayer().getId()) {
                            /*System.out.println("GIVE POINTS ROUND LOST");
                            System.out.println("card " + card.getCard().getSuit() +  " " + card.getCard().getCard());
                            System.out.println("round " + round.getRoundSuit() + " " + round.getCards().size());

                            System.out.println("probs iniciais " + probabilities.toString());
                            System.out.println("id " + card.getPlayer().getId());*/

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
                                //System.out.println("probAux " + probAux);
                                //System.out.println("id " + ids[i]);

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
                                            //System.out.println("entrou \n");
                                            probAux = probabilities.get(key);
                                            probAux = probAux + probToSplit;
                                            probabilities.put(key, probAux);
                                        }
                                    }
                                }
                            }

                            //System.out.println("probs finais " + probabilities.toString() + "\n");
                        }
                    }
                }
            }
        }
        return cards;
    }

    public boolean checkIfHigherCardNotYetPlayed(CardPlayed card, LinkedList<CardPlayed> playedCards) {
        int num = 0;
        for (CardPlayed playedCard : playedCards) {
            if ((playedCard.getCard().getSuit() == card.getCard().getSuit()) && (playedCard.getCard().getValue() > card.getCard().getValue())) {
                num++;
            }
        }
        if (card.getCard().getValue() == 2 && num == 4) {
            return false;
        } else if (card.getCard().getValue() == 3 && num == 3) {
            return false;
        } else if (card.getCard().getValue() == 4 && num == 2) {
            return false;
        } else if (card.getCard().getValue() == 10 && num == 1) {
            return false;
        } else {
            return true;
        }
    }

}
