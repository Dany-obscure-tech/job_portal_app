package com.daniyalfarid.jobportal.SpinnerPopulation;

public class SpinnerPopulation {
    private String title;

    public SpinnerPopulation(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

}
