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
    

    @Override
    public String toString() {
        return getPlayer1().getName() + ", " + getPlayer2().getName() + ", score:" + getFinalScore();
    } 
    
    
}
