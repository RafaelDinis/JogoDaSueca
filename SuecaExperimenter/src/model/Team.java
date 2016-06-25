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
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
    
    public boolean belongsToTeam(Player player){
        if(player1.getName().equalsIgnoreCase(player.getName()) || player2.getName().equalsIgnoreCase(player.getName())){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public String toString() {
        return getPlayer1().getName() + ", " + getPlayer2().getName() + ", score:" + getFinalScore();
    } 

    @Override
    protected Team clone() {
        Team t = new Team();
        t.setFinalScore(finalScore);
        t.setPlayer1(player1.clone());
        t.setPlayer2(player2.clone());
        t.setName(name);
        return t;
    }
    
    
    
    
}
