package com.example.moneymanager.models;

public class SmartReminder {
    private String time;
    private boolean turn_on;

    public SmartReminder() {

    }

    public SmartReminder(String time, boolean turn_on) {
        this.time = time;
        this.turn_on = turn_on;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isTurn_on() {
        return turn_on;
    }

    public void setTurn_on(boolean turn_on) {
        this.turn_on = turn_on;
    }
}
