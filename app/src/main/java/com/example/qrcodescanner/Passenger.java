package com.example.qrcodescanner;

public class Passenger {
    private String name;
    private float balance;

    public Passenger() {
    }

    public Passenger(String name, float balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
