package com.example.moneymanager.models;

public class Data {
    private String name;
    private double percent;
    private double amount;

    public Data(String name, double percent, double amount) {
        this.name = name;
        this.percent = percent;
        this.amount = amount;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
