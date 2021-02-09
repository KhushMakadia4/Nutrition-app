package com.example.nutritiondatabaseapp;

public class Daily {
    private double calories;
    private double fat;
    private double carbs;
    private double protein;
    private double sugar;

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public void setSugar(double sugar) {
        this.sugar = sugar;
    }


    public double getCalories() {
        return calories;
    }

    public double getFat() {
        return fat;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getProtein() {
        return protein;
    }

    public double getSugar() {
        return sugar;
    }
}
