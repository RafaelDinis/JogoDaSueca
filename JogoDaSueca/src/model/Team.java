/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import agent.Agent;

/**
 *
 * @author Yeezus
 */
public class Team {
    private Player player1;
    private Player player2;
    private Agent agent1;
    private Agent agent2;
    private int finalScore;

    public Team() {
    }
    
    
    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }
    
    public void addScore(int score){
        this.finalScore = finalScore + score;
    }

    public Agent getAgent1() {
        return agent1;
    }

    public void setAgent1(Agent agent1) {
        this.agent1 = agent1;
    }

    public Agent getAgent2() {
        return agent2;
    }

    public void setAgent2(Agent agent2) {
        this.agent2 = agent2;
    }
    

    @Override
    public String toString() {
        return getPlayer1().getName() + ", " + getPlayer2().getName() + ", score:" + getFinalScore();
    }
    
    
   
    
    
}
