package com.example.moneymanager.models;


import java.util.ArrayList;

public class History {
    private String date;
    private ArrayList<HistoryChild>mListChild;

    public History(String date,ArrayList<HistoryChild> mListChild) {
        this.date = date;

        this.mListChild = mListChild;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<HistoryChild> getmListChild() {
        return mListChild;
    }

    public void setmListChild(ArrayList<HistoryChild> mListChild) {
        this.mListChild = mListChild;
    }
}
