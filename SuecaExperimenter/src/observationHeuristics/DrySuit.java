/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observationHeuristics;

import agent.Heuristic;
import java.util.HashMap;
import java.util.LinkedList;
import model.CardPlayed;
import model.CardProb;
import model.Round;

/**
 *
 * @author Rafael
 */
public class DrySuit extends Heuristic {

    @Override
    public LinkedList<CardProb> analyze(LinkedList<CardProb> cards, CardPlayed card, Round round) {
        //LinkedList<CardProb> cardsClone = (LinkedList) cards.clone();
        double probToSplit = 0.0;
        if (!card.getCard().getSuit().equals(round.getRoundSuit())) {
            for (CardProb c : cards) {
                if (c.getCard().getSuit().equals(round.getRoundSuit())) {
                    HashMap<Integer, Double> probabilities = (HashMap<Integer, Double>) c.getProbabilities().clone();
                    int[] ids = getIds(probabilities);
                    
                    //System.out.println("probabilities" + probabilities);
                    //System.out.println("ids " + ids);
                    if (checkEqualProbabilities(probabilities, ids)) {
                        for (int i = 0; i<ids.length; i++) {
                            if(ids[i] == card.getPlayer().getId()){
                                //c.getProbabilities().put(ids[i], 0.0);
                                probToSplit = probabilities.get(card.getPlayer().getId());
                                probabilities.remove(ids[i]);
                                //UPDATE DOS OUTROS
                                if(ids.length ==3){
                                    for (Integer key : probabilities.keySet()) {
                                        probabilities.put(key, 0.5);
                                    }
                                } else{
                                    for (Integer key : probabilities.keySet()) {
                                        probabilities.put(key, 1.0);
                                    }
                                }
                                
                                //System.out.println("CARD " + card.getCard().getSuit() + " ROUND " + round.getRoundSuit());
                                /*System.out.println("ROUND " + round.getRoundSuit());*/
                               // System.out.println("removeu id " + ids[i] + " probs " + probabilities.toString());
                            }/*
                            else{
                                //System.out.println("id para 0.5 " + ids[1]);
                                c.getProbabilities().put(ids[i], 0.5);
                            }*/
                        }
                    } else {
                        //System.out.println("jesus " + probToSplit);
                        double prob = probToSplit*0.5;
                        for (int i = 0; i<ids.length; i++) {
                            if(ids[i] == card.getPlayer().getId()){
                                probabilities.remove(ids[i]);
                                //UPDATE DOS OUTROS
                                for (Integer key : probabilities.keySet()) {
                                    probabilities.put(key, probabilities.get(key)+prob);
                                }
                            } /*else{
                                double prob2 = probabilities.get(i);
                                probabilities.put(i, prob+prob2);
                            }*/
                        }
                    }
                    c.setProbabilities(probabilities);
                }                
            }
            return cards;
        }
        return cards;

    }

    private int[] getIds(HashMap<Integer, Double> probabilities) {
        int[] ids = new int[probabilities.size()];
        int i = 0;
        for (Integer key : probabilities.keySet()) {
            ids[i] = key;
            i++;
        }
        return ids;
    }
    
    private boolean checkEqualProbabilities(HashMap<Integer, Double> probabilities, int[] ids){
        if(ids.length == 3){
            //System.out.println("ids " + ids[0] + " " + ids[1] + " " + ids[2]);
            if((probabilities.get(ids[0]).compareTo(probabilities.get(ids[1])) == 0) && (probabilities.get(ids[1]).compareTo(probabilities.get(ids[2])) == 0)){
                return true;
            }  
        } else if(ids.length == 2){
            //System.out.println("ids " + ids[0] + " " + ids[1]);
            if(probabilities.get(ids[0]).compareTo(probabilities.get(ids[1])) == 0){
                return true;
            }
        }        
        return false;
    }
    

}
