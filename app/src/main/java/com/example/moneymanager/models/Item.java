package com.example.moneymanager.models;

public class Item {
    private String type;
    private String name;
    private boolean isExpense;

    public Item(String type, String name, boolean isExpense){
        this.type = type;
        this.name = name;
        this.isExpense = isExpense;
    }

    public Item(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public Item(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isExpense() {
        return isExpense;
    }

    public void setExpense(boolean expense) {
        isExpense = expense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
