package com.example.finalproject.Model;


public class Cart {

    public String Name;

    public String image;

    public int Amount;

    public double price;

    public int Sugar;

    public int 	Ice;

    public int id;

    public String ToppingExtras;

    public String message;

    public boolean error;

    public Cart() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSugar() {
        return Sugar;
    }

    public void setSugar(int sugar) {
        Sugar = sugar;
    }

    public int getIce() {
        return Ice;
    }

    public void setIce(int ice) {
        Ice = ice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToppingExtras() {
        return ToppingExtras;
    }

    public void setToppingExtras(String toppingExtras) {
        ToppingExtras = toppingExtras;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}