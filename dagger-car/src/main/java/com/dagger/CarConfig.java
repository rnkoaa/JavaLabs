package com.dagger;

public class CarConfig {
    private int year;

    public CarConfig(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "CarConfig{" +
                "year=" + year +
                '}';
    }
}
